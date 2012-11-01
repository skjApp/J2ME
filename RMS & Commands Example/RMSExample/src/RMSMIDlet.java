
import javax.microedition.midlet.* ;
import javax.microedition.lcdui.* ;
import java.io.* ;
import javax.microedition.rms.* ;

/**
 *
 * @author  Saurabh Jain
 * @version 1.0
 * Practice Example : 6
 *
 * An example MIDlet which gives an option to either save or retrieve a simple text string
 * Please save the data before retrieving it.
 * This example also introduces the commands to you.
 */

public class RMSMIDlet extends MIDlet implements CommandListener
{
    private boolean init ;
    private Command save;		// The save command
    private Command retrieve;	// The retrieve command
    
    private Display display;    // The display for this MIDlet
    private TextBox t ;
    
    private RecordStore recordStore ;
    
	public RMSMIDlet()
    {
        // NOTE : Heavy initialisations should be avoided in the constructor of the MIDlet.
		// The MIDlet is in the paused state when the constructor is called.
		// It does not have access to the resources it needs at this stage.
		// Therefore all heavy initialisations should be done in the way shown below in 
		// the startApp method.
    }

    /**
     * Start up the RMSMIDlet by creating the TextBox and associating
     * the commands and listener.
     */
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
            display = Display.getDisplay(this) ;
            
			// Initialising the commands
			this.save = new Command("Save",Command.OK,1) ;
            this.retrieve = new Command("Retrieve",Command.EXIT,1) ;
            
			// Initialising the text box
            t = new TextBox("RMSMIDlet", "Test string", 256, 0);

			// Adding the commands to the TextBox
            t.addCommand(this.save);
            t.addCommand(this.retrieve);
            
			// Setting the command listener for the TextBox
			t.setCommandListener(this);
            
            init = true ;
        }

        display.setCurrent(t);
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
    
    /*
     * Respond to commands
     */
    public void commandAction(Command c, Displayable s)
    {
        if(c == this.save)              // On pressing save command
        {
            this.save(t.getString()) ;
        }
        else if (c == this.retrieve)    // On pressing retrieve command
        {
            t.setString(this.retrieve()) ;
        }
    }
    
    // Method for saving a text string
    private void save(String text)
    {
		// Declaring the byte array
        byte[] data;

		try
        {
            // Opening the recordStore
            // If the RecordStore exists opens it else creates one
            this.recordStore = RecordStore.openRecordStore("database", true) ;
            
            // Converts the String text into byte array
            data = text.getBytes() ;
            
            // Checking to see whether the RecordStore is not empty
            if(this.recordStore.getNumRecords() == 0)
            {
                // Adding the record in the RecordStore
                this.recordStore.addRecord(data,0,data.length) ;
            }
            else
            {
                // Setting the record in the RecordStore at position 1
                this.recordStore.setRecord(1,data,0,data.length) ;
            }
		} 
        catch (Exception e)
        {
			e.printStackTrace() ;
		}
    }
    
    // Method for retrieving a text string
    private String retrieve()
    {
        String s = "" ;
        
        try
        {
            // Opening the recordStore
            // If the RecordStore exists, opens it, else creates one
            this.recordStore = RecordStore.openRecordStore("database", true) ;
           
            // Checking to see whether the RecordStore is not empty
            if(this.recordStore.getNumRecords() == 0 ) 
            {
                s = new String("RecordStore Empty") ;
            }
            else
            {
                s = new String(this.recordStore.getRecord(1)) ;
            }
       }
       catch(Exception e)
       {
            e.printStackTrace() ;
       }
        
       return s ;
    }
}
