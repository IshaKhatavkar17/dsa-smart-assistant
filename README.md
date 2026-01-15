# DSA Smart Assistant

Your personal AI-powered desktop tool for mastering Data Structures & Algorithms.

Master 45+ DSA patterns with offline-first access and optional AI-powered explanations. No internet? No problem – all patterns work instantly. Online? Get smart AI insights powered by Groq.

![License: MIT](https://img.shields.io/badge/License-MIT-green) ![Java 11+](https://img.shields.io/badge/Java-11%2B-blue) ![Status](https://img.shields.io/badge/Status-Production%20Ready-brightgreen) ![PRs Welcome](https://img.shields.io/badge/PRs-Welcome-brightgreen)

## ✨ What Makes This Special?

I built this tool because reviewing DSA patterns while solving problems is tedious. Switching between notes, tutorials, and the code editor kills productivity.

**DSA Smart Assistant** keeps 45 curated patterns and their implementations right at your fingertips in a beautiful desktop application. 

Think of it as your DSA companion that's always ready to help – whether you're grinding LeetCode or preparing for interviews.

## 🎯 Features

### 🧠 Smart Pattern Recognition
- Detects and suggests relevant DSA patterns
- Instant access to 45+ patterns across 9 categories
- Trie-based search for lightning-fast lookups
- Pattern details with complexity analysis

### ⚡ Offline + AI Power
- **Offline-First**: All patterns available instantly, no internet required
- **AI-Enhanced**: Optional Groq API integration for intelligent explanations
- Seamless fallback to offline mode if API unavailable
- No dependencies on external services

### 🎨 Clean, Intuitive Interface
- Tabbed UI for organized pattern browsing
- Syntax-highlighted code templates
- Pattern cards with intuition, examples, and complexity info
- Quick copy-to-clipboard functionality

### 📚 Comprehensive Pattern Coverage
- **Arrays** (6): Two Pointers, Sliding Window, Prefix Sum, Monotonic Stack, Fast-Slow Pointers, Cyclic Sort
- **Dynamic Programming** (6): Fibonacci, 0/1 Knapsack, LCS, Palindromic Subsequence, Unbounded Knapsack, DP on Grids
- **Tree & Graph** (6): BFS, DFS, Trie, Union Find, Island Patterns, Topological Sort
- **Search & Optimization** (6): Binary Search, Backtracking, Greedy, Two Heaps, Top K Elements, K-Way Merge
- **Graph Advanced** (6): Dijkstra, Bellman-Ford, Floyd-Warshall, Prim/Kruskal, SCC, Eulerian/Hamiltonian
- **String Patterns** (5): Boyer-Moore Voting, KMP, Z-Algorithm, Dutch National Flag, Kadane's Algorithm
- **Math & Bitwise** (3): Sieve of Eratosthenes, Bitwise XOR, Divide & Conquer
- **Range Queries** (3): Segment Tree, Fenwick Tree, Sparse Table
- **Geometry** (4): Convex Hull, Line Sweep, Bitmasking DP, Reservoir Sampling

## 🚀 How It Works

### 1. Launch the Application
```bash
javac -d out src/pattern/*.java src/ai/*.java src/engine/*.java src/model/*.java src/ui/*.java src/util/*.java src/dsa/*.java
java -cp out ui.MainUI
```

### 2. Browse Patterns
- Explore patterns organized by category (Arrays, DP, Graphs, etc.)
- Click any pattern to see detailed information

### 3. View Details
Each pattern includes:
- **Intuition**: Why and when to use this pattern
- **Java Template**: Production-ready code
- **Complexity**: Time and space analysis
- **Common Mistakes**: What to watch out for

### 4. Optional: Enable AI Mode
- Add your Groq API key in settings
- Get AI-powered explanations and insights
- Works online or falls back to offline instantly

## 📦 Installation

### Prerequisites
- Java 11 or higher
- Optional: Groq API key (for AI features)

### Setup
```bash
# Clone the repository
git clone https://github.com/IshaKhatavkar17/dsa-smart-assistant.git
cd dsa-smart-assistant

# Compile
javac -d out src/pattern/*.java src/ai/*.java src/engine/*.java src/model/*.java src/ui/*.java src/util/*.java src/dsa/*.java

# Run
java -cp out ui.MainUI
```

## 🎮 Usage

### Basic Navigation
1. **Browse Categories**: Click tabs to explore different pattern types
2. **View Pattern**: Select a pattern to see its details
3. **Copy Code**: Click on code templates to copy them
4. **Search**: Use the search box to find specific patterns

### AI Features (Optional)
Add your Groq API key in the application settings to unlock:
- AI-generated pattern explanations
- Smart pattern recommendations
- Complex problem walkthrough

### Configuration
Configure the application via the settings panel:
- API Key: Your Groq API key (optional)
- Timeout: How long to wait for AI responses
- Display: Font size and UI customization

## ⚙️ Architecture

**Offline Pattern Engine**
- `TrieIndex`: O(log n) pattern search using Trie data structure
- `FilePatternSource`: Efficient pattern file loading and parsing
- `OfflinePatternEngine`: Blazing-fast pattern matching and retrieval

**AI Integration**
- `GenAIClient`: Groq API communication with graceful fallback
- `PromptBuilder`: Intelligent prompt engineering for pattern context
- `ResponseParser`: Structured response parsing and formatting

**Clean Architecture**
- SOLID principles throughout
- Dependency injection for testability
- Separation of concerns (UI, Engine, Data, AI)
- Production-ready error handling

## 🔧 Development

### Project Structure
See [PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md) for complete architecture details.

### Build Commands
```bash
# Compile all classes
javac -d out src/pattern/*.java src/ai/*.java src/engine/*.java src/model/*.java src/ui/*.java src/util/*.java src/dsa/*.java

# Run application
java -cp out ui.MainUI
```

### Adding New Patterns
1. Create pattern file in `resources/[category]/pattern_name.txt`
2. Follow the pattern format:
   ```
   KEY: pattern_name
   ALIASES: alias1, alias2
   CATEGORY: category_name
   INTUITION: Why use this pattern
   JAVA_TEMPLATE: Full working code
   COMPLEXITY: Time - O(n), Space - O(n)
   COMMON_MISTAKES: What to avoid
   ```
3. Recompile and patterns auto-load

### Extending AI Integration
Edit `src/ai/GenAIClient.java` to:
- Switch AI providers (OpenAI, Claude, etc.)
- Customize prompt templates in `src/ai/PromptBuilder.java`
- Add new response parsing rules in `src/util/ResponseParser.java`

## 🤝 Contributing

Want to add a pattern or improve the tool? We'd love your help!

### How to Contribute
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-pattern`)
3. Add your pattern or improvement
4. Commit with clear messages (`git commit -m "Add pattern: XYZ"`)
5. Push to your fork (`git push origin feature/new-pattern`)
6. Open a Pull Request

### Ideas for Contributions
- Add new DSA patterns or variations
- Improve pattern explanations
- Add pattern visualizations
- Support additional AI providers
- Performance optimizations
- UI/UX improvements

## 📚 Pattern Categories

| Category | Count | Patterns |
|----------|-------|----------|
| Arrays | 6 | Two Pointers, Sliding Window, Prefix Sum, Monotonic Stack, Fast-Slow Pointers, Cyclic Sort |
| Dynamic Programming | 6 | Fibonacci, 0/1 Knapsack, LCS, Palindromic Subsequence, Unbounded Knapsack, DP on Grids |
| Tree & Graph | 6 | BFS, DFS, Trie, Union Find, Island Patterns, Topological Sort |
| Search & Optimization | 6 | Binary Search, Backtracking, Greedy, Two Heaps, Top K Elements, K-Way Merge |
| Graph Advanced | 6 | Dijkstra, Bellman-Ford, Floyd-Warshall, Prim/Kruskal, SCC, Eulerian/Hamiltonian |
| String Patterns | 5 | Boyer-Moore Voting, KMP, Z-Algorithm, Dutch National Flag, Kadane's Algorithm |
| Math & Bitwise | 3 | Sieve of Eratosthenes, Bitwise XOR, Divide & Conquer |
| Range Queries | 3 | Segment Tree, Fenwick Tree, Sparse Table |
| Geometry | 4 | Convex Hull, Line Sweep, Bitmasking DP, Reservoir Sampling |

## 🐛 Bug Reports & Feature Requests

Found an issue or have a great idea?

- **🐛 Bug Reports**: [GitHub Issues](https://github.com/IshaKhatavkar17/dsa-smart-assistant/issues)
- **💡 Feature Requests**: [GitHub Discussions](https://github.com/IshaKhatavkar17/dsa-smart-assistant/discussions)

## 📄 License

MIT License - see [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Built for developers who value productivity
- Pattern collection inspired by common DSA interview problems
- Architecture based on SOLID design principles
- Community contributions and feedback

## 🌟 Show Your Support

If this tool helps with your DSA journey:

- ⭐ Star the repository on GitHub
- 🐦 Share with fellow developers
- 📝 Open an issue with feedback
- 🤝 Contribute improvements

---

**Made with ❤️ for developers who are serious about DSA**
