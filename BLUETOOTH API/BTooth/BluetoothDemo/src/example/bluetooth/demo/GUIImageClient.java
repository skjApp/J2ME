/*
 * @(#)GUIImageClient.java	1.2 04/06/24
 *
 * Copyright (c) 2000-2004 Sun Microsystems, Inc. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL
 * Use is subject to license terms
 */
package example.bluetooth.demo;

// midp GUI classes
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.StringItem;

// midp/cldc classes
import java.io.IOException;
import java.util.Hashtable;
import java.util.Enumeration;

/**
 * Provides a GUI to present the download options
 * to used, gives a chance to make a choice,
 * finally shows the downloaded image.
 *
 * @version 1.2, 06/24/04
 */
final class GUIImageClient implements CommandListener {

    /** This command goes to demo main screen. */
    private final Command SCR_MAIN_BACK_CMD = new Command("Back", Command.BACK,
            2);

    /** Starts the proper services search. */
    private final Command SCR_MAIN_SEARCH_CMD = new Command("Find", Command.OK,
            1);

    /** Cancels the device/services discovering. */
    private final Command SCR_SEARCH_CANCEL_CMD = new Command("Cancel",
            Command.BACK, 2);

    /** This command goes to client main screen. */
    private final Command SCR_IMAGES_BACK_CMD = new Command("Back",
            Command.BACK, 2);

    /** Start the chosen image download. */
    private final Command SCR_IMAGES_LOAD_CMD = new Command("Load", Command.OK,
            1);

    /** Cancels the image download. */
    private final Command SCR_LOAD_CANCEL_CMD = new Command("Cancel",
            Command.BACK, 2);

    /** This command goes from image screen to images list one. */
    private final Command SCR_SHOW_BACK_CMD = new Command("Back", Command.BACK,
            2);

    /** The main screen of the client part. */
    private final Form mainScreen = new Form("Image Viewer");

    /** The screen with found images names. */
    private final List listScreen = new List("Image Viewer", List.IMPLICIT);

    /** The screen with download image. */
    private final Form imageScreen = new Form("Image Viewer");

    /** Keeps the parent MIDlet reference to process specific actions. */
    private DemoMIDlet parent;

    /** This object handles the real transmission. */
    private BTImageClient bt_client;

    /** Constucts client GUI. */
    GUIImageClient(DemoMIDlet parent) {
        this.parent = parent;
        bt_client = new BTImageClient(this);
        mainScreen.addCommand(SCR_MAIN_BACK_CMD);
        mainScreen.addCommand(SCR_MAIN_SEARCH_CMD);
        mainScreen.setCommandListener(this);
        listScreen.addCommand(SCR_IMAGES_BACK_CMD);
        listScreen.addCommand(SCR_IMAGES_LOAD_CMD);
        listScreen.setCommandListener(this);
        imageScreen.addCommand(SCR_SHOW_BACK_CMD);
        imageScreen.setCommandListener(this);
    }

    /**
     * Process the command events.
     *
     * @param c - the issued command.
     * @param d - the screen object the command was issued for.
     */
    public void commandAction(Command c, Displayable d) {

        // back to demo main screen
        if (c == SCR_MAIN_BACK_CMD) {
            destroy();
            parent.show();
            return;
        }

        // starts images (device/services) search
        if (c == SCR_MAIN_SEARCH_CMD) {
            Form f = new Form("Searching...");
            f.addCommand(SCR_SEARCH_CANCEL_CMD);
            f.setCommandListener(this);
            f.append(new Gauge("Searching images...", false, Gauge.INDEFINITE,
                    Gauge.CONTINUOUS_RUNNING));
            Display.getDisplay(parent).setCurrent(f);
            bt_client.requestSearch();
            return;
        }

        // cancels device/services search
        if (c == SCR_SEARCH_CANCEL_CMD) {
            bt_client.cancelSearch();
            Display.getDisplay(parent).setCurrent(mainScreen);
            return;
        }

        // back to client main screen
        if (c == SCR_IMAGES_BACK_CMD) {
            bt_client.requestLoad(null);
            Display.getDisplay(parent).setCurrent(mainScreen);
            return;
        }

        // starts image download
        if (c == SCR_IMAGES_LOAD_CMD) {
            Form f = new Form("Loading...");
            f.addCommand(SCR_LOAD_CANCEL_CMD);
            f.setCommandListener(this);
            f.append(new Gauge("Loading image...", false, Gauge.INDEFINITE,
                    Gauge.CONTINUOUS_RUNNING));
            Display.getDisplay(parent).setCurrent(f);
            List l = (List) d;
            bt_client.requestLoad(l.getString(l.getSelectedIndex()));
            return;
        }

        // cancels image load
        if (c == SCR_LOAD_CANCEL_CMD) {
            bt_client.cancelLoad();
            Display.getDisplay(parent).setCurrent(listScreen);
            return;
        }

        // back to client main screen
        if (c == SCR_SHOW_BACK_CMD) {
            Display.getDisplay(parent).setCurrent(listScreen);
            return;
        }
    }

    /**
     * We have to provide this method due to "do not do network
     * operation in command listener method" restriction, which
     * is caused by crooked midp design.
     *
     * This method is called by BTImageClient after it is done
     * with bluetooth initialization and next screen is ready
     * to appear.
     */
    void completeInitialization(boolean isBTReady) {

        // bluetooth was initialized successfully.
        if (isBTReady) {
            StringItem si = new StringItem("Ready for images search!", null);
            si.setLayout(StringItem.LAYOUT_CENTER | StringItem.LAYOUT_VCENTER);
            mainScreen.append(si);
            Display.getDisplay(parent).setCurrent(mainScreen);
            return;
        }

        // something wrong
        Alert al = new Alert("Error", "Can't inititialize bluetooth", null,
                AlertType.ERROR);
        al.setTimeout(DemoMIDlet.ALERT_TIMEOUT);
        Display.getDisplay(parent).setCurrent(al, parent.getDisplayable());
    }

    /** Destroys this component. */
    void destroy() {

        // finilize the image client work
        bt_client.destroy();
    }

    /**
     * Informs the error during the images search.
     */
    void informSearchError(String resMsg) {
        Alert al = new Alert("Error", resMsg, null, AlertType.ERROR);
        al.setTimeout(DemoMIDlet.ALERT_TIMEOUT);
        Display.getDisplay(parent).setCurrent(al, mainScreen);
    }

    /**
     * Informs the error during the selected image load.
     */
    void informLoadError(String resMsg) {
        Alert al = new Alert("Error", resMsg, null, AlertType.ERROR);
        al.setTimeout(DemoMIDlet.ALERT_TIMEOUT);
        Display.getDisplay(parent).setCurrent(al, listScreen);
    }

    /**
     * Shows the downloaded image.
     */
    void showImage(Image img, String imgName) {
        imageScreen.deleteAll();
        imageScreen.append(new ImageItem(imgName, img,
                            ImageItem.LAYOUT_CENTER | ImageItem.LAYOUT_VCENTER,
                            "Downloaded image: " + imgName));
        Display.getDisplay(parent).setCurrent(imageScreen);
    }

    /**
     * Shows the available images names.
     *
     * @returns false if no images names were found actually
     */
    boolean showImagesNames(Hashtable base) {
        Enumeration keys = base.keys();

        // no images actually
        if (!keys.hasMoreElements()) {
            informSearchError("No images names in found services");
            return false;
        }

        // prepare the list to be shown
        while (listScreen.size() != 0) {
            listScreen.delete(0);
        }

        while (keys.hasMoreElements()) {
            listScreen.append((String) keys.nextElement(), null);
        }
        Display.getDisplay(parent).setCurrent(listScreen);
        return true;
    }
} // end of class 'GUIImageClient' definition
