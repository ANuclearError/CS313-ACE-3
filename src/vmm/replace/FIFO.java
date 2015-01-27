package vmm.replace;

/**
 * The 'First In, First Out' algorithm means that a queue is essentially
 * implemented. The entry that has been present the longest will be replaced.
 * This is implemented through the use of a circular array, so that when the
 * array is filled after cold starts, we simply return to the beginning.
 * 
 * @author Aidan O'Grady
 * @version 0.3
 * @since 0.3
 *
 */
public class FIFO implements ReplacementAlgorithm{
	
	/**
	 * The next index to be replaced
	 */
	private int nextIndex;
	
	/**
	 * The size of the TLB using this algorithm
	 */
	private int size;
	
	/**
	 * Constructs the algorithm
	 * @param size - the size of the TLB using this algorithm
	 */
	public FIFO(int size){
		nextIndex = 0;
		this.size = size;
	}

	@Override
	public int getIndex() {
		int index = nextIndex;
		nextIndex++;
		nextIndex = nextIndex % size; // Ensures the circularity of the array
		return index;
	}

}
