package Harpo;

import game.NibblesFrame;

import java.awt.Font;
import java.io.IOException;

import javax.swing.UIManager;


public class Nibbles {
	public static void main(String[] args) throws IOException{
		System.out.println("*** Benvenuto in: Nibbles! ***\n");
		setUIFont (new javax.swing.plaf.FontUIResource("Myriad Pro",Font.PLAIN,15));
		String one = null, two = null;
		 try {
			 System.out.println("-\n-");
		        one = args[0];
		        two = args[1];
		    }
		    catch (ArrayIndexOutOfBoundsException e){
		        System.out.println("Nessun argomento ricevuto, trucchi disattivati.");
		    }
		    finally {
				new NibblesFrame(one, two);
		    }
	}
	
	public static void setUIFont (javax.swing.plaf.FontUIResource f){
	    @SuppressWarnings("rawtypes")
		java.util.Enumeration keys = UIManager.getDefaults().keys();
	    while (keys.hasMoreElements()) {
	      Object key = keys.nextElement();
	      Object value = UIManager.get (key);
	      if (value != null && value instanceof javax.swing.plaf.FontUIResource)
	        UIManager.put (key, f);
	      }
	    }


}
