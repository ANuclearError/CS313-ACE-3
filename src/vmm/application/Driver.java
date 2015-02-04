package vmm.application;

public class Driver {
	public static void main(String[] args){
		VMM manager = new VMM("input/InputFile.txt");
		manager.run();
	}
}
