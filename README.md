# Automata Programs (Java)

Five command-line programs for basic automata theory tasks:

1. DFA simulation
2. NFA-to-DFA conversion (without epsilon transitions)
3. DFA minimization
4. Intersection of two DFAs
5. PDA simulation (deterministic, without epsilon moves)

## Run

Requires JDK 17 or newer.

```bash
javac -d out src/*.java
java -cp out Main
```

All interaction and output are in the command line. DFA alphabet symbols and
input symbols are single characters. Enter states separated by spaces. Use `-`
when there are no final states or no NFA destination states.