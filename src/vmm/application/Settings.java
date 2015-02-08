package vmm.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * The settings file is responsible for retrieving the configuration of the
 * virtual memory manager. The use of the Properties class ensures that there is
 * a way to choose the sizes of various objects and choosing the replacement
 * policy of the TLB
 * 
 * @author Aidan O'Grady
 * @version 1.4
 * @since 1.3
 *
 */
public class Settings {
	
	/**
	 * The list of properties to be stored.
	 */
	private Properties properties;
	
	/**
	 * The file to store the properties in.
	 */
	private File file;
	
	/**
	 * Constructs the new settings
	 */
	public Settings(){
		properties = new Properties();
		file = new File("files/settings.properties");
		openFile();
	}
	
	/**
	 * Attempts to load the .properties and its content, if it fails, then
	 * createFile() will create one.
	 */
	private void openFile(){
		try {
			FileInputStream input = new FileInputStream(file);
			properties.load(input);
		} catch (FileNotFoundException e) {
			System.out.println(".propeties file not found, creating default.");
			createFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * If the file being loaded isn't found, then a new one is created with
	 * default values.
	 */
	private void createFile(){
		properties.setProperty("tlb_size", "16");
		properties.setProperty("tlb_replacement", "FIFO");
		properties.setProperty("pt_size", "256");
		properties.setProperty("page_replacement", "FIFO");
		properties.setProperty("memory_size", "256");
		properties.setProperty("frame_size", "256");
		properties.setProperty("backing_store", "files/BACKING_STORE");
		try{
			FileOutputStream output = new FileOutputStream(file);
			properties.store(output, "This file contains all values for all"
					+ " variables relating to the configuration of the "
					+ " memory sytem. They're not quite sorted, but it'll "
					+ "do.");
		} catch (IOException io) {
			io.printStackTrace();
		}
	}
	
	/**
	 * Returns the tlb_size property's integer value.
	 * 
	 * @return tlb_size
	 */
	public int getTLBSize(){
		String size = properties.getProperty("tlb_size");
		return Integer.parseInt(size);
	}
	
	/**
	 * Returns the requested tlb_replacement value
	 * 
	 * @return tlb_replacement
	 */
	public String getTLBReplacement(){
		String policy = properties.getProperty("tlb_replacement");
		return policy;
	}
	
	/**
	 * Returns the pages property's integer value.
	 * 
	 * @return pages
	 */
	public int getPages(){
		String size = properties.getProperty("pages");
		return Integer.parseInt(size);
	}
	
	/**
	 * Returns the requested page_replacement value
	 * 
	 * @return page_replacement
	 */
	public String getPageReplacement(){
		String policy = properties.getProperty("page_replacement");
		return policy;
	}
	
	/**
	 * Returns the frame_size property's integer value.
	 * 
	 * @return frame_size
	 */
	public int getFrameSize(){
		String size = properties.getProperty("frame_size");
		return Integer.parseInt(size);
	}
	
	/**
	 * Return the backing_store property's value
	 * 
	 * @return backing_store
	 */
	public String getBackingStore(){
		String backingStore = properties.getProperty("backing_store");
		return backingStore;
	}
	
}
