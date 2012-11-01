/*
 * @(#)DemoMIDlet.java	1.1 04/04/24
 *
 * Copyright (c) 2000-2004 Sun Microsystems, Inc. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL
 * Use is subject to license terms
 */
package example.bluetooth.demo;
import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

/**
 * Contains the Bluetooth API demo, that allows to download
 * the specific images from the other devices.
 *
 * @author Vladimir K. Beliaev
 * @version 1.1, 04/24/04
 */
public final class DemoMIDlet extends MIDlet implements CommandListener {

    /** The messages are shown in this demo this amount of time. */
    static final int ALERT_TIMEOUT = 2000;

    /** Soft button for exiting the demo. */
    private final Command EXIT_CMD = new Command("Exit", Command.EXIT, 2);

    /** Soft button for launching a client or sever. */
    private final Command OK_CMD = new Command("Ok", Command.SCREEN, 1);

    /** A list of menu items */
    private static final String[] elements = { "Server", "Client" };

    /** A menu list instance */
    private final List menu = new List("Bluetooth Demo", List.IMPLICIT,
            elements, null);

    /** A GUI part of server that publishes images. */
    private GUIImageServer imageServer;

    /** A GUI part of client that receives image from client */
    private GUIImageClient imageClient;

    /**
     * Constructs main screen of the MIDlet.
     */
    public DemoMIDlet() {
        menu.addCommand(EXIT_CMD);
        menu.addCommand(OK_CMD);
        menu.setCommandListener(this);
    }

    /**
     * Creates the demo view and action buttons.
     */
    public void startApp() {
        show();
    }

    /**
     * Destroys the application.
     */
    protected void destroyApp(boolean unconditional) {
        if (imageServer != null) {
            imageServer.destroy();
        }

        if (imageClient != null) {
            imageClient.destroy();
        }
    }

    /**
     * Does nothing. Redefinition is required by MIDlet class.
     */
    protected void pauseApp() {}

    /**
     * Responds to commands issued on "client or server" form.
     *
     * @param c command object source of action
     * @param d screen object containing actioned item
     */
    public void commandAction(Command c, Displayable d) {
        if (c == EXIT_CMD) {
            destroyApp(true);
            notifyDestroyed();
            return;
        }

        switch (menu.getSelectedIndex()) {
        case 0:
            imageServer = new GUIImageServer(this);
            break;
        case 1:
            imageClient = new GUIImageClient(this);
            break;
        default:
            System.err.println("Unexpected choice...");
            break;
        }
    }

    /** Shows main menu of MIDlet on the screen. */
    void show() {
        Display.getDisplay(this).setCurrent(menu);
    }

    /**
     * Returns the displayable object of this screen -
     * it is required for Alert construction for the error
     * cases.
     */
    Displayable getDisplayable() {
        return menu;
    }
} // end of class 'DemoMIDlet' definition
