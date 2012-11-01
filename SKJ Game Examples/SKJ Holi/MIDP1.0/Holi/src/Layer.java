/*
 * Layer.java
 *
 * Created on May 20, 2003, 4:44 PM
 */

import javax.microedition.lcdui.*;

/**
 *
 * @author  SKJ Technologies
 * @version 1.0.0
 */
public abstract class Layer 
{
    // Declaring all the private variables
    private int x ;
    private int y ;
    private int width ;
    private int height ;
    private boolean visible ;
    
    /***********************************/
    // Getter methods
    /***********************************/
    public int getX()
    {
        return this.x ;
    }
    
    public int getY()
    {
        return this.y ;
    }
    
    public int getWidth()
    {
        return this.width ;
    }
    
    public int getHeight()
    {
        return this.height ;
    }
    
    public boolean getVisible()
    {
        return this.visible ;
    }
    
    /***********************************/
    // Setter methods
    /***********************************/
    public void setVisible(boolean visible )
    {
        this.visible = visible ;
    }
    
    public void setWidth(int width)
    {
        this.width = width ;
    }
    
    public void setHeight(int height)
    {
        this.height = height ;
    }
    
    public void setPosition( int x, int y )
    {
        this.x = x ;
        this.y = y ;
    }
    
    /***********************************/
    // Other methods
    /***********************************/
    public boolean isVisible()
    {
        return this.visible ;
    }
    
    public void move( int x, int y )
    {
        this.x += x ;
        this.y += y ;
    }
    
    public abstract void paint( Graphics g ) ;
}
