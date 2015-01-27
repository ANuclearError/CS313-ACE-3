package vmm.main;

/**
 * This class is responsible for acting as the page table in our virtual
 * memory manager. It allows us to look up the contents of a given page and also
 * update the contents of a given page as well. It was a surprisingly simple
 * class to implement, which makes me suspicious that there's an error in my
 * design somewhere, though it's yet to appear, hiding in the shadows waiting to
 * strike.
 * 
 * @author Aidan O'Grady
 * @version 0.3
 * @since 0.2
 *
 */
public class PageTable {
	
	/**
	 * The page table, the index is the page number while its content is the
	 * mapped frame.
	 */
	private int[] pageTable;
	
	/**
	 * The number of total pages.
	 */
	private int size;
	
	private int checks;
	
	private int faults;
	
	/**
	 * Constructs a new page table of a given size.
	 * 
	 * @param size - how many pages the table should have.
	 */
	public PageTable(int size){
		
		this.size = size;
		pageTable = new int[this.size];
		
		// All pages are set to -1 to represent emptiness rather than 0.
		for(int i=0; i<this.size; i++){
			pageTable[i] = -1;
		}
		
		checks = 0;
		faults = 0;
	}
	
	/**
	 * Returns the content of a given page.
	 * @param page - The page to be looked up.
	 * @return the page's content. If the page is empty, -1 is returned.
	 */
	public int lookup(int page){
		checks++;
		if(pageTable[page] < 0){
			faults++;
		}
		return pageTable[page];
	}
	
	/**
	 * Updates a given frame with the content provided. No precautions are taken
	 * in replacing data held in a page.
	 * 
	 * @param frame - the data to be written to the page.
	 * @param page - the page to be updated.
	 */
	public void insert(int page, int frame){
		pageTable[page] = frame;
	}
	
	public float getFaultRate(){
		float faultRate = (float)faults/(float)checks * 100;
		return faultRate;
	}
	
	public int getChecks(){
		return checks;
	}
	public int getFaults(){
		return faults;
	}

	
}
