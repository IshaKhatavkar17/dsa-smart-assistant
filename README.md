# DSA Smart Assistant

**Your personal AI-powered VS Code extension for mastering Data Structures & Algorithms.**  
No more switching between tutorials and notes – get **blur-code templates** and **smart DSA suggestions** right inside your editor.



[![VS Code Marketplace](https://img.shields.io/badge/VS%20Code-Marketplace-blue)](https://marketplace.visualstudio.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)
[![GitHub Stars](https://img.shields.io/github/stars/IshaKhatavkar17/dsa-smart-assistant)](https://github.com/IshaKhatavkar17/dsa-smart-assistant)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](https://github.com/IshaKhatavkar17/dsa-smart-assistant/pulls)


## ✨ What Makes This Special?

I built this extension because I used to forget algorithm patterns while practicing 🙃.  
This tool makes learning less painful by **showing algorithm templates as blur overlays** right when you’re coding.

Think of it as a **DSA buddy inside your VS Code** 💡.

## 🎯 Features

### 🧠 Smart Coding Help
- Detects common DSA patterns **as you type**
- Provides instant blur-code overlays with solutions
- One-click insertion of full code templates

### ⚡ Productivity Boost
- 8+ built-in algorithm templates:
  - Two Sum, Binary Search, BFS, DFS, Sliding Window, Quick Sort, DP, and more
- Multi-language support: Java, Python, JavaScript, TypeScript, C++, C
- Customizable detection sensitivity & overlay delay

### 🎨 Developer Experience
- Clean blur overlays with indentation & comments
- Status bar indicators (green when patterns are detected)
- Manual commands via shortcuts or right-click menu


## 🚀 How It Works

### 1. **Code & Detect** 
Start typing your algorithm - the extension detects patterns automatically:
```java
// As you type this...
int[] nums = {2, 7, 11, 15};
int target = 9;
Map<Integer, Integer> map = new HashMap<>();
```

### 2. **Get Smart Suggestions**
Extension shows: *"DSA Pattern Detected! Are you implementing Two Sum?"*

### 3. **See Blur Template**
Blur overlay appears showing the complete algorithm structure:
```java
// Your code
int[] nums = {2, 7, 11, 15};
int target = 9;

// Blur overlay appears:
// Map<Integer, Integer> map = new HashMap<>();     [BLUR]
// for (int i = 0; i < nums.length; i++) {         [BLUR]
//     int complement = target - nums[i];           [BLUR]
//     if (map.containsKey(complement)) {           [BLUR]
//         return new int[]{map.get(complement), i}; [BLUR]
//     }                                            [BLUR]
//     map.put(nums[i], i);                         [BLUR]
// }                                                [BLUR]
```

### 4. **Click to Insert**
Click anywhere in the blur code → Complete template gets inserted with proper formatting!

## 📦 Installation

### Method 1: VS Code Marketplace
1. Open VS Code
2. Go to Extensions (`Ctrl+Shift+X`)
3. Search for "DSA Smart Assistant"
4. Click "Install"

### Method 2: Command Line
```bash
code --install-extension your-publisher-name.dsa-smart-assistant
```

### Method 3: Local Installation (.vsix)
```bash
# Download the .vsix file
code --install-extension dsa-smart-assistant-1.0.0.vsix
```

## 🎮 Usage

### Automatic Detection
Just start coding! The extension automatically:
- Detects DSA patterns as you type
- Shows pattern count in status bar
- Displays blur overlays for suggested algorithms

### Manual Commands
- **`Ctrl+Alt+D`** (Windows/Linux) or **`Cmd+Alt+D`** (Mac): Show DSA suggestions
- **`Ctrl+Alt+Shift+D`**: Browse all available templates
- **Right-click** in editor → "Show DSA Algorithm Suggestions"

### Status Bar
- **Green**: Patterns detected - click to see suggestions
- **Default**: No patterns detected in current file

## 🧠 Supported Algorithms

| Algorithm | Pattern Keywords | Use Cases |
|-----------|-----------------|-----------|
| **Two Sum** | `array`, `target`, `sum`, `hashmap` | Find pair with target sum |
| **Binary Search** | `sorted`, `search`, `mid`, `left`, `right` | Search in sorted arrays |
| **BFS** | `queue`, `level`, `breadth`, `treenode` | Tree/graph level traversal |
| **DFS** | `recursive`, `depth`, `stack`, `treenode` | Tree/graph deep traversal |
| **Sliding Window** | `substring`, `window`, `left`, `right` | Subarray/substring problems |
| **Quick Sort** | `sort`, `partition`, `pivot`, `divide` | Efficient sorting |
| **Dynamic Programming** | `dp`, `memoization`, `fibonacci`, `dynamic` | Optimization problems |

## ⚙️ Configuration

Open VS Code Settings (`Ctrl+,`) and search for "DSA Assistant":

```json
{
  "dsaAssistant.enableAutoSuggestions": true,
  "dsaAssistant.showBlurOverlay": true,
  "dsaAssistant.autoShowSuggestions": false,
  "dsaAssistant.suggestionDelay": 500,
  "dsaAssistant.patternSensitivity": "medium",
  "dsaAssistant.enableStatusBar": true
}
```

### Settings Explained
- **`enableAutoSuggestions`**: Enable/disable automatic pattern detection
- **`showBlurOverlay`**: Show/hide blur code templates
- **`autoShowSuggestions`**: Auto-popup suggestions (can be distracting)
- **`suggestionDelay`**: Delay before analysis (100-2000ms)
- **`patternSensitivity`**: Detection sensitivity (low/medium/high)
- **`enableStatusBar`**: Show status in status bar

## 🌍 Language Support

| Language | Extension | Status |
|----------|-----------|--------|
| Java | `.java` | ✅ Full Support |
| Python | `.py` | ✅ Full Support |
| JavaScript | `.js` | ✅ Full Support |
| TypeScript | `.ts` | ✅ Full Support |
| C++ | `.cpp` | ✅ Full Support |
| C | `.c` | ✅ Full Support |

## 🔧 Development Setup

Want to contribute or modify the extension?

```bash
# Clone the repository
git clone https://github.com/YOUR-USERNAME/dsa-smart-assistant.git
cd dsa-smart-assistant

# Install dependencies
npm install

# Compile TypeScript
npm run compile

# Watch for changes
npm run watch

# Test the extension
# Press F5 in VS Code to open Extension Development Host
```

### Build Commands
```bash
# Lint code
npm run lint

# Run tests
npm run test

# Package extension
npm run package
# Creates: dsa-smart-assistant-1.0.0.vsix

# Publish to marketplace
npm run publish
```

## 🤝 Contributing

We welcome contributions! Here's how you can help:

### Add New Algorithm Templates
1. Edit `src/extension.ts`
2. Add new template to `initializeTemplates()` method:
```typescript
this.templates.set('merge-sort', {
    name: 'Merge Sort',
    description: 'Divide and conquer sorting algorithm',
    code: `// Your merge sort implementation here`,
    pattern: ['merge', 'sort', 'divide', 'conquer']
});
```

### Improve Pattern Detection
- Enhance regex patterns in `analyzeCode()` method
- Add more sophisticated scoring algorithms
- Support for more programming languages

### Ideas for New Features
- [ ] Interactive algorithm visualizations
- [ ] Complexity analysis overlay
- [ ] LeetCode problem suggestions
- [ ] Custom template creation
- [ ] Team collaboration features
- [ ] Algorithm performance hints

## 📈 Roadmap

### Version 1.1.0
- [ ] Python-specific templates
- [ ] Custom template creation UI
- [ ] Algorithm complexity annotations
- [ ] Better click detection for blur overlays

### Version 1.2.0
- [ ] Interactive algorithm visualizations
- [ ] LeetCode integration
- [ ] Code performance analysis
- [ ] Multi-cursor template insertion

### Version 2.0.0
- [ ] AI-powered custom suggestions
- [ ] Real-time collaboration
- [ ] Advanced pattern learning
- [ ] Mobile companion app

## 🐛 Bug Reports & Feature Requests

Found a bug or have an idea? We'd love to hear from you!

- **🐛 Bug Reports**: [GitHub Issues](https://github.com/YOUR-USERNAME/dsa-smart-assistant/issues)
- **💡 Feature Requests**: [GitHub Discussions](https://github.com/YOUR-USERNAME/dsa-smart-assistant/discussions)
- **💬 Community**: [Discord Server](https://discord.gg/dsa-assistant)

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Inspired by the amazing VS Code community
- Algorithm templates based on common interview patterns
- Special thanks to all contributors and beta testers

## 🌟 Show Your Support

If this extension helps you learn DSA faster:
- ⭐ **Star** the repository on GitHub
- 📝 **Review** the extension on VS Code Marketplace  
- 🐦 **Share** with your developer friends
- ☕ **Buy me a coffee** (link coming soon!)

---

**Happy Coding! 🚀**

*Made with ❤️ for the developer community*