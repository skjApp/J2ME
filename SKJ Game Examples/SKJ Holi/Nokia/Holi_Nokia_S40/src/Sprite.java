/*
 * Sprite.java
 *
 * Created on May 20, 2003, 4:45 PM
 */

import javax.microedition.lcdui.*;

/**
 *
 * @author  SKJ Technologies
 * @version 1.0.0
 */
public class Sprite extends Layer
{
    // Declaring the Instance variables
    private Image image ;
    private int frame ;
	private int frames ;
    private int state ;
    private Sprite sprite ;
	private int [][] recs = null ;
    
    public Sprite(Image img,int frWidth, int frHeight)
    {
        this.image = img ;
        this.setWidth(frWidth) ;
        this.setHeight(frHeight) ;

		frames = img.getWidth() / frWidth ;

		if(frames * frWidth == img.getWidth())
		{
			frames-- ;
		}
		else
		{
			return ;
		}
    }

	public void setCR(int [][] lRecs)
	{
		recs = lRecs ;
	}

	public int [][] getCR()
	{
		return recs ;
	}
    
    public boolean collidesWith(Sprite sprite )
    {
        if(this.getVisible() == false || sprite.getVisible() == false)
        {
            return false ;
        }
        
		int lx = 0 ;
		int ly = 0 ;
		int lwidth = 0 ;
		int lheight = 0 ;
		  
		int spx = 0 ;
		int spy = 0 ;
		int spwidth = 0 ;
		int spheight = 0 ;

		int [][] spRecs ;

		if(recs == null)
		{
			lx =  this.getX() ;
			ly = this.getY() ;
			lwidth = this.getWidth() ;
			lheight = this.getHeight() ;
			  
			spx = sprite.getX() ;
			spy = sprite.getY() ;
			spwidth = sprite.getWidth() ;
			spheight = sprite.getHeight() ;
			
			return collide(lx,ly,lwidth,lheight,spx,spy,spwidth,spheight) ;
		}
		else
		{
			spRecs = sprite.getCR() ;

			for(int i = 0;i < recs.length;i++)
			{
				lx =  this.getX() + recs[i][0] ;
				ly = this.getY() + recs[i][1] ;
				lwidth = recs[i][2] ;
				lheight = recs[i][3] ;

				if(spRecs == null)
				{
					spx = sprite.getX() ;
					spy = sprite.getY() ;
					spwidth = sprite.getWidth() ;
					spheight = sprite.getHeight() ;

					if(collide(lx,ly,lwidth,lheight,spx,spy,spwidth,spheight))
					{
						return true ;
					}
				}
				else
				{  // System.out.println("collide");
					for(int j = 0;j < spRecs.length;j++)
					{
						spx = sprite.getX() + spRecs[j][0] ;
						spy = sprite.getY() + spRecs[j][1] ;
						spwidth = spRecs[j][2] ;
						spheight = spRecs[j][3] ;

						if(collide(lx,ly,lwidth,lheight,spx,spy,spwidth,spheight))
						{
							return true ;
						}
					}
				}
			}
		}

		return false ;
    }
    
	private boolean collide(int lx,int ly,int lwidth,int lheight,int spx,int spy,int spwidth,int spheight)
	{
		if (lx <= spx && 
				ly <= spy && 
				lx + lwidth >= spx && 
				ly + lheight >= spy)
			{
				return true ;
			}
			else if (lx <= spx + spwidth && 
					 ly <= spy && 
					 lx + lwidth >= spx + spwidth && 
					 ly + lheight >= spy)
			{
				return true ;
			}
			else if (lx <= spx && 
					 ly <= spy + spheight && 
					 lx + lwidth >= spx && 
					 ly + lheight >= spy + spheight)
			{
				return true ;
			}
			else if (lx <= spx + spwidth &&
					 ly <= spy + spheight &&
					 lx + lwidth  >= spx + spwidth &&
					 ly + lheight >= spy + spheight)
			{
				return true ;
			}
			
			return false ;
	}

    /***********************************************/
    // Getter methods
    /***********************************************/
    public int getFrame()
    {
        return this.frame ;
    }
    
    public int getState()
    {
        return this.state ;
    }
    
    /***********************************************/
    // Setter methods
    /***********************************************/
    public void setFrame( int fr )
    {
        this.frame = fr ;
    }

	public void nextFrame()
    {
		if(frame < frames)
		{
			this.frame++ ;
		}
		else
		{
			this.frame = 0 ;
		}
    }

	public void prevFrame()
    {
        if(frame > 0)
		{
			this.frame-- ;
		}
		else
		{
			this.frame = frames ;
		}
    }
    
    public void setState( int state )
    {
        this.state = state ;
    }

    public void paint(Graphics g) 
    {
        if(this.getVisible() == true)
        {
            int lx = g.getClipX() ;
            int ly = g.getClipY() ;

            int lWidth = g.getClipWidth() ;
            int lHeight = g.getClipHeight() ;

            g.setClip( this.getX(),this.getY(),this.getWidth(),this.getHeight()) ;
            g.drawImage( this.image,this.getX() -( frame * this.getWidth()), this.getY(), Graphics.TOP | Graphics.LEFT ) ;
            g.setClip( lx,ly,lWidth,lHeight ) ;
        }
    }
}
