This assignment concerns graphs and traversals algorithms.
For this assignment, you are to implement a simple representation of a
graph, and the possibility to search the graph.

Detailed Instructions
1. Implement some representation of directed graphs. You are
  advised to use adjacency list representation.
2. Implement the possibility of doing a BFS traversal of your graph,
  starting at a user-specified vertex. The program should print the
  order in which the vertices are discovered.
3. Implement the possibility of doing a DFS traversal of your graph,
  starting at a user-specified vertex. The program should print the
  order in which the vertices are discovered.
4. Implement a path finding algorithm. That is, compute a directed
  path from vertex u to vertex v, or reporting such path does not exist
  Hint: you can specialize the DFS algorithm to find a path
  between two given vertices u and v by calling DFS(u) with u as
  the start vertex. You can use stack to keep track of the path
  between the start vertex and the current vertex.
5. Bonus mark: Write a program to detect cycle in the directed
   graph. You can adopt any graph traversal algorithm of your choice
   to detect cycles. 
