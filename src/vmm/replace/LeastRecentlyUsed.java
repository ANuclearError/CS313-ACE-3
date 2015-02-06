package vmm.replace;

/**
 * The LRU replacement policy is a system that keeps a track of when a TLB entry
 * was last used, and uses this to determine which entry is to be replaced. This
 * is achieved by keeping a separate array that is tied to the TLB that holds
 * when a TLB entry was last looked up, with the time measurement being each
 * time an entry is looked up.
 * 
 * @author Aidan O'Grady
 * @version 1.3
 * @since 0.4
 *
 */
public class LeastRecentlyUsed implements Replacement{

	/**
	 * A count is kept for each TLB element. TLB entry 0's count is counts[0].
	 */
	private int[] counts;
	
	/**
	 * The size of the TLB being tracked.
	 */
	private int size;
	
	/**
	 * Constructs a new LRU policy with the given size in mind.
	 * @param size
	 */
	public LeastRecentlyUsed(int size){
		this.size = size;
		counts = new int[size];
	}

	@Override
	public int getIndex() {
		int min = 0;
		//Linear search for minimum, contemplating using a temporary sort first.
		for(int i=1; i<size; i++){
			if(counts[i] < counts[min])
				min = i;
		}
		return min;
	}
	
	@Override
	public void update(int index, int check){
		counts[index] = check;
	}

	@Override
	public String getName() {
		return "LRU";
	}
	
}
