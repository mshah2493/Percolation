

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats 
{
    private final int t;
	private final double[] thresholds;
	private final static double constant = 1.96;
	
	// perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
    	if (n <= 0 || trials <= 0)
    	{
    		throw new IllegalArgumentException("Grid size and trials cannot be less than 1.");
    	}
    	
    	t = trials;
    	thresholds = new double[trials];
    	
    	for (int i = 0; i < trials; i++)
    	{
    		Percolation percolation = new Percolation(n);
    		
            while (!percolation.percolates()) 
            {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                
                percolation.open(row, col);
            }
            
            thresholds[i] = (double) percolation.numberOfOpenSites() / (n * n);
    	}
    }

    // sample mean of percolation threshold
    public double mean()
    {
    	return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
    	return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
    	return mean() - ((constant * stddev()) / Math.sqrt(t));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
    	return mean() + ((constant * stddev()) / Math.sqrt(t));
    }
    
    // test client (see below)
    public static void main(String[] args)
    {
    	int gridSize;
    	int trials;
    	
    	if (args.length == 2) 
        {
    		gridSize = Integer.parseInt(args[0]);
    		trials = Integer.parseInt(args[1]);
        }
    	else
    	{
    		throw new IllegalArgumentException("Please provide the values of grid size and trials.");
    	}
         
        PercolationStats p = new PercolationStats(gridSize, trials);
    	
    	System.out.println("mean                    = " + p.mean());
        System.out.println("stddev                  = " + p.stddev());
        System.out.println("95% confidence interval = [" + p.confidenceLo() + ", " + p.confidenceHi() + "]");
    }
}
