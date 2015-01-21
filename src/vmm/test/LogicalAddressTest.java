package vmm.test;

import static org.junit.Assert.*;
import org.junit.Test;
import vmm.main.*;

/**
 * The following tests are based upon the 'How to Begin' section of the book's
 * specification for this assignment. These tests are basic, due to the rather
 * basic nature of the class.
 * 
 * @author Aidan O'Grady
 * @version 0.1
 * @since 0.1
 *
 */
public class LogicalAddressTest {
	
	// The following tests are based upon the numbers given in the 'How to 
	// Begin' section of page 455. 

	/**
	 * Testing with the logical address of 1.
	 */
	@Test
	public void test1() {
		LogicalAddress la = new LogicalAddress(1);
		assertEquals(la.getLogicalAddress(), 1);
		assertEquals(la.getPageNumber(), 0);
		assertEquals(la.getOffset(), 1);
	}
	
	/**
	 * Testing with the logical address of 256.
	 */
	@Test
	public void test256() {
		LogicalAddress la = new LogicalAddress(256);
		assertEquals(la.getLogicalAddress(), 256);
		assertEquals(la.getPageNumber(), 1);
		assertEquals(la.getOffset(), 0);
	}
	
	/**
	 * Testing with the logical address of 32768.
	 */
	@Test
	public void test32768() {
		LogicalAddress la = new LogicalAddress(32768);
		assertEquals(la.getLogicalAddress(), 32768);
		assertEquals(la.getPageNumber(), 128);
		assertEquals(la.getOffset(), 0);
	}
	
	/**
	 * Testing with the logical address of 32769.
	 */
	@Test
	public void test32769() {
		LogicalAddress la = new LogicalAddress(32769);
		assertEquals(la.getLogicalAddress(), 32769);
		assertEquals(la.getPageNumber(), 128);
		assertEquals(la.getOffset(), 1);
	}
	
	/**
	 * Testing with the logical address of 128.
	 */
	@Test
	public void test128() {
		LogicalAddress la = new LogicalAddress(128);
		assertEquals(la.getLogicalAddress(), 128);
		assertEquals(la.getPageNumber(), 0);
		assertEquals(la.getOffset(), 128);
	}
	
	/**
	 * Testing with the logical address of 65534.
	 */
	@Test
	public void test65534() {
		LogicalAddress la = new LogicalAddress(65534);
		assertEquals(la.getLogicalAddress(), 65534);
		assertEquals(la.getPageNumber(), 255);
		assertEquals(la.getOffset(), 254);
	}
	
	/**
	 * Testing with the logical address of 33153.
	 */
	@Test
	public void test33153() {
		LogicalAddress la = new LogicalAddress(33153);
		assertEquals(la.getLogicalAddress(), 33153);
		assertEquals(la.getPageNumber(), 129);
		assertEquals(la.getOffset(), 129);
	}

}
