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
 * @version 1.0.0
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
        
    }
    public void startApp() 
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
			bluetooth() ;
		}
		else if(c == stop)
		{
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

	private void send()
	{
		try
		{
			String address ;

			//for(int i = 0;i < adds.size();i++)
			{
				address = (String)adds.elementAt(0) ;
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
			// System.out.println("btl2cap://" + ((RemoteDevice)rDevices.elementAt(i)).getBluetoothAddress() + ":1003") ;
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