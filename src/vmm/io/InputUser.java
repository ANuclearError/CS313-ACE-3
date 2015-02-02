package vmm.io;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The Input class handles any user input that is required in a system. It
 * includes many classes that allow for the retrieval of specific types, such
 * as strings, ints and booleans. It also allows for specialist input such as
 * 'Yes' or 'No' responses, which will come in handy later on.
 * 
 * @author Aidan O'Grady
 * @version 1.0
 * @since 1.0
 *
 */
public class InputUser {

	private Scanner scanner;
	
	public InputUser(){
		scanner = new Scanner(System.in);
	}
	
	public String getString(){
		String input = "";
		while(input.equals("")){
			input = scanner.nextLine();
		}
		return input;
	}
	
	public int getInt(){
		try{
			int input = scanner.nextInt();
			return input;
		} catch(InputMismatchException e){
			System.out.println("Error: Int not found, please try again.");
			scanner.next();
		}
		return getInt();
	}
	
	public boolean getBoolean(){
		try{
			boolean input = scanner.nextBoolean();
			return input;
		} catch(InputMismatchException e){
			System.out.println("Error: Boolean not found, please try again.");
			scanner.next();
		}
		return getBoolean();
	}
	
	public boolean getYesNo(){
		try{
			String input = scanner.nextLine().toLowerCase();
			if(input.equals("yes") || input.equals("y") || input.equals("aye"))
				return true;
			if(input.equals("no") || input.equals("n") || input.equals("naw"))
				return false;
		} catch(InputMismatchException e){
			System.out.println("Error: Yes/No not found, please try again.");
			scanner.next();
		}
		return getYesNo();
	}
	
	public void printBigLine(int n){
		printLine(n, '=');
	}
	
	public void printSmallLine(int n){
		printLine(n, '-');
	}
	
	private void printLine(int n, char c){
		for(int i=0; i<n; i++)
			System.out.print(c);
		System.out.println();
	}
	
	public void close(){
		scanner.close();
	}
}
