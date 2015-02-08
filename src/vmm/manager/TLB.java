package vmm.manager;

import vmm.replace.*;

/**
 * The translation lookaside buffer is a small buffer that will store page
 * numbers and their respective frame numbers. This will provide better
 * performance in real life memory management. However, in our simulator, I have
 * the suspicion that the system is slower with it, but I shouldn't worry aobut
 * it.
 * 
 * @author Aidan O'Grady
 * @version 1.4
 * @since 0.3
 *
 */
public class TLB {
	
	/**
	 * The representation of the TLB as a 2d array.
	 */
	private int[][] tlb;
	
	/**
	 * The number of entries in the TLB
	 */
	private int size;
	
	/**
	 * The number of times a hit has occurred.
	 */
	private int hits;
	
	/**
	 * The number of times the TLB is consulted.
	 */
	private int checks;
	
	/**
	 * The replacement algorithm to be used.
	 */
	private Replacement algo;
	
	/**
	 * Constructs a new TLB of a given length and using the given algorithm.
	 * 
	 * @param size - the number of entries to be included
	 * @param algo - the replacement algorithm to be used
	 */
	public TLB(int size, Replacement algo){
		this.size = size;
		tlb = new int[size][2];
		this.algo = algo;
		
		hits = 0;
		checks = 0;
		
		// Since a page number or frame number of 0 is allowed, we need to
		// ensure empty entries are not 0.
		for(int i=0; i < size; i++){
			tlb[i][0] = -1;
			tlb[i][1] = -1;
		}
	}
	
	/**
	 * Inserts a page number and frame number into the TLB, using the algorithm
	 * to determine where this new entry will be held.
	 * 
	 * @param pageNum - the page number to be added
	 * @param frameNum - the frame number to be added
	 */
	public void insert(int pageNum, int frameNum){
		int index = algo.getIndex(); // The index to be added to
		tlb[index][0] = pageNum;
		tlb[index][1] = frameNum;
		algo.update(index, checks);
	}
	
	/**
	 * Returns the frame number that the given page number is mapped to, if any.
	 * 
	 * @param pageNum - the page number to be looked up
	 * @return the mapped frame number if found, otherwise -1
	 */
	public int lookup(int pageNum){
		checks++;
		// TODO use more efficient search
		for(int i=0; i<size; i++){
			if(tlb[i][0] == pageNum){
				algo.update(i, checks);
				hits++;
				return tlb[i][1];
			}
		}
		// TLB miss has occurred
		return -1;
	}
	
	/**
	 * Returns the number of times the TLB was consulted
	 * @return the number of checks
	 */
	public int getChecks(){
		return checks;
	}
	
	/**
	 * Returns the number of times a miss occurred.
	 * @return the number of misses
	 */
	public int getHits(){
		return hits;
	}
	
	/**
	 * Returns the miss rate of the TLB
	 * @return the miss rate
	 */
	public float getHitRate(){
		float missRate = (float)hits/(float)checks * 100;
		return missRate;
	}
	
	/**
	 * Returns the size (number of entries) in the TLB
	 * 
	 * @return size
	 */
	public int getSize(){
		return size;
	}
	
	/**
	 * Returns the name of the replacement policy being used
	 * 
	 * @return name of the algorithm
	 */
	public String getAlgoName(){
		return algo.getName();
	}
}
