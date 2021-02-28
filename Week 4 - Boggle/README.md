# Boggle 
<b>The Boggle game</b>. [Boggle](https://en.wikipedia.org/wiki/Boggle) is a word game designed by Allan Turoff and distributed by Hasbro. It involves a board made up of 16 cubic dice, where each die has a letter printed on each of its 6 sides. At the beginning of the game, the 16 dice are shaken and randomly distributed into a 4-by-4 tray, with only the top sides of the dice visible. The players compete to accumulate points by building <em>valid</em> words from the dice, according to these rules:

* A valid word must be composed by following a sequence of <em>adjacent dice</em>—two dice are adjacent if they are horizontal, vertical, or diagonal neighbors.
* A valid word can use each die at most once.
* A valid word must contain at least 3 letters.
* A valid word must be in the dictionary (which typically does not contain proper nouns).

<b>Scoring</b>. Valid words are scored according to their length, using this table:

|    Word length   | Points    |
| :-------------: |:-------------:| 
|    3, 4     | 1       |
|     5    | 2  |
|     6      | 3     |
|     7    | 5     |
|     8+    | 11      | 

<b>The Qu special case</b>. In the English language, the letter <code>Q</code> is almost always followed by the letter <code>U</code>. Consequently, the side of one die is printed with the two-letter sequence <code>Qu</code> instead of <code>Q</code> (and this two-letter sequence must be used together when forming words). When scoring, <code>Qu</code> counts as two letters; for example, the word QuEUE scores as a 5-letter word even though it is formed by following a sequence of only 4 dice.

<b>Our task</b>. Our challenge is to write a Boggle solver that finds all valid words in a given Boggle board, using a given dictionary.

# Conclusion
Project involves my solution to the Boggle assignment. This problem is one of the assignments given in the online algorithm course of Princeton University. My work consists of DeluxeTrie.java and BoggleSolver.java. BoggleBoard.java is already given in the specification of the assignment. Timing is intentionally, more challenging on this assignment. To optimize my solution, I implemented nonrecursive <em>prefix query</em> backtracking operation, advised in the specification. I keep both a 26-way trie and a hashset to store the words in the dictionary so that I can perform prefix query operations more efficiently, and check if a word is present in the dictionary in constant time. I keep a variable for each node which shows if the node in question is a leaf node (in other words, there is no string with that prefix in the dictionary, except possibly the prefix itself). It also makes the algorithm much more efficient since iterating through all children nodes (to see if any) for each node can be a bottleneck operation.


# References
* https://coursera.cs.princeton.edu/algs4/assignments/boggle/specification.php
* https://coursera.cs.princeton.edu/algs4/assignments/boggle/faq.php
* https://en.wikipedia.org/wiki/Boggle
