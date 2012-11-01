
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 *
 *	@author  Saurabh Jain
 *	@version 1.0
 *	Practice Example : 3
 *  
 *	The aim of this example is to make you practice the UI element TextBox.
 *  Please experiment with the example to reep full benefits.
 */
public class TextBoxMIDlet extends javax.microedition.midlet.MIDlet 
{
    private Display display ;
    private TextBox tb ;
	private boolean init ;
    
    public TextBoxMIDlet()
    {
        // NOTE : Heavy initialisations should be avoided in the constructor of the MIDlet.
		// The MIDlet is in the paused state when the constructor is called.
		// It does not have access to the resources it needs at this stage.
		// Therefore all heavy initialisations should be done in the way shown below in 
		// the startApp method.
    }
    public void startApp() 
    {
		// NOTE : The startApp method is called whenever the Application Management Software(AMS)
		// decides that the MIDlet needs to get activated.
		// During the lifecycle of particular instance of the application this situation may arise
		// many times. Therefore to save un-necessary initialisations every time the startApp method
		// is called by the AMS, we should use a boolean variable to control the number of initialisations to 1.

		if(init == false)
		{
			// This is the first step to using the user interface in MIDP
			// The statement sets the Display object in our display variable
			// This display object is unique to this MIDlet
			this.display = Display.getDisplay(this) ;        
			
			// NOTE : TextBox supports the following kinds of input constraints defined in TextField:
			// ANY
			// CONSTRAINT_MASK
			// EMAILADDR
			// NUMERIC
			// PASSWORD
			// PHONENUMBER
			// URL

			//Initializing a TextBox
			this.tb = new TextBox("Text Box 1","Text",100,TextField.ANY) ;       

			// Displaying the TextBox
			this.display.setCurrent(this.tb) ;

			init = true ;
		}
    }
    
    public void pauseApp() 
    {
        
    }
    
    public void destroyApp(boolean unconditional) 
    {
        
    }
}


