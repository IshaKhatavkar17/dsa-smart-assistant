# Project Structure

## Directory Layout

```
src/
├── pattern/                  # Pattern discovery and offline engine
│   ├── PatternSource.java               # Interface for pattern sources
│   ├── DSAPattern.java                  # Data model for patterns
│   ├── FilePatternSource.java           # File-based pattern loader
│   ├── PatternParser.java               # Enhanced format parser
│   ├── TrieIndex.java                   # Trie-based indexing for fast search
│   ├── PatternSearchService.java        # Search strategies orchestration
│   └── OfflinePatternEngine.java        # Main offline pattern engine
│
├── ai/                       # AI integration
│   ├── GenAIClient.java                 # Groq API client
│   └── PromptBuilder.java               # Prompt generation
│
├── engine/                   # Core engine
│   └── AutocompleteEngine.java          # Main engine facade
│
├── ui/                       # User interface
│   └── MainUI.java                      # Swing-based GUI with tabs
│
├── model/                    # Data models
│   ├── AIResponse.java                  # Response wrapper
│   ├── DSAPattern.java                  # Pattern data model
│   └── TabularPatternResult.java        # Tab-based display model
│
├── util/                     # Utilities
│   ├── InternetConnectivityChecker.java # Internet detection
│   └── ResponseParser.java              # Response parsing
│
└── dsa/                      # DSA utilities
    ├── Trie.java                        # Trie data structure
    ├── TrieNode.java                    # Trie node implementation
    └── AlgorithmGraph.java              # Graph representation

resources/                   # Pattern files (45 total)
├── array/                   (6 patterns)
│   ├── two_pointers.txt
│   ├── sliding_window.txt
│   ├── fast_slow_pointers.txt
│   ├── prefix_sum.txt
│   ├── monotonic_stack.txt
│   └── cyclic_sort.txt
│
├── array_string_advanced/   (5 patterns)
│   ├── kadane.txt
│   ├── dutch_national_flag.txt
│   ├── kmp_algorithm.txt
│   ├── z_algorithm.txt
│   └── boyer_moore_voting.txt
│
├── dp/                      (6 patterns)
│   ├── knapsack_01.txt
│   ├── unbounded_knapsack.txt
│   ├── lcs.txt
│   ├── palindromic_subsequence.txt
│   ├── fibonacci.txt
│   └── dp_on_grids.txt
│
├── tree_graph/              (6 patterns)
│   ├── bfs.txt
│   ├── dfs.txt
│   ├── topological_sort.txt
│   ├── island_pattern.txt
│   ├── union_find.txt
│   └── trie.txt
│
├── search_optimization/     (6 patterns)
│   ├── modified_binary_search.txt
│   ├── backtracking.txt
│   ├── top_k_elements.txt
│   ├── two_heaps.txt
│   ├── k_way_merge.txt
│   └── greedy.txt
│
├── math_bitwise/            (3 patterns)
│   ├── bitwise_xor.txt
│   ├── sieve_of_eratosthenes.txt
│   └── divide_and_conquer.txt
│
├── graph_advanced/          (6 patterns)
│   ├── dijkstra.txt
│   ├── bellman_ford.txt
│   ├── floyd_warshall.txt
│   ├── prim_kruskal.txt
│   ├── scc_tarjan_kosaraju.txt
│   └── eulerian_hamiltonian.txt
│
├── geometry_math/           (4 patterns)
│   ├── convex_hull.txt
│   ├── line_sweep.txt
│   ├── reservoir_sampling.txt
│   └── bitmasking_dp.txt
│
└── range_query_ds/          (3 patterns)
    ├── segment_tree.txt
    ├── fenwick_tree.txt
    └── sparse_table.txt

out/                         # Compiled class files (generated)

docs/                        # Local documentation (not for GitHub)
```

## Class Descriptions

### Core Pattern Classes

**PatternSource.java** - Interface defining pattern source contract
- `loadPatterns()` - Load all patterns
- `findByKey(String)` - Find specific pattern
- `searchByKeyword(String)` - Keyword-based search
- `getByCategory(String)` - Category filtering

**FilePatternSource.java** - Loads patterns from text files
- Implements PatternSource interface
- Uses concurrent HashMap for thread-safe access
- Automatically discovers all .txt files in resources/

**TrieIndex.java** - Fast prefix-based search
- O(log n) pattern retrieval
- Supports partial matching
- Pre-warmed on startup

**PatternSearchService.java** - Search orchestration
- Multiple search strategies
- Ranking and result aggregation
- Fuzzy matching support

**OfflinePatternEngine.java** - Main offline engine
- Facade pattern for pattern operations
- Coordinates search service and indexing
- Handles caching and prewarming

### UI Classes

**MainUI.java** - Swing GUI with tabs
- Tab 1: Pattern overview (name, category, aliases)
- Tab 2: Explanation (when to use, intuition)
- Tab 3: Java template (complete code)
- Tab 4: Complexity (time/space analysis, mistakes)
- Tab 5: AI insights (online only)

### AI Classes

**GenAIClient.java** - Groq API integration
- Optional LLaMA model integration
- Offline fallback mechanism
- Error handling and timeouts

**PromptBuilder.java** - Prompt generation
- Builds optimization prompts from patterns
- Structures requests for AI enhancement

### Utility Classes

**InternetConnectivityChecker.java** - Internet detection
- DNS lookup strategy
- HTTP fallback strategy
- Async checking to avoid UI blocking

**AIResponse.java** - Response wrapper
- Handles offline/online/hybrid modes
- Combines pattern and AI responses

## Design Patterns Used

1. **Facade Pattern** - OfflinePatternEngine simplifies access
2. **Strategy Pattern** - Multiple search strategies
3. **Template Method** - FilePatternSource pattern loading
4. **Adapter Pattern** - AIResponse unifies responses
5. **Dependency Injection** - Loose coupling between components

## Data Flow

```
User Query
    |
    v
MainUI (Swing)
    |
    v
AutocompleteEngine (Entry point)
    |
    v
OfflinePatternEngine (Facade)
    |
    +----> PatternSearchService (Strategy)
    |      |
    |      +----> TrieIndex (Fast lookup)
    |      +----> FilePatternSource (Data)
    |
    +----> InternetConnectivityChecker (Optional)
           |
           v
        GenAIClient (Optional AI)
           |
           v
        AIResponse (Result wrapper)
           |
           v
        MainUI (Display)
```

## Pattern File Format

Each pattern file follows this structure:

```
KEY: PATTERN_NAME
ALIASES: alias1, alias2, alias3
CATEGORY: Category Name
DIFFICULTY: Easy/Medium/Hard

WHEN_TO_USE:
[Usage indicators]

INTUITION:
[Explanation with examples]

JAVA_TEMPLATE:
[Complete code]

TIME_COMPLEXITY: O(n)
SPACE_COMPLEXITY: O(1)

COMMON_MISTAKES:
[Pitfalls to avoid]
```

## Adding New Patterns

1. Create file: `resources/[category]/[name].txt`
2. Follow the format above
3. Restart application - loads automatically
4. No recompilation needed

## Build Output

- **out/** - Contains compiled .class files
- Generated at build time
- Can be deleted and regenerated
