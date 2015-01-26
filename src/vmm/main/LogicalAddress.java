package vmm.main;
/**
 * This class will be in charge of extracting the page number and offset from a
 * given logical address. Setters and getters will be used to allow for the
 * extraction of the required elements and allow them to be used elsewhere.
 * 
 * Please bear in mind that since this section may be required to be less
 * independent and may be infused with another class if need be.
 * 
 * @author Aidan O'Grady
 * @version 0.2
 * @since 0.1
 *
 */
public class LogicalAddress {
	
	/** The given logical address that is to be translated. */
	private int logicalAddress;
	
	/** The corresponding page number. */
	private int pageNumber;
	
	/** The corresponding offset. */
	private int offset;
	
	/**
	 * Constructs a new logical address with the given address.
	 * 
	 * @param logicalAddress - the address to be translated.
	 */
	public LogicalAddress(int logicalAddress){
		this.logicalAddress = logicalAddress;
		setPageNumber();
		setOffset();
	}

	/**
	 * Sets pageNumber by extracting bits 8-15 from the address.
	 */
	private void setPageNumber() {
		// Bit masking and shifting leaves us with bits 8-15, the page number.
		 this.pageNumber = (logicalAddress & 0x0000FF00) >> 8;
	}

	/**
	 * Sets offset by extracting bits 0-7 from the address.
	 */
	private void setOffset() {
		// Bit masking converts bits 8-31 to 0s so they can be ignored.
		this.offset = (logicalAddress & 0xFF);
	}
	
	/**
	 * Returns the logical address.
	 * @return the logical address.
	 */
	public int getLogicalAddress(){
		return logicalAddress;
	}
	
	/**
	 * Returns the page number.
	 * @return the page number.
	 */
	public int getPageNumber(){
		return pageNumber;
	}
	
	/**
	 * Returns the offset.
	 * @return the offset.
	 */
	public int getOffset(){
		return offset;
	}
	
	public static void main(String[] args){
		LogicalAddress la = new LogicalAddress(30198);
		System.out.print("Logical Address: " + la.getLogicalAddress() + " ");
		System.out.print("Page Number: " + la.getPageNumber() + " ");
		System.out.println("Offset: " + la.getOffset());
	}
}
