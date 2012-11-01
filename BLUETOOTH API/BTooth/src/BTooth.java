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
 * Practice Example : 15
 * This example demonstrates the basic functionality of Bluetooth API.
 * For proper results please run this example's JAR file on a J2ME device having Bluetooth API in it.
 */

public class BTooth extends javax.microedition.midlet.MIDlet implements CommandListener,DiscoveryListener
{
	// LCDUI variables
    private Display display ;
	private Form form ;
	private Command search ;
	private Command send ;
	private Command stop ;
	private Command exit ;

	private boolean init ;
	
	// Bluetooth variables
	private LocalDevice localDevice ;
    private DiscoveryAgent dAgent ;
	private L2CAPConnection conn ;

	private Vector rDevices ;
	private Vector adds ;
	private Thread thread ;
	private Runnable TRun ;

    public BTooth()
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
			rDevices = new Vector() ;
			adds = new Vector() ;

			search = new Command("Search",Command.OK,1) ;
			send = new Command("Send",Command.OK,1) ;
			stop = new Command("Stop",Command.STOP,1) ;
			exit = new Command("Exit",Command.EXIT,1) ;

			form = new Form("Bluetooth") ;
			form.addCommand(search) ;
			form.addCommand(send) ;
			form.addCommand(stop) ;
			form.addCommand(exit) ;
			form.setCommandListener(this) ;	

			init = true ;
		}

		display.setCurrent(form) ;
    }
    
    public void pauseApp() 
    {
        
    }
    
    public void destroyApp(boolean unconditional) 
    {
        
    }
    
    public void setDisplayable(Displayable d)
    {
        this.display.setCurrent(d) ;
    }

	public void commandAction(Command c,Displayable d)
	{
		if(c == search)
		{
			rDevices.removeAllElements() ;
			bluetooth() ;
		}
		else if(c == stop)
		{
			// Cancels the device search
			dAgent.cancelInquiry(this) ;
		}
		else if(c == send)
		{
			thread.start() ;
		}
		else
		{
			notifyDestroyed() ;
		}
	}

	private void bluetooth()
	{
		try
		{
			// Getting the local device
			localDevice = LocalDevice.getLocalDevice() ;
			localDevice.setDiscoverable(DiscoveryAgent.GIAC) ;

			// Getting the DiscoveryAgent for the local device
			dAgent = localDevice.getDiscoveryAgent() ;

			// Starting the search for active devices
			// This will take time, so be patient while this code is runing on your device
			dAgent.startInquiry(DiscoveryAgent.GIAC,this) ;

			thread = new Thread()
			{
				public void run()
				{
					send() ;
				}
			};
		}
		catch(BluetoothStateException bse)
		{
			bse.printStackTrace() ;
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
			String address ;

			for(int i = 0;i < adds.size();i++)
			{
				address = (String)adds.elementAt(i) ;
				conn = (L2CAPConnection)Connector.open(address);
				conn.send("Hello".getBytes()) ;
			}
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
		rDevices.addElement(btDevice) ;
	}

	// Called when an inquiry is completed.
	public void inquiryCompleted(int discType)
	{
		form.append(rDevices.size() + " devices discovered.") ;
		
		for(int i = 0;i < rDevices.size() ;i++ )
		{
			
			// Adds the address of the remote bluetooth device
			adds.addElement(   "btl2cap://" + ((RemoteDevice)rDevices.elementAt(i)).getBluetoothAddress() + ":1003;ReceiveMTU=512;TransmitMTU=512") ;
		}
	}

	// Called when service(s) are found during a service search.
	public void servicesDiscovered(int transID, ServiceRecord[] servRecord)
	{
		
	}

	// Called when a service search is completed or was terminated because of an error.
	public void serviceSearchCompleted(int transID, int respCode)
	{
		
	}
}