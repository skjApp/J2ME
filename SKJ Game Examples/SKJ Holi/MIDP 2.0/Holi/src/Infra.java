/*
 * Infra.java
 *
 * Created on May 20, 2003, 1:17 PM
 */

import javax.microedition.lcdui.* ;
import java.util.* ;
import javax.microedition.rms.* ;
import java.io.* ;

/**
 *
 * @author  SKJ Technologies
 * @version 1.0.0
 */
public class Infra extends Canvas implements CommandListener
{
    private Holi parent ;
    private Game game ;
    private Command back ;
    private Command select ;
    private Command OK ;
	private Command exit;
    
    // Logo Screen Variables
    private Timer timer ;
    private TimerTask timerTask ;
    private Image logo ;
    private boolean logoShown ;
    private boolean shMenu;
 	private boolean logoShown1;

    // MainMenu Variables
    private List menu ;
    private List menu1 ;

	// Game variables
    private Command pause ;
    private Command resume ;
    
    // Instruction screen Variables
    private Form insScr ;

    // HighScore Screen Variables
    private Form hScore ;
    private int[] scores ;
    private RecordStore recordStore ;
    
    //Utility function
    private Form form ;
    private boolean nextSCr ;
    private Random random ;

    public Infra(Holi parent)
    {
        super() ;
        
        // Setting the variables
		logoShown1 = true;
		shMenu = false;
        this.parent = parent ;
        
		this.back = new Command("Back",Command.OK,1 ) ;
        this.select = new Command("Select",Command.EXIT,1 ) ;
		this.exit = new Command("Exit",Command.EXIT,1 ) ;
        this.OK = new Command("OK",Command.BACK,1 ) ;
        
        this.form = new Form("") ;
        this.form.addCommand(this.OK) ;
        this.form.setCommandListener(this) ;
        
        // Calling methods
        this.init() ;
    }
    
     // Basic Functionality
    private void init()
    {
        this.initLogo() ;
        this.initMainMenu() ;
        this.initIns() ;
        this.initHS() ;
        this.initUtility() ;
        this.initGame() ;
    }
    
    /***************************************************/
    // Logo Screen Functionality
    /***************************************************/
    private void initLogo()
    {
        // Initializing the timer and timertask for the logo screen
        this.timer = new Timer () ;
        timerTask = new TimerTask()
        {
            public void run()
            {
                logoShown = false ;
				repaint();
            }
        };

		showLogo() ;
    }
    
	public void showLogo()
    {
        this.logoShown = true ;
        timer.schedule(timerTask,2000) ;

		parent.setDisplayable(this) ;
        repaint() ;
    }
    
    /***************************************************/
    // Game functionality
    /***************************************************/
    private void initGame()
    {
        // Initializing the game and command associated with the game
        this.game = new Game(this.parent.getDisplay(),this) ;
        this.pause = new Command("Pause",Command.STOP,1) ;
        this.resume = new Command("Resume",Command.STOP,1) ;
        this.game.addCommand(this.back) ;
        this.game.addCommand(this.pause) ;
        this.game.setCommandListener(this) ;
    }

    /***************************************************/
    // MainMenu functionality
    /***************************************************/
    private void initMainMenu()
    {
        this.menu = new List("SKJ Holi",List.IMPLICIT) ;
        this.menu.append("Game",null) ;
        this.menu.append("High Scores",null) ;
        this.menu.append("Instructions",null) ;
		this.menu.append("Exit",null) ;

        this.menu.addCommand(this.select) ;
        
        // Setting the commandListener for Commands
        this.menu.setCommandListener(this) ;
    }

    /***************************************************/
    // Logo Functionality
    /***************************************************/
	protected void setTrue()
	{
		this.logoShown1 = false;
		repaint();
	}

    public void showMenu()
    {
        this.parent.setDisplayable(this.menu) ;
		shMenu = true;
 		this.game.musicPlay();
		repaint() ;  
    }

    /***************************************************/
    // Instruction screen functionality
    /***************************************************/
    private void initIns()
    {
        this.insScr = new Form("Instructions") ;
 		this.insScr.append("Goal:\nTo hit as many people on the road as you can. You should save the main character 'Sahib' from being hit by the bird.\nPress LEFT Or 4 Key for moving Left.\nPress RIGHT Or 6 Key for moving Right.\nPress DOWN Or 8 Key for sitting down.\nPress UP Or 2 Key for standing Up.\nPress FIRE Or 5 Key for releasing the balloons." ) ;
        this.insScr.addCommand(this.back) ;
        this.insScr.setCommandListener(this) ;
    }
    
    public void showIns()
    {
        this.parent.setDisplayable(insScr) ;
    }

	protected void removeCommand()
	{
		this.game.removeCommand(this.pause);
	}

	protected void addCommand()
	{
		this.game.addCommand(this.pause);
	}
    
    /***************************************************/
    // High Score Screen Functionalty
    /***************************************************/
    private void initHS()
    {
        // Initialising variables
        this.scores = new int[5] ;
        this.retrieveHS() ;
        this.hScore = new Form("High Scores") ;
        this.hScore.addCommand(this.back) ;
        this.hScore.setCommandListener(this) ;
    }
    
    private void showHS()
    {
        while(this.hScore.size() > 0)
        {
            this.hScore.delete(this.hScore.size() - 1) ;
        }
            
        this.parent.setDisplayable( this.hScore ) ;
            
        int li = 1 ;
          
        for(int i=0; i<5;i++)
        {
            if(this.scores[i] > 0)
            {
                this.hScore.append(String.valueOf(li) + ". " + String.valueOf(this.scores[i]) + "\n") ;
                li++ ;
            }
        }
    }
    
    public int getHS(int i)
    {
        return this.scores[i] ;
    }
    
	// The method for setting the high score
    public void setHS(int sc)
    {
       for(int i=0; i < 5; i++)
		{
		   if(scores[i] == sc)
			{
			   return;
			}
		}

		for(int i = 0; i < 5;i++)
        {
            if(this.scores[i] < sc )
            {
                int hs[] = new int[5-i] ;
                
                for( int j = i ; j < 5;j++)
                {
                    hs[j-i] = this.scores[j] ;
                }

                this.scores[i] = sc ;
                
                for( int j=i + 1;j < 5;j++)
                {
                    this.scores[j] = hs[j-i-1] ;
                }
                
                break ;
            }
        }
            
        this.saveHS() ;
    }
    
	// The method for retrieving the High Scores from the RMS
    private void retrieveHS()
    {
        try
        {
            this.recordStore = RecordStore.openRecordStore("scores",true) ;

            if( this.recordStore.getNumRecords() == 0 )
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream() ;
                DataOutputStream dos = new DataOutputStream(baos) ;

                byte[] b ;
                dos.writeInt(0) ;
                b = baos.toByteArray() ;

                for( int i=0; i<5;i++)
                {
                    this.recordStore.addRecord( b,0,b.length) ;
                }
                
                dos.close() ;
            }
            else
            {
                // Read high score

                ByteArrayInputStream bais ;
                DataInputStream dis ;

                byte[] data ;

                for( int i=0;i<5; i++)
                {
                    data = this.recordStore.getRecord( i+1) ;

                    if(data != null )
                    {
                        bais = new ByteArrayInputStream(data) ;
                        dis = new DataInputStream(bais) ;
                        this.scores[i] = dis.readInt() ;
                        dis.close() ;
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace() ;
        }
    }
    
	// The method for saving the High Scores in the RMS
    private void saveHS()
    {
        ByteArrayOutputStream baos ;
        DataOutputStream das;
        byte[] data ;
        
        try
        {
            baos = new ByteArrayOutputStream() ;
            das= new DataOutputStream(baos) ;
            
            for( int i=0;i<5;i++)
            {
                das.writeInt(this.scores[i]) ;
                data = baos.toByteArray() ;
                
                this.recordStore.setRecord( i+1,data,0,data.length) ;
                
                // For resetting the variabl;es
                baos.reset() ;
                data = null ;
            }
            
            das.close() ;
        }
        catch(Exception e)
        {
            e.printStackTrace() ;
        }
    }
    
    /***************************************************/
    // Canvas Functionality
    /***************************************************/

    public void paint( Graphics g )
    {   
        try
        {
            if( this.logoShown == false && this.logoShown1 == false && shMenu == false) 
            {
				showMenu();
            }
			else
			{
				g.setColor(0,0,0 ) ;
				g.fillRect(0,0,this.getWidth(),this.getHeight()) ;
				g.drawImage(Image.createImage("/SKJ.png"),(this.getWidth() - 90)/2,(this.getHeight() - 67)/2,Graphics.TOP|Graphics.LEFT) ;
			}
        }
        catch(Exception e)
        {
            e.printStackTrace() ;
        }
    }

    /***************************************************/
    // CommandListener functionality
    /***************************************************/
    public void commandAction(Command c, Displayable d )
    {
	    if(d == this.game)
        {
            if(c == this.pause)
            {  
                this.game.setPause(true) ;
                this.game.removeCommand(this.pause) ;
                this.game.addCommand(this.resume)  ;
            }
            else if(c == this.resume)
            {
                this.game.setPause(false) ;
                this.game.removeCommand(this.resume) ;
                this.game.addCommand(this.pause)  ;
				this.game.removeCh();
            }
        }
	    else if(d == this.menu)
        {
            if(c == this.select || c == List.SELECT_COMMAND)
            {
                switch(this.menu.getSelectedIndex())
                {
                    case 0: // Game
                    {
                        this.game.newGame() ;
                        this.parent.setDisplayable(this.game) ;
                        break ;
                    }

                    case 1: // High scores
                    {
                        this.showHS() ;
                        break ;
                    }

                    case 2: // Instructions
                    {
                        this.showIns() ;
                        break ;
                    }

					case 3: // Exit
                    {
						this.parent.notifyDestroyed() ;
                        break ;
                    }
                }
            }
        }
        else if(d == this.form)
         {
           if(c == this.OK)
             {
                if(this.nextSCr == false)
                  {
                    this.showMenu() ;
                  }
                else
                  {
                   this.parent.setDisplayable(this.game) ;
                  }
             }
        }
		if(c == this.back)
        {
            if(d == this.game)
            {
				this.game.gameOver();
				this.game.removeCh();

				this.game.removeCommand(this.pause) ;
				this.game.removeCommand(this.resume) ;
            }

            this.parent.setDisplayable(this.menu) ;
        }
    }
    
    /***************************************************/
    // Utility functionality
    /***************************************************/
    public Game getGame()
    {
        return this.game ;
    }
    
    private void initUtility()
    {
        this.random = new Random() ;
    }
    
    public void setForm(String title,String text,boolean disp)
    {
        for(int i = 0; i < this.form.size();i++)
        {
            this.form.delete(this.form.size() - 1) ;
        }
        
        this.form.setTitle(title) ;
        this.form.append(text) ;
        
        this.parent.setDisplayable(this.form) ;
        this.nextSCr = disp ;
    }
    
    public int getRandom( int min, int max ) 
    {
        int lVar ;
        lVar = max - min ;
        if(lVar != 0)
        {
            lVar = (this.random.nextInt() / (Integer.MAX_VALUE / lVar)) + 1 ;
        }
        
        // For getting the absolute value
        if(lVar < 0)
        {
            lVar = -lVar ;
        }
        return ( min + lVar ) ;
    }
}