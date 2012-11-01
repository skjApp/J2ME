import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 *
 * @author SKJ Technologies
 * @version 1.0.0
 */
public class Holi extends javax.microedition.midlet.MIDlet
{
	private Display display ;
    private Infra infra ;

	private boolean init ;
    
	/************************************/
    // Begin MIDlet State Methods
    /************************************/
    public void startApp()
    {
		if(init == false)
		{
			display = Display.getDisplay(this) ;
			
			this.infra = new Infra(this) ;

			init = true ;
		}
		else
		{
			this.infra.getGame().setPause(false) ;
		}
    }
    
    public void pauseApp()
    {
        this.infra.getGame().setPause(true) ;
    }
    
    public void destroyApp(boolean unconditional)
    {
        
    }

	/************************************/
    // End MIDlet State Methods
    /************************************/

	/************************************/
    // Begin Display Methods
    /************************************/
	
	public Display getDisplay()
	{
		return display ;
	}

	public void setDisplayable(Displayable d)
	{
		display.setCurrent(d) ;
	}

	/************************************/
    // End MIDlet Display Methods
    /************************************/
}
