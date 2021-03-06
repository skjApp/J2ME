
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 *
 *	@author  Saurabh Jain
 *	@version 1.0
 *	Practice Example : 2
 *  
 *	The aim of this example is to make you practice the UI element Form.
 *  You will also be able to practice all the sub-classes of Item in this example.
 *  Please experiment with the example to reep full benefits.
 */
public class FormMIDlet extends javax.microedition.midlet.MIDlet 
{
    private Display display ;
    private Form form ;
	private boolean init ;
    
    public FormMIDlet()
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
			this.form = new Form("FORM") ;       
			
			// This line appends a string to the form
			this.form.append("Example 2") ;

			// The lines below append a new ChoiceGroup to the form
			// NOTE : Choice interface defines three kinds of choice UI elements.
			// IMPLICIT		:	Can only be used with List. Cannot be used with ChoiceGroup.
			// EXCLUSIVE	:	Can be used with List and ChoiceGroup.
			// MULTIPLE		:	Can be used with List and ChoiceGroup.

			ChoiceGroup cg = new ChoiceGroup("Choice Group 1",Choice.EXCLUSIVE) ;
			cg.append("Choice 1",null) ;		// Appends string("Choice 1") and image part (null) to the ChoiceGroup.
			cg.append("Choice 2",null) ;		// Appends string("Choice 2") and image part (null) to the ChoiceGroup.
			this.form.append(cg) ;

			// The lines below append a new DateField to the form
			// NOTE : DateField defines three kinds of input modes.
			// DATE			:	Input mode for date(day,month,year) information.
			// DATE_TIME	:	Input mode for date(day,month,year) and time(hours,minutes) information.
			// TIME			:	Input mode for time(hours,minutes) information.

			DateField df = new DateField("Date Field 1",DateField.DATE_TIME) ;
			this.form.append(df) ;

			// The lines below append a new Gauge to the form
			// NOTE : There are two kinds of Guage's, namely:
			// Interactive			:	End user can change it's value
			// Non-Interactive		:	End user cannot change it's value
			
			Gauge g = new Gauge("Gauge 1", true,10,0) ;
			this.form.append(g) ;

			// The lines below append a new ImageItem to the form

			try
			{
				Image img = Image.createImage("/SKJ.png") ;			// Creates a new immutable image	

				// NOTE : ImageItem has the following kinds of layouts:
				// LAYOUT_CENTRE
				// LAYOUT_DEFAULT
				// LAYOUT_LEFT
				// LAYOUT_NEWLINE_AFTER
				// LAYOUT_NEWLINE_BEFORE
				// LAYOUT_CENTRE_RIGHT

				ImageItem imgItem = new ImageItem("SKJ",img,ImageItem.LAYOUT_DEFAULT,"SKJ") ;
				this.form.append(imgItem) ;
			}
			catch (Exception e)
			{
				// This line is useful for debugging
				e.printStackTrace() ;
			}

			// The lines below append a new StringItem to the form
			StringItem si = new StringItem("String Item 1","Text") ;
			this.form.append(si) ;

			// The lines below append a new TextField to the form
			// NOTE : TextField supports the following kinds of input constraints:
			// ANY
			// CONSTRAINT_MASK
			// EMAILADDR
			// NUMERIC
			// PASSWORD
			// PHONENUMBER
			// URL

			TextField tf = new TextField("Text Field 1","Text",100,TextField.ANY) ;
			this.form.append(tf) ;

			// Displaying the Form
			this.display.setCurrent(this.form) ;

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


