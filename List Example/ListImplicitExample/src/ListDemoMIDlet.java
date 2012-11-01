/*
 * ListDemoMIDlet.java
 */

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 *
 *	@author  Saurabh Jain
 *	@version 1.0
 *	Practice Example : 5
 *  
 *	The aim of this example is to make you practice the UI element List.
 *  Please experiment with the example to reep full benefits.
 */

public class ListDemoMIDlet extends javax.microedition.midlet.MIDlet implements CommandListener
{
    private Display display ;
    private List list ;
	private boolean init ;
    
	public ListDemoMIDlet()
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

			// Initialising List
			this.list = new List(" Screen Title",List.IMPLICIT ) ;
			this.list.append("Item 1", null) ;
			this.list.append("Item 2", null) ;
			this.list.append("Item 3", null) ;
			this.list.append("Item 4", null) ;
			this.list.append("Item 5", null) ;

			// Setting this midlet as CommandListener to receive command related events.
			this.list.setCommandListener( this ) ;

			// Displaying the List
			this.display.setCurrent(this.list) ;
		}
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) 
    {
    }
    
    public void commandAction( Command comm, Displayable disp )
    {
        if (disp == this.list )
        {
            if ( comm == List.SELECT_COMMAND )
            {
				// Switching according to the item selected
				switch(this.list.getSelectedIndex())
				{
					case 0 :
					{
						// Sets the title of the list
						this.list.setTitle("Item 1 Clicked") ;
						break ;
					}

					case 1 :
					{
						// Sets the title of the list
						this.list.setTitle("Item 2 Clicked") ;	
						break ;
					}

					case 2 :
					{
						// Sets the title of the list
						this.list.setTitle("Item 3 Clicked") ;	
						break ;
					}

					case 3 :
					{
						// Sets the title of the list
						this.list.setTitle("Item 4 Clicked") ;	
						break ;
					}

					case 4 :
					{
						// Sets the title of the list
						this.list.setTitle("Item 5 Clicked") ;	
						break ;
					}
				}
            }
        }
    }
}
