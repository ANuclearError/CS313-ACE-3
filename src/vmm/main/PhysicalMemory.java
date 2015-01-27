package vmm.main;

/**
 * This class represents our physical memory, and is essentially a 2d array of
 * bytes. The memory is able to be referred to and updated throughout the
 * execution of the program.
 * 
 * @author Aidan O'Grady
 * @version 0.2
 * @since 0.2
 *
 */
public class PhysicalMemory {
	
	/**
	 * The memory's data is stored in this array.
	 */
	private byte[][] physicalMemory;
	
	/**
	 * The next available frame in memory.
	 */
	private int nextIndex;
	
	/**
	 * The number of frames within memory.
	 */
	private int frameNum;
	
	/**
	 * Constructs new physical memory with a given number of frames.
	 * 
	 * @param noOfFrames - the number of frames to be included.
	 */
	public PhysicalMemory(int noOfFrames){
		frameNum = noOfFrames;
		nextIndex = 0;
		physicalMemory = new byte[frameNum][256];
	}
	
	/**
	 * Returns the byte referred to by the given address.
	 * 
	 * @param address - the address to be looked up.
	 * @return the 8 bit signed int referred to.
	 */
	public int lookup(int address){
		// The frame number and offset will be parsed.
		int frame = (address & 0x0000FF00) >> 8;
		int offset = address & 0xFF;
		return physicalMemory[frame][offset];
	}
	
	/**
	 * Adds the given data to memory and returns the frame that was updated.
	 * Since the frames are updated sequentially, we must ensure that the
	 * correct frame number is referred to.
	 * 
	 * @param data - the bytes to be added to memory
	 * @return the frame that the data was added to.
	 */
	public int insert(byte[] data){
		int frame = nextIndex;
		if(nextIndex < frameNum){
			physicalMemory[nextIndex] = data;
			nextIndex++; // The next available index is updated.
		}
		return frame; // Note, this needs improved, right now it seems dodgey.
	}
}
