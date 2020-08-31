import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation 
{
    private final int n, source, sink;
	private int openSites;
	private boolean[] status;
	private final WeightedQuickUnionUF grid;
	private final WeightedQuickUnionUF fullGrid;
	
	 // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
    	if (n <= 0) throw new IllegalArgumentException("The value of n should be greather than 0.");
    	
    	this.n = n;
    	openSites = 0;
    	
    	source = flattenIndices(this.n, this.n) + 1;
    	sink = flattenIndices(this.n, this.n) + 2;
    	
    	status = new boolean[n * n];
    
    	grid = new WeightedQuickUnionUF(n * n + 2);
    	fullGrid = new WeightedQuickUnionUF(n * n + 1);
    }
    
    private int flattenIndices(int row, int col)
    {
    	validate(row, col);
    	
    	return this.n * (row - 1) + col - 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
    	if (isOpen(row, col)) return;
    	
    	int index = flattenIndices(row, col);
    	
    	status[index] = true;
    	
    	if (row == 1) 
    	{
    		grid.union(source, index);
    		fullGrid.union(source, index);
    	}
    	
    	if (row == this.n) grid.union(sink, index);
    	
    	if (isOutOfBounds(row, col - 1) && isOpen(row, col - 1)) 
    	{
    		grid.union(flattenIndices(row, col - 1), index);
    		fullGrid.union(flattenIndices(row, col - 1), index);
    	}
    	
    	if (isOutOfBounds(row, col + 1) && isOpen(row, col + 1)) 
    	{
    		grid.union(flattenIndices(row, col + 1), index);
    		fullGrid.union(flattenIndices(row, col + 1), index);
    	}
    	
    	if (isOutOfBounds(row - 1, col) && isOpen(row - 1, col)) 
    	{
    		grid.union(flattenIndices(row - 1, col), index);
    		fullGrid.union(flattenIndices(row - 1, col), index);
    	}
    	
    	if (isOutOfBounds(row + 1, col) && isOpen(row + 1, col)) 
    	{
    		grid.union(flattenIndices(row + 1, col), index);
    		fullGrid.union(flattenIndices(row + 1, col), index);
    	}
    	
    	++openSites;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {    	
    	return status[flattenIndices(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) 
    {	
    	return fullGrid.connected(source, flattenIndices(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
    	return openSites;
    }

    // does the system percolate?
    public boolean percolates()
    {
    	return grid.connected(source, sink);
    }
    
    private void validate(int row, int col)
    {
    	if (!isOutOfBounds(row, col)) throw new IllegalArgumentException("The value of n should be greather than 0.");
    }
    
    private boolean isOutOfBounds(int row, int col)
    {
    	return row > 0 && col > 0 && row <= this.n && col <= this.n;
    }
}
