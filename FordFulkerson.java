import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class FrameworkFlow
{
	public class Edge
	{
		int headNode;
		int tailNode;
		int capacity = 0;
		int flow = 0;
		Edge originalEdge;
		boolean isForwardEdge;
		
		public Edge( int tN, int hN, int c )
		{
			tailNode = tN;
			headNode = hN;
			capacity = c;
		}
	}
	
	int n;
	int s; // the number of node s 
	int t; // the number of node t
	Edge adjacencyList[][];
	// for every node numbered i adjacencyList[i][.] contains all edges that have node numbered i as its tail
	int maxCapacity;

	void input(String input_name)
	{
		File file = new File(input_name);
		BufferedReader reader = null;

		try
		{
			reader = new BufferedReader(new FileReader(file));

			String text = reader.readLine();
			n=Integer.parseInt( text );
			s=Integer.parseInt( reader.readLine() );
			t=Integer.parseInt( reader.readLine() );
			adjacencyList = new Edge[n][];
			String [] parts;
			for (int i=0;i<n;i++)
			{
				text=reader.readLine();
				parts=text.split(" ");
				adjacencyList[i]=new Edge[parts.length/2];
				for (int j=0;j<parts.length/2;j++)
				{
					adjacencyList[i][j] = new Edge( i, Integer.parseInt(parts[j*2]), Integer.parseInt(parts[j*2+1]) );
					if ( maxCapacity < adjacencyList[i][j].capacity )
						maxCapacity = adjacencyList[i][j].capacity;
				}
			}
			reader.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	//writing the output
	void output(String output_name)
	{
		try
		{
			PrintWriter writer = new PrintWriter(output_name, "UTF-8");
			
			writer.println( n );
			writer.println( s );
			writer.println( t );

			for (int i=0;i<n;i++)
			{
				for (int j=0;j<adjacencyList[i].length;j++)
	                writer.print( adjacencyList[i][j].headNode + " " + adjacencyList[i][j].flow + " " );
				writer.println();
			}

			writer.close();
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
		
	public FrameworkFlow(String []Args)
	{
		input(Args[0]);

		//YOUR CODE GOES HERE
		
//		create residual graph
		int Gf[][]=new int[n][n];
		int u,v;
		for(u=0;u<n;u++){
			for(v=0;v<n;v++){
				Gf[u][v]=0;
			}
			for (int j=0;j<adjacencyList[u].length;j++){
				Gf[u][adjacencyList[u][j].headNode]=adjacencyList[u][j].capacity;
				
			}
		}

		int parent[]=new int[n];

		boolean visited[]=new boolean[n];
		visited[t]=true;
		//iteratively find augmenting path and create residue graph
		while(visited[t]==true){
			//bfs
			for(int i=0;i<n;i++){
				visited[i]=false;
			}
			
			Queue<Integer> queue1=new LinkedList<Integer>();
			queue1.add(s);
			visited[s]=true;
			parent[s]=-1;

			while(queue1.size()!=0){
				int node=queue1.poll();

				for(int i=0;i<n;i++){

					if(Gf[node][i]>0 && visited[i]==false){

						queue1.add(i);
						parent[i]=node;
						visited[i]=true;
//						if(node==3){
//							System.out.print(visited[4]);
//							System.out.print(parent[4]);
//							System.out.print(Gf[3][4]);
//						}
					}
				}
			}
			for(int i=0;i<n;i++){
				System.out.print(parent[i]);
			}
			
		int minflow=Integer.MAX_VALUE;
//		find minflow on a path
		for(int i=t;i!=s;i=parent[i]){
			int j=parent[i];
			minflow=Math.min(minflow, Gf[j][i]);
		}
		System.out.print(minflow);
//		update residual graph
		for(int i=t;i!=s;i=parent[i]){
			int j=parent[i];
			Gf[j][i]-=minflow;
			Gf[i][j]+=minflow;
		}
		System.out.print(Gf[0][1]);

		
		}
		//create output
		for(u=0;u<n;u++){
			for (int j=0;j<adjacencyList[u].length;j++){
				adjacencyList[u][j].flow=adjacencyList[u][j].capacity-Gf[u][adjacencyList[u][j].headNode];
				if(adjacencyList[u][j].flow<0){
					adjacencyList[u][j].flow=0;
					System.out.print(adjacencyList[u][j].flow);
				}
			}
		}

		
		//END OF YOUR CODE

		output(Args[1]);
	}
		

	public static void main(String [] Args) //Strings in Args are the name of the input file followed by the name of the output file
	{
		new FrameworkFlow(Args);
	}
}
