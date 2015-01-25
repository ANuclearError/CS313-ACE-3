package vmm.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AddressTranslator {
	private PageTable pt;
	private PhysicalMemory pm;
	
	private String inputFileName;
	private String backingFileName;
	
	private int pageFaults;
	private int count;
	
	public AddressTranslator(){
		pt = new PageTable(256);
		pm = new PhysicalMemory(256);
		
		backingFileName = "files/BACKING_STORE";
		inputFileName = "input/InputFile.txt";
		readInput();
	}
	
	private void readInput(){
		String line = "";
		int lineNum = 0;
		try {
			FileReader fr = new FileReader(inputFileName);
			LineNumberReader lnr = new LineNumberReader (fr);
			Scanner sc = null;
			line = lnr.readLine();
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
	
	private void translate(int address){
		LogicalAddress la = new LogicalAddress(address);
		
		int pageNum = la.getPageNumber();
		int frameNum = pt.lookup(pageNum);
		if(frameNum < 0){
			pageFaults++;
			pageFault(pageNum);
		}
		
	}
	
	private void pageFault(int pageNum){
			try {
				File file = new File(backingFileName);
				RandomAccessFile backingStore = new RandomAccessFile(file, "r");
				
				backingStore.seek(pageNum * 256);
				byte[] data = new byte[256];
				backingStore.read(data);
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
