package vmm.main;

import vmm.replace.*;

public class TLB {
	
	private int[][] tlb;
	private int size;
	
	private int misses;
	private int checks;
	
	private ReplacementAlgorithm algo;
	
	public TLB(int size, ReplacementAlgorithm algo){
		this.size = size;
		tlb = new int[size][2];
		this.algo = algo;
		
		misses = 0;
		checks = 0;
		
		for(int i=0; i < size; i++){
			tlb[i][0] = -1;
			tlb[i][1] = -1;
		}
	}
	
	public void insert(int pageNum, int frameNum){
		int index = algo.getIndex();
		tlb[index][0] = pageNum;
		tlb[index][1] = frameNum;
	}
	
	public int lookup(int pageNum){
		checks++;
		for(int i=0; i<size; i++){
			if(tlb[i][0] == pageNum)
				return tlb[i][1];
		}
		// TLB miss has occurred
		misses++;
		return -1;
	}
	
	public void print(){
		String string = "{";
		for(int i=0; i<size; i++){
			string+= ("{" + tlb[i][0] + "," + tlb[i][1] + "}");
		}
		string += "}";
		System.out.println(string);
	}
	
	public float getMissRate(){
		float missRate = (float)misses/(float)checks * 100;
		return missRate;
	}
	
	public int getChecks(){
		return checks;
	}
	public int getMisses(){
		return misses;
	}
}
