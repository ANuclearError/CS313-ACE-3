package vmm.manager;

import vmm.replace.FIFO;

/**
 * The purpose of the Translator class is to handle the translation from logical
 * to physical addresses. The translator is responsible for accessing the TLB,
 * Page Table, Physical Memory and Backing store, and is thus the backbone of
 * the whole manager.
 * 
 * @author Aidan O'Grady
 * @version 1.1
 * @since 1.1
 *
 */
public class Translator {
	
	/**
	 * The memory manager's TLB
	 */
	private TLB tlb;
	
	/**
	 * The memory manager's Page Table
	 */
	private PageTable pt;
	
	/**
	 * The memory manager's Physical Memory
	 */
	private PhysicalMemory pm;
	
	/**
	 * The backing store used to handle page faults.
	 */
	private BackingStore bs;
	
	/**
	 * Constructs a new translator, currently with fixed values.
	 */
	public Translator(){
		tlb = new TLB(16, new FIFO(16));
		pt = new PageTable(256);
		pm = new PhysicalMemory(256);
		bs = new BackingStore("files/BACKING_STORE");
	}
	
	/**
	 * Given a logical address, the corresponding physical address will be
	 * obtained.
	 * 
	 * @param address - The logical address being translated.
	 * @return A string combine 
	 */
	public String translate(int address){
		int frameNum = -1;
		
		// Parse the page number and offset from address.
		int pageNum = (address & 0x0000FF00) >> 8;
		int offset = (address & 0xFF);
		
		frameNum = tlb.lookup(pageNum); // TLB lookup
		
		if(frameNum < 0){ // TLB Miss
			frameNum = pt.lookup(pageNum);
			
			if(frameNum < 0) // Page Fault
				frameNum = pageFault(pageNum);
				
			tlb.insert(pageNum, frameNum);
		}
		
		int physicalAddress = (frameNum * 256) + offset;
		int value = pm.lookup(physicalAddress);
		
		//Display output.
		String result = "Virtual address: " + address + " Physical address: " +
				physicalAddress + " Value: " + value;
		return result;
	}
	
	/**
	 * When a page fault occurs, the backing storage must be consulted, this
	 * allows us to find the correct frame number.
	 * 
	 * @param pageNum : The page number being translated
	 * @return the corresponding frame number.
	 */
	private int pageFault(int pageNum){
		int frameNum = -1;
		byte[] data = bs.read(pageNum*256);
		frameNum = pm.insert(data);  // Memory is updated
			
		// The Page Table must be updated now.
		pt.insert(pageNum, frameNum);
		
		return frameNum;
	}
	
	/**
	 * Prints information about the system set up.
	 */
	public void startInfo(){
		System.out.println("TLB size: " + 16 + " entries");
		System.out.println("TLB Replacement Algorithm: FIFO");
		System.out.println("Page Table Size: " + 256 +  " pages");
		System.out.println("Page Size: " + 256 + " bytes");
		System.out.println("Physical Memory Size: " + 256 + " frames");
		System.out.println("Physical Memory Frame Size: " + 256 + " bytes");
		System.out.println("Backing Storage file: " + bs.getFilename());
		System.out.println("----------");

	}
	
	/**
	 * Prints statistics about TLB and PT.
	 */
	public void statistics(){
		System.out.println("----------");
		System.out.print("TLB Lookups: " + tlb.getChecks());
		System.out.print(" | TLB Misses: " + tlb.getMisses());
		System.out.println(" | TLB Miss Rate: " + tlb.getMissRate());
		System.out.println("----------");
		System.out.print("Page Table Lookups: " + pt.getChecks());
		System.out.print(" | Page Table Faults: " + pt.getFaults());
		System.out.println(" | Page Table Fault Rate: " + pt.getFaultRate());
	}
}
