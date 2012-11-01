/*
 * TiledLayer.java
 *
 * Created on May 20, 2003, 4:48 PM
 */

import javax.microedition.lcdui.*;

/**
 *
 * @author  SKJ
 * @version 1.0.0
 */
public class TiledLayer extends Layer
{
    private int columns ;
    private int rows ;
    private Image image ;
    private int tWidth ;
    private int tHeight ;
    private int[][] cells ;
    private int tIndex ;

    // Constructor
    public TiledLayer( int col, int row, Image img, int tileW, int tileH )
    {
        this.columns = col ;
        this.rows = row ;
        this.image = img ;
        this.tWidth = tileW ;
        this.tHeight = tileH ;
            
        this.cells = new int[row][col] ;
    }

    public void fillCells( int col, int row, int nCol, int nRow, int tIndex )
    {
		tIndex-- ;

        for( int i=0; i<nRow; i++ )
        {
            for( int j=0; j<nCol; j++ )
            {
                cells[row+i][col+j] = tIndex ;
            }
        }
    }
 
    public void setCell( int col, int row, int tIndex )
    {
		tIndex-- ;

        if(col < this.columns && row <= this.rows)
        {
            cells[row][col] = tIndex ;  
        }
    }
    
    public void paint(Graphics g) 
    {
        int lx = g.getClipX() ;
        int ly = g.getClipY() ;
        
        int lWidth = g.getClipWidth() ;
        int lHeight = g.getClipHeight() ;
        
        for( int i=0; i < this.rows; i++ )
        {
            for( int j=0; j < this.columns; j++ )
            {
                g.setClip( j * this.tWidth, i * tHeight,tWidth,tHeight ) ;
                g.drawImage( this.image,j * this.tWidth - (cells[i][j] * tWidth), i * tHeight, Graphics.TOP|Graphics.LEFT ) ;
                g.setClip( lx,ly,lWidth,lHeight ) ;
            }
        }
    }
}
