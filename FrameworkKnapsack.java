import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class FrameworkKnapsack
{
    int n;
    int v[];
    int w[];
    int W;
    double beta;
    boolean picked[];
    void input(String input_name)
    {
        File file = new File(input_name);
        BufferedReader reader = null;

        try 
        {
            reader = new BufferedReader(new FileReader(file));

            String text = reader.readLine();
            String parts[];
            parts=text.split(" ");
            n=Integer.parseInt(parts[0]);
            W=Integer.parseInt(parts[1]);
            v=new int[n];
            w=new int[n];
            picked=new boolean[n];
            for (int i=0;i<n;i++)
            {
                text=reader.readLine();
                parts=text.split(" ");
                v[i]=Integer.parseInt(parts[0]);
                w[i]=Integer.parseInt(parts[1]);
            }
            reader.close();
        } 
        catch (Exception e) 
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

            int total_v=0;
            for (int i=0;i<n;i++)
              if (picked[i])
                total_v += v[i];
            writer.println(total_v);
            for (int i=0;i<n;i++)
              if (picked[i])
                writer.println("1");
              else 
                writer.println("0");

            writer.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public Integer[] sortedIndices( final double[] a )
    {
        Integer[] idx = new Integer[ n ];
        for ( int i = 0 ; i < a.length ; i++ )
        	idx[ i ] = new Integer( i );
        Arrays.sort
        ( idx, new Comparator<Integer>() 
	        {
			    @Override public int compare( final Integer i1, final Integer i2) 
			    {
			        return Double.compare( a[ i1 ], a[ i2 ] );
			    }
		    }
	    );
	    return idx;
    }
    
    public Integer[] sortedIndices( final int[] a )
    {
        Integer[] idx = new Integer[ n ];
        for ( int i = 0 ; i < a.length ; i++ )
        	idx[ i ] = new Integer( i );
        Arrays.sort
        ( idx, new Comparator<Integer>() 
	        {
			    @Override public int compare( final Integer i1, final Integer i2) 
			    {
			        return Integer.compare( a[ i1 ], a[ i2 ] );
			    }
		    }
	    );
	    return idx;
    }

    public FrameworkKnapsack(String []Args)
    {
        beta = Double.parseDouble( Args[ 0 ] );
        input(Args[ 1 ]);

// YOUR CODE STARTS HERE

// CS 4820, Spring 2017 Homework 11, Problem 3
// Name: Yuxiang Peng
// NetId: yp344
// Collaborator: jl3455, zl542

        
//get epsilon using equation Beta = 1/(1+epsilon)
//get D using D = Epsilon/n * Vmax
//need to round vi tilda to D[vi/D]
//create table U n by Vsum

        
//getting epsilon
        double ep = (1/beta) - 1;

//getting maxV
        int maxV = Integer.MIN_VALUE;
        for(int i = 0; i < n; i++){
        	if(w[i]<= W){
        		maxV = Math.max(v[i], maxV);
        	}
        }
        System.out.println("max is " + maxV);
        
//getting the d
        double D = ep/(n) * maxV;
        System.out.println("epsilon is " + ep);
        System.out.println("D is " + D);

//round
       int[] vTilda = new int[n];
       int sumV = 0;
       for(int i = 0; i < n; i++){
    	   vTilda[i] = (int) Math.ceil(v[i]/D);
    	   if(w[i] <= W){
    		   sumV+=vTilda[i];
    	   }
       }
       System.out.println("Sum of V is " + sumV); 

//making table
       int[][] U = new int[n][sumV];
       System.out.println("instantiated");

//instantiating
       for(int i = 0; i < n; i++){
    	   U[i][0] = 0;
       }
       
//dynamic programming
//if k > sum j = 1, i -1 vj
       for(int i = 1; i < n; i++){
    	   int comp = 0;
    	   for(int j = 1; j < i; j++){
    		   comp+= vTilda[j];
       		}
    	   for(int k = 1; k < sumV; k++){
			   if(k > comp){
				   U[i][k] = w[i] + U[i-1][k];
			   }
			   else{
				   int index = Math.max(0, k - vTilda[i]);
				   U[i][k] = Math.min(U[i-1][k], w[i] + U[i-1][index]);
			   }
		   }
       }

//getting best value
       int opt = Integer.MIN_VALUE;
       for(int k = 0; k < sumV; k++){
    	  opt = Math.max(opt, U[n-1][k]);
       }
       System.out.println(opt);
       System.out.println("compiled");
       if(opt < 595851894){
    	   System.out.println("failed");
       }
       
// YOUR CODE ENDS HERE

        output(Args[ 2 ]);
    }

    public static void main(String [] Args) //Strings in Args are 1. beta, 2. the name of the input file and 3. the name of the output file
    {
    	long timeNow = System.currentTimeMillis()/1000;
        new FrameworkKnapsack(Args);
        long timeAfter = System.currentTimeMillis()/1000;
        System.out.println(timeAfter - timeNow);
    }
}





