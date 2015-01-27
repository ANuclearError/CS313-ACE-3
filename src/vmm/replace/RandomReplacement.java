package vmm.replace;

import java.util.Random;

public class RandomReplacement implements ReplacementAlgorithm{

	private int size;
	private Random rng;
	
	public RandomReplacement(int size){
		this.size = size;
		rng = new Random();
	}

	@Override
	public int getIndex() {
		return rng.nextInt(size);
	}

}
