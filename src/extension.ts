import * as vscode from 'vscode';

// Algorithm template definitions
interface AlgorithmTemplate {
    name: string;
    description: string;
    code: string;
    pattern: string[];
}

// Core extension class
export class DSASmartAssistant {
    private statusBar: vscode.StatusBarItem;
    private decorationType: vscode.TextEditorDecorationType;
    private blurDecorationType: vscode.TextEditorDecorationType;
    private templates: Map<string, AlgorithmTemplate> = new Map();
    private currentBlurDecorations: vscode.DecorationOptions[] = [];
    private currentEditor: vscode.TextEditor | undefined;
    
    constructor() {
        // Create status bar item
        this.statusBar = vscode.window.createStatusBarItem(vscode.StatusBarAlignment.Right, 100);
        this.statusBar.text = "$(lightbulb) DSA Assistant";
        this.statusBar.show();
        
        // Create decoration type for suggestions
        this.decorationType = vscode.window.createTextEditorDecorationType({
            after: {
                color: '#666666',
                fontStyle: 'italic',
                textDecoration: 'none; opacity: 0.6;'
            }
        });

        // Create blur decoration type - clickable blur overlay
        this.blurDecorationType = vscode.window.createTextEditorDecorationType({
            opacity: '0.4',
            cursor: 'pointer',
            backgroundColor: 'rgba(100, 100, 100, 0.1)',
            border: '1px solid rgba(150, 150, 150, 0.3)',
            borderRadius: '3px'
        });
        
        this.initializeTemplates();
    }
    
    // Initialize algorithm templates with multiple language support
    private initializeTemplates() {
        // Two Sum Template
        this.templates.set('two-sum', {
            name: 'Two Sum (HashMap)',
            description: 'Find two numbers that add up to target using HashMap',
            code: `// Two Sum Algorithm - O(n) time, O(n) space
Map<Integer, Integer> map = new HashMap<>();
for (int i = 0; i < nums.length; i++) {
    int complement = target - nums[i];
    if (map.containsKey(complement)) {
        return new int[]{map.get(complement), i};
    }
    map.put(nums[i], i);
}
return new int[]{-1, -1}; // Not found`,
            pattern: ['array', 'target', 'sum', 'two', 'pair', 'complement']
        });
        
        // Binary Search Template
        this.templates.set('binary-search', {
            name: 'Binary Search',
            description: 'Search in sorted array with O(log n) complexity',
            code: `// Binary Search - O(log n) time, O(1) space
int left = 0, right = nums.length - 1;
while (left <= right) {
    int mid = left + (right - left) / 2;
    if (nums[mid] == target) {
        return mid; // Found target
    } else if (nums[mid] < target) {
        left = mid + 1; // Search right half
    } else {
        right = mid - 1; // Search left half
    }
}
return -1; // Target not found`,
            pattern: ['sorted', 'search', 'mid', 'left', 'right', 'binary', 'log']
        });
        
        // BFS Template
        this.templates.set('bfs', {
            name: 'Breadth-First Search (BFS)',
            description: 'Level-by-level traversal using Queue',
            code: `// BFS Traversal - O(n) time, O(w) space where w is max width
Queue<TreeNode> queue = new LinkedList<>();
List<Integer> result = new ArrayList<>();
if (root != null) queue.offer(root);

while (!queue.isEmpty()) {
    TreeNode current = queue.poll();
    result.add(current.val);
    
    // Add children to queue
    if (current.left != null) queue.offer(current.left);
    if (current.right != null) queue.offer(current.right);
}
return result;`,
            pattern: ['queue', 'level', 'treenode', 'breadth', 'bfs', 'linkedlist']
        });
        
        // DFS Template
        this.templates.set('dfs', {
            name: 'Depth-First Search (DFS)',
            description: 'Recursive tree/graph traversal',
            code: `// DFS Traversal - O(n) time, O(h) space where h is height
public List<Integer> dfs(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    dfsHelper(root, result);
    return result;
}

private void dfsHelper(TreeNode root, List<Integer> result) {
    if (root == null) return;
    
    result.add(root.val); // Preorder: process current
    dfsHelper(root.left, result); // Traverse left
    dfsHelper(root.right, result); // Traverse right
}`,
            pattern: ['recursive', 'treenode', 'depth', 'traversal', 'dfs', 'preorder']
        });
        
        // Sliding Window Template
        this.templates.set('sliding-window', {
            name: 'Sliding Window',
            description: 'Efficient subarray/substring problems',
            code: `// Sliding Window - O(n) time, O(1) space
int left = 0, maxLength = 0;
Map<Character, Integer> charCount = new HashMap<>();

for (int right = 0; right < s.length(); right++) {
    char rightChar = s.charAt(right);
    charCount.put(rightChar, charCount.getOrDefault(rightChar, 0) + 1);
    
    // Shrink window if condition violated
    while (charCount.size() > k) { // Example: k unique characters
        char leftChar = s.charAt(left);
        charCount.put(leftChar, charCount.get(leftChar) - 1);
        if (charCount.get(leftChar) == 0) {
            charCount.remove(leftChar);
        }
        left++;
    }
    
    maxLength = Math.max(maxLength, right - left + 1);
}
return maxLength;`,
            pattern: ['substring', 'subarray', 'window', 'left', 'right', 'sliding', 'two pointer']
        });

        // Quick Sort Template
        this.templates.set('quick-sort', {
            name: 'Quick Sort',
            description: 'Divide and conquer sorting algorithm',
            code: `// Quick Sort - Average O(n log n), Worst O(n²)
public void quickSort(int[] arr, int low, int high) {
    if (low < high) {
        int pivotIndex = partition(arr, low, high);
        quickSort(arr, low, pivotIndex - 1); // Sort left
        quickSort(arr, pivotIndex + 1, high); // Sort right
    }
}

private int partition(int[] arr, int low, int high) {
    int pivot = arr[high]; // Choose last element as pivot
    int i = low - 1; // Index of smaller element
    
    for (int j = low; j < high; j++) {
        if (arr[j] <= pivot) {
            i++;
            swap(arr, i, j);
        }
    }
    swap(arr, i + 1, high);
    return i + 1;
}`,
            pattern: ['sort', 'partition', 'pivot', 'divide', 'conquer', 'quick']
        });

        // Dynamic Programming Template
        this.templates.set('dynamic-programming', {
            name: 'Dynamic Programming (Fibonacci)',
            description: 'DP approach with memoization',
            code: `// Dynamic Programming - O(n) time, O(n) space
Map<Integer, Integer> memo = new HashMap<>();

public int fibonacci(int n) {
    if (n <= 1) return n;
    
    // Check if already computed
    if (memo.containsKey(n)) {
        return memo.get(n);
    }
    
    // Compute and store result
    int result = fibonacci(n - 1) + fibonacci(n - 2);
    memo.put(n, result);
    return result;
}

// Bottom-up approach (Tabulation)
public int fibonacciIterative(int n) {
    if (n <= 1) return n;
    
    int[] dp = new int[n + 1];
    dp[0] = 0;
    dp[1] = 1;
    
    for (int i = 2; i <= n; i++) {
        dp[i] = dp[i - 1] + dp[i - 2];
    }
    return dp[n];
}`,
            pattern: ['dp', 'memoization', 'fibonacci', 'dynamic', 'programming', 'tabulation']
        });
    }
    
    // Analyze code and detect patterns with improved algorithm
    public analyzeCode(document: vscode.TextDocument): string[] {
        const text = document.getText().toLowerCase();
        const lines = text.split('\n');
        const detectedPatterns: { name: string, score: number }[] = [];
        
        // Enhanced pattern matching
        for (const [templateName, template] of this.templates.entries()) {
            let score = 0;
            let keywordMatches = 0;
            
            // Check for keyword matches
            for (const keyword of template.pattern) {
                const keywordLower = keyword.toLowerCase();
                const keywordCount = (text.match(new RegExp(keywordLower, 'g')) || []).length;
                if (keywordCount > 0) {
                    keywordMatches++;
                    score += keywordCount * 10; // Base score for keyword presence
                }
            }
            
            // Bonus points for code structure patterns
            if (templateName === 'two-sum' && text.includes('hashmap') && text.includes('complement')) {
                score += 50;
            }
            
            if (templateName === 'binary-search' && text.includes('while') && text.includes('mid')) {
                score += 50;
            }
            
            if (templateName === 'sliding-window' && text.includes('left') && text.includes('right')) {
                score += 40;
            }

            if (templateName === 'bfs' && text.includes('queue') && text.includes('poll')) {
                score += 50;
            }

            if (templateName === 'dfs' && (text.includes('recursive') || text.includes('stack'))) {
                score += 40;
            }
            
            // Consider pattern if enough keywords match and minimum score
            const matchPercentage = keywordMatches / template.pattern.length;
            if (matchPercentage >= 0.3 && score >= 20) {
                detectedPatterns.push({ name: templateName, score });
            }
        }
        
        // Return top 3 patterns sorted by score
        return detectedPatterns
            .sort((a, b) => b.score - a.score)
            .slice(0, 3)
            .map(p => p.name);
    }
    
    // Show algorithm suggestions with enhanced UI
    public async showSuggestions(patterns: string[]) {
        if (patterns.length === 0) return;
        
        const items = patterns.map(pattern => {
            const template = this.templates.get(pattern)!;
            return {
                label: `$(symbol-method) ${template.name}`,
                description: template.description,
                detail: `Click to preview ${template.name} template`,
                templateName: pattern
            };
        });
        
        // Add "Show All Templates" option
        items.push({
            label: "$(list-unordered) Show All DSA Templates",
            description: "Browse all available algorithm templates",
            detail: "View complete list of DSA algorithms",
            templateName: 'show-all'
        });
        
        const selected = await vscode.window.showQuickPick(items, {
            placeHolder: '🚀 DSA Pattern Detected! Choose an algorithm:',
            matchOnDescription: true,
            matchOnDetail: true
        });
        
        if (selected) {
            if (selected.templateName === 'show-all') {
                this.showAllTemplates();
            } else {
                this.showBlurOverlay(selected.templateName);
            }
        }
    }

    // Show all available templates
    private async showAllTemplates() {
        const allItems = Array.from(this.templates.entries()).map(([key, template]) => ({
            label: `$(symbol-method) ${template.name}`,
            description: template.description,
            detail: `Click to preview ${template.name}`,
            templateName: key
        }));

        const selected = await vscode.window.showQuickPick(allItems, {
            placeHolder: '📚 All DSA Templates - Choose one to preview:',
            matchOnDescription: true,
            matchOnDetail: true
        });

        if (selected) {
            this.showBlurOverlay(selected.templateName);
        }
    }
    
    // Show blur overlay with clickable functionality
    public showBlurOverlay(templateName: string) {
        const editor = vscode.window.activeTextEditor;
        if (!editor) return;
        
        const template = this.templates.get(templateName);
        if (!template) return;

        this.currentEditor = editor;
        const position = editor.selection.active;
        const templateLines = template.code.split('\n');
        
        // Clear existing decorations
        editor.setDecorations(this.blurDecorationType, []);
        this.currentBlurDecorations = [];
        
        // Create blur decorations for each line
        templateLines.forEach((line, index) => {
            if (line.trim()) {
                const lineNumber = position.line + index;
                
                // Ensure we don't go beyond document
                if (lineNumber >= editor.document.lineCount) return;
                
                const currentLine = editor.document.lineAt(lineNumber);
                const startPos = new vscode.Position(lineNumber, currentLine.text.length);
                const endPos = new vscode.Position(lineNumber, currentLine.text.length + line.length);
                
                const decoration: vscode.DecorationOptions = {
                    range: new vscode.Range(startPos, endPos),
                    renderOptions: {
                        after: {
                            contentText: `  ${line}`,
                            color: '#888888',
                            fontStyle: 'italic'
                        }
                    }
                };
                
                this.currentBlurDecorations.push(decoration);
            }
        });
        
        // Apply decorations
        editor.setDecorations(this.blurDecorationType, this.currentBlurDecorations);
        
        // Show notification with insertion option
        const insertAction = 'Insert Template';
        const dismissAction = 'Dismiss';
        
        vscode.window.showInformationMessage(
            `💡 ${template.name} template preview ready! Click anywhere in the blur code to insert.`,
            insertAction,
            dismissAction
        ).then(selection => {
            if (selection === insertAction) {
                this.insertTemplate(templateName);
            }
            this.clearBlurOverlay();
        });
        
        // Auto-clear after 15 seconds
        setTimeout(() => {
            this.clearBlurOverlay();
        }, 15000);
    }

    // Clear blur overlay
    private clearBlurOverlay() {
        if (this.currentEditor) {
            this.currentEditor.setDecorations(this.blurDecorationType, []);
            this.currentBlurDecorations = [];
        }
    }
    
    // Insert template at cursor position with formatting
    public async insertTemplate(templateName: string) {
        const editor = vscode.window.activeTextEditor;
        if (!editor) return;
        
        const template = this.templates.get(templateName);
        if (!template) return;
        
        // Clear blur overlay first
        this.clearBlurOverlay();
        
        const position = editor.selection.active;
        const currentLine = editor.document.lineAt(position.line);
        const indentation = currentLine.text.substring(0, currentLine.firstNonWhitespaceCharacterIndex);
        
        // Add proper indentation to each line
        const indentedCode = template.code
            .split('\n')
            .map(line => line.trim() ? indentation + line : line)
            .join('\n');
        
        await editor.edit(editBuilder => {
            editBuilder.insert(position, '\n' + indentedCode + '\n');
        });
        
        // Show success message with template info
        const learnMoreAction = 'Learn More';
        vscode.window.showInformationMessage(
            `✅ ${template.name} template inserted successfully!`,
            learnMoreAction
        ).then(selection => {
            if (selection === learnMoreAction) {
                vscode.env.openExternal(vscode.Uri.parse('https://github.com/DSA-Templates/learn'));
            }
        });
    }
    
    // Update status bar with detected patterns
    public updateStatusBar(patterns: string[]) {
        if (patterns.length > 0) {
            this.statusBar.text = `$(lightbulb) DSA: ${patterns.length} pattern${patterns.length > 1 ? 's' : ''} detected`;
            this.statusBar.tooltip = `Detected: ${patterns.map(p => this.templates.get(p)?.name || p).join(', ')}\nClick to view suggestions`;
            this.statusBar.command = 'dsaAssistant.showSuggestions';
            this.statusBar.backgroundColor = new vscode.ThemeColor('statusBarItem.prominentBackground');
        } else {
            this.statusBar.text = "$(lightbulb) DSA Assistant";
            this.statusBar.tooltip = "DSA Smart Assistant - No patterns detected in current file";
            this.statusBar.backgroundColor = undefined;
        }
    }
    
    dispose() {
        this.statusBar.dispose();
        this.decorationType.dispose();
        this.blurDecorationType.dispose();
        this.clearBlurOverlay();
    }
}

// Global variables
let assistant: DSASmartAssistant;
let analysisTimeout: NodeJS.Timeout;

// Extension activation
export function activate(context: vscode.ExtensionContext) {
    console.log('🚀 DSA Smart Assistant is now active!');
    
    assistant = new DSASmartAssistant();
    
    // Register commands
    const showSuggestionsCommand = vscode.commands.registerCommand('dsaAssistant.showSuggestions', () => {
        const editor = vscode.window.activeTextEditor;
        if (editor) {
            const patterns = assistant.analyzeCode(editor.document);
            assistant.showSuggestions(patterns);
        } else {
            vscode.window.showInformationMessage('Please open a code file to see DSA suggestions');
        }
    });

    const insertTemplateCommand = vscode.commands.registerCommand('dsaAssistant.insertTemplate', (templateName: string) => {
        assistant.insertTemplate(templateName);
    });

    const showAllTemplatesCommand = vscode.commands.registerCommand('dsaAssistant.showAllTemplates', () => {
        // This will be handled by the showSuggestions with 'show-all' option
        assistant.showSuggestions(['show-all']);
    });
    
    // Register text document change listener
    const textChangeListener = vscode.workspace.onDidChangeTextDocument(event => {
        const editor = vscode.window.activeTextEditor;
        if (!editor || event.document !== editor.document) return;
        
        // Only analyze supported languages
        const supportedLanguages = ['java', 'python', 'javascript', 'typescript', 'cpp', 'c'];
        if (!supportedLanguages.includes(event.document.languageId)) {
            assistant.updateStatusBar([]);
            return;
        }
        
        // Debounce analysis to avoid too frequent calls (500ms delay)
        clearTimeout(analysisTimeout);
        analysisTimeout = setTimeout(() => {
            const patterns = assistant.analyzeCode(event.document);
            assistant.updateStatusBar(patterns);
            
            // Auto-show suggestions for strong pattern matches (optional)
            const config = vscode.workspace.getConfiguration('dsaAssistant');
            if (config.get('autoShowSuggestions', false) && patterns.length > 0) {
                assistant.showSuggestions(patterns);
            }
        }, 500);
    });

    // Register active editor change listener
    const activeEditorListener = vscode.window.onDidChangeActiveTextEditor(editor => {
        if (editor) {
            const patterns = assistant.analyzeCode(editor.document);
            assistant.updateStatusBar(patterns);
        } else {
            assistant.updateStatusBar([]);
        }
    });

    // Register click handler for blur overlay
    const clickHandler = vscode.window.onDidChangeTextEditorSelection(event => {
        // This is a simplified click detection - in real implementation,
        // you'd need more sophisticated click detection within blur overlay
    });
    
    // Add to subscriptions for proper cleanup
    context.subscriptions.push(
        assistant,
        textChangeListener,
        activeEditorListener,
        clickHandler,
        showSuggestionsCommand,
        insertTemplateCommand,
        showAllTemplatesCommand
    );
    
    // Show welcome message
    vscode.window.showInformationMessage(
        '🚀 DSA Smart Assistant is ready! Start coding and get algorithm suggestions.',
        'View Templates'
    ).then(selection => {
        if (selection === 'View Templates') {
            vscode.commands.executeCommand('dsaAssistant.showSuggestions');
        }
    });
}

// Extension deactivation
export function deactivate() {
    if (assistant) {
        assistant.dispose();
    }
    clearTimeout(analysisTimeout);
}