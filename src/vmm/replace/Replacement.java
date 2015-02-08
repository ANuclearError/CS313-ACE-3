package vmm.replace;

/**
 * A replacement algorithm is used to determine which index a new entry to the
 * TLB (and in future, the Page Table) should be added to.
 * 
 * @author Aidan O'Grady
 * @version 1.4
 * @since 0.3
 *
 */
public interface Replacement {
	/**
	 * Returns the index to be updated
	 * @return index
	 */
	public int getIndex();
	
	/**
	 * Updates the tracking system of a replacement algorithm, if required. This
	 * is needed for cases such as a LRU replacement policy.
	 * 
	 * @param index - The index of the TLB entry being updated
	 * @param check - The nth time the TLB has been looked up
	 */
	public void update(int index, int check);
	
	public String getName();
}
