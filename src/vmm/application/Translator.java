package vmm.application;

import vmm.manager.BackingStore;
import vmm.manager.PageTable;
import vmm.manager.PhysicalMemory;
import vmm.manager.TLB;
import vmm.replace.*;

/**
 * The purpose of the Translator class is to handle the translation from logical
 * to physical addresses. The translator is responsible for accessing the TLB,
 * Page Table, Physical Memory and Backing store, and is thus the backbone of
 * the whole manager.
 * 
 * @author Aidan O'Grady
 * @version 1.3
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
	 * Determines the settings to be used.
	 */
	private Settings settings;
	
	/**
	 * Constructs a new translator, currently with fixed values.
	 */
	public Translator(){
		settings = new Settings();
		setup();
	}
	
	/**
	 * The various components of the system (TLB, Page Table etc) are created
	 * here. The parameters for each component is derived from the settings
	 * class, which contains all the information required.
	 */
	private void setup(){
		try {
			String policy = settings.getTLBReplacement().toLowerCase();
			int tlbSize = settings.getTLBSize(); // Used alot, separate variable.
			Replacement algo;
			// Need to determine which policy is desired.
			if(policy.equalsIgnoreCase("random"))
				algo = new RandomReplacement(tlbSize);
			if(policy.equalsIgnoreCase("lru"))
				algo = new LeastRecentlyUsed(tlbSize);
			else{ // If the string isn't valid, we resort to FIFO just in case.
				algo = new FIFO(tlbSize);
			}
			tlb = new TLB(tlbSize, algo);
			pt = new PageTable(settings.getPTSize());
			pm = new PhysicalMemory(settings.getMemSize(), settings.getFrameSize());
			bs = new BackingStore("files/BACKING_STORE");
		} catch (NumberFormatException e) {
			System.out.println("Error: The Settings file must be messed up.");
			System.exit(0);
		}

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
		
		// Output.
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
		byte[] data = bs.read(pageNum*256, 256);
		frameNum = pm.insert(data);  // Memory is updated
			
		// The Page Table must be updated now.
		pt.insert(pageNum, frameNum);
		
		return frameNum;
	}
	
	/**
	 * Prints information about the system set up.
	 */
	public void startInfo(){
		System.out.println("TLB size: " + tlb.getSize() + " entries");
		System.out.println("TLB Replacement Algorithm: " + tlb.getAlgoName());
		System.out.println("Page Table Size: " + pt.getSize() +  " pages");
		System.out.println("Physical Memory Size: " + pm.getSize() + " frames");
		System.out.println("Frame Size: " + pm.getFrameSize() + " bytes");
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
