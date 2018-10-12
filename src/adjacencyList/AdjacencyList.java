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
	
	// Creating the "Vertices" aka Linked Lists
	private static void graph(int vertices) {
		
		adjLists = new LinkedList[vertices];
		
		for (int i = 0; i < vertices; i++) {
			adjLists[i] = new LinkedList();
		}	
		
	}
	
	private static void addEdge(int src, int dest, double weight) {
		edge newEdge = new edge(dest, weight);
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
		
		// Create an array of all the values within the text file named inputArray
		// Values 0 - 3 in this list are as follows: 0 - numbers of vertices, 1 - number of edges, 
		// 2 - Directed / undirected, 3 - Weighted / Unweighed
		// All values following 3 are values for the graph.
		List<Double> inputArray = new ArrayList<>();
		
		while(txtFile.hasNextDouble()) {
			inputArray.add(txtFile.nextDouble());
		}
		
		// Now let's establish the properties of the graph, these will be passed to our build graph method.
		boolean isDirected = isDirected(inputArray.get(2));
		boolean isWeighted = isWeighted(inputArray.get(3));		
		int numVertices = inputArray.get(0).intValue();
		int numEdges = inputArray.get(1).intValue();
		
		graph(numVertices);
		
		int vertexTracker = 4;
		int edgeTracker = 5;
		int weightTracker = 6;
		
		
		for(int i = 0; i < numEdges; i++) {
			addEdge(inputArray.get(vertexTracker).intValue(), inputArray.get(edgeTracker).intValue(), inputArray.get(weightTracker));
			
			vertexTracker = (vertexTracker + 3);
			edgeTracker = (edgeTracker + 3);
			weightTracker = (weightTracker + 3);
		}
		
	}

}

class edge {
	
	int vertex;
	double weight;
	
	public edge(int v, double w) {
		vertex = v;
		weight = w;
	}
	
}
