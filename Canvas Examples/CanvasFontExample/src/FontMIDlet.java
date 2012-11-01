import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 * @author  Saurabh Jain
 * @version 1.0
 * Practice Example : 11
 *
 * An example which shows how to use the Canvas and the Font class
*/

public class FontMIDlet extends javax.microedition.midlet.MIDlet 
{
	// The variable for knowing the initialization state
    private boolean init ;

    private Display display ;
    private FontCanvas fCanvas ; 

	public FontMIDlet() 
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

			this.fCanvas = new FontCanvas(this) ;
		}

		this.display.setCurrent(this.fCanvas) ;
    }

    public void pauseApp()
	{

    }
    
    /**
     * Destroy must cleanup everything not handled by the garbage collector.
     * In this case there is nothing to cleanup.
     */
    public void destroyApp(boolean unconditional)
	{

    }
    
    public void setDisplayable( Displayable disp )
    {
        this.display.setCurrent( disp ) ;
    }    
}


