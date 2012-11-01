/*
 * MainMenu.java
 *
 * Created on March 5, 2003, 7:17 PM
 */

import javax.microedition.lcdui.* ;

/**
 *
 * @author  Saurabh Jain
 * @version 1.0
 */
public class MainMenu extends List implements CommandListener
{
    // Difining the instance variable for this class
    private NCMIDlet parent ;
    private List list ;
    private Command exit ;
    private Game game ;
    private Settings settings ;
    private HighScore highScore ;
    private About instructions ;
    
    private boolean isChecked ;
    
    public MainMenu( String str, int p1, NCMIDlet parent )
    {
        super ( str,p1 ) ;
        init( ) ;
        this.parent = parent ;
        this.game = new Game( parent,this ) ;
        this.settings = new Settings("Settings", List.IMPLICIT,this.parent,this) ;
        this.highScore = new HighScore(this.parent,this) ;
        this.instructions = new About(this.parent,this) ;
    }
    
    public void init(  )
    {
        this.append( "Game", null ) ;
        this.append( "Settings", null ) ;
        this.append( "Scores", null ) ;
        this.append("Instructions",null) ;

        this.exit = new Command("Exit",Command.EXIT,1) ;
        this.addCommand(this.exit) ;
        this.setCommandListener( this ) ;
    }
    
    public void commandAction( Command com, Displayable disp )
    {
        if ( com == List.SELECT_COMMAND ) 
        {
            // for Game
            if( this.getSelectedIndex() == 0 )
            {
                parent.setDisplayable( this.game ) ;
            }
            // for settings
            else if( this.getSelectedIndex() == 1 )
            {
                parent.setDisplayable( this.settings ) ;
            }
            // for HighScore
            else if( this.getSelectedIndex() == 2 )
            {
                this.highScore.displayHS() ;
            }
            // For instructions
            else if(this.getSelectedIndex() == 3)
            {
                this.parent.setDisplayable(this.instructions) ;
            }
        }
        else if(com == this.exit)
        {
            parent.notifyDestroyed() ;
        }
    }
}
