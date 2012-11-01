/*
 * About.java
 *
 * Created on March 5, 2003, 7:18 PM
 */

import javax.microedition.lcdui. * ;

/**
 *
 * @author Saurabh Jain
 * @version 1.0
 */
public class About extends Form implements CommandListener
{
    // Defining the instance variable for this class
   private Command back ;
   private NCMIDlet parent ;
   private MainMenu menu ;
    
    // Declaring the constructor of Instruction form
    public About(NCMIDlet parent, MainMenu menu )
    {
       super("Instructions") ;
       
        this.parent = parent ;
        this.menu = menu ;
        this.back = new Command("Back",Command.BACK,1) ;

        // Adding the contents
        this.append("The aim of the game is to place your symbol on 3 simultaneous places. The player with the first chance has the 'Cross' symbol. The first chance keeps on rotating between the 2 players.\n\nTo move Left press the LEFT Key.\nTo move Right press the RIGHT Key. To move Up press the UP Key. To move Down press the DOWN Key. To place the symbol press the FIRE Key.\n\nTo change the animation settings go to 'Settings' menu and choose the desired setting. To view last 5 scores go to 'Scores' menu.") ;

        this.addCommand(this.back) ;
        this.setCommandListener(this) ;
    }
          
    // Declaring the commandAction method for this class
    public void commandAction( Command c, Displayable d )
    {
        if(c == this.back)
        {
            this.parent.setDisplayable(this.menu) ;
        }
    }
}
