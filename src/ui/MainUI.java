package ui;

import ai.GenAIClient;
import ai.PromptBuilder;
import engine.AutocompleteEngine;
import model.AIResponse;
import model.TabularPatternResult;
import pattern.DSAPattern;
import pattern.OfflinePatternEngine.PatternResult;
import util.InternetConnectivityChecker;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Optional;

/**
 * SOLID - Single Responsibility Principle
 * Responsible ONLY for UI presentation with tab-based interface.
 * Delegates pattern search, AI generation, and connectivity checks to appropriate services.
 * 
 * Tab Structure:
 * 1. Overview Tab - Pattern name, category, aliases
 * 2. Explanation Tab - When to use + Intuition
 * 3. Java Template Tab - Code template (guaranteed offline)
 * 4. Complexity Tab - Time/Space complexity + Common mistakes
 * 5. AI Insights Tab - Only shown when online (optimizations, variations)
 */
public class MainUI {

    private static boolean isOnline = false;
    private static final AutocompleteEngine engine = new AutocompleteEngine();
    private static JLabel modeLabel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainUI::initializeUI);
    }

    private static void initializeUI() {
        JFrame mainFrame = new JFrame("🎓 DSA Learning Assistant - Offline-First");
        JTextArea searchInput = new JTextArea(6, 60);
        JButton searchBtn = new JButton("🔍 Search Patterns");
        modeLabel = new JLabel("🔒 OFFLINE MODE", SwingConstants.CENTER);
        
        // Check internet availability in background
        checkInternetStatus(modeLabel);

        // Search input panel
        searchInput.setFont(new Font("Consolas", Font.PLAIN, 12));
        searchInput.setLineWrap(true);
        searchInput.setWrapStyleWord(true);
        searchInput.setText("Enter pattern name or description...\nExamples: two_pointers, kadane, dijkstra, kmp");
        
        modeLabel.setFont(new Font("Arial", Font.BOLD, 12));
        modeLabel.setForeground(Color.BLUE);
        
        searchBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        searchBtn.addActionListener(e -> handleSearch(searchInput));

        // Top panel with mode indicator and search
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.add(modeLabel, BorderLayout.NORTH);
        topPanel.add(new JScrollPane(searchInput), BorderLayout.CENTER);
        topPanel.add(searchBtn, BorderLayout.SOUTH);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(topPanel, BorderLayout.NORTH);

        mainFrame.setSize(1000, 750);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private static void checkInternetStatus(JLabel modeLabel) {
        InternetConnectivityChecker.checkInternetAsync(available -> {
            isOnline = available;
            SwingUtilities.invokeLater(() -> {
                if (isOnline) {
                    modeLabel.setText("🌐 AI-AUGMENTED MODE (Online)");
                    modeLabel.setForeground(new Color(34, 139, 34)); // Green
                } else {
                    modeLabel.setText("🔒 OFFLINE MODE (No Internet)");
                    modeLabel.setForeground(Color.BLUE);
                }
            });
        });
    }

    private static void handleSearch(JTextArea searchInput) {
        String userInput = searchInput.getText().trim();

        if (userInput.isEmpty() || userInput.startsWith("Enter")) {
            JOptionPane.showMessageDialog(null, "Please enter a pattern name or description.");
            return;
        }

        // Create output window with tabbed interface
        JFrame outputFrame = new JFrame("📚 Pattern Details");
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Create placeholder tabs
        JTextArea overviewArea = createTextArea();
        JTextArea explanationArea = createTextArea();
        JTextArea templateArea = createTextArea();
        JTextArea complexityArea = createTextArea();
        JTextArea aiArea = createTextArea();
        
        overviewArea.setText("Loading pattern information...");
        
        tabbedPane.addTab("📘 Overview", new JScrollPane(overviewArea));
        tabbedPane.addTab("🧠 Explanation", new JScrollPane(explanationArea));
        tabbedPane.addTab("💻 Java Template", new JScrollPane(templateArea));
        tabbedPane.addTab("⏱ Complexity", new JScrollPane(complexityArea));
        
        outputFrame.add(tabbedPane);
        outputFrame.setSize(1000, 700);
        outputFrame.setLocationRelativeTo(null);
        outputFrame.setVisible(true);

        // Search on background thread
        new Thread(() -> {
            try {
                List<PatternResult> results = engine.searchOfflinePatterns(userInput);
                
                if (!results.isEmpty()) {
                    DSAPattern pattern = results.get(0).pattern;
                    String aiInsights = null;
                    
                    // If online, fetch AI augmentation
                    if (isOnline) {
                        String augmentPrompt = PromptBuilder.buildPatternAugmentationPrompt(userInput, pattern);
                        Optional<String> aiResponse = GenAIClient.generateCode(augmentPrompt);
                        if (aiResponse.isPresent()) {
                            aiInsights = aiResponse.get();
                        }
                    }
                    
                    // Create tabular result
                    TabularPatternResult tabResult = TabularPatternResult.from(pattern, aiInsights, isOnline);
                    
                    SwingUtilities.invokeLater(() -> {
                        overviewArea.setText(tabResult.getOverviewTab());
                        explanationArea.setText(tabResult.getExplanationTab());
                        templateArea.setText(tabResult.getJavaTemplateTab());
                        complexityArea.setText(tabResult.getComplexityTab());
                        
                        // Add AI tab only if online and insights available
                        if (isOnline && tabResult.getAiInsightsTab() != null) {
                            tabbedPane.addTab("🤖 AI Insights", new JScrollPane(aiArea));
                            aiArea.setText(tabResult.getAiInsightsTab());
                        }
                        
                        outputFrame.setTitle("📚 " + pattern.getName() + " - " + 
                            (isOnline ? "🌐 Online" : "🔒 Offline"));
                    });
                } else if (isOnline) {
                    // No offline pattern, try AI
                    String prompt = PromptBuilder.buildPrompt(userInput, engine.getAlgorithmHints(userInput));
                    Optional<String> aiResponse = GenAIClient.generateCode(prompt);
                    
                    if (aiResponse.isPresent()) {
                        SwingUtilities.invokeLater(() -> {
                            overviewArea.setText("Pattern not found in offline repository.");
                            templateArea.setText(aiResponse.get());
                            outputFrame.setTitle("🤖 AI Generated Response");
                        });
                    } else {
                        SwingUtilities.invokeLater(() -> {
                            overviewArea.setText("Pattern not found and AI generation failed.");
                        });
                    }
                } else {
                    SwingUtilities.invokeLater(() -> {
                        overviewArea.setText("Pattern not found in offline repository.\n" +
                            "Go online to use AI-augmented search.");
                    });
                }
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    overviewArea.setText("Error: " + e.getMessage());
                    e.printStackTrace();
                });
            }
        }).start();
    }
    
    private static JTextArea createTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 11));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setMargin(new Insets(10, 10, 10, 10));
        return textArea;
    }}