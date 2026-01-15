# Setup and Usage Guide

## Prerequisites

- Java 11 or higher
- Windows, Linux, or macOS

Verify Java installation:
```bash
java -version
```

## Compilation

Navigate to project root and compile all sources:

```bash
javac -d out src/ai/*.java src/dsa/*.java src/engine/*.java \
  src/model/*.java src/pattern/*.java src/ui/*.java src/util/*.java
```

Expected output: No errors, no warnings. Check `out/` directory for generated .class files.

### Compile Verification

If successful, you should see:
- `out/ai/GenAIClient.class`
- `out/ui/MainUI.class`
- And other .class files for all source files

If compilation fails, verify:
- All source files exist in src/ directories
- Java version is 11 or higher
- All pattern files exist in resources/

## Running the Application

### Basic Execution

```bash
java -cp out ui.MainUI
```

This launches the GUI window with a search interface.

### With API Key (Optional)

To enable AI augmentation, set the Groq API key:

**Windows (PowerShell):**
```powershell
$env:GROQ_API_KEY="your_api_key_here"
java -cp out ui.MainUI
```

**Windows (Command Prompt):**
```cmd
set GROQ_API_KEY=your_api_key_here
java -cp out ui.MainUI
```

**Linux/Mac:**
```bash
export GROQ_API_KEY="your_api_key_here"
java -cp out ui.MainUI
```

## Using the Application

### Main Window

The application displays:
- Mode indicator at top (Offline or Online)
- Search input area
- Search button

### Searching Patterns

1. Type a pattern name or keyword in the search field
2. Click "Search Patterns" button
3. Results appear in a new window with tabs

### Pattern Tabs

**Overview Tab** - Pattern name, category, aliases

**Explanation Tab** - When to use the pattern and intuition

**Java Template Tab** - Complete, runnable Java code

**Complexity Tab** - Time complexity, space complexity, common mistakes

**AI Insights Tab** - Additional tips and variations (only when online)

### Example Searches

- "kadane" - Maximum subarray algorithm
- "dijkstra" - Single-source shortest path
- "kmp" - String pattern matching
- "sliding window" - Window-based techniques
- "binary search" - Search optimization
- "knapsack" - Dynamic programming
- "graph" - Graph algorithms
- "tree" - Tree algorithms

### Offline Mode

When offline, the application displays "Offline Mode" indicator. All 45 patterns still work fully.

### Online Mode

When internet is detected, the application displays "AI-Augmented Mode". The AI Insights tab appears with additional suggestions.

## Adding New Patterns

To add a new pattern without recompiling:

1. Create a new file in an existing category folder:
   ```
   resources/[category]/[pattern_name].txt
   ```

2. Follow this format:
   ```
   KEY: PATTERN_NAME
   ALIASES: alias1, alias2
   CATEGORY: Category Name
   DIFFICULTY: Medium
   
   WHEN_TO_USE:
   [Describe when this pattern applies]
   
   INTUITION:
   [Explain the core idea]
   
   JAVA_TEMPLATE:
   [Complete code example]
   
   TIME_COMPLEXITY: O(n)
   SPACE_COMPLEXITY: O(1)
   
   COMMON_MISTAKES:
   [List pitfalls]
   ```

3. Restart the application. The pattern loads automatically.

## Troubleshooting

### Application won't start

**Check Java installation:**
```bash
java -version
```

Must be Java 11 or higher.

**Check current directory:**
Ensure you run the command from the project root where `src/` and `resources/` exist.

### No patterns load

Verify `resources/` directory exists with pattern files:
```bash
ls resources/array/
```

Should show files like `two_pointers.txt`, `sliding_window.txt`, etc.

### Search returns no results

- Try alternative names (check ALIASES in pattern files)
- Use broader keywords like "array" or "graph"
- Verify pattern files exist in resources/

### AI mode not working

- Verify internet connection
- Set GROQ_API_KEY environment variable
- Check that Groq API credentials are valid
- Without API key, offline mode still works normally

## Performance

Typical performance metrics:
- Application startup: < 1 second
- Pattern search: < 100ms
- Pattern display: Instant
- AI augmentation: 2-5 seconds (when online)

## System Requirements

- RAM: 50 MB minimum (all patterns loaded in memory)
- Disk: 5 MB for source code and patterns
- Network: Optional (works fully offline)

## Java Classpath

The `-cp out` flag tells Java where to find compiled classes. Alternatively:

```bash
java -cp "out" ui.MainUI
```

If you have pattern files in a different location, ensure the resource path in code matches the actual directory structure.

## Clean Build

To clean and rebuild:

```bash
# Remove compiled files
rm -r out

# Recompile
javac -d out src/ai/*.java src/dsa/*.java src/engine/*.java \
  src/model/*.java src/pattern/*.java src/ui/*.java src/util/*.java

# Run
java -cp out ui.MainUI
```

## Next Steps

After verifying the setup works:
- Explore different pattern categories
- Study the code implementations
- Try modifying patterns
- Add custom patterns
- Integrate into your own projects
