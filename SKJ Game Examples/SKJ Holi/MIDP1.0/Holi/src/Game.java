import javax.microedition.lcdui.* ;
import java.util.* ;
import java.io.* ;

/**
 *
 * @author  SKJ Technologies
 * @version 1.0.0
 * Note : The main character's name in this game is Ankit
 */
public class Game extends Canvas implements Runnable
{   
	private Display display ;
	private Infra infra ;

	private Sprite [] sprite ;
	private int [] state ;
	private int [] speed ;

	private int maxX ;
	private int maxY ;

	// Important Game UI Variables
	private LayerManager layerManager ;

	private TiledLayer tiledLayer ;
	private int rows ;
	private int columns ;

	// Game Variables
	private int stage ;
	private int score ;
	private int chances = 2 ;
	private int [][] bornIndex =	{{8,4,5,1,6,-1},				// Stage 1
									 {5,3,6,1,7,4,1,2,-1},			// Stage 2
									 {6,1,5,2,4,1,7,2,8,5,1,6,-2}};	// Stage 3
		
	private int spriteTime ;
	private int dieState ;
	private boolean gamePaused ;
	private int screenID ;
	//Total Gubbare thrown & hits
	private int col;
	private int tnogb;
	//For showing the points
	private int px;
	private int py;
	private boolean t;
	private int scr;
	private int st;
	private boolean ch;
	
	// Timer variables
	private Timer timer ;
	private TimerTask timerTask ;
	private int timePassed ;
	private boolean paused ;
	private int x;
	private int y;
	private int x1;
	private int nohit;
	private int nohit1;
	private int y1;	

	// Other variables
	private Font font ;

	private int a0[] [] = { {9,1,8,10},{1,1,15,9},{14,0,8,24} };
	private int a1[] [] = { {1,1,10,8} , {12,4,4,5},{16,0,5,5}};
	private int a2[] [] = { {31,2,13,16}  , {1,10,67,12}};
	private int a3[] [] = { {25,0,30,7} , {2,9,64,20}};
	private int a4[] [] = { {7,0,5,23} , {13,11,3,13}};
	private int a5[] [] = { {7,0,6,25} , {3,21,3,5},{14,16,4,4}};
	private int a6[] [] = { {5,0,5,18} , {0,17,5,5},{10,17,4,11}};
	private int a7[] [] = { {4,0,4,24}};
	private int a8[] [] = { {22,1,32,6},{1,1,63,20}};
	private int a9[] [] = { {0,0,8,8} };
	private int a10[] [] = { {0,0,8,8} };
	private int a11[] [] = { {0,0,8,8} };

	public Game(Display lDisplay, Infra lInfra)
	{
		super() ;

		// Initializing the important variables
		display = lDisplay ;
		infra = lInfra ;
		font = Font.getDefaultFont() ;
		init() ;
	}

	private void init()
	{
		// Initializing the layer manager
		layerManager = new LayerManager() ;
		sprite = new Sprite[12] ;
		state = new int[12] ;
		speed = new int[12] ;
		col =0;
		tnogb=0;
		
	
		// Initializing maxX and maxY.
		// Example of 'SKJ UI Portability Technique' in  action.
		// This helps in soft coding based on canvas  width and height
		// instead of hard coding values.
		maxX = this.getWidth() ;		
		maxY = this.getHeight() ;

		x=maxX;
		x1=maxX;
		y=maxX;
		rows = maxY / 16 ;
		if(maxY % 16 > 0)
		{
			rows++ ;	
		}

		columns = maxX / 16 ;
		if(maxX % 16 > 0)
		{
			columns++ ;	
		}
		
		// Initializing the sprite
		layerManager.setViewWindow(0,0,maxX,maxY);

		try
		{
			new Thread(this).start();

			for(int i = 0;i < state.length;i++)
			{
				state[i] = 0 ;
				speed[i] = 0 ;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace() ;
		}

		initTimer() ;
	}

	public void run()
	{
		Image santa1,man1,car1,gubara2,bird,man2;
		
		try
		{
		santa1 = Image.createImage("/sardar.png");
		car1 = Image.createImage("/car.png");
		gubara2 = Image.createImage("/gu.png");
		bird = Image.createImage("/bird.png");
		man1 = Image.createImage("/man.png");
		man2 = Image.createImage("/man1.png"); 

		sprite[9] = new Sprite(gubara2,8,8) ;
		sprite[10] = new Sprite(gubara2,8,8) ;
		sprite[11] = new Sprite(gubara2,8,8) ;
		sprite[0]=new Sprite(santa1,28,25);
		sprite[3] = new Sprite(car1,70,26) ;
		sprite[8] = new Sprite(car1,70,26) ;
		sprite[1] = new Sprite(bird,21,13) ;
		sprite[2] = new Sprite(car1,70,26) ;
		sprite[4] = new Sprite(man2,18,28) ;			
		sprite[5] = new Sprite(man1,18,30) ;
		sprite[6] = new Sprite(man1,18,30) ;
		sprite[7] = new Sprite(man1,18,30) ;

		sprite[0].setCR(a0);
		sprite[1].setCR(a1);
		sprite[2].setCR(a2);
		sprite[3].setCR(a3);
		sprite[4].setCR(a4);
		sprite[5].setCR(a5);
		sprite[6].setCR(a6);
		sprite[7].setCR(a7);
		sprite[8].setCR(a8);
		sprite[9].setCR(a9);
		sprite[10].setCR(a10);
		sprite[11].setCR(a11);
	
		tiledLayer = new TiledLayer(columns*2,rows,Image.createImage("/tile.png"),16,16) ;
        
		if(rows > 8)
		{
		tiledLayer.fillCells(0,0,columns,1,1);
		tiledLayer.fillCells(0,1,columns,1,1);
		tiledLayer.fillCells(0,2,columns,1,3);
		tiledLayer.fillCells(0,3,columns,1,4);
		tiledLayer.fillCells(0,4,columns,1,5);
		tiledLayer.fillCells(0,5,columns,1,6);
		tiledLayer.fillCells(0,6,columns,rows-6,2);
		}
		else if (rows == 8)
		{
		tiledLayer.fillCells(0,0,columns,1,1);
		tiledLayer.fillCells(0,1,columns,1,1);
		tiledLayer.fillCells(0,2,columns,1,3);
		tiledLayer.fillCells(0,3,columns,1,4);
		tiledLayer.fillCells(0,4,columns,1,6);
		tiledLayer.fillCells(0,5,columns,3,2);
		}
		else if (rows == 7)
		{
		tiledLayer.fillCells(0,0,columns,1,1);
		tiledLayer.fillCells(0,1,columns,1,1);
		tiledLayer.fillCells(0,2,columns,1,4);
		tiledLayer.fillCells(0,3,columns,1,5);
		tiledLayer.fillCells(0,4,columns,3,2);
		}
		else if (rows < 7)
		{
		tiledLayer.fillCells(0,0,columns,1,1);
		tiledLayer.fillCells(0,1,columns,1,1);
		tiledLayer.fillCells(0,2,columns,1,3);
		tiledLayer.fillCells(0,3,columns,3,2);
		}
		//Ankit
		sprite[0].setPosition(maxX/2-15,6) ;
		sprite[0].setFrame(1) ;
		sprite[0].setVisible(true) ;

		// Steffi
		sprite[1].setPosition(-100,-100) ;
		sprite[1].setFrame(0) ;
		sprite[1].setVisible(false) ;

		// Granny
		sprite[2].setPosition(-100,-100) ;
		sprite[2].setFrame(0) ;
		sprite[2].setVisible(false) ;

		// Police
		sprite[3].setPosition(-100,-100) ;
		sprite[3].setFrame(0) ;
		sprite[3].setVisible(false) ;

		// Basu
		sprite[4].setPosition(-100,-100) ;
		sprite[4].setFrame(0) ;
		sprite[4].setVisible(false) ;

		// Mihir
		sprite[5].setPosition(-100,-100) ;
		sprite[5].setFrame(0) ;
		sprite[5].setVisible(false) ;

		// Sunny
		sprite[6].setPosition(-100,-100) ;
		sprite[6].setFrame(0) ;
		sprite[6].setVisible(false) ;

		// Sardar
		sprite[7].setPosition(-100,-100) ;
		sprite[7].setFrame(0) ;
		sprite[7].setVisible(false) ;

		// Bird
		sprite[8].setPosition(-100,-100) ;
		sprite[8].setFrame(0) ;
		sprite[8].setVisible(false) ;

		// Gubara 1
		sprite[9].setPosition(-100,-100) ; 
		sprite[9].setFrame(0) ;
		sprite[9].setVisible(false) ;

		// Gubara 2
		sprite[10].setPosition(-100,-100) ;
		sprite[10].setFrame(1) ;
		sprite[10].setVisible(false) ;

		// Gubara 3
		sprite[11].setPosition(-100,-100) ;
		sprite[11].setFrame(2) ;
		sprite[11].setVisible(false) ;

		// Appending sprite in layerManager
	    layerManager.append(sprite[1]) ;
		layerManager.append(sprite[2]) ;
		layerManager.append(sprite[3]) ;
		layerManager.append(sprite[4]) ;
		layerManager.append(sprite[5]) ;
		layerManager.append(sprite[6]) ;
		layerManager.append(sprite[7]) ;
		layerManager.append(sprite[8]) ;
		layerManager.append(sprite[0]) ;
		layerManager.append(tiledLayer) ;

		this.infra.setTrue();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	private void initTimer()
	{
		timer = new Timer() ;

		timerTask = new TimerTask()
		{
			public void run()
			{
				if(gamePaused == false)
				{
					// Game loop lifecycle methods
					spriteBorn() ;
					move() ;
					collision() ;
					animate() ;
					
					timePassed += 125 ;
					spriteTime += 125 ;
				}
				repaint() ;
			}
		};

		gamePaused = true ;
		timer.schedule(timerTask,125,125) ;
	}

	/************************************/
    // Begin Game State Methods
    /************************************/
	
	// All the code for initialising a new game should be kept here
	public void newGame()
	{
		// Making all sprites die except 'Ankit'
		for(int i = 1;i < 12;i++)
		{
			spriteDie(i) ;
		}

		sprite[0].setPosition(maxX/2-15,6) ;
		t = false;
		stage = 0 ;
		chances = 2 ;
		score = 0 ;
		timePassed = 0 ;
		spriteTime = 0 ;
		dieState = 0 ;
		screenID = 4 ;
		sprite[0].setVisible(true) ;
		state[0] = 0 ;
		gamePaused = false ;
		col = 0;
		tnogb = 0;
		nohit=0;
		y1=maxY;
		ch=false;
	}

	private void setScreen(int ID)
	{
		screenID = ID ;
		gamePaused = true ;
		repaint() ;
	}

	//For drawing the points
	private void drawsc(Graphics g)
	{
		int lpx;
		int lpy;
		Font font ;
		int x, y, strH,width ;
		String s;
		
		g.setColor(255,255,255);
		
		//For drawing points on all the characters
		if(t == true)
		{
			g.drawString(String.valueOf(scr),px+1,py-22,Graphics.TOP|Graphics.LEFT);
		}
		
		//For drawing Ohhhhhhh on Granny
		if(st >0 && sprite[2].isVisible() == true)
		{
			lpx = px + 10;
			lpy = py;
			s="Oheeee";
			
			if(st >0 && st < 3)
			{
				for(int i=0;i<s.length();i++)
				{
					g.setColor(255,255,255);
					lpy = lpy-10;
					lpx += 2;
					g.drawString(String.valueOf(s.charAt(i)),lpx,lpy,Graphics.TOP|Graphics.LEFT);
				}
				
				st++;
			}
			else if(st == 3)
			{
				st = 0;
			}
	   }
		
		//For Print Balle on Sardar Only
		if(st > 0 && sprite[7].isVisible() == true)
		{
			lpx = px + 10;
			lpy = py;
			s = "Thank You";
			
			if(st >0 && st < 5)
			{
				for(int i=0;i<2;i++)
				{
					g.setColor(255,255,255);
					lpy = lpy-10;
					lpx += 2;
					g.drawString(s,lpx,lpy,Graphics.TOP|Graphics.LEFT);
				}
				
				st++;
			}
			else if(st == 4)
			{
				st =0;
			}
		}
	
			//For Print Balle on Sardar Only
		if(st > 0 && sprite[4].isVisible() == true)
		{
			lpx = px + 10;
			lpy = py;
			s = "Mat Maro";
			
			if(st >0 && st < 5)
			{
				for(int i=0;i<2;i++)
				{
					g.setColor(255,255,255);
					lpy = lpy-10;
					lpx += 2;
					g.drawString(s,lpx,lpy,Graphics.TOP|Graphics.LEFT);
				}
				
				st++;
			}
			else if(st == 4)
			{
				st =0;
			}
		}
		if(st > 0 && sprite[6].isVisible() == true)
		{
			lpx = px + 10;
			lpy = py;
			s = "Nahi Maro";
			
			if(st >0 && st < 5)
			{
				for(int i=0;i<2;i++)
				{
					g.setColor(255,255,255);
					lpy = lpy-10;
					lpx += 2;
					g.drawString(s,lpx,lpy,Graphics.TOP|Graphics.LEFT);
				}
				
				st++;
			}
			else if(st == 4)
			{
				st =0;
			}
		}
		
		t = false;
		if(gamePaused == true && ch == true)
		{
			s=" PAUSED";
			font = Font.getDefaultFont() ;
			width = font.stringWidth("PAUSED") + 9 ;		// This is the longest string
			strH = font.getHeight() ;
			x = (maxX/2) - (width / 2) ;
			y = (maxY/2) - strH ;
			// Drawing the background black board
			g.setColor(0,0,0) ;
			g.fillRoundRect(x,y,width,strH +3,8,8) ;
			g.setColor(255,255,255) ;
			// Drawing the text
			y++ ;
			g.drawString(s,x + ((width - font.stringWidth(s))/2),y,Graphics.TOP|Graphics.LEFT) ;
		}
	}

	// Code related to calling new stages should be kept here
	private void nextStage()
	{
		// Making all sprites die except 'Ankit'
		for(int i = 1;i < 12;i++)
		{
			spriteDie(i) ;
		}
		y1=maxY;

		if(nohit < 6 && stage == 0)
		{
			setScreen(5);
			return;
		}
		else if(nohit < 8 && stage == 1)
		{
			setScreen(5);
			return;
		}
		else if(nohit < 14 && stage == 2)
		{
			setScreen(5);
			return;
		}

		stage++ ;
		timePassed = 0 ;
		t=false;
		spriteTime = 0 ;
		dieState = 0 ;
		state[0] = 0 ;
		gamePaused = true ;
		setScreen(1) ;
		ch=false;
		nohit=0;
	}

	// All the code that should be called after the chance over of the main character should be placed here
	private void chanceOver()
	{
		// Making all sprites die except 'Ankit' the main character
		for(int i = 1;i < 12;i++)
		{
			spriteDie(i) ;
		}

		sprite[0].setVisible(false) ;	// Making ankit invisible

		timePassed = 0 ;
		spriteTime = 0 ;
		dieState = 0 ;
		col = 0;
		tnogb = 0;
		state[0] = 0 ;
		gamePaused = true ;
		sprite[0].setPosition(maxX/2-15,6) ;

		if(chances > 0)
		{
			chances-- ;
			setScreen(2) ;
		}
		else
		{
			setScreen(5) ;
			nohit1 =nohit;
		}
		nohit =	0;
	}

	// All code relating to actions that occur after the game gets over should
	// be placed here.
	protected void gameOver()
	{
		infra.setHS(score) ;
		infra.showMenu() ;
	}

	/************************************/
    // End Game State Methods
    /************************************/
	
	/************************************/
    // Begin Game Logic Methods
    /************************************/
	// All the code related to birth of sprites within the game should be
	// placed in this method. This should not be confused with sprite initialisation.
	// Sprite initialization has been done at the time of game initialization.
	private void spriteBorn()
	{
		if(spriteTime == 500)
		{
			born(bornIndex[stage][dieState]) ;
		}

		if(timePassed % 3 == 0)
		{
			balloonBorn() ;
		}
	}

	private void balloonBorn()
	{
		if(state[0] == 5)
		{
			if(sprite[9].isVisible() == false)
			{
				born(9) ;
				tnogb++;
			}
			else if(sprite[10].isVisible() == false)
			{
				born(10) ;
				tnogb++;
			}
			else if(sprite[11].isVisible() == false)
			{
				born(11) ;
				tnogb++;
			}
		}
	}

	private void born(int index)
	{
		if(index == -1)					// Next Stage Born
		{
			nextStage() ;
			return ;
		}
		else if(index == -2)			// Game Over
		{
			setScreen(6) ;				// Calling the game over screen
			return ;
		}

		// If the sprite is already visible it should not born
		if(sprite[index].isVisible() == true)
		{
			return ;
		}

		switch(index)
		{
			case 1:
			{
				sprite[1].setPosition(-10,3) ;
				sprite[1].setFrame(0) ;
				state[1]=0;
				speed[1]=3;
				break ;
			}

			case 2:
			{
				sprite[2].setPosition(-60,maxY - 27) ;
				sprite[2].setFrame(2) ;
				speed[2] = 4;
				break ;
			}

			case 3:
			{
				sprite[3].setPosition(maxX,maxY - 25) ;
				sprite[3].setFrame(4) ;
				speed[3]=-5;
				break ;
			}

			case 4:
			{
				sprite[4].setPosition(maxX,maxY - 28) ;
				sprite[4].setFrame(0) ;
				speed[4] = -4;
				break ;
			}

			case 5:
			{
				sprite[5].setPosition(maxX,maxY - 26) ;
				sprite[5].setFrame(7) ;
				speed[5] = -6 ;
				break ;
			}

			case 6:
			{
				sprite[6].setPosition(-20,maxY - 26) ;
				sprite[6].setFrame(9) ;
				speed[6] =6 ;
				break ;
			}

			case 7:
			{
				sprite[7].setPosition(maxX,maxY - 26) ;
 				sprite[7].setFrame(17) ;
				speed[7] = -3 ;
				break ;
			}

			case 8:
			{
				sprite[8].setPosition(maxX,maxY - 26) ;
				sprite[8].setFrame(7) ;
				speed[8] = -5 ;
				break ;
			}
		}
		if(index > 8 && index < 12)		// Ankit's balloon
		{
			sprite[index].setPosition(sprite[0].getX() + 14,sprite[0].getY() + 19) ;
			sprite[index].setFrame(index % 3) ;
		}

		sprite[index].setVisible(true) ;
	}

	// All the code related to movement of any sprite should be placed here
	private void move()
	{
		// Ankit
		if(state[0] == 3)			// Left
		{
			sprite[0].move(-6,0) ;
		}
		else if(state[0] == 4)		// Right
		{
			sprite[0].move(6,0) ;
		}

		if(sprite[0].getX() < -8 || sprite[0].getX() > (maxX - 21))
		{
			state[0] = 0 ;
		}

		// All other characters
		for(int i = 1;i < 13;i++ )
		{
			switch(i)
			{
				case 1:
				{
					if(sprite[1].isVisible() == true)
					{
						sprite[1].move(speed[1],0);
					}
					
					// Sprite Die
					if(sprite[1].getX() > maxX && sprite[1].isVisible() == true)
					{
						spriteDie(1) ;
					}
					break ;
				}

				case 2:
				{
					if(sprite[2].isVisible() == true)
					{
						sprite[2].move(speed[2],0) ;
					}

					// Sprite Die
					if(sprite[2].getX() > maxX && sprite[2].isVisible() == true)
					{
						spriteDie(2) ;
					}

					break ;
				}

				case 3:
				{
					if(sprite[3].isVisible() == true)
					{
						sprite[3].move(speed[3],0) ;
						px = sprite[3].getX();
						py = sprite[3].getY();
					}

					// Sprite Die
					if(sprite[3].getX()< -75 && sprite[3].isVisible() == true)
					{
						spriteDie(3) ;
					}

					break ;
				}

				case 4:
				{
					if(sprite[4].isVisible() == true)
					{
						sprite[4].move(speed[4],0) ;
					}

					// Sprite Die
					if(sprite[4].isVisible() == true && sprite[4].getX() < -20)
					{
						spriteDie(4) ;
					}

					break ;
				}

				case 5:
				{
					if(sprite[5].isVisible() == true && state[5] == 0)
					{
						sprite[5].move(speed[5],0) ;
					}

					if(sprite[5].isVisible() == true && (state[5] != 2 || state[5] != 3 || state[5] != 0 ))
					{
						sprite[5].move(-1,0) ;
					}

					if(sprite[5].isVisible() == true && state[5] == 3)
					{
						sprite[5].move(speed[5],0) ;
					}
					
					if(sprite[5].isVisible() == true && state[5] == 2)
					{
						sprite[5].move(speed[5],0) ;
					}

					// Sprite Die
					if(sprite[5].isVisible() == true && sprite[5].getX() < -25)
					{
						spriteDie(5) ;
					}

					break ;
				}

				case 6:
				{
					if(sprite[6].isVisible() == true && state[6] == 0)
					{
						sprite[6].move(speed[6],0) ;
					}
					if(sprite[6].isVisible() == true && state[6] == 1)
					{
						sprite[6].move(1,0) ;
					}
					if(sprite[6].isVisible() == true && (state[6] == 2 || state[6] == 3))
					{
						sprite[6].move(speed[6],0) ;
					}
					// Sprite Die
					if(sprite[6].isVisible() == true && sprite[6].getX() > maxX)
					{
						spriteDie(6) ;
					}

					break ;
				}

				case 7:
				{
					if(sprite[7].isVisible() == true && state[7] == 0)
					{
						sprite[7].move(speed[7],0) ;
					}
					else if(sprite[7].isVisible() == true && state[7] == 1)
					{
						sprite[7].move(speed[7],0) ;
					}
					
					else if(sprite[7].isVisible() == true && (state[7] == 2 || state[7] ==3 || state[7] ==4 || state[7] == 5 || state[7] == 6))
					{
						sprite[7].move(-1,0) ;
					}

					// Sprite Die
					if(sprite[7].isVisible() == true && sprite[7].getX() < -25)
					{
						spriteDie(7) ;
					}
					break ;
				}

				case 8:
				{
					if(sprite[8].isVisible() == true)
					{
						sprite[8].move(speed[8],0) ;
					}

					// Sprite Die
					if(sprite[8].isVisible() == true && sprite[8].getX() < -75)
					{
						spriteDie(8) ;
					}
					break ;
				}
			}

			// Gubbare
			if(i > 8 && i < 12)
			{
				if(sprite[i].isVisible() == true)
				{
					sprite[i].move(0,4 * state[i]) ;

					if(i > 9 && sprite[i].getY() == sprite[i - 1].getY())
					{
						sprite[i].move(0,8) ;
					}

					if(state[i] < 3)
					{
						state[i]++ ;
					}

					if(sprite[i].getY() > maxY - 6)
					{
						spriteDie(i) ;
					}
				}
			}
		}
	}

	// All the code related to collision of any sprite should be placed here
	private void collision()
	{
		for(int i = 9;i < 12;i++)
		{
			for(int j = 1;j < 9;j++ )
			{
				if(sprite[i].collidesWith(sprite[j]) == true || sprite[j].collidesWith(sprite[i]) == true)
				{
					switch(j)
					{
						case 1:
						{
							score += 40 ;
							
							//Calculating total no of hits
							col++;
							
							//For printing the score getting the cordinates & score
							px = sprite[i].getX();
							py = sprite[i].getY();
							t = true;
							scr = 40;			
							nohit++;
							state[1]=1;
							
							repaint();
							break ;
						}

						case 2:
						{
							score += 15 ;
							state[2] = 1 ;
							st = state[2];
							
							//Calculating total no of hits
							col++;
							
							//For printing the score getting the cordinates & score
							px = sprite[i].getX();
							py = sprite[i].getY();
							t = true;
							scr = 15;	
							nohit++;
							repaint();
							break ;
						}

						case 3:
						{
							score += 10 ;
							state[3] = 1 ;
							st = state[3];
							
							//Calculating total no of hits
							col++;
							
							//For printing the score getting the cordinates & score
							px = sprite[i].getX();
							py = sprite[i].getY();
							t = true;
							scr = 10;	
							nohit++;
							sprite[3].setFrame(6);
							repaint();
							break ;
						}

						case 4:
						{
							score += 30 ;
							
							//Calculating total no of hits
							col++;
							
							//For printing the score getting the cordinates & score
							px = sprite[i].getX();
							py = sprite[i].getY();
							t = true;
							scr = 30;
							nohit++;
							state[4]=2;
							st = state[4];
							repaint();
							break ;
						}

						case 5:
						{
							score += 40 ;
							
							//Calculating total no of hits
							col++;
							
							//For printing the score getting the cordinates & score
							px = sprite[i].getX();
							py = sprite[i].getY();
							t = true;
							scr = 40;
							nohit++;
							state[5]=1;
							sprite[5].setFrame(38);
							repaint();
							break ;
						}

						case 6:
						{
							score += 50 ;
							state[6] = 1 ;
							st = state[6];
							
							//Calculating total no of hits
							col++;
							
							//For printing the score getting the cordinates & score
							px = sprite[i].getX();
							py = sprite[i].getY();
							t = true;
							scr = 50 ;
							speed[6]++ ;
							nohit++;
							sprite[6].setFrame(36);
							repaint();
							break ;
						}

						case 7:
						{
							score += 30 ;
							state[7] = 2 ;
							st = state[7] ;
							
							//Calculating total no of hits
							col++;
							
							//For printing the score getting the cordinates & score
							px = sprite[i].getX();
							py = sprite[i].getY();
							t = true;
							scr = 30;
							nohit++;
							
							repaint();
							break ;
						}


						case 8:
						{
							score += 15 ;
							state[8] = 1 ;
							st = state[8];
							
							//Calculating total no of hits
							col++;
							
							//For printing the score getting the cordinates & score
							px = sprite[i].getX();
							py = sprite[i].getY();
							t = true;
							scr = 15;	
							nohit++;
							sprite[8].setFrame(8);
							repaint();
							break ;
						}
					}

					spriteDie(i) ;
				}
			}
		}

		// When Ankit is hit		
		if(sprite[0].collidesWith(sprite[1]) == true || sprite[1].collidesWith(sprite[0]) == true)	// When Basu's balloon hits Ankit
		{
			chanceOver() ;
		}
	}

	// All the code related to animation(setting frames) of any sprite should be placed here
	private void animate()
	{
		// Ankit
		if(state[0] == 0)				// Left
		{
			sprite[0].setFrame(0) ;
		}
		else if(state[0] == 1)			// Up
		{
			sprite[0].setVisible(true) ;
		}
		else if(state[0] == 2)			// Down
		{
			sprite[0].setVisible(false) ;
		}
		else if(state[0] == 3)			// Left
		{
			sprite[0].setFrame(3) ;
		}
		else if(state[0] == 4)			// Left
		{
			sprite[0].setFrame(2) ;
		}
		else if(state[0] == 5)			// Fire
		{
			sprite[0].setFrame(1) ;
		}

		// All other characters except balloons
		for(int i = 1;i < 9;i++ )
		{
			switch(i)
			{
				case 1:
				{
					if(state[1] == 0 && sprite[1].isVisible() == true && sprite[1].getFrame() < 4)
					{
						sprite[1].nextFrame();
					}
					else if(state[1] == 0)
					{
						sprite[1].setFrame(0);
					}
					else if(state[1] == 1)
					{
						sprite[1].setFrame(0);
						state[1]=0;
					}

					break;
				}
				case 2:
				{
					if(state[2] == 0 && sprite[2].getFrame() == 2 && sprite[2].isVisible() == true)
					{
						sprite[2].setFrame(1) ;
					}
					else if(state[2] == 0 && sprite[2].isVisible() == true && sprite[2].getFrame() == 1)
					{
						sprite[2].setFrame(2) ;
					}
					else if(state[2] == 1)
					{
						sprite[2].setFrame(0) ;
						state[2]=2;
					}	
					else if(state[2] == 2)
					{
						sprite[2].setFrame(0) ;
						state[2]=3;
					}	
					else if(state[2] == 3)
					{
						sprite[2].setFrame(0) ;
						state[2]=4;
					}	
					else if(state[2] == 4)
					{
						sprite[2].setFrame(0) ;
						state[2]=5;
					}	
					else if(state[2] == 5)
					{
						sprite[2].setFrame(1) ;
						state[2]=0;
					}	
					break ;
				}
				case 3:
				{
					if(state[3]== 0 && sprite[3].isVisible() == true &&sprite[2].getFrame() == 4)
					{
						sprite[3].setFrame(3);
					}
					else if(state[3]== 0 && sprite[3].isVisible() == true &&sprite[2].getFrame() == 3)
					{
						sprite[3].setFrame(4);
					}
					if(state[3]== 1 && sprite[3].isVisible() == true &&sprite[2].getFrame() == 6)
					{
						sprite[3].setFrame(5);
					}
					else if(state[3]== 0 && sprite[3].isVisible() == true &&sprite[2].getFrame() == 5)
					{
						sprite[3].setFrame(6);
					}
					break;
				}

				case 4:
				{
					if(state[4] == 0 && sprite[4].getFrame() >  0 && sprite[4].isVisible() == true)
					{ 
						sprite[4].prevFrame() ;
					}
					else if (state[4] == 0)
					{
						sprite[4].setFrame(8);
					}

					else if (state[4] == 2)
					{
						sprite[4].setFrame(9);
						state[4] = 3;
					}

					else if (state[4] == 3)
					{
						sprite[4].setFrame(9);
						state[4] = 1;
					}

					if(state[4] == 1 && sprite[4].getFrame() >  10 && sprite[4].isVisible() == true)
					{ 
						sprite[4].prevFrame() ;
					}
					else if (state[4] == 1)
					{
						sprite[4].setFrame(18);
					}
					break ;
				}

				case 5:
				{
					if(state[5] == 0 && sprite[5].getFrame() == 7 )
					{
						sprite[5].setFrame(6) ; 
					}
					else if (state[5] == 0 && sprite[5].getFrame() == 6)
					{
						sprite[5].setFrame(7) ; 
					}
					if(state[5] == 1 && sprite[5].getFrame() == 38 )
					{
						sprite[5].setFrame(37) ; 
					}
					else if (state[5] == 1 && sprite[5].getFrame() == 37)
					{
						sprite[5].setFrame(38) ; 
						state[5] = 4;
					}
					else if (state[5] == 4 && sprite[5].getFrame() == 38)
					{
						sprite[5].setFrame(37) ; 
						state[5] = 5;
					}
					else if (state[5] == 5 && sprite[5].getFrame() == 37)
					{
						sprite[5].setFrame(38) ; 
						state[5] = 6;
					}
					else if (state[5] == 6 && sprite[5].getFrame() == 38)
					{
						sprite[5].setFrame(37) ; 
						state[5] = 7;
					}
					else if (state[5] == 7 && sprite[5].getFrame() == 37)
					{
						sprite[5].setFrame(36) ; 
						state[5] = 2;
					}
					if (state[5] == 2)
					{
						sprite[5].setFrame(35);
						state[5] = 3;
					}
					if (state[5] == 3 && sprite[5].getFrame() == 35)
					{
						sprite[5].setFrame(34);
					}
					else if (state[5] == 3 && sprite[5].getFrame() == 34)
					{
						sprite[5].setFrame(35);
					}
					break ;
				}

				case 6:
				{
					if(state[6] == 0 && sprite[6].getFrame() == 9)
					{
						sprite[6].setFrame(8); 
					}
					else if(state[6] == 0 && sprite[6].getFrame() == 8)
					{
						sprite[6].setFrame(9) ;
					}
					if(state[6] == 1)
					{
						sprite[6].setFrame(36); 
						state[6] = 4;
					}
					else if(state[6] == 4)
					{
						sprite[6].setFrame(36); 
						state[6] = 5;
					}
					else if(state[6] == 5)
					{
						sprite[6].setFrame(36); 
						state[6] = 2;
					}
					if (state[6] == 2)
					{
						sprite[6].setFrame(25); 
						state[6] =3;
					}
					if(state[6] == 3 && sprite[6].getFrame() == 25)
					{
						sprite[6].setFrame(24) ;
					}
					else if(state[6] == 3 && sprite[6].getFrame() == 24)
					{
						sprite[6].setFrame(25) ;
					}
					break ;
				}

				case 7:
				{
					if(state[7] == 0 && sprite[7].getFrame() > 10)
					{
						sprite[7].prevFrame(); 
					}
					else if(state[7] == 0)
					{
						sprite[7].setFrame(17) ;
					}

					else if (state[7] == 2)
					{
						sprite[7].setFrame(41);
						state[7] = 3;
					}
					else if (state[7] == 3 && sprite[7].getFrame() == 41)
					{
						sprite[7].setFrame(40);
						state[7] = 4;
					}
					else if (state[7] == 4 && sprite[7].getFrame() == 40)
					{
						sprite[7].setFrame(41);
						state[7] = 5;
					}
					else if (state[7] == 5 && sprite[7].getFrame() == 41)
					{
						sprite[7].setFrame(40);
						state[7] = 6;
					}
					else if (state[7] == 6 && sprite[7].getFrame() == 40)
					{
						sprite[7].setFrame(41);
						state[7] = 7;
					}
					else if (state[7] == 7 && sprite[7].getFrame() == 41)
					{
						sprite[7].setFrame(40);
						state[7] = 8;
					}
					else if (state[7] == 8)
					{
						sprite[7].setFrame(33);
						state[7] = 1;
					}
					if(state[7] == 1 && sprite[7].getFrame() > 26)
					{
						sprite[7].prevFrame(); 
					}
					else if(state[7] == 1)
					{
						sprite[7].setFrame(33) ;
					}
					break ;
				}

				case 8:
				{
					if(state[8] == 0 && sprite[8].isVisible() == true && sprite[8].getFrame() == 7)
					{
						sprite[8].setFrame(7) ; 
					}
					if(state[8] == 1 && sprite[8].isVisible() == true && sprite[8].getFrame() == 8)
					{
						sprite[8].setFrame(8) ; 
					}
					break ;
				}
				default:
				{
					sprite[i].nextFrame() ;
					break ;
				}
			}
		}
	}

	// All the code related to making any sprite invisible and inactive should be placed here
	private void spriteDie(int index)
	{
		sprite[index].setPosition(-100,-100) ;
		sprite[index].setFrame(0) ;
		sprite[index].setVisible(false) ;

		state[index] = 0 ;
		
		if(index > 0 && index < 9)
		{
			x1=maxX;
			dieState++ ;
			spriteTime = 0 ;
			st=0;
		}
	}

	/************************************/
    // End Game Logic Methods
    /************************************/

    /************************************/
    // Begin Game Canvas Methods
    /************************************/
    public void paint(Graphics g)
	{
		// Printing the sprites
		layerManager.paint(g) ;

		for(int i = 9; i < 12 ;i++)
		{
			if(sprite[i].isVisible() == true)
			{
				int color = 0x00ff0000;

				switch(i)
				{
					case 10:	// Green
					{
						color = 0x00ffff00 ;
						break ;
					}

					case 11:	// Blue
					{
						color = 0x0000ff00 ;
						break ;
					}
				}

				g.setColor(color) ;
				g.fillArc(sprite[i].getX(),sprite[i].getY(),8,8,0,360) ;
			}
		}
		
		// Printing the score
		g.setColor(255,255,255);
		String score = String.valueOf(this.score) ;
		g.drawString(score,maxX - (font.stringWidth(score) + 5),2,Graphics.TOP|Graphics.LEFT) ;
		drawsc(g);
		
		// Drawing the alerts
		if(screenID > 0)
		{
			drawAlert(g) ;
		}
    }
	
	// The method for drawing game alerts
	private void drawAlert(Graphics g)
	{
		Font font ;
		String s ;
		
		int x, y, strH,width ;
		font = Font.getDefaultFont() ;
		s = "" ;
		this.infra.removeCommand();
		ch=false;
		
		switch(screenID)
		{
			case 1:				// Stage Over
			{
				// Drawing the background black board
				g.setColor(0,0,0) ;
				g.fillRect(0,0,maxX,maxY) ;
				
				//Drawing the text
				g.setColor(255,255,255) ;
				
				s = "Stage Over: " + String.valueOf(stage) ;
				g.drawString(s,1,x1+15,Graphics.TOP|Graphics.LEFT) ;
				//For Calculating the Hit percentage
				int avg;
				int nogb;
				int col;
				col = this.col;
				nogb = this.tnogb;
				if(col == 0)
				{
					avg = 0;
				}
				else
				{
					avg = (col*100) / nogb;
				}

				//Total Number of Ballons Thrown
				s = "Thrown: "  + String.valueOf(nogb);
				g.drawString(s,1,x1+20+font.getHeight(),Graphics.TOP|Graphics.LEFT);
				
				//Total Number of Hits
				s = "Hits: "  + String.valueOf(col);
				g.drawString(s,1,x1+40+font.getHeight(),Graphics.TOP|Graphics.LEFT);
			
				//Hits percentage
				s = "Hits %: "  + String.valueOf(avg)+"%";
				g.drawString(s,1,x1+60+font.getHeight(),Graphics.TOP|Graphics.LEFT);
				
				// Drawing the points
				s = "Total: " + String.valueOf(score) ;
				g.drawString(s,1,x1+80+font.getHeight(),Graphics.TOP|Graphics.LEFT) ;

				s="Press any Key";
				g.drawString(s,1,x1+100+font.getHeight(),Graphics.TOP|Graphics.LEFT) ;
				s="to Continue";
				g.drawString(s,1,x1+120+font.getHeight(),Graphics.TOP|Graphics.LEFT) ;
				x1=x1-5;
				if(x1 <-120)
				{
					x1 = maxX;
				}
				break ;
			}

			case 2:				// Chance Over
			{
				s = "Chance(s) Left : " + String.valueOf(chances + 1) ;
				break ;
			}

			case 3:				// Game Over
			{
				s = "GAME OVER : ";
				break ;
			}
			case 4:
			{
				s="Number of Hits";
				break;
			}
			case 5:
			{
				s="Sorry: ";
				break;
			}
			case 6:
			{
				s="Thanks For Good";
				break;
			}
		}
		if(screenID==2 || screenID==3)
		{
			width = font.stringWidth("Chance(s) Left : 0") + 9 ;		// This is the longest string
			strH = font.getHeight() ;
			x = (maxX/2) - (width / 2) ;
			y = (maxY/2) - strH ;
			
			// Drawing the background black board
			g.setColor(0,0,0) ;
			g.fillRoundRect(x,y,width,(strH * 2) + 3,8,8) ;
			g.setColor(255,255,255) ;
			
			// Drawing the text
			y++ ;
			g.drawString(s,x + ((width - font.stringWidth(s))/2),y,Graphics.TOP|Graphics.LEFT) ;
			
			// Drawing the points
			s = "Points : " + String.valueOf(score) ;
			y++ ;
			g.drawString(s,x + ((width - font.stringWidth(s))/2),y + strH,Graphics.TOP|Graphics.LEFT) ;
			x1 = x1- 5;
			g.setColor(255,255,255);
			
			if(screenID == 2)
			{
				g.setColor(255,255,255) ;
				s="Press any Key to Continue";
				g.drawString(s,x1,maxY - font.getHeight(),Graphics.TOP|Graphics.LEFT) ;
			}
			
			if(x1 < -100)
			{
				x1 = maxX;
			}
			
			if(screenID == 3)
			{
				s="Press any Key for Menu";
				g.drawString(s,x1,maxY - font.getHeight(),Graphics.TOP|Graphics.LEFT) ;
				if(x1 < -100)
				{
					x1 = maxX;
				}
			}
		}
		else if(screenID == 4)
		{
			gamePaused = true;
			width = font.stringWidth("Number of Hits") + 9 ;		// This is the longest string
			strH = font.getHeight() ;
			x = (maxX/2) - (width / 2) ;
			y = (maxY/2) - strH ;
			
			// Drawing the background black board
			g.setColor(0,0,0) ;
			g.fillRoundRect(x,y,width,(strH * 2) + 3,8,8) ;
			g.setColor(255,255,255) ;
			
			// Drawing the text
			y++ ;
			g.drawString(s,x + ((width - font.stringWidth(s))/2),y,Graphics.TOP|Graphics.LEFT) ;
			
			// Drawing the points
			int hits = 0;
			
			if(stage == 0)
			{
				hits = 6;
			}
			else if(stage == 1)
			{
				hits = 8;
			}
			else if(stage >= 2)
			{
				hits = 14;
			}

			s = "Required : " + String.valueOf(hits) ;
			y++ ;
			g.drawString(s,x + ((width - font.stringWidth(s))/2),y + strH,Graphics.TOP|Graphics.LEFT) ;
			x1=x1-5;
			
			if (x1 < - 100)
			{
				x1 = maxX;
			}
			
			s="Press any Key to Continue";
			g.setColor(255,255,255);
			g.drawString(s,x1,maxY - font.getHeight(),Graphics.TOP|Graphics.LEFT) ;
		}
		else if(screenID == 5)
		{
			gamePaused = true;
			width = font.stringWidth("Unable to Accomplish") + 9 ;		// This is the longest string
			strH = font.getHeight() ;
			x = (maxX/2) - (width / 2) ;
			y = (maxY/2) - strH ;
			
			// Drawing the background black board
			int hit = 0;
			
			if(nohit < 6 && stage == 0)
			{
				hit = 6;
			}
			else if(nohit < 8 && stage == 1)
			{
				hit = 8;
			}
			else if(nohit < 14 && stage == 2)
			{
				hit = 14;
			}

			y1=y1 -3;
			g.setColor(255,255,255);
			g.drawString(s,getWidth()/2,y1,Graphics.HCENTER|Graphics.BASELINE);
		
			//Total Number of Ballons Thrown
			s = "Required : "  + String.valueOf(hit);
			g.drawString(s,getWidth()/2,y1+font.getHeight(),Graphics.HCENTER|Graphics.BASELINE);
		
			if(chances == 0)
			{
				s = "Acheived : "  + String.valueOf(nohit1);
			}
			else
			{
				s = "Acheived : "  + String.valueOf(nohit);
			}

			g.drawString(s,getWidth()/2,y1+20+font.getHeight(),Graphics.HCENTER|Graphics.BASELINE);
			s="Press any Key ";
			g.drawString(s,getWidth()/2,y1+35+font.getHeight(),Graphics.HCENTER|Graphics.BASELINE);
			s="to Continue";
			g.drawString(s,getWidth()/2,y1+50+font.getHeight(),Graphics.HCENTER|Graphics.BASELINE);
			
			if (y1 < - 80)
			{
				y1 = maxY;
			}
		}
		else if(screenID == 6)
		{
			if(nohit < 14)
			{
				setScreen(5);
				return;
			}
			else
			{
				g.setColor(0,0,0) ;
				g.fillRect(0,0,maxX,maxY) ;
				g.setColor(255,255,255) ;
				font = Font.getDefaultFont() ;
				y1= y1 - 5;
		
				g.setColor(0,0,0) ;
				g.setColor(255,255,255) ;
				s ="Holi Hai";
				g.drawString(s,getWidth()/2,y1+font.getHeight()+40,Graphics.HCENTER|Graphics.BASELINE);
				s ="Balle Balle" ;
				g.drawString(s,getWidth()/2,y1+font.getHeight()+55,Graphics.HCENTER|Graphics.BASELINE);
				s ="Game Over";
				g.drawString(s,getWidth()/2,y1+font.getHeight()+70,Graphics.HCENTER|Graphics.BASELINE);
				s ="Points : "+String.valueOf(score);
				g.drawString(s,getWidth()/2,y1+font.getHeight()+85,Graphics.HCENTER|Graphics.BASELINE);
				s ="Press any Key";
				g.drawString(s,getWidth()/2,y1+font.getHeight()+100,Graphics.HCENTER|Graphics.BASELINE);
				s ="For Menu";
				g.drawString(s,getWidth()/2,y1+font.getHeight()+115,Graphics.HCENTER|Graphics.BASELINE);
			
				if(y1<-85)
				{
					y1=maxY;
				}
			}
		}
	}

    protected  void keyPressed(int keyCode)
    {
		int action;
		if(screenID == 1 || screenID == 2)		// Stage Over and Chance Over
		{
			screenID = 4 ;
			sprite[0].setVisible(true) ;		// Making Ankit visible again
			gamePaused = false ;
			col=0;
			tnogb=0;
			return ;
		}
		else if(screenID == 3)					// Game Over
		{
			gameOver() ;
			return ;
		}
		else if(screenID == 4)
		{
			screenID = 0;
			sprite[0].setVisible(true) ;
			setScreen(0);
			gamePaused = false;
			this.infra.addCommand();
			return;
		}
		else if(screenID == 5)
		{
			setScreen(3);
			return;
		}
		else if(screenID == 6)
		{
			gameOver();
			return;
		}
		
		if(keyCode > 49 && keyCode < 57)
		{
			action = -1 ;
		}
		else
		{
			action = getGameAction(keyCode) ;
		}

		if(action == Canvas.UP || keyCode == 50)
		{
			this.action(1) ;	
		}
		else if(action == Canvas.DOWN || keyCode == 56)
		{
			this.action(2) ;	
		}
		else if(action == Canvas.LEFT || keyCode == 52)
		{
			this.action(3) ;	
		}
		else if(action == Canvas.RIGHT || keyCode == 54)
		{
			this.action(4) ;	
		}
		else if(action == Canvas.FIRE || keyCode == 53)
		{
			this.action(5) ;	
		}
		
	}
	
	private void action(int act)
	{
		switch(act)
		{
			case 1:		// Up
			{
				sprite[0].setVisible(true);
				break ;
			}

			case 2:		// Down
			{
				sprite[0].setVisible(false);
				break ;
			}

			case 3:		// Left
			{
				if(sprite[0].getX() > 0)
				{
					state[0] = 3 ;
				}

				break ;
			}

			case 4:		// Right
			{
				if(sprite[0].getX() < (maxX-52))
				{
					state[0] = 4 ;
				}

				break ;
			}

			case 5:		// Fire
			{
				if(sprite[0].isVisible() == true && gamePaused == false)
				{
					state[0] = 5 ;
					balloonBorn() ;
				}
				break ;
			}
		 }
	}
	
	
    protected  void keyReleased(int keyCode)
    {
        state[0] = 0 ;
    }

	/************************************/
    // End Game Canvas Methods
    /************************************/

	/************************************/
    // Pause methods
    /************************************/
    protected boolean getPaused()
    {
        return this.gamePaused ;
    }
    
    protected void setPause(boolean p) 
    {
		if(screenID == 0)
		{
			this.gamePaused = p ;
		}
		ch = true;
    }

	protected void removeCh()
	{
		ch = false;
	}
}