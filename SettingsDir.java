package classAccessory;
import java.io.File;

//Crea un file impostazioni (nascosto su Unix) nella cartella documenti dell'utente.
//Questo previene problemi che potrebbero essere causati se il file residesse nel jar 
//(uso simmultano, cambiamento di preferenze, accessi in scrittura etcc...)
//Dovrei mettere qui anche un po tutto in effetti.... :|

public class SettingsDir {
	public static File getSettingsDirectory() {
	    String userHome = System.getProperty("user.home");
	    if(userHome == null) {
	        throw new IllegalStateException("user.home==null");
	    }
	    File home = new File(userHome);
	    File settingsDirectory = new File(home, ".HarpoSaves");
	    if(!settingsDirectory.exists()) {
	        if(!settingsDirectory.mkdir()) {
	            throw new IllegalStateException(settingsDirectory.toString());
	        }
	    }
	    settingsDirectory.canRead();
	    settingsDirectory.canWrite();
	    System.out.println("Directory usata: "+settingsDirectory.getAbsolutePath());
	    return settingsDirectory;
	}
}
