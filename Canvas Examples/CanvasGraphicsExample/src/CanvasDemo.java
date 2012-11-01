import javax.microedition.lcdui.* ;
import java.io.IOException ;
       
/**
 * @author  Saurabh Jain
 * @version 1.0
 * Practice Example : 10
 *
 * An example which shows how to use the Canvas and the Graphics class
*/

public class CanvasDemo extends Canvas implements CommandListener
{
	// These two variables help a lot in porting the application to different screen sizes
    private int maxX ;		// Canvas Width
    private int maxY ;		// Canvas Height

    private Command back ;
    private CanvasMIDlet parent ;

    private List list ;
    private Image image ;
    private char[] ch ;
    
    public CanvasDemo(CanvasMIDlet p)
    {
        super() ;

        this.maxX = super.getWidth() ;
        this.maxY = super.getHeight() ;
        
		this.parent = p ;
        this.initCommand() ;
        this.setList() ;
        this.createImage() ;
        this.initCharArray() ;
    }
    
    public void commandAction(Command com, Displayable disp )
    {
        if (disp == this)
        {
            if (com == this.back)
            {
                this.parent.setDisplayable(this.list) ;
            }
        }
    }
    
    private void initCharArray()
    {
        this.ch = new char[12] ;
        this.ch[0] = 's' ;
        this.ch[1] = 'k' ;
        this.ch[2] = 'j' ;
        this.ch[3] = 'w' ;
        this.ch[4] = 'o' ;
        this.ch[5] = 'r' ;
        this.ch[6] = 'l' ;
        this.ch[7] = 'd' ;
        this.ch[8] = '.' ;
        this.ch[9] = 'c' ;
        this.ch[10] = 'o' ;
        this.ch[11] = 'm' ;
    }
    
    private void createImage() 
    {
        try
        {
            this.image = javax.microedition.lcdui.Image.createImage("/icon.png") ;
        }
        catch( IOException ioe )
        {
     		ioe.printStackTrace() ;
        }
    }
    
    private void setList()
    {
        this.list = this.parent.getMainList() ;
    }
    
    private void initCommand()
    {
        this.back = new Command( "Back",Command.BACK,2 ) ;
        this.addCommand( this.back ) ;
        this.setCommandListener( this ) ;
    }

    protected void paint( Graphics g )
    {
		// Clearing the screen
		g.setColor( 255,255,255 ) ;
        g.fillRect( 0,0,getWidth(),getHeight()) ;
		
		// Setting the blue color
		g.setColor( 0,0,255) ;
             
        switch(this.parent.getInt())
        {
            case 0:		// Drawing an arc
            {
				g.drawArc(0,0,30,30,90,180 ) ;
                break ;
            }
            case 1:		// Drawing a rectangle
            {
				g.drawRect(0,0,40,40 ) ;
                break ;
            }
            case 2:		// Drawing a round edged rectangle
            {
				g.drawRoundRect(0,0,30,30,10,10 ) ; 
               break ;
            }
            case 3:		// Drawing a line
            {
                g.drawLine(10,10,this.maxX,this.maxY ) ;
                break ;
            }
            case 4:		// Drawing a filled arc
            {
                g.fillArc( 0,0,40,40,45,180 ) ;
                break ;
            }
            case 5:		// Drawing a filled rectangle
            {
                g.fillRect( 0,0,30,30 ) ;
                break ;
            }
            case 6:		// Drawing a filled round edged rectangle
            {
                g.fillRoundRect( 0,0,30,30,10,10 ) ;
                break ;
            }
            case 7:		// Drawing an image
            {
                g.drawImage( this.image,10,10,Graphics.TOP | Graphics.LEFT ) ;
                break ;
            }
            case 8:		// Drawing a string
            {
                g.drawString("Drawing Example",10,10,Graphics.TOP | Graphics.LEFT) ;
                break ;
            }
            case 9:		// Drawing a character
            {
                char ch = 'a' ;
                g.drawChar(ch, 10,10,Graphics.TOP | Graphics.LEFT) ;
                
                break ;
            }
            case 10:	// Drawing characters
            {
                g.drawChars( ch,0,ch.length,10,10,Graphics.TOP | Graphics.LEFT) ;
                break ;
            }
            case 11:	// Drawing a substring
            {
                String subStr = "Welcome to SKJWORLD.COM" ;

				// Do experiment yourself with the substring
                g.drawSubstring(subStr,0,subStr.length(),10,10,Graphics.TOP | Graphics.LEFT) ;
                break ;
            }            
        }
    }
}
