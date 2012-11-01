/*
 * BTooth.java
 *
 * Created on April 27, 2004, 11:54 PM
 */


import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.io.* ;
import javax.bluetooth.* ;
import java.util.* ;

/**
 *
 * @author  Saurabh Jain
 * @version 1.0
 * Practice Example : Bluetooth 2
 * This example demonstrates the basic functionality of Bluetooth API.
 * For proper results please run this example's JAR file on a J2ME device having Bluetooth API in it.
 */

public class BluetoothServerMIDlet extends javax.microedition.midlet.MIDlet implements CommandListener
{
	// LCDUI variables
    private Display display ;
	private Form form ;
	private Command search ;
	private Command server ;
	private Command send ;
	private Command receive ;
	private Command stop ;
	private Command exit ;

	private boolean init ;
	
	// Bluetooth variables
	private LocalDevice localDevice ;
    private DiscoveryAgent dAgent ;
	private L2CAPConnectionNotifier serverConnNotifier ;
	private L2CAPConnection serverConn ;

	/** Describes this server */
    private static final UUID MY_UUID = new UUID("F0E0D0C0B0A000908070605040302010", false);

	 /** Keeps the information about this server. */
    private ServiceRecord record;

	private Thread thread ;

    public BluetoothServerMIDlet()
    {
        // NOTE : Heavy initialisations should be avoided in the constructor of the MIDlet.
		// The MIDlet is in the paused state when the constructor is called.
		// It does not have access to the resources it needs at this stage.
		// Therefore all heavy initialisations should be done in the way shown below in 
		// the startApp method.
    }
    public void startApp() 
    {
		// NOTE : The startApp method is called whenever the Application Management Software(AMS)
		// decides that the MIDlet needs to get activated.
		// During the lifecycle of particular instance of the application this situation may arise
		// many times. Therefore to save un-necessary initialisations every time the startApp method
		// is called by the AMS, we should use a boolean variable to control the number of initialisations to 1.

		if(init == false)
		{
			this.display = Display.getDisplay(this) ;

			receive = new Command("Receive",Command.SCREEN,1) ;
			exit = new Command("Exit",Command.EXIT,1) ;
			form = new Form("Bluetooth Server") ;
			form.addCommand(receive) ;
			form.addCommand(exit) ;
			form.setCommandListener(this) ;	
			display.setCurrent(form) ;

			try
			{
				// Getting the local device
				localDevice = LocalDevice.getLocalDevice() ;
				localDevice.setDiscoverable(DiscoveryAgent.GIAC) ;	// Setting status to General/Unlimited Inquiry Access Code (GIAC) mode

				thread = new Thread()
				{
					public void run()
					{
						startServer() ;
					}
				};

				thread.run() ;
			}
			catch(BluetoothStateException bse)
			{
				bse.printStackTrace() ;
			}


			init = true ;
		}
    }
    
    public void pauseApp() 
    {
        
    }
    
    public void destroyApp(boolean unconditional) 
    {
        
    }
    
    public void commandAction(Command c,Displayable d)
	{
		if(c == receive)
		{
			try
			{
				Thread thread1 = new Thread()
				{
					public void run()
					{
						receive() ;
					}
				};

				thread1.start() ;
			}
			catch (Exception e)
			{
				e.printStackTrace() ;
			}
			
		}
		else
		{
			notifyDestroyed() ;
		}
	}

	private void startServer()
	{
		try
		{
				serverConnNotifier = (L2CAPConnectionNotifier)Connector.open("btl2cap://localhost:" + MY_UUID.toString() + ";ReceiveMTU=512;TransmitMTU=512");
				serverConn = serverConnNotifier.acceptAndOpen() ;
				record = localDevice.getRecord(serverConnNotifier);
		}
		catch(Exception e)
		{
			e.printStackTrace() ;
		}
	}
	
	private void receive()
	{
		byte [] data ;
		data = new byte[100] ;

		try
		{
			if (serverConn.ready() == true)
			{
				serverConn.receive(data) ;
			}

			form.deleteAll() ;
			form.append(new String(data)) ;
		}
		catch(Exception e)
		{
			e.printStackTrace() ;
		}
	}
}