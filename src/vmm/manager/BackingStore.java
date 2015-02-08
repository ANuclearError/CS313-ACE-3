package vmm.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * This class represents the backing storage accessed in the system. When a page
 * fault occurs, data is read from the backing store file to then be passed to
 * memory.
 * 
 * @author Aidan O'Grady
 * @version 1.4
 * @since 1.1
 *
 */
public class BackingStore {
	
	/**
	 * The location of backing storage
	 */
	private String filename;
	
	/**
	 * The backing storage representation
	 */
	private RandomAccessFile backingStore;
	
	/**
	 * Constructs a BackingStore with the given file location
	 * 
	 * @param filename - The file to be used
	 */
	public BackingStore(String filename){
		this.filename = filename;
		openFile();
	}
	
	/**
	 * Opens the file if it is found.
	 */
	private void openFile(){
		try{
			File file = new File(filename);
			backingStore = new RandomAccessFile(file, "r");
		} catch (FileNotFoundException e) {
			System.out.print("ERROR: File: '" + filename + "' not found.");
			System.exit(0);
		}
	}
	
	/**
	 * Reads the file at the given location and returns the byte array found.
	 * 
	 * @param location - The location in the file to be read from
	 * @param chunk - the number of bytes to be read
	 * 
	 * @return the byte array found at this location
	 */
	public byte[] read(int location, int chunk){
		try {
			backingStore.seek(location);
			byte[] data = new byte[chunk];
			backingStore.read(data); // Data is read
			return data;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Returns the file location of the backing store
	 * 
	 * @return file name
	 */
	public String getFilename(){
		return filename;
	}
}
