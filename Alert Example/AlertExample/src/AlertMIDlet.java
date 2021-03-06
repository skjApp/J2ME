
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 *
 *	@author  Saurabh Jain
 *	@version 1.0
 *	Practice Example : 1
 *
 *  The aim of this example is to make you practice the UI element Alert.
 *  You will also be able to practice UI element Form in this example.
 *  Please experiment with the example to reep full benefits.
 */
public class AlertMIDlet extends javax.microedition.midlet.MIDlet 
{
    private Display display ;
    private Alert alert ;
    private Form form ;
	private boolean init ;
    
    public AlertMIDlet()
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
			
			//Initializing a form 
			this.form = new Form("FORM" ) ;       
			this.form.append("Example 1") ;
			
			//The line below may be use for initialising an alert of type 'CONFIRMATION'
			this.alert = new Alert("Confirmation Alert","After 5 sec. you will move to the form...",null,AlertType.CONFIRMATION ) ;

			//The line below may be use for initialising an alert of type 'ALARM'
			//this.alert = new Alert("Alarm Alert","After 5 sec. you will move to the form...",null,AlertType.ALARM ) ;

			//The line below may be use for initialising an alert of type 'ERROR'
			//this.alert = new Alert("Error Alert","After 5 sec. you will move to the form...",null,AlertType.ERROR ) ;

			//The line below may be use for initialising an alert of type 'INFO'
			//this.alert = new Alert("Info Alert","After 5 sec. you will move to the form...",null,AlertType.INFO ) ;

			//The line below may be use for initialising an alert of type 'WARNING'
			//this.alert = new Alert("Warning Alert","After 5 sec. you will move to the form...",null,AlertType.WARNING ) ;

			this.alert.setTimeout(5000) ;	// Sets alerts display time to 5 seconds
			
			// The line below converts this Alert into modal alert
			//this.alert.setTimeout(Alert.FOREVER) ;	// Sets alerts display time to 5 seconds

			// Display Alert first and then Form
			// NOTE : When working with alerts use only setCurrent method of this signature type
			// The other signature of setCurrent does not work properly for alerts
			this.display.setCurrent(this.alert,this.form) ;

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


