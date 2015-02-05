package vmm.application;

import java.awt.Desktop;
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
 * @version 1.2
 * @since 1.1
 *
 */
public class VMM {
	
	/**
	 * The location of the input file
	 */
	private String inputFile;
	
	/**
	 * The translator used within the system
	 */
	private Translator translator;
	
	/**
	 * LNR reads in the input
	 */
	private LineNumberReader lnr;
	
	/**
	 * Output writing
	 */
	private BufferedWriter bw;
	
	/**
	 * File used to storing output
	 */
	private File file;
	
	/**
	 * Constructs a new virtual memory manager using input from the given file.
	 * 
	 * @param filename - The input file to be read from
	 */
	public VMM(String filename){
		translator = new Translator();
		inputFile = filename;
	}
	
	/**
	 * Starts the process.
	 */
	public void run(){
		translator.startInfo();
		openFiles(inputFile);
		start();
		translator.statistics();
	}
	
	/**
	 * Opens up the files required for reading and writing
	 * 
	 * @param filename - the location of the input file
	 */
	private void openFiles(String filename){
		try {
			FileReader fr = new FileReader(filename);
			// LNR is used since I'm too lazy to keep track myself.
			lnr = new LineNumberReader(fr);
			
			// The output folder includes a timestamp
			SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			String output = "output/" + sdf.format(new Date()) + ".txt";
			
			// Ensuring that the output folder exists
			file = new File("output");
			if(!file.exists())
				file.mkdirs();
						
			file = new File(output);
						
			FileWriter fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			
			System.out.println("Reading input from '" + filename + "'...");
			System.out.println("Writing results to '" + output + "'...");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads the given input file and continuously translates each line from
	 * virtual to physical addresses and writes to the input file
	 * 
	 * @param filename - the location of the input file
	 */
	private void start(){
		String line = "";
		int lineNum = 0;
		try {
			Scanner sc = null;
			line = lnr.readLine();

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
			
			Desktop.getDesktop().open(file);
			
			bw.close();
			lnr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InputMismatchException e) {
			System.out.println("ERROR: Input mismatch at line " + lineNum + " '"
					+ line + "'.");
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
