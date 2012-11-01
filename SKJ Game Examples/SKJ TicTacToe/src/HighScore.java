/*
 * HighScore.java
 *
 * Created on March 5, 2003, 7:16 PM
 */

import javax.microedition.midlet.* ;
import javax.microedition.lcdui. * ;

/**
 *
 * @author  Saurabh Jain
 * @version 1.0
 */
public class HighScore implements CommandListener
{
    private NCMIDlet parent = null ;
    private MainMenu menu = null ;
    private Form form ;
    private Command back ;
    
    public HighScore( NCMIDlet parent, MainMenu menu )
    {
        this.parent = parent ;
        this.menu = menu ;
    }
        
    private void refresh()
    {   
        this.form = new Form("Scores") ;
        this.back = new Command("Back", Command.BACK, 1) ;
        
        // Adding the commands
        this.form.addCommand( back ) ;
        
        // Setting the command listener
        this.form.setCommandListener(this) ;
        
        int pos = 1 ;
        
        // Printing the high score
        for(int i = 0; i < 5; i++)
        {   
            this.form.append(String.valueOf(pos) + ". " + this.parent.getScoreAt(i) + "\n") ;
            pos++ ;
        }
    }
    
    public void commandAction(Command p0, Displayable p1)
    {
        if(p0 == this.back)
        {
            this.parent.setDisplayable(this.menu) ;
        }
    }
    
    public void displayHS()
    {
        this.refresh() ;
        this.parent.setDisplayable(this.form) ;
    }
}