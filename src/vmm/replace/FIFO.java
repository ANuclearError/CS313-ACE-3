package vmm.replace;

public class FIFO implements ReplacementAlgorithm{
	
	private int nextIndex;
	private int size;
	
	public FIFO(int size){
		nextIndex = 0;
		this.size = size;
	}

	@Override
	public int getIndex() {
		int index = nextIndex;
		nextIndex++;
		nextIndex = nextIndex % size;
		return index;
	}

}
