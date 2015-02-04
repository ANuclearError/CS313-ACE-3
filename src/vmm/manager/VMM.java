package vmm.manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The VMM wraps everything together in a nice wee package. The VMM is in charge
 * of reading virtual addresses from a file, using the translator to translate
 * to a physical address, and writing the results of that translation to an 
 * input file.
 * 
 * @author wlb12153
 *
 */
public class VMM {
	
	/**
	 * The translator used within the system
	 */
	private Translator translator;
	
	/**
	 * Constructs a new virtual memory manager using input from the given file.
	 * 
	 * @param filename - The input file to be read from
	 */
	public VMM(String filename){
		translator = new Translator();
		translator.startInfo();
		readInput(filename);
		translator.statistics();
	}
	
	/**
	 * Reads the given input file and continuously translates each line from
	 * virtual to physical addresses and writes to the input file
	 * 
	 * @param filename - the location of the input file
	 */
	private void readInput(String filename){
		String line = "";
		int lineNum = 0;
		try {
			FileReader fr = new FileReader(filename);
			// LNR is used since I'm too lazy to keep track myself.
			LineNumberReader lnr = new LineNumberReader(fr);
			
			// The output folder includes a timestamp
			SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			String output = "output/" + sdf.format(new Date()) + ".txt";
			
			// Ensuring that the output folder exists
			File file = new File("output");
			file.mkdirs();
			
			
			FileWriter fw = new FileWriter(output);
			BufferedWriter bw = new BufferedWriter(fw);
			
			Scanner sc = null;
			line = lnr.readLine();
			System.out.println("Reading input from '" + filename + "'...");
			System.out.println("Writing results to '" + output + "'...");

			// Go through each line.
			while(line != null){
				lineNum = lnr.getLineNumber();
				sc = new Scanner(line);
				int num = sc.nextInt();

				String result = translator.translate(num);
				bw.write(result);
				bw.newLine();
				
				line = lnr.readLine();
			}
			bw.close();
			lnr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InputMismatchException e) {
			System.out.print("ERROR: Input mismatch at line " + lineNum + " '" + 
					line + "'.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		VMM vmm = new VMM("input/InputFile.txt");
	}
}
