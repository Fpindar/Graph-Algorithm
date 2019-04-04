import java.io.*;
import java.util.*;

class Graph {
  LinkedList<Integer> nodes;
  LinkedList<LinkedList<Integer>> adjList;

  Graph() {
    this.nodes = new LinkedList();
    this.adjList = new LinkedList();
  }

  private int size() {
    return this.nodes.size();
  }

  private int indexOfNode(int node) {
    return this.nodes.indexOf(node);
  }

  private int nodeAt(int index) {
    return this.nodes.get(index);
  }

  private boolean hasNode(int node) {
    return this.nodes.contains(node);
  }

  private void addNode(int node) {
    this.nodes.add(node);
    this.adjList.add(new LinkedList<Integer>());
  }

  private LinkedList<Integer> getAdjacents(int node) {
    return this.adjList.get(this.indexOfNode(node));
  }

  void addEdge(int u, int v) {
    if(!this.hasNode(u)) {
      this.addNode(u);
    }
    if(!this.hasNode(v)) {
      this.addNode(v);
    }
    LinkedList<Integer> adjacents = this.getAdjacents(u);
    adjacents.add(v); 
  }


  
  void BFSUtil(int node, boolean visited[]) {
    
    LinkedList<Integer> queue = new LinkedList<Integer>();

    
    visited[this.indexOfNode(node)] = true;
    queue.add(node);

    while (queue.size() != 0) {
      node = queue.poll();
      System.out.print(node+" ");

      Iterator<Integer> iter = this.getAdjacents(node).listIterator();
      while (iter.hasNext()) {
        int n = iter.next();
        if (!visited[this.indexOfNode(n)]) {
          visited[this.indexOfNode(n)] = true;
          queue.add(n);
        }
      }
    }
  }

  
  void BFS(int start) {
    boolean visited[] = new boolean[this.size()];
    visited[this.indexOfNode(start)] = true;
    this.BFSUtil(start, visited);
    for (int i = 0; i < this.size(); i++) {
      if (visited[i] == false) {
        this.BFSUtil(this.nodeAt(i), visited); 
      }
    }
  }

  
  void DFSUtil(int node, boolean visited[]) {
    
    visited[this.indexOfNode(node)] = true;
    System.out.print(node+" ");

    
    Iterator<Integer> iter = this.getAdjacents(node).listIterator();
    while (iter.hasNext()) {
      int n = iter.next();
      if (!visited[this.indexOfNode(n)]) {
        this.DFSUtil(n, visited);
      }
    }
  }

  
  void DFS(int start) {
    boolean visited[] = new boolean[this.size()];
    visited[this.indexOfNode(start)] = true;
    this.DFSUtil(start, visited);

    for (int i = 0; i < this.size(); i++) {
      if (visited[i] == false) {
        this.DFSUtil(this.nodeAt(i), visited);
      }
    }
  }


  
  Boolean isReachable(int start, int dest) {
    boolean visited[] = new boolean[this.size()];
    LinkedList<Integer> queue = new LinkedList<Integer>();
    visited[this.indexOfNode(start)] = true;
    System.out.print(start+" - ");
    queue.add(start);

    while (queue.size() != 0) {
      start = queue.poll();
      Iterator<Integer> iter = this.getAdjacents(start).listIterator();
      while (iter.hasNext()) {
        int node = iter.next();
        if (node == dest) {
          System.out.print(node+"\n");
          return true;
        }
        
        System.out.print(node+" - ");
        if (!visited[this.indexOfNode(node)]) {
          visited[this.indexOfNode(node)] = true;
          queue.add(node);
        }
      }
    }
    
    return false;
  }

  boolean detectCycleUtil(int node, boolean[] visited, boolean[] stack) {
    int i = this.indexOfNode(node);
    if(!visited[i]) {
      visited[i] = true;
      stack[i] = true;
      LinkedList<Integer> neighbors = this.getAdjacents(node);
      for(Integer neighbor : neighbors) {
        System.out.println("Parent: "+node+", Child: "+neighbor);
        if(!visited[this.indexOfNode(neighbor)] && this.detectCycleUtil(neighbor, visited, stack)) {
          return true;
        } else if(stack[this.indexOfNode(neighbor)]) {
          return false;
        }
      }
    }
    stack[this.indexOfNode(node)] = false;
    return false;
  }


  
  boolean detectCycle() {
    
    boolean[] visited = new boolean[this.size()];
    boolean[] stack = new boolean[this.size()];


    
    for (int i = 0; i < this.size(); i++) {
      if (this.detectCycleUtil(this.nodeAt(i), visited, stack)) {
        return true;
      }
    }

    return false;
  }

  boolean parseFile(String filePath) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(filePath));
      String line = reader.readLine();
      while(line != null) {
        String vals[] = line.split(" ");
        int u = Integer.parseInt(vals[0]);
        int v = Integer.parseInt(vals[1]);
        this.addEdge(u, v);
        line = reader.readLine();
      }
      reader.close();
      return true;
    } catch(IOException e) {
      return false;
    }
  }

  static String getCommand(String raw) {
    ArrayList<String> availableCommands = new ArrayList<String>();
    availableCommands.add("BFS");
    availableCommands.add("DFS");
    availableCommands.add("PATH");
    availableCommands.add("CYCLE");
    
    String input = raw.toUpperCase();
    if(availableCommands.indexOf(input) < 0) {
      return null;
    } 

    return input;
  }

  static void bfsCLI(Graph graph, int start) {
    System.out.print("BFS: ");
    graph.BFS(start);
    System.out.println("");
  }

  static void dfsCLI(Graph graph, int start) {
    System.out.print("DFS:");
    graph.DFS(start);
    System.out.println("");
  }

  static void pathCLI(Graph graph, int start) {
    System.out.println("Enter destination node : ");
    Scanner scanner = new Scanner(System.in);
    int dest = scanner.nextInt();

    if (graph.isReachable(start, dest)) {
      System.out.println("There is a path from " + start +" to " + dest);
    } else {
      System.out.println("There is no path from " + start +" to " + dest);
    }
    scanner.close();
  }

  static void cycleCLI(Graph graph) {
    if(graph.detectCycle()) {
      System.out.println("Graph contains cycle.");
    } else {
      System.out.println("Graph doesn't contain cycle.");
    }
  }

  static void runCommand(String command, Graph graph, int start) {
    switch(command) {
      case "BFS":
        bfsCLI(graph, start);   
        break;
      case "DFS":
        dfsCLI(graph, start);
        break;
      case "PATH":
        pathCLI(graph, start);
        break;
      case "CYCLE":
        cycleCLI(graph);
        break;
    }
  }

  static void cli(Graph graph, String command, int start) {
    if(command == null) {
      System.out.println("Invalid Command.");
      return;
    }

    if(!graph.hasNode(start)) {
      System.out.println("Starting node is not defined in source file.");
      return;
    }
      
    runCommand(command, graph, start);
  }

  public static void main(String[] args) {
    if(args.length == 3){
      Graph graph = new Graph();
      String filePath = args[0];
      String command = getCommand(args[1]);
      int start = Integer.parseInt(args[2]);
      boolean validFile = graph.parseFile(filePath);

      if(!validFile) {
        System.out.println("File not found or has invalid format.");
        return;
      }
      cli(graph, command, start);
    } else {
      System.out.println("Invalid arguments.");
    }
  }
}