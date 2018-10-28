package adjacencyList;

import java.util.*;
import java.io.FileNotFoundException;
import java.io.File;

public class AdjacencyList {

	// Create an array of all the values within the text file named inputArray
	// Values 0 - 3 in this list are as follows: 0 - numbers of vertices, 1 - number of edges, 
	// 2 - Directed / undirected, 3 - Weighted / Unweighed
	// All values following 3 are values for the graph.
	private static List<Double> inputArray = new ArrayList<>();

    private static void addEdgeWeighted(Graph graph, int src, int dest, double weight) {

        // Assigning the next value of every new edge to null by default
        Edge next = null;

        // Create a new edge using the passed in values and giving it a null next value
        Edge newEdge = new Edge(dest, weight, next);

        // If this edge is not the first then assign the previous edges next to the newly created edge.
        if(!graph.adjListArray[src].isEmpty()) {
            graph.adjListArray[src].getLast().next = newEdge;
        }

        // Add our edge to the list
        graph.adjListArray[src].addFirst(newEdge);

    }

    private static void addEdgeUnweighted(Graph graph, int src, int dest) {

        // Assigning the next value of every new edge to null by default
        Edge next = null;

        // Create a new edge using the passed in values and giving it a null next value and a 0 for weight
        Edge newEdge = new Edge(dest, 0, next);

        // If this edge is not the first then assign the previous edges next to the newly created edge.
        if(!graph.adjListArray[src].isEmpty()) {
            graph.adjListArray[src].getLast().next = newEdge;
        }

        // Add our edge to the list
        graph.adjListArray[src].addFirst(newEdge);

    }

	private static void printGraph(Graph graph) {

	    System.out.println("Print Adjacency List:\n");

	    for(int i=0; i<graph.getNumberOfVertices();i++) {

            System.out.print(i + ":");

            for (AdjacencyList.Edge degree: graph.adjListArray[i]) {

                System.out.print(" -> " + degree.vertex);
                // If the graph is weighted, print out the weight with the vertex
                if(graph.getIsWeighted()) {
                    System.out.print(" (" + degree.weight + ")");
                }

            }

            System.out.println("\n");
        }


	}
	
	// Printing a BFS style representation of our graph.
	private static void BFS(Graph graph) {
		
		System.out.println("Print BFS:\n");
		
		for(int i=0; i<graph.getNumberOfVertices();i++) {
			
			System.out.println("Process " + i + " early\n");
			
			for(AdjacencyList.Edge degree: graph.adjListArray[i]) {
				System.out.println("Process edge " + i + " " + degree.vertex + "\n");
			}
			
			System.out.println("Process " + i + " late\n");
		}

	}

    // Printing a DFS style representation of our graph.
	private static void DFS(Graph graph) {

        boolean visited[] = new boolean[graph.numberOfVertices];
        int parent[] = new int[graph.numberOfVertices];
        //boolean processed[] = new boolean[graph.numberOfVertices];

        System.out.println("Print DFS:\n");
        for(int i=0; i < graph.numberOfVertices; ++i) {
            if(visited[i] == false) {
                DFSrec(graph, i, visited, parent);
            }
        }
    }

    private static void DFSrec(Graph graph, int vertStart, boolean visited[], int parent[]) {

        int y;

        visited[vertStart] = true;
        //process_vertex_early(vertStart);
        System.out.println("Process " + vertStart + " early\n");

        for(AdjacencyList.Edge degree: graph.adjListArray[vertStart]) {

            y = degree.vertex;
            if(!visited[y]) {
                parent[y] = vertStart;
                //process_edge(vertStart,y);
                System.out.println("Process edge " + vertStart + " " + y + "\n");
                DFSrec(graph, y, visited, parent);

            } else if((!visited[y]) || (graph.getIsDirected())) {
                //process_edge(vertStart,y);
                System.out.println("Process edge " + vertStart + " " + y + "\n");
            }

        }

        System.out.println("Process " + vertStart + " late\n");
        //process_vertex_late(vertStart);

    }

	private static class Graph {

        private int numberOfVertices;
        private int numberOfEdges;
		private boolean isDirected;
		private boolean isWeighted;

        public LinkedList<AdjacencyList.Edge> adjListArray[];

        public Graph(int v, int e) {
            this.numberOfVertices = v;
            this.numberOfEdges = e;
            // Set directed and weighted to false automatically
            this.isDirected = false;
            this.isWeighted = false;
            // Creating linked list of the linked lists (Nodes)
            this.adjListArray = new LinkedList[this.numberOfVertices];

            initializeGraph();
        }

        private void initializeGraph() {

        	for(int i=0;i<this.numberOfVertices; i++) {
                // Create a new list for each vertex
        		this.adjListArray[i] = new LinkedList<>();
			}

		}

		public int getNumberOfVertices() {
			return this.numberOfVertices;
		}

        public void setDirected(double directed) {
            if(directed == 1) {
                isDirected = true;
            } else {
                isDirected = false;
            }
        }

        public void setWeighted(double weighted) {
            if(weighted == 1) {
                isWeighted = true;
            } else {
                isWeighted = false;
            }
        }

        public boolean getIsDirected() {
            return this.isDirected;
        }

		public boolean getIsWeighted() {
            return this.isWeighted;
        }

	}

	private static class Edge {

        private int vertex;
        private double weight;
        private Edge next;

        public Edge(int v, double w, Edge e) {
            this.vertex = v;
            this.weight = w;
            this.next = e;
        }
	}

	public static void main(String[] args) throws FileNotFoundException {

		// Create scanner to read input .txt file with name txtFile
		File file = new File("src/Input.txt");
        //This is the test case file from the Programming 1-2.pdf
        //File file = new File("src/testCase.txt");

		Scanner txtFile = new Scanner(file);

		while(txtFile.hasNextDouble()) {
			inputArray.add(txtFile.nextDouble());
		}

		// Now let's establish the properties of the graph, these will be passed to our build graph method.
		int numVertices = inputArray.get(0).intValue();
		int numEdges = inputArray.get(1).intValue();

		// Make a new graph object with the properties
		Graph graph = new Graph(numVertices, numEdges);
		graph.setDirected(inputArray.get(2));
		graph.setWeighted(inputArray.get(3));

        // Check if graph is weighted or unweighted, then build edges depending on this.
        // Non-weighted graphs will just have edges created with 0 weight.
        if(graph.getIsWeighted() == true) {


            int vertexTracker = 4;
            int edgeTracker = 5;
            int weightTracker = 6;

            // Undirected graphs need to have their vertex's added twice
            if(!graph.getIsDirected()) {
                 for(int i = 0; i < numEdges; i++) {
                    addEdgeWeighted(graph, inputArray.get(vertexTracker).intValue(), inputArray.get(edgeTracker).intValue(), inputArray.get(weightTracker));
                    addEdgeWeighted(graph, inputArray.get(edgeTracker).intValue(), inputArray.get(vertexTracker).intValue(), inputArray.get(weightTracker));
                    vertexTracker = (vertexTracker + 3);
                    edgeTracker = (edgeTracker + 3);
                    weightTracker = (weightTracker + 3);
                 }
            } else {
                for(int i = 0; i < numEdges; i++) {
                    addEdgeWeighted(graph, inputArray.get(vertexTracker).intValue(), inputArray.get(edgeTracker).intValue(), inputArray.get(weightTracker));
                    vertexTracker = (vertexTracker + 3);
                    edgeTracker = (edgeTracker + 3);
                    weightTracker = (weightTracker + 3);
                }
            }


        } else {

            int vertexTracker = 4;
            int edgeTracker = 5;

            if(!graph.getIsDirected()) {
                 for(int i = 0; i < numEdges; i++) {
                     addEdgeUnweighted(graph, inputArray.get(vertexTracker).intValue(), inputArray.get(edgeTracker).intValue());
                     addEdgeUnweighted(graph, inputArray.get(edgeTracker).intValue(), inputArray.get(vertexTracker).intValue());
                     vertexTracker = (vertexTracker + 2);
                     edgeTracker = (edgeTracker + 2);
                 }
            } else {
                for(int i = 0; i < numEdges; i++) {
                    addEdgeUnweighted(graph, inputArray.get(vertexTracker).intValue(), inputArray.get(edgeTracker).intValue());
                    vertexTracker = (vertexTracker + 2);
                    edgeTracker = (edgeTracker + 2);
                }
            }


        }

		printGraph(graph);
		BFS(graph);
		DFS(graph);

	}
}
