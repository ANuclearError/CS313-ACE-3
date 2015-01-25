package vmm.main;

public class PageTable {
	public int size;
	
	private int[] pageTable;
	
	public PageTable(int size){
		this.size = size;
		
		pageTable = new int[size];
		
		for(int i: pageTable){
			i = -1;
		}
	}
	
	public int lookup(int page){
		return pageTable[page];
	}
	
	public void update(int page, int frame){
		pageTable[page] = frame;
	}
	
	public static void main(String[] args){
		PageTable pt = new PageTable(4);
	}
}
