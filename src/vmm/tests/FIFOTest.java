package vmm.tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import vmm.manager.TLB;
import vmm.replace.FIFO;
import vmm.replace.Replacement;

/**
 * Testing FIFO replacement policy with a quick test based on page 410 of the OS
 * Concepts with Java textbook. It's very short and sweet due to a bit of
 * laziness off of my part.
 * 
 * @author Aidan O'Grady
 *
 */
public class FIFOTest {
	
	private static int[] stuff;
	private static Replacement fifo;
	private static TLB tlb;
	
	@BeforeClass
	public static void setUpClass(){
		stuff =  new int[]{7,0,1,2,0,3,0,4,2,3,0,3,2,1,2,0,1,7,0,1};
		fifo = new FIFO(3);
		tlb = new TLB(3, fifo);
	}

	@Test
	public void testUpDate() {
		for(int num : stuff){
			int result = tlb.lookup(num); // TLB lookup
			
			if(result < 0){ // TLB Miss
					
				tlb.insert(num, num);
			}
		}
		assertEquals(tlb.getChecks(), 20);
		assertEquals(tlb.getMisses(), 15);
		assertEquals(tlb.getMissRate(), 75.0, 0.0);
	}

}
