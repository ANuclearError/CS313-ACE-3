package vmm.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.util.InputMismatchException;
import java.util.Scanner;

import vmm.replace.*;

/**
 * This class is the central area of the memory manager. The class will read the
 * input file and translate each address within it, while keeping track of the
 * various statistics that are to be recorded.
 * 
 * @author Aidan O'Grady
 * @version 0.3
 * @since 0.2
 *
 */
public class AddressTranslator {
	/**
	 * The memory manager's TLB
	 */
	private TLB tlb;
	
	/**
	 * The memory manager's Page Table
	 */
	private PageTable pt;
	
	/**
	 * The memory manager's Physical Memory
	 */
	private PhysicalMemory pm;
	
	/**
	 * The name of the input file
	 */
	private String inputFileName;
	
	/**
	 * The backing storage name file
	 */
	private String backingFileName;
		
	/**
	 * Constructs a new translator, currently used hard coded values, though
	 * this will eventually be changed.
	 */
	public AddressTranslator(){
		
		// TODO: Not have magic numbers etc
		
		tlb = new TLB(16, new RandomReplacement(16));
		pt = new PageTable(256);
		pm = new PhysicalMemory(256);
		
		backingFileName = "files/BACKING_STORE";
		inputFileName = "input/InputFile.txt";
		long startTime = System.nanoTime();
		readInput();
		statistics(startTime);
	}
	
	/**
	 * The input file is read, to start the translating process.
	 * Whenever a line is read from the file, it will be translated and its
	 * results displayed.
	 */
	private void readInput(){
		String line = "";
		int lineNum = 0;
		try {
			FileReader fr = new FileReader(inputFileName);
			
			// LNR is used since I'm too lazy to keep track myself.
			LineNumberReader lnr = new LineNumberReader (fr);
			Scanner sc = null;
			line = lnr.readLine();
			
			// Go through each line.
			while(line != null){
				lineNum = lnr.getLineNumber();
				sc = new Scanner(line);
				int num = sc.nextInt();

				translate(num);
				
				line = lnr.readLine();
			}
			lnr.close();
		} catch (FileNotFoundException e) {
			System.out.print("ERROR: File: '" + inputFileName + "' not found.");
		} catch (InputMismatchException e) {
			System.out.print("ERROR: Input mismatch at line " + lineNum + " '" + 
					line + "'.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * The given logical address is converted to a physical one. The system
	 * looks through the TLB, page table and physical memory to find the 
	 * proper address and data.
	 * 
	 * @param address - the address to be translated.
	 */
	private void translate(int address){
		int frameNum = -1;
		
		// Parse the page number and offset from address.
		int pageNum = (address & 0x0000FF00) >> 8;
		int offset = (address & 0xFF);
		
		frameNum = tlb.lookup(pageNum); // TLB lookup
		
		if(frameNum < 0){ // TLB Miss
			frameNum = pt.lookup(pageNum);
			
			if(frameNum < 0) // Page Fault
				frameNum = pageFault(pageNum);
				
			tlb.insert(pageNum, frameNum);
		}
		
		int physicalAddress = (frameNum * 256) + offset;
		int value = pm.lookup(physicalAddress);
		
		//Display output.
		System.out.print("Logical Address: " + address);
		System.out.print(" Physical Address: " + physicalAddress);
		System.out.println(" Value: " + value);

	}
	
	/**
	 * Page faults are handled here, the backing store file is read to find the
	 * data to be added to memory. 
	 * 
	 * @param pageNum - The page number being translated.
	 */
	private int pageFault(int pageNum){
		int frameNum = -1;
		try {
			File file = new File(backingFileName);
			RandomAccessFile backingStore = new RandomAccessFile(file, "r");
			
			backingStore.seek(pageNum * 256); // The data is stored here.
				
			byte[] data = new byte[256];
			
			backingStore.read(data); // Data is read
			
			frameNum = pm.insert(data);  // Memory is updated
			
			// The Page Table must be updated now.
			pt.insert(pageNum, frameNum);
		 		
			backingStore.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return frameNum;
	}
	
	/**
	 * Prints statistics about TLB and PT.
	 */
	private void statistics(long startTime){
		long endTime = System.nanoTime();
		long duration = endTime - startTime;
		System.out.println("----------");
		System.out.println("Duration (ms): " + (duration/1000000));
		System.out.println("----------");
		System.out.print("TLB Lookups: " + tlb.getChecks());
		System.out.print(" | TLB Misses: " + tlb.getMisses());
		System.out.println(" | TLB Miss Rate: " + tlb.getMissRate());
		System.out.println("----------");
		System.out.print("Page Table Lookups: " + pt.getChecks());
		System.out.print(" | Page Table Faults: " + pt.getFaults());
		System.out.println(" | Page Table Fault Rate: " + pt.getFaultRate());
	}
	
	/**
	 * Main method.
	 * @param args
	 */
	public static void main(String[] args){
		AddressTranslator at = new AddressTranslator();
	}
}
