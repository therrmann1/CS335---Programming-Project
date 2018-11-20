package adjacencyList;

import java.util.*;
import java.io.FileNotFoundException;
import java.awt.Point;
import java.awt.geom.Point2D;
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
        Edge newEdge = new Edge(dest, src, weight, next);

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
        Edge newEdge = new Edge(dest, src, 0, next);

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
	    
        try {
			AdjacencyList.main(null);
		} catch (FileNotFoundException e) {
		}

	}
	
	private static void BFS(Graph graph) {
		boolean processed[] = new boolean[graph.getNumberOfVertices()];
		boolean discovered[] = new boolean[graph.getNumberOfVertices()];
		int parent[] = new int[graph.getNumberOfVertices()];
		
		for(int i = 0;i< graph.getNumberOfVertices();i++) {
			processed[i] = discovered[i] =  false;
			parent[i] = -1;
		}
		
		BFSrec(graph, discovered, processed, parent);
		
        try {
			AdjacencyList.main(null);
		} catch (FileNotFoundException e) {
		}
	}
	
	// Printing a BFS style representation of our graph.
	private static void BFSrec(Graph graph, boolean discovered[], boolean processed[], int parent[]) {
		LinkedList q = new LinkedList();
		LinkedList tempQ = new LinkedList();
		int[] depth = new int[graph.numberOfVertices];
		int v;
		int y;
		ArrayList<Edge> p = new ArrayList<Edge>();
		boolean found[] = new boolean[graph.adjListArray.length];
		
		// Get the number of edges that are connected to the currently processing vertex
		int currentVertex = 0;
		int e = graph.adjListArray[0].size();
		
		tempQ.add(0);
		q.add(0);
		found[0] = true;
		
		while(q.size() != graph.adjListArray.length) {
			for(int i=0; i<e; i++) {
				if(!q.contains(graph.adjListArray[currentVertex].get(i).vertex)) {
					if(graph.adjListArray[currentVertex].get(i).vertex != 0) {
						q.add(graph.adjListArray[currentVertex].get(i).vertex);
						tempQ.add(graph.adjListArray[currentVertex].get(i).vertex);
						found[graph.adjListArray[currentVertex].get(i).vertex] = true;
					}
				}			
			}
			
			if(!tempQ.isEmpty()) {
				currentVertex = (int) tempQ.remove();
				e = graph.adjListArray[currentVertex].size();
			} else {
				for(int i=0; i != graph.adjListArray.length; i++) {
					if(found[i] == false) {
						tempQ.add(i);
						q.add(i);
						currentVertex = i;
						e = graph.adjListArray[currentVertex].size();
						break;
					}
				}
			}

		}
		
		System.out.println("Print BFS:\n");
		
		while(!q.isEmpty()) {
			
			System.out.println("Process " + q.peek() + " early\n");
			processed[(int)q.peek()] = true;
			v = (int)q.remove();
			
			for(int i=0; i<graph.adjListArray[v].size(); i++) {
				p.add(graph.adjListArray[v].get(i));
			}
			
			
			while(p.size() > 0) {
				int counter = 0;
				y = p.remove(counter).vertex;

				if(discovered[y] == false) {
					if(discovered[v] == false) {
						discovered[v] = true;
					}
					discovered[y] = true;
					depth[y] = depth[v] + 1;
					parent[y] = v;
				}
				
				if(!graph.isDirected) {
					if(depth[y] - depth[v] >= 0) {
						if(parent[y] == v) {
							System.out.println("Process edge " + v + " " + y + " (Tree edge)\n");
						} else if(!processed[y]) {
							System.out.println("Process edge " + v + " " + y + " (Cross edge)\n");
						}
					}
				}
				
				if(graph.isDirected) {
					if(depth[y] - depth[v] >= 0) {
						if(parent[y] == v) {
							System.out.println("Process edge " + v + " " + y + " (Forward edge)\n");
						} else if(!processed[y]) {
							System.out.println("Process edge " + v + " " + y + " (Cross edge)\n");
						} else {
						System.out.println("Process edge " + v + " " + y + " (back edge)\n");
						}
					}
				}

				// Check if graph is directed or undirected and make sure to only have tree and cross edges for 
				// undirected graphs. Do not use parent.
				
				counter++;
			}
			
			
			
			System.out.println("Process " + v + " late\n");
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
        
        try {
			AdjacencyList.main(null);
		} catch (FileNotFoundException e) {
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
    
    private static void MST(Graph graph) {
    	
    	if(graph.isDirected || !graph.isWeighted) {
    		System.out.println("kruskal's algorithm requires an undirected and weighted graph. The data given is either directed or unweighted. ");
            
    		try {
    			AdjacencyList.main(null);
    		} catch (FileNotFoundException e) {
    		}
    	} else {
    		List<Edge> edges = new ArrayList<>();
        	int edgeTracker = 0;
    		Point p[] = new Point[graph.numberOfEdges]; 
    		boolean duplicateEdge = false;
        	
        	for(int i=0; i != graph.adjListArray.length; i++) {
        		for(int j=0; j != graph.adjListArray[i].size(); j++) {
        			
        			for(int k=0; k!=edgeTracker; k++) {
        				if(p[k].x == i && p[k].y == graph.adjListArray[i].get(j).vertex) {
        					duplicateEdge = true;
        					break;
        				}
        			}
        			
        			if(!duplicateEdge) {
        				edges.add(graph.adjListArray[i].get(j));
            			p[edgeTracker] = new Point(graph.adjListArray[i].get(j).vertex, i);
            			edgeTracker++;
        			}
        			duplicateEdge = false;
        		}
        	}
        	        	
        	Collections.sort(edges);
        	
    		List<Edge> mstEdges = new ArrayList<>();
            int mstTracker = 0;
            graph.numberOfEdges = 0;
            int mstWeight = 0;
    		
            for(int i=0;i!=edges.size(); i++) {
            	mstEdges.add(edges.get(i));
            	graph.numberOfEdges++;
            	
            	if(isCycle(graph, mstEdges) == 1) {
            		mstEdges.remove(mstTracker);
            		graph.numberOfEdges--;
            	} else {
            		mstTracker++;
            		mstWeight += edges.get(i).weight;
            		System.out.println("Edge (" + edges.get(i).src+","+edges.get(i).vertex+") weight = " + edges.get(i).weight);
            	}
            }            
             
        	System.out.println("MST Cost = " + mstWeight);
            try {
    			AdjacencyList.main(null);
    		} catch (FileNotFoundException e) {
    		}
    	}
    	
    }
    
    static int find(int parent[], int i) 
    { 
        if (parent[i] == -1) {
        	return i; 
        }  
        return find(parent, parent[i]); 
    } 

    static void Union(int parent[], int x, int y) 
    { 
        int setX = find(parent, x); 
        int setY = find(parent, y); 
        parent[setX] = setY; 
    } 
  
  
    static int isCycle(Graph graph, List<Edge> edges) { 
        int parent[] = new int[graph.numberOfVertices]; 
  
        for (int i=0; i<graph.numberOfVertices; ++i) {
            parent[i]=-1;
        }
   
        for (int i = 0; i < graph.numberOfEdges; ++i) 
        { 
            int x = find(parent, edges.get(i).src); 
            int y = find(parent, edges.get(i).vertex); 
  
            if (x == y) {
                return 1; 
            }
            
            Union(parent, x, y); 
        } 
        return 0; 
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

	private static class Edge implements Comparable<Edge> {

        private int vertex;
        private int src;
        private double weight;
        private Edge next;
        
	    @Override
	    public int compareTo(Edge o) {
	    double passedWeight = o.weight;

	    if (weight == passedWeight)
	        return 0;
	    else if (weight > passedWeight)
	        return 1;
	    else
	        return -1;
	    }

        public Edge(int v, int s, double w, Edge e) {
        	this.src = s;
            this.vertex = v;
            this.weight = w;
            this.next = e;
            
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
        
        Scanner userIn = new Scanner(System.in);
        System.out.println("Choose one of the following options: ");
        System.out.println("1- Print the adjacency list");
        System.out.println("2- BFS");
        System.out.println("3- DFS");
        System.out.println("4- MST");
        System.out.println("5- Exit");
        
        int userSel = userIn.nextInt();
        
        if(userSel == 1) {
    		printGraph(graph);
        } else if(userSel == 2) {
    		BFS(graph);
        } else if(userSel == 3) {
    		DFS(graph);
        } else if(userSel == 4) {
        	MST(graph);
        } else if(userSel == 5) {
        	System.exit(0);
        } else {
        	System.out.println("Invalid input please try again.");
        }
        

		userIn.close();
	}
}
