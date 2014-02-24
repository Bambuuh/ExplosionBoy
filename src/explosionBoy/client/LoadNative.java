package explosionBoy.client;

import java.io.File;

public class LoadNative {
	
	private String os = System.getProperty("os.name").toLowerCase();
	//Loads the natives needed to run the program based on the operating system of the computer.
	public void loadNatives(){
		 if (isWindows()) {
				System.out.println("This is Windows");
				System.setProperty("org.lwjgl.librarypath", new File("jars/lwjgl/natives/windows").getAbsolutePath());
			} else if (isMac()) {
				System.out.println("This is Mac");
				System.setProperty("org.lwjgl.librarypath", new File("jars/lwjgl/natives/macosx").getAbsolutePath());
			} else {
				System.out.println("Your OS is not supported!!");
			}
	}
	public boolean isWindows() {
		 
		return (os.indexOf("win") >= 0);
 
	}
 
	public boolean isMac() {
 
		return (os.indexOf("mac") >= 0);
 
	}
	
}
