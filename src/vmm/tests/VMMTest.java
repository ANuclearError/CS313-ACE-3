package vmm.tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Test;

import vmm.application.Translator;

/**
 * This test compares the results of putting InputFile.txt to Correct.txt.
 * Due to a bit of beign rushed, it's not quite thorough, but it works.
 * 
 * @author Aidan O'Grady
 *
 */
public class VMMTest {
	
	private static Translator translator;
	private static LineNumberReader lnr;
	private static LineNumberReader lnr_correct;
	private final static String INPUT = "input\\InputFile.txt";
	private final static String CORRECT = "input\\Correct.txt";

	@BeforeClass
	public static void setUp() {
		translator = new Translator();
		try {
			FileReader fr = new FileReader(INPUT);
			// LNR is used since I'm too lazy to keep track myself.
			lnr = new LineNumberReader(fr);
			
			FileReader fr_correct = new FileReader(CORRECT);
			lnr_correct = new LineNumberReader(fr_correct);
			
			System.out.println("Reading input from '" + INPUT + "'...");
			System.out.println("Comparing results to '" + CORRECT + "'...");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test(){
		String line = "";
		String expected = "";
		int lineNum = 0;
		try {
			Scanner sc = null;
			line = lnr.readLine();
			expected = lnr_correct.readLine();

			// Go through each line.
			while(line != null){
				lineNum = lnr.getLineNumber();
				
				sc = new Scanner(line);
				int num = sc.nextInt();
				String result = translator.translate(num);
				assertEquals(result, expected);
				line = lnr.readLine();
				expected = lnr_correct.readLine();
			}
			
			lnr.close();
			lnr_correct.close();
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
