package adjacencyList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class AdjacencyList {
	
	// Creating linked list of the linked lists (Nodes)
	private static LinkedList<edge> adjLists[];
	
	// Create an array of all the values within the text file named inputArray
	// Values 0 - 3 in this list are as follows: 0 - numbers of vertices, 1 - number of edges, 
	// 2 - Directed / undirected, 3 - Weighted / Unweighed
	// All values following 3 are values for the graph.
	static List<Double> inputArray = new ArrayList<>();
	
	// Creating the "Vertices" aka Linked Lists
	private static void initializeGraph(int vertices) {
		
		adjLists = new LinkedList[vertices];
		
		for (int i = 0; i < vertices; i++) {
			adjLists[i] = new LinkedList();
		}	
		
	}
	
	private static void addEdgeWeighted(int src, int dest, double weight) {
		
		// Assigning the next value of every new edge to null by default
		edge next = null;
		
		// Create a new edge using the passed in values and giving it a null next value
		edge newEdge = new edge(dest, weight, next);
		
		// If this edge is not the first then assign the previous edges next to the newly created edge.
		if(!adjLists[src].isEmpty()) {
			adjLists[src].getLast().next = newEdge;
		}
		
		// Add our edge to the list
		adjLists[src].add(newEdge);
		
		
	}
	
	private static void addEdgeUnweighted(int src, int dest) {
		
		// Assigning the next value of every new edge to null by default
		edge next = null;
		
		// Create a new edge using the passed in values and giving it a null next value and a 0 for weight
		edge newEdge = new edge(dest, 0, next);
		
		// If this edge is not the first then assign the previous edges next to the newly created edge.
		if(!adjLists[src].isEmpty()) {
			adjLists[src].getLast().next = newEdge;
		}
		
		// Add our edge to the list
		adjLists[src].add(newEdge);
		
	}
	
	// Check flag if graph is directed.
	private static boolean isDirected(Double inputValue) {
		if(inputValue == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	// Check flag if graph is weighted.
	private static boolean isWeighted(Double inputValue) {
		if(inputValue == 1) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		
		// Create scanner to read input .txt file with name txtFile
		File file = new File("src/Input.txt");
		Scanner txtFile = new Scanner(file);
		

		
		while(txtFile.hasNextDouble()) {
			inputArray.add(txtFile.nextDouble());
		}
		
		// Now let's establish the properties of the graph, these will be passed to our build graph method.
		boolean isDirected = isDirected(inputArray.get(2));
		boolean isWeighted = isWeighted(inputArray.get(3));		
		int numVertices = inputArray.get(0).intValue();
		int numEdges = inputArray.get(1).intValue();
		
		initializeGraph(numVertices);
		
		// Check if graph is weighted or unweighted, then build edges depending on this. 
		// Non-weighted graphs will just have edges created with 0 weight.
		if(isWeighted == true) {
			int vertexTracker = 4;
			int edgeTracker = 5;
			int weightTracker = 6;
			
			
			for(int i = 0; i < numEdges; i++) {
				addEdgeWeighted(inputArray.get(vertexTracker).intValue(), inputArray.get(edgeTracker).intValue(), inputArray.get(weightTracker));
				vertexTracker = (vertexTracker + 3);
				edgeTracker = (edgeTracker + 3);
				weightTracker = (weightTracker + 3);
			}
		} else {
			int vertexTracker = 4;
			int edgeTracker = 5;
			
			for(int i = 0; i < numEdges; i++) {
				addEdgeUnweighted(inputArray.get(vertexTracker).intValue(), inputArray.get(edgeTracker).intValue());
				vertexTracker = (vertexTracker + 2);
				edgeTracker = (edgeTracker + 2);
			}
			
		}

	}
}


// Edge objects
class edge {
	
	int vertex;
	double weight;
	edge next;
	
	public edge(int v, double w, edge e) {
		vertex = v;
		weight = w;
		next = e;
	}
	
}

// Graph objects
class graph {
	int numberOfVertices;
	int numberOfEdges;
	boolean directed;
	
	public graph(int v, int e, boolean d) {
		numberOfVertices = v;
		numberOfEdges = e;
		directed = d;
	}
}
