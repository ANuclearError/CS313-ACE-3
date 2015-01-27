package vmm.main;

import java.util.Arrays;

import vmm.replace.*;

public class TLB {
	
	private int[][] tlb;
	private int size;
	
	private ReplacementAlgorithm algo;
	
	public TLB(int size, ReplacementAlgorithm algo){
		this.size = size;
		this.tlb = new int[size][2];
		this.algo = algo;
	}
	
	public void insert(int pageNum, int frameNum){
		int index = algo.getIndex();
		tlb[index][0] = pageNum;
		tlb[index][1] = frameNum;
	}
	
	public int lookup(int pageNum){
		for(int i=0; i<size; i++){
			if(tlb[i][0] == pageNum)
				return tlb[i][1];
		}
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
}
