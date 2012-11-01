/*
 * WMADemoMIDlet.java
 *
 * Created on September 2, 2004, 5:41 PM
 */

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.io.* ;
import javax.wireless.messaging.* ;

/**
 *
 * @author  Saurabh Jain
 * @version 1.0
 * Practice Example : 13
 * This example demonstrates the TextMessage interface of Wireless Messaging API as contained in JSR-120.
 *
 */

public class WMADemoMIDlet extends MIDlet implements CommandListener
{   
	private Display display;    // The display for this MIDlet

    private Command send;		// The command for sending a text message
	private Command receive;	// The command for expressing an interest in receiving a text message
    private Command back;		// The command for going back to the text box

	private TextBox t ;
	private Form f ;
	private TextField tf ;

	private MessageConnection conn ;
	private MyListener ml ;

    private boolean init ;

    public WMADemoMIDlet()
    {
		// NOTE : Heavy initialisations should be avoided in the constructor of the MIDlet.
		// The MIDlet is in the paused state when the constructor is called.
		// It does not have access to the resources it needs at this stage.
		// Therefore all heavy initialisations should be done in the way shown below in 
		// the startApp method.
    }
    
    /**
     * Start up the Hello MIDlet by creating the TextBox and associating
     * the exit command and listener.
     */
    public void startApp()
    {
		// NOTE : The startApp method is called whenever the Application Management Software(AMS)
		// decides that the MIDlet needs to get activated.
		// During the lifecycle of particular instance of the application this situation may arise
		// many times. Therefore to save un-necessary initialisations every time the startApp method
		// is called by the AMS, we should use a boolean variable to control the number of initialisations to 1.

        if(init == false)
		{
			display = Display.getDisplay(this);

			send = new Command("Send", Command.SCREEN, 1);
			receive = new Command("Receive", Command.SCREEN, 2);
			back = new Command("Back", Command.SCREEN, 2);

			t = new TextBox("Message","",160,TextField.ANY) ;

			f = new Form("Address") ;
			tf = new TextField("Phone Number","sms://+",160,TextField.ANY) ;

			t.addCommand(send);
			t.addCommand(receive);
			t.setCommandListener(this);

			f.addCommand(send);
			f.addCommand(back);
			f.setCommandListener(this);

			ml = new MyListener(this) ;

			init = true ;
		}
        
        display.setCurrent(t);
    }
    
    /**
     * Pause is a no-op since there are no background activities or
     * record stores that need to be closed.
     */
    public void pauseApp()
    {
        
    }
    
    /**
     * Destroy must cleanup everything not handled by the garbage collector.
     * In this case there is nothing to cleanup.
     */
    public void destroyApp(boolean unconditional)
    {
		try
		{
			// Will throw exception but necessary to empty instance
			conn.setMessageListener(null) ;
			conn.close() ;
		}
		catch(Exception e)
		{
			e.printStackTrace() ;
		}
    }
    
    /*
     * Respond to commands, including exit
     * On the exit command, cleanup and notify that the MIDlet has been destroyed.
     */
    public void commandAction(Command c, Displayable d)
    {
		if(d == t)
		{
			if(c == send)
			{
				while(f.size() > 0)
				{
					f.delete(0) ;
				}

				f.append("Please enter the number of the mobile phone to which you want to send an SMS") ;
				f.append(tf) ;

				display.setCurrent(f);
			}
			else if(c == receive)
			{
				MyThreadReceive mtr = new MyThreadReceive(this) ;
				mtr.start() ;
			}
		}
		else if(d == f)
		{
			if(c == send)
			{
				MyThreadSend mts = new MyThreadSend(this,t.getString(), tf.getString()) ;
				mts.start() ;
			}
			else if(c == back)
			{
				display.setCurrent(t);
			}
		}
    }
    
	public void sendMessage(String message,String adr)
	{
		try
		{
			conn = null ;
			conn = (MessageConnection) Connector.open(adr + ":6222");
			TextMessage msg = (TextMessage)conn.newMessage(MessageConnection.TEXT_MESSAGE);

			msg.setPayloadText(message);
			conn.send(msg);
		}
		catch (Exception e)
		{
			e.printStackTrace() ;
		}
	}

	public void receiveMessage()
	{
		try
		{
			while(f.size() > 0)
			{
				f.delete(0) ;
			}

			conn = null ;
			conn = (MessageConnection) Connector.open("sms://:6222");
			conn.setMessageListener(ml) ;
		}
		catch(Exception e)
		{
			Alert a = new Alert("Error") ;
			a.setString(e.toString()) ;
			a.setTimeout(Alert.FOREVER) ;
			display.setCurrent(a,t) ;
		}
	}

	public void messageReceived()
	{
		TextMessage tm ;

		try
		{
			tm = (TextMessage) conn.receive() ;
			t.setString(tm.getPayloadText()) ;
		}
		catch(Exception e)
		{
			e.printStackTrace() ;
		}

		// In this example we are displaying the received message in TextBox
		display.setCurrent(t);
	}
}

class MyListener implements MessageListener
{
	private WMADemoMIDlet parent ;

	public MyListener(WMADemoMIDlet lParent)
	{
		parent = lParent ;
	}

	public void notifyIncomingMessage(MessageConnection conn)
	{
		try
		{
			parent.messageReceived() ;
		}
		catch(Exception e)
		{
			e.printStackTrace() ;
		}
	}
}

class MyThreadSend extends Thread
{
	private WMADemoMIDlet wma ;
	private String msg ;
	private String add ;

	public MyThreadSend(WMADemoMIDlet w,String ms,String ad)
	{
		wma = w ;
		msg = ms ;
		add = ad ;
	}

	public void run()
	{
		wma.sendMessage(msg,add) ;
	}
}

class MyThreadReceive extends Thread
{
	private WMADemoMIDlet wma ;

	public MyThreadReceive(WMADemoMIDlet w)
	{
		wma = w ;
	}

	public void run()
	{
		wma.receiveMessage() ;
	}
}