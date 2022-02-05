package hr.fer.oprpp1.hw08.jnotepadpp.utilities;

import java.io.IOException;
import java.io.InputStream;
import javax.swing.ImageIcon;

/**
 * Utility class for loading images
 * @author renat
 *
 */
public class IconLoader {
	
	/**
	 * Loads an image.
	 * @param imageName name of an image we want to load
	 * @return loaded image
	 */
	public static ImageIcon loadImageIcon( String imageName) {
		try( InputStream is = IconLoader.class.getResourceAsStream( "../icons/"+ imageName+ ".png")){
			if(is==null) throw new NullPointerException("");
			byte[] bytes = is.readAllBytes();
			return new ImageIcon( bytes);			
		}catch( IOException e) {
			e.printStackTrace();
		};
		return null;
	}
	
}
