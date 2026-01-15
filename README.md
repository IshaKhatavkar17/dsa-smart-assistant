# AI-Augmented DSA Code Assistant

An offline-first Java application providing 45 curated data structures and algorithms patterns. Integrates with Groq API to generate optimized solutions and coding hints when internet is available.

## What It Does

- Offline pattern library with 45 DSA algorithms across 9 categories
- Fast O(log n) pattern retrieval using Trie-based indexing
- Complete Java implementations with complexity analysis for each pattern
- Optional AI enhancement via Groq API for solution optimization
- Clean SOLID architecture for maintainability and extensibility

## Key Features

- **Offline-First**: All 45 patterns work without internet connection
- **Fast Search**: Trie-indexed lookup with support for aliases and keyword matching
- **Detailed Patterns**: Each includes intuition, usage scenarios, complexity analysis, and common mistakes
- **AI Integration**: Automatically detects internet and augments patterns with optimization suggestions
- **Tab-Based Interface**: Educational UI organized into tabs for overview, explanation, code, and complexity analysis

## How It Helps

- Quick reference for DSA patterns with complete implementations
- Learning tool with beginner-friendly explanations and practical examples
- Study guide with common patterns and their applications
- Ready-to-use Java templates for coding practice

## Technology Stack

- Java 11+
- Swing (GUI)
- Groq API (optional AI enhancement)
- Trie indexing for fast search

## Quick Start

### Compile
```bash
javac -d out src/ai/*.java src/dsa/*.java src/engine/*.java \
  src/model/*.java src/pattern/*.java src/ui/*.java src/util/*.java
```

### Run
```bash
java -cp out ui.MainUI
```

Search for patterns like "kadane", "dijkstra", or "sliding window" through the GUI interface.

## Documentation

- [Project Structure](PROJECT_STRUCTURE.md) - Detailed directory layout
- [Setup Guide](SETUP_GUIDE.md) - Compilation and usage instructions

## Pattern Categories

- Array patterns (11 total: basic and advanced techniques)
- Dynamic Programming (6 patterns)
- Tree and Graph algorithms (6 patterns)
- Search and optimization (6 patterns)
- Math and bitwise operations (3 patterns)
- Graph advanced techniques (6 patterns)
- Geometry and computational methods (4 patterns)
- Range query data structures (3 patterns)

## Architecture

The implementation follows SOLID principles:
- **Single Responsibility**: Each class handles one specific concern
- **Open/Closed**: New pattern sources can be added via the PatternSource interface
- **Liskov Substitution**: FilePatternSource fully implements PatternSource contract
- **Interface Segregation**: Focused, minimal interfaces without unnecessary methods
- **Dependency Inversion**: All dependencies flow through abstractions

## License

Open source - free to use and extend.
