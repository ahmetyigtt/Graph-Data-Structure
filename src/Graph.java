import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;
import java.util.Iterator;
import java.util.Map;

public class Graph {
	private HashMap<String, Vertex> vertices;
	private HashMap<String, Edge> edges;
	private HashMap<String,Score> nodes;
	private ArrayList<String> nodeList;
	private ArrayList<String> pathList;
	
	private String HighestNodeBetweennes;
	private String HighestNodeCloseness;
	private int max = -1;
	private int min = 999999999;
	
	
	
	private Stack<String> path;
	

	public Graph() {
		this.vertices = new HashMap<>();
		this.edges = new HashMap<>();
		this.path= new Stack<>();
		this.nodes = new HashMap<>();
		this.pathList =new ArrayList<>();
		this.nodeList=new ArrayList<>();
	}

	public void addEdge(String source, String destination, int weight) {

		if (edges.get(source + "-" + destination) == null && edges.get(destination + "-" + source) == null) {
			Vertex source_v, destination_v;

			if (vertices.get(source) == null) {
				source_v = new Vertex(source);
				vertices.put(source, source_v);
			} else
				source_v = vertices.get(source);

			if (vertices.get(destination) == null) {
				destination_v = new Vertex(destination);
				vertices.put(destination, destination_v);
			} else
				destination_v = vertices.get(destination);

			Edge edge = new Edge(source_v, destination_v, weight);
			source_v.addNeighbor(destination_v);
			destination_v.addNeighbor(source_v);
			edges.put(source + "-" + destination, edge);
		} 
		
	}

	public void print() {

		System.out.println("Source\tDestination\tWeight");
		
		for (Edge e : edges.values()) {
			System.out.println("" + e.getSource().getName() + "\t" + e.getDestination().getName() + "\t\t" + e.getWeight() + " ");
		}
		
		for (Vertex v : vertices.values()) {
			System.out.print("" + v.getName() +"      :");
			
			for(Vertex n:v.getNeighbors()) {
				System.out.print(n.getName()+ " ");
				
			}
			System.out.println();System.out.println();
		}
		 
	}

	public Iterable<Vertex> vertices() {
		return vertices.values();
	}

	public Iterable<Edge> edges() {
		return edges.values();
	}

	public int size() {
		return vertices.size();
	}
	
	public void showPath() {   // just for controlling not about main program

		Iterator<String> paths = pathList.iterator();

		while (paths.hasNext()) {
			
			System.out.print(paths.next() + " ");
		}
		System.out.println();		
		
	}
	
	public void resetVerticesAndPath() {

		Iterator entries = vertices.entrySet().iterator();

		while (entries.hasNext()) {
			
			Map.Entry nextVertex = (Map.Entry) entries.next();
			
			Vertex vertex=((Vertex)nextVertex.getValue());
			vertex.setVisited(false);
			vertex.setCost(0);
			vertex.setPrev(null);
		}
		
		path.clear();
		pathList.clear();

	}
	
	public void copyHashMap() {	   //  Creating Hash-Map of nodes with scores and nodeList

		
		Iterator entries = vertices.entrySet().iterator();

		while (entries.hasNext()) {

			Map.Entry nextVertex = (Map.Entry) entries.next();
			String vertex=((String)nextVertex.getKey());
			nodes.put(vertex, new Score(0,0)); 
			nodeList.add(vertex); 
		}
		

	}			
	
	
	
	
	public int getShortestPath(String begin, String end)
	{
		resetVerticesAndPath();
		boolean done = false;
		Queue<Vertex> vertexQueue = new LinkedList<>();

		Vertex originVertex = vertices.get(begin);
		Vertex endVertex = vertices.get(end);

		originVertex.setVisited(true);

		vertexQueue.add(originVertex);

		while (!done && !vertexQueue.isEmpty())
		{
			
			Vertex frontVertex = vertexQueue.poll();
			Iterator<Vertex> neighbors = frontVertex.getNeighbors().iterator();
			
			while (!done && neighbors.hasNext())
			{
				Vertex nextNeighbor = neighbors.next();
				
				if (!nextNeighbor.isVisited()) {
					
					nextNeighbor.setVisited(true);
					nextNeighbor.setCost(1 + frontVertex.getCost());
					nextNeighbor.setPrev(frontVertex);
					vertexQueue.add(nextNeighbor);
				} // end if
				
				if (nextNeighbor.equals(endVertex))
					done = true;
			} // end while1
			
		} // end while2
		
		int pathLength = (int)endVertex.getCost();
		
		path.add(endVertex.getName());
		pathList.add(endVertex.getName());
		
		Vertex vertex = endVertex;

		while (vertex.getPrev() != null) {
			
			vertex = vertex.getPrev();

			path.add(vertex.getName());
			pathList.add(vertex.getName());
		} // end while
		return pathLength;

	}
	
	public void getHighestBetweennesAndClosenessNode() 
	{
		
		copyHashMap();
		
		while(nodeList.size()!=1) {   
			
			String start = nodeList.remove(0);
			
			for (int i = 0; i < nodeList.size(); i++) {
				
				String end =nodeList.get(i);
				int pathLenght = getShortestPath(start,end);   // all bipartite combinastions of vertex
				
				
				if(pathLenght!=0) {
					
					int prevScore=nodes.get(start).getTotalDistanceScore();
					nodes.get(start).setTotalDistanceScore(prevScore+pathLenght);
					prevScore=nodes.get(end).getTotalDistanceScore();
					nodes.get(end).setTotalDistanceScore(prevScore+pathLenght);     // part about closeness
					
					for (int j = 0; j < pathList.size(); j++) {   
						
						int old = nodes.get(pathList.get(j)).getBetweennesScore();   // part about betweeness
						nodes.get(pathList.get(j)).setBetweennesScore(old+1);
					}
					
				}
				else {   // if graph is not complete

					int prevScore = nodes.get(start).getTotalDistanceScore();
					nodes.get(start).setTotalDistanceScore(prevScore + 100);
					prevScore = nodes.get(end).getTotalDistanceScore();
					nodes.get(end).setTotalDistanceScore(prevScore + 100); // part about closeness

					for (int j = 0; j < pathList.size(); j++) {

						int old = nodes.get(pathList.get(j)).getBetweennesScore();   // part about betweeness
						nodes.get(pathList.get(j)).setBetweennesScore(old + 1);
					}
					
				}
				
			}
			
		}  // nodeList.size()!=1


		Iterator nodes_ = nodes.entrySet().iterator(); 

		while (nodes_.hasNext()) {
			Map.Entry nextNode = (Map.Entry) nodes_.next();

			if(((Score)nextNode.getValue()).getBetweennesScore()>max)
			{
				
				max=((Score)nextNode.getValue()).getBetweennesScore();
				HighestNodeBetweennes=(String)nextNode.getKey();
				
			}
			
			if(((Score)nextNode.getValue()).getTotalDistanceScore()<min)
			{
				min=((Score)nextNode.getValue()).getTotalDistanceScore();
				HighestNodeCloseness=(String)nextNode.getKey();
			}
			
		}
		
	} 
	
	public void showResult()    // just for controlling not about main program
	{

		Iterator nodes_ = nodes.entrySet().iterator(); // printing nodes
		
		System.out.println("nodes:");
		while (nodes_.hasNext()) 
		{
			
			Map.Entry nextNode = (Map.Entry) nodes_.next();
			
			System.out.println((String)nextNode.getKey()+"  betweenes:"+((Score)nextNode.getValue()).getBetweennesScore()+"  closeness:"+((Score)nextNode.getValue()).getTotalDistanceScore());
		}
		
		
	}
	

	public String getHighestNodeBetweennes() {
		return HighestNodeBetweennes;
	}

	public void setHighestNodeBetweennes(String highestNodeBetweennes) {
		HighestNodeBetweennes = highestNodeBetweennes;
	}

	public String getHighestNodeCloseness() {
		return HighestNodeCloseness;
	}

	public void setHighestNodeCloseness(String highestNodeCloseness) {
		HighestNodeCloseness = highestNodeCloseness;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
