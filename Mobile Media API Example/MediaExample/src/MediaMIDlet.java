
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.media.* ;
import javax.microedition.media.control.* ;
import java.io.* ;

/**
 *
 *	@author  Saurabh Jain
 *	@version 1.0
 *	Practice Example : 12
 *  
 *	The aim of this example is to make you understand the Mobile Media API.
 *  Please experiment with the example to reep full benefits.
 */
public class MediaMIDlet extends javax.microedition.midlet.MIDlet implements CommandListener
{
    private Display display ;
    private Form form ;
	private Command start ;
	private Command stop ;

	private Player player ;

	private boolean init ;
    
    public MediaMIDlet()
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

			//Initializing Form
			form = new Form("Media Example") ;
			form.append("Press 'Start' for starting media file. Press 'Stop' for stopping media file.") ;

			// Initialising the commands
			start = new Command("Start",Command.OK,1) ;
			stop = new Command("Stop",Command.CANCEL,1) ;

			// Setting the command listener in form
			form.addCommand(start) ;
			form.addCommand(stop) ;
			form.setCommandListener(this) ;

			// Audio Code
			// Enable this code and disable video code to run audio
			try
			{
				InputStream is = getClass().getResourceAsStream("/j0082185.mid") ;
				player = Manager.createPlayer(is,"audio/midi") ;
				player.prefetch() ;
			}
			catch (Exception e)
			{
				e.printStackTrace() ;
			}

			// Video Code
			// Enable this code and disable audio code to run video
			/*try
			{

				InputStream is = getClass().getResourceAsStream("/8pounder.mpg") ;
				player = Manager.createPlayer(is,"video/mpeg") ;
				player.realize() ;
				VideoControl vc;

				if ((vc = (VideoControl)player.getControl("VideoControl")) != null)
				{
					form.append((Item)vc.initDisplayMode(vc.USE_GUI_PRIMITIVE, null)) ;
					player.prefetch() ;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace() ;
			}*/


			// Displaying the Form
			this.display.setCurrent(form) ;

			init = true ;
		}
    }
    
    public void pauseApp() 
    {
        
    }
    
    public void destroyApp(boolean unconditional) 
    {
        
    }

	public void commandAction(Command c, Displayable d)
	{
		if (c == start)
		{
			try
			{
				player.start() ;
			}
			catch (Exception e)
			{
				e.printStackTrace() ;
			}
		}
		else if (c == stop)
		{
			try
			{
				player.stop() ;
			}
			catch (Exception e)
			{
				e.printStackTrace() ;
			}
		}
	}
}


