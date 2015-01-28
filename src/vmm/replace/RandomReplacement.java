package vmm.replace;

import java.util.Random;

/**
 * The random replacement algorithm is rather straight forward. A random index
 * is chosen without any discrimination between entries. An RNG is used to allow
 * this random choice to take place.
 * 
 * @author Aidan O'Grady
 * @version 1.0
 * @since 0.3
 *
 */
public class RandomReplacement implements Replacement{
	/**
	 * The upper limit of the RNG, based on the size of the TLB
	 */
	private int size;
	
	/**
	 * The RNG
	 */
	private Random rng;
	
	private int next;
	
	/**
	 * Constructs the algorithm
	 * @param size - the size of the TLB using this algoritm
	 */
	public RandomReplacement(int size){
		this.size = size;
		rng = new Random();
	}

	@Override
	public int getIndex() {
		return rng.nextInt(size);
	}
	
	@Override
	public void update(int index, int check){
	}

}
