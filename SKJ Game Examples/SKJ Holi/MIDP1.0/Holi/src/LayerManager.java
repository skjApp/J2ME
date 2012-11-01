/*
 * LayerManager.java
 *
 * Created on May 20, 2003, 4:44 PM
 */

import javax.microedition.lcdui.*;
import java.util.Vector ;

/**
 *
 * @author  SKJ
 * @version 1.0.0
 */
public class LayerManager 
{
    // Basic Functionality
    private Vector layers ;
    private int x ;
    private int y ;
    private int width ;
    private int height ;
    
    // Constructor
    public LayerManager()
    {
        this.layers = new Vector() ;
    }

    // Appending layer l
    public void append(Layer l)
    {
        this.layers.addElement(l) ;
    }

    // Setting the rectangular view window
    public void setViewWindow( int x, int y, int width, int height )
    {
        this.x = x ;
        this.y = y ;
        this.width = width ;
        this.height = height ;
    }

    // paint method
    public void paint(Graphics g)
    {
        g.setClip( this.x,this.y,this.width,this.height ) ;
          
        for(int i = this.layers.size() - 1; i >= 0 ; i--)
        {
            ((Layer)(this.layers.elementAt(i))).paint(g) ;
        }
    }
}
