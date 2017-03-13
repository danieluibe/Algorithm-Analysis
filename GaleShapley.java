import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class GaleShapley
{
	int n; // number of men (women)

	int MenPrefs[][]; // preference list of men (n*n)
	int WomenPrefs[][]; // preference list of women (n*n)

	ArrayList<MatchedPair> MatchedPairsList; // your output should fill this arraylist which is empty at start

	public class MatchedPair
	{
		int man; // man's number
		int woman; // woman's number

		public MatchedPair(int Man,int Woman)
		{
			man=Man;
			woman=Woman;
		}

		public MatchedPair()
		{
		}
	}

	// reading the input
	void input(String input_name)
	{
		File file = new File(input_name);
		BufferedReader reader = null;

		try
		{
			reader = new BufferedReader(new FileReader(file));

			String text = reader.readLine();

			String [] parts = text.split(" ");
			n=Integer.parseInt(parts[0]);

			MenPrefs=new int[n][n];
			WomenPrefs=new int[n][n];

			for (int i=0;i<n;i++)
			{
				text=reader.readLine();
				String [] mList=text.split(" ");
				for (int j=0;j<n;j++)
				{
					MenPrefs[i][j]=Integer.parseInt(mList[j]);
				}
			}

			for (int i=0;i<n;i++)
			{
				text=reader.readLine();
				String [] wList=text.split(" ");
				for(int j=0;j<n;j++)
				{
					WomenPrefs[i][j]=Integer.parseInt(wList[j]);
				}
			}

			reader.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// writing the output
	void output(String output_name)
	{
		try
		{
			PrintWriter writer = new PrintWriter(output_name, "UTF-8");

			for(int i=0;i<MatchedPairsList.size();i++)
			{
				writer.println(MatchedPairsList.get(i).man+" "+MatchedPairsList.get(i).woman);
			}

			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public GaleShapley(String []Args)
	{
		input(Args[0]);

		MatchedPairsList=new ArrayList<MatchedPair>(); // you should put the final stable matching in this array list

		/* NOTE
		 * if you want to declare that man x and woman y will get matched in the matching, you can
		 * write a code similar to what follows:
		 * MatchedPair pair=new MatchedPair(x,y);
		 * MatchedPairsList.add(pair);
		*/

		//YOUR CODE STARTS HERE
		/** freemen is a LinkedList of single men**/		
		LinkedList freemen = new LinkedList();
		for (int i=0;i<n;i++){
			freemen.addFirst(i);
		}
		/** Array next[i] represents the index of the next woman that man i wants to propose**/
		int[] next = new int[n];
		for (int i=0;i<next.length;i++){
			next[i] = 0;
		}
		/** Array current[i] represents the current boyfriend of woman i**/
		/** if woman i is single, current[i]=1111**/
		int[] current = new int[n];
		for (int i=0;i<current.length;i++){
			current[i] = 1111;	//1111 is the notation for single
		}
		/** double array ranking **/
		int[][] ranking = new int[n][n];
		for (int i=0;i<n;i++){
			for (int j=0;j<n;j++){
				ranking[i][WomenPrefs[i][j]] = j;
			}
		}
		/** start of the Gale-Shapley algorithm **/
		int m,w,m_curr;		// m:man, w:woman, m_curr will be defined below
		while(!freemen.isEmpty()){
			m = (int)freemen.getFirst();
			w = MenPrefs[m][next[m]];
			next[m] += 1;
			if (current[w]==1111){
				current[w] = m;
				freemen.removeFirst();
			}else{
				m_curr = current[w];	// the current boyfriend of women w
				//the smaller the ranking number, the higher the priority
				if (ranking[w][m]<ranking[w][m_curr]){	
					current[w] = m;
					freemen.removeFirst();
					freemen.addFirst(m_curr);
				}
			}
		}
		// store the matched couples into MatchedPairsList
		for (int i=0;i<n;i++){
			m = current[i];
			w = i;
			MatchedPair pair=new MatchedPair(m,w);
			MatchedPairsList.add(pair);
		}
		//YOUR CODE ENDS HERE

		output(Args[1]);
	}

	public static void main(String [] Args) // Strings in Args are the name of the input file followed by the name of the output file
	{
		new GaleShapley(Args);
	}
}
