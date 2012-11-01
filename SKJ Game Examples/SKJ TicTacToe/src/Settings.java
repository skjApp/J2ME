/*
 * Settings.java
 *
 * Created on March 5, 2003, 7:17 PM
 */
import javax.microedition.lcdui.*;

/**
 *
 * @author Saurabh Jain
 * @version 1.0
 */
public class Settings extends List implements CommandListener
{
    private NCMIDlet parent = null ;
    private MainMenu menu = null ;
    
    private Command back = new Command("Back", Command.BACK, 2) ;
    private Alert alert ;
    
    // Declaring the constructor( )
    public Settings(String p0, int p1,NCMIDlet parent, MainMenu menu)
    {
        super(p0,p1) ;
        
        this.parent = parent ;
        this.menu = menu ;
        
        init() ;
    }
    
    private void init()
    {
        this.addCommand( back ) ;
        this.setCommandListener(this) ;

        // appending the game options
        this.append("Animated", null) ;
        this.append("Non-Animated", null) ;

        this.alert = new Alert("Settings","The changed settings will take effect from the next game.",null, AlertType.INFO ) ;
    }
    
    public void commandAction(Command p0, Displayable p1)
    {
        if (p0 == back)
        {
            this.parent.setDisplayable(menu) ;
        }
        else
        {
            List lis = (List)p1 ;
            int idx = lis.getSelectedIndex() ;

            switch (idx)
            {
                case 0:
				{
                    //Animated
                    this.parent.setAnimate(true) ;
                    this.parent.getDisplay().setCurrent(this.alert,menu) ;
                    break ;
				}
                case 1:
				{
					//Non - Animated
                    this.parent.setAnimate(false) ;
                    this.parent.getDisplay().setCurrent(this.alert,menu) ;
                    break ;
				}
            }
        }
    }
}
