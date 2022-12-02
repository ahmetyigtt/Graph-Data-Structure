import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) throws IOException {
		
		// karate_club_network.txt
	Graph graph = new Graph();
		
		File file = new File("karate_club_network.txt");                 
		Scanner scanner = new Scanner(file,"UTF-8");
		
		while (scanner.hasNextLine()) {
			
			   String line = scanner.nextLine();
			   String[] splitted = line.split(" ");
			   
			   graph.addEdge(splitted[0].trim(), splitted[1].trim(), 0);
			   
			   
			  
			}
		scanner.close();
		
		graph.getHighestBetweennesAndClosenessNode();
		System.out.println("2019510088 Ahmet Yiðit");
		
		System.out.println("Zachary Karate Club Network - "+"The Highest Node for Betweennes: "+graph.getHighestNodeBetweennes()+" Value: "+graph.getMax());
		System.out.println("Zachary Karate Club Network - "+"The Highest Node for Closeness : "+graph.getHighestNodeCloseness()+" Value: "+(double)1/graph.getMin());
		System.out.println();
		
		// facebook_social_network.txt
		
		Graph graph2 = new Graph();

		File file2 = new File("facebook_social_network.txt");
		Scanner scanner2 = new Scanner(file2, "UTF-8");

		while (scanner2.hasNextLine()) {

			String line = scanner2.nextLine();
			String[] splitted = line.split(" ");

			graph2.addEdge(splitted[0].trim(), splitted[1].trim(), 0);

		}
		scanner2.close();
		
		graph2.getHighestBetweennesAndClosenessNode();
		
		System.out.println("Facebook Social Network - "+"The Highest Node for Betweennes: "+graph2.getHighestNodeBetweennes()+"  Value: "+graph2.getMax());
		System.out.println("Facebook Social Network - "+"The Highest Node for Closeness : "+graph2.getHighestNodeCloseness()+"   Value: "+(double)1/graph2.getMin());
		
		
		
		
		
		
		
	
	   
	 
	   
	   
	    

	}

}
