package vmm.application;

/**
 * Main method for the application is stored here.
 * 
 * @author Aidan O'Grady
 * @version 1.3
 * @since 1.3
 *
 */
public class Driver {
	/**
	 * Main method
	 * @param args - arguments for process, input file must be contained here.
	 */
	public static void main(String[] args){
		if(args.length != 1){
			System.out.println("Usage: ACE3 <file>");
			System.exit(0);
		}
		System.out.println("CS313 ACE 3 version 1.3");
		System.out.println("Author: Aidan O'Grady, Reg no. 201518150");
		System.out.println("----------");
		VMM manager = new VMM(args[0]);
		manager.run();
	}
}
