# Seam Carving
Seam-carving is a content-aware image resizing technique where the image is reduced in size by one pixel of height (or width) at a time. A _vertical seam_ in an image is a path of pixels connected from the top to the bottom with one pixel in each row; a _horizontal seam_ is a path of pixels connected from the left to the right with one pixel in each column. Below left is the original 505-by-287 pixel image; below right is the result after removing 150 vertical seams, resulting in a 30% narrower image. Unlike standard content-agnostic resizing techniques (such as cropping and scaling), seam carving preserves the most interest features (aspect ratio, set of objects present, etc.) of the image.

Although the [underlying algorithm](https://www.youtube.com/watch?v=6NcIJXTlugc) is simple and elegant, it was not discovered until 2007. Now, it is now a core feature in Adobe Photoshop and other computer graphics applications.
# Energy Calculation
The first step is to calculate the energy of each pixel, which is a measure of the importance of each pixel—the higher the energy, the less likely that the pixel will be included as part of a seam (as we'll see in the next step). In this assignment, we will implement the _dual-gradient energy function_. 
The energy is high (white) for pixels in an image where there is a rapid color gradient (such as the boundary between the sea and sky and the boundary between the surfing man on the left and the ocean behind him). The seam-carving technique avoids removing such high-energy pixels.
# Seam Identification
The next step is to find a vertical seam of minimum total energy. This is similar to the classic shortest path problem in an edge-weighted digraph except for the following:
 * The weights are on the vertices instead of the edges.
 * We want to find the shortest path from any of the W pixels in the top row to any of the W pixels in the bottom row.
 * The digraph is acyclic, where there is a downward edge from pixel (<em>x</em>, <em>y</em>) to pixels (<em>x</em> − 1, <em>y</em> + 1), (<em>x</em>, <em>y</em> + 1), and (<em>x</em> + 1, <em>y</em> + 1), assuming that the coordinates are in the prescribed range.
 
Computing a seam consists of finding a path of minimum energy cost from one end of the image to another. This can be done via <b>Dijkstra's algorithm</b>, <b>dynamic programming</b>, <b>greedy algorithm</b> or <b>graph cuts</b> among others.
 
The final step is to remove from the image all of the pixels along the seam.

# Conclusion
Project involves my solution to the Seam Carving assignment. This problem is one of the assignments given
in the online algorithm course of Princeton University. My work consists of SeamCarver.java. Other java files PrintEnergy.java, PrintSeams.java, SCUtility.java, ShowEnergy.java, ShowSeams.java and ResizeDemo.java are given in the specification of the assignment to test our code. ResizeDemo.java can be used with any image from seam.zip or other sources to observe the effect of the algorithm.


# References
* https://coursera.cs.princeton.edu/algs4/assignments/seam/specification.php
* https://coursera.cs.princeton.edu/algs4/assignments/seam/faq.php
* https://en.wikipedia.org/wiki/Seam_carving
