package vmm.main;

public class PhysicalMemory {
	private byte[][] physicalMemory;
	
	public PhysicalMemory(int noOfFrames){
		physicalMemory = new byte[noOfFrames][256];
	}
	
	public int lookup(int address){
		int frame = (address & 0x0000FF00) >> 8;
		int offset = address & 0xFF;
		return 0;
	}
	
	public void update(int frame, byte[] data){
		physicalMemory[frame] = data;
	}
}
