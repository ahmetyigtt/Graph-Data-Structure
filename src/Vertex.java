import java.util.ArrayList;

public class Vertex {
	private String name;
	private ArrayList<Vertex> neighbors;
	boolean visited;
	private Vertex prev;
	private int cost;

	public Vertex(String name) {
		this.name = name;
		neighbors = new ArrayList<Vertex>();
		visited = false;
		prev = null;
		cost = 0;
	}

	public void addNeighbor(Vertex e) {
		neighbors.add(e);
	}

	public ArrayList<Vertex> getNeighbors() {
		return this.neighbors;
	}

	public String getName() {
		return name; 
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public Vertex getPrev() {
		return prev;
	}

	public void setPrev(Vertex prev) {
		this.prev = prev;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	

}
