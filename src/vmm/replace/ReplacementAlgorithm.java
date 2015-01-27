package vmm.replace;

/**
 * A replacement algorithm is used to determine which index a new entry to the
 * TLB (and in future, the Page Table) should be added to.
 * 
 * @author Aidan O'Grady
 * @version 0.3
 * @since 0.3
 *
 */
public interface ReplacementAlgorithm {
	/**
	 * Returns the index to be updated
	 * @return index
	 */
	public int getIndex();
}
