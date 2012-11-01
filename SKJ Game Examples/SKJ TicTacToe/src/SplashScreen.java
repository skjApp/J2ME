/*
 * SplashScreen.java
 *
 * Created on March 5, 2003, 7:18 PM
 */

import javax.microedition.midlet.*;
import javax.microedition.lcdui. * ;
import java.util.Timer ;
import java.util.TimerTask ;

/**
 *
 * @author Saurabh Jain
 * @version 1.0
 */
public class SplashScreen extends Canvas
{
    // Defining the instance variable 
    private NCMIDlet parent = null ;
    private MainMenu menu = null ;
    private Timer timer = null ;
    
    private Image img ;
    private int maxX ;
    private int maxY ;
    
    public SplashScreen( NCMIDlet parent )
    {
        try
        {
            this.parent = parent ;
            this.menu = new MainMenu( "Game", List.IMPLICIT,parent ) ;
            
            this.maxX = this.getWidth() ;
            this.maxY = this.getHeight() ;
            
            this.img = Image.createImage("/SKJ.png") ;
            
            startTimer() ;
        }
        catch(Exception e)
        {
            e.printStackTrace() ;
        }
    }
     
    protected void keyPressed( int keyCode )
    {
        this.timer.cancel() ;
        parent.setDisplayable( menu ) ;
    }
    
    protected void keyReleased( int keyCode )
    {
        
    }
    
    protected void paint( Graphics g )
    {
        g.setColor(0) ;
        g.fillRect(0,0,this.maxX,this.maxY) ;
        g.drawImage(this.img,maxX/2,maxY/2,Graphics.HCENTER|Graphics.VCENTER) ;
    }
    
    private void startTimer( )
    {
        TimerTask task = new TimerTask()
        {
            public void run()
            {
                parent.setDisplayable( menu ) ;
            }
        } ; 
        timer = new Timer() ;
        timer.schedule(task, 2000 ) ;
    }
}
