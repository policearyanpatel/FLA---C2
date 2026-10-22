# Automata Programs (Java)

Five simple, exam-friendly command-line programs for basic automata theory tasks:

1. **DFA Simulation** (`DfaSimulation.java`): Simulates a binary DFA on an input string.
2. **NFA to DFA Conversion** (`NfaToDfa.java`): Performs subset construction on an NFA.
3. **DFA Minimization** (`DfaMinimization.java`): Finds equivalent states using the table-filling method.
4. **Intersection of Two DFAs** (`DfaIntersection.java`): Computes the product DFA transitions and final states.
5. **PDA Simulation** (`PdaSimulation.java`): Deterministic stack-based simulation recognizing $a^n b^n$.

## Run

Requires JDK 17 or newer.

```bash
javac -d out src/*.java
java -cp out Main
```

Each program can also be executed standalone:

```bash
java -cp out DfaSimulation
java -cp out NfaToDfa
java -cp out DfaMinimization
java -cp out DfaIntersection
java -cp out PdaSimulation
```
