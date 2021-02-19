# WordNet
[WordNet](https://wordnet.princeton.edu/) is a semantic lexicon for the English language that computational linguists and cognitive scientists use extensively. For example, WordNet was a key component in IBM’s Jeopardy-playing Watson computer system. WordNet groups words into sets of synonyms called synsets. For example, { _AND circuit, AND gate_ } is a synset that represent a logical gate that fires only when all of its inputs fire. WordNet also describes semantic relationships between synsets. One such relationship is the is-a relationship, which connects a hyponym (more specific synset) to a hypernym (more general synset). For example, the synset { _gate, logic gate_ } is a hypernym of { _AND circuit, AND gate_ } because an AND gate is a kind of logic gate.
# Shortest Ancestral Path
Shortest ancestral path. An ancestral path between two vertices v and w in a digraph is a directed path from v to a common ancestor x, together with a directed path from w to the same ancestor x. A shortest ancestral path is an ancestral path of minimum total length. We refer to the common ancestor in a shortest ancestral path as a shortest common ancestor. Note also that an ancestral path is a path, but not a directed path.
# Measuring the semantic relatedness of two nouns
 Semantic relatedness refers to the degree to which two concepts are related. Measuring semantic relatedness is a challenging problem.
 We define the semantic relatedness of two WordNet nouns x and y as follows:
 * A = set of synsets in which x appears
 * B = set of synsets in which y appears
 * distance(x, y) = length of shortest ancestral path of subsets A and B
 * sca(x, y) = a shortest common ancestor of subsets A and B
# Outcast detection
Given a list of WordNet nouns x1, x2, ..., xn, which noun is the least related to the others? To identify an outcast, compute the sum of the distances between each noun and every other one:

   _d<sub>i</sub>   =   distance(x<sub>i</sub>, x<sub>1</sub>)   +   distance(x<sub>i</sub>, x<sub>2</sub>)   +   ...   +   distance(x<sub>i</sub>, x<sub>n</sub>)_
     
and return a noun x<sub>t</sub> for which dt is maximum. Note that distance(x<sub>i</sub>, x<sub>i</sub>) = 0, so it will not contribute to the sum.

# Conclusion
Project involves my solution to the WordNet assignment. This problem is one of the assignments given
in the online algorithm course of Princeton University. My work consists of DeluxeBFS.java, WordNet.java, SAP.java and Outcast.java.

# References
* https://coursera.cs.princeton.edu/algs4/assignments/wordnet/specification.php
* https://coursera.cs.princeton.edu/algs4/assignments/wordnet/faq.php
