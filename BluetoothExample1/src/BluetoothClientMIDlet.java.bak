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

public class BluetoothClientMIDlet extends javax.microedition.midlet.MIDlet implements CommandListener,DiscoveryListener
{
	// LCDUI variables
    private Display display ;
	private Form form ;
	private Command connect ;
	private Command send ;
	private Command exit ;

	private boolean init ;
	
	// Bluetooth variables
	private LocalDevice localDevice ;
    private DiscoveryAgent dAgent ;
	private L2CAPConnection conn ;

	/** Describes this server */
    private static final UUID MY_UUID = new UUID("F0E0D0C0B0A000908070605040302010", false);

	 /** Keeps the information about this server. */
    private ServiceRecord record;

	private String serverAdd ;

    public BluetoothClientMIDlet()
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

			connect = new Command("Connect",Command.SCREEN,1) ;
			send = new Command("Send",Command.SCREEN,1) ;
			exit = new Command("Exit",Command.EXIT,1) ;

			form = new Form("Bluetooth Client") ;
			form.addCommand(connect) ;
			form.addCommand(send) ;
			form.addCommand(exit) ;
			form.setCommandListener(this) ;	

			try
			{
				// Getting the local device
				localDevice = LocalDevice.getLocalDevice() ;
				localDevice.setDiscoverable(DiscoveryAgent.GIAC) ;	// Setting status to General/Unlimited Inquiry Access Code (GIAC) mode

				// Getting the DiscoveryAgent for the local device
				dAgent = localDevice.getDiscoveryAgent() ;
			}
			catch(BluetoothStateException bse)
			{
				bse.printStackTrace() ;
			}


			init = true ;
		}

		display.setCurrent(form) ;
    }
    
    public void pauseApp() 
    {
        
    }
    
    public void destroyApp(boolean unconditional) 
    {
        try
		{
			conn.close() ;
		}
		catch(Exception e)
		{
			e.printStackTrace() ;
		}
    }
    
    public void setDisplayable(Displayable d)
    {
        this.display.setCurrent(d) ;
    }

	public void commandAction(Command c,Displayable d)
	{
		if(c == connect)
		{
			try
			{
				// Starting the search for active devices
				// This will take time, so be patient while this code is runing on your device
				dAgent.startInquiry(DiscoveryAgent.GIAC,this) ;
			}
			catch(BluetoothStateException bse)
			{
				bse.printStackTrace() ;
			}
		}
		/*else if(c == stop)
		{
			// Cancels the device search
			dAgent.cancelInquiry(this) ;
		}*/
		else if(c == send)
		{	
			Thread thread = new Thread()
			{
				public void run()
				{
					send() ;
				}
			};

			thread.start() ;
		}
		else
		{
			notifyDestroyed() ;
		}
	}

	// This method is just a demonstration of how to send data.
	// This data would not be displayed on any device as we are not sending any
	// recognizable object to a bluetooth enabled device. But please note the
	// change in the status of the bluetooth icon on the phone and device.
	// When my Nokia 7610 is sending data to my desktop using this method my desktop's bluetooth
	// icon changes from white to green signifying the receiving of data. Also a small bluetooth
	// icon emerges on top right corner of my Nokia 7610 for the time the data is being sent.
	private void send()
	{
		try
		{
			conn.send("I have done it!".getBytes()) ;
		}
		catch(Exception e)
		{
			e.printStackTrace() ;
		}
	}

	// DiscoveryListener Methods

	// Called when a device is found during an inquiry.
	public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod)
	{
		UUID [] UUIDS = {MY_UUID} ;
		
		try
		{
			dAgent.searchServices(null,UUIDS,btDevice,this) ;
			System.out.println("Device : " + btDevice.getBluetoothAddress()) ;
		}
		catch ( Exception e)
		{
			e.printStackTrace() ;
		}
	}

	// Called when an inquiry is completed.
	public void inquiryCompleted(int discType)
	{
		
	}

	// Called when service(s) are found during a service search.
	public void servicesDiscovered(int transID, ServiceRecord[] servRecord)
	{
		try
		{
			serverAdd = servRecord[0].getConnectionURL(ServiceRecord.AUTHENTICATE_ENCRYPT,false);
			conn = (L2CAPConnection)Connector.open(serverAdd + ";ReceiveMTU=512;TransmitMTU=512");
			System.out.println("Server Connection URL - " + serverAdd) ;
		}
		catch (Exception e)
		{
			e.printStackTrace() ;
		}
	}

	// Called when a service search is completed or was terminated because of an error.
	public void serviceSearchCompleted(int transID, int respCode)
	{
		
	}
}