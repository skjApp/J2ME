/*
 * @(#)GUIImageServer.java	1.2 04/05/28
 *
 * Copyright (c) 2000-2004 Sun Microsystems, Inc. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL
 * Use is subject to license terms
 */
package example.bluetooth.demo;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.Ticker;
import java.io.IOException;
import java.util.Vector;

/**
 * Allows to customize the images list to be published,
 * creates the corresponding service record to discribe this list
 * and send the images to clients by request.
 *
 * @author Vladimir K. Beliaev
 * @version 1.2, 05/28/04
 */
final class GUIImageServer implements CommandListener {

    /** Keeps the help message of this demo. */
    private final String helpText = "The server is started by default.\n\n"
            + "No images are published initially. Change this by corresponding"
            + " commands - the changes have an effect immediately.\n\n"
            + "If image is removed from the published list, it can't "
            + "be download.";

    /** This command goes to demo main screen. */
    private final Command backCommand = new Command("Back", Command.BACK, 2);

    /** Adds the selected image to the published list. */
    private final Command addCommand = new Command("Publish image",
            Command.SCREEN, 1);

    /** Removes the selected image from the published list. */
    private final Command removeCommand = new Command("Remove image",
            Command.SCREEN, 1);

    /** Shows the help message. */
    private final Command helpCommand = new Command("Help", Command.HELP, 1);

    /** The list control to configure images. */
    private final List imagesList = new List("Configure Server", List.IMPLICIT);

    /** The help screen for the server. */
    private final Alert helpScreen = new Alert("Help");

    /** Keeps the parent MIDlet reference to process specific actions. */
    private DemoMIDlet parent;

    /** The list of images file names. */
    private Vector imagesNames;

    /** These images are used to indicate the picture is published. */
    private Image onImage, offImage;

    /** Keeps an information about what images are published. */
    private boolean[] published;

    /** This object handles the real transmission. */
    private BTImageServer bt_server;

    /** Constucts images server GUI. */
    GUIImageServer(DemoMIDlet parent) {
        this.parent = parent;
        bt_server = new BTImageServer(this);
        setupIdicatorImage();
        setupImageList();
        published = new boolean[imagesList.size()];

        // prepare main screen
        imagesList.addCommand(backCommand);
        imagesList.addCommand(addCommand);
        imagesList.addCommand(removeCommand);
        imagesList.addCommand(helpCommand);
        imagesList.setCommandListener(this);

        // prepare help screen
        helpScreen.addCommand(backCommand);
        helpScreen.setTimeout(Alert.FOREVER);
        helpScreen.setString(helpText);
        helpScreen.setCommandListener(this);
    }

    /**
     * Process the command event.
     *
     * @param c - the issued command.
     * @param d - the screen object the command was issued for.
     */
    public void commandAction(Command c, Displayable d) {
        if (c == backCommand && d == imagesList) {
            destroy();
            parent.show();
            return;
        }

        if (c == backCommand && d == helpScreen) {
            Display.getDisplay(parent).setCurrent(imagesList);
            return;
        }

        if (c == helpCommand) {
            Display.getDisplay(parent).setCurrent(helpScreen);
            return;
        }

        /*
         * Changing the state of base of published images
         */
        int index = imagesList.getSelectedIndex();

        // nothing to do
        if ((c == addCommand) == published[index]) {
            return;
        }

        // update information and view
        published[index] = c == addCommand;
        Image stateImg = c == addCommand ? onImage : offImage;
        imagesList.set(index, imagesList.getString(index), stateImg);

        // update bluetooth service information
        if (!bt_server.changeImageInfo(imagesList.getString(index),
                published[index])) {

            // either a bad record or SDDB is buzy
            Alert al = new Alert("Error", "Can't update base", null,
                    AlertType.ERROR);
            al.setTimeout(DemoMIDlet.ALERT_TIMEOUT);
            Display.getDisplay(parent).setCurrent(al, imagesList);

            // restore internal information
            published[index] = !published[index];
            stateImg = published[index] ? onImage : offImage;
            imagesList.set(index, imagesList.getString(index), stateImg);
        }
    }

    /**
     * We have to provide this method due to "do not do network
     * operation in command listener method" restriction, which
     * is caused by crooked midp design.
     *
     * This method is called by BTImageServer after it is done
     * with bluetooth initialization and next screen is ready
     * to appear.
     */
    void completeInitialization(boolean isBTReady) {

        // bluetooth was initialized successfully.
        if (isBTReady) {
            Ticker t = new Ticker("Choose images you want to publish...");
            imagesList.setTicker(t);
            Display.getDisplay(parent).setCurrent(imagesList);
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

        // finilize the image server work
        bt_server.destroy();
    }

    /** Gets the image file name from its title (label). */
    String getImageFileName(String imgName) {
        if (imgName == null) {
            return null;
        }

        // no interface in List to get the index - should find
        int index = -1;

        for (int i = 0; i < imagesList.size(); i++) {
            if (imagesList.getString(i).equals(imgName)) {
                index = i;
                break;
            }
        }

        // not found or not published
        if (index == -1 || !published[index]) {
            return null;
        }
        return (String) imagesNames.elementAt(index);
    }

    /**
     * Creates the image to idicate the base state.
     */
    private void setupIdicatorImage() {

        // create "on" image
        try {
            onImage = Image.createImage("/images/st-on.png");
        } catch (IOException e) {

            // provide off-screen image then
            onImage = createIndicatorImage(12, 12, 0, 255, 0);
        }

        // create "off" image
        try {
            offImage = Image.createImage("/images/st-off.png");
        } catch (IOException e) {

            // provide off-screen image then
            offImage = createIndicatorImage(12, 12, 255, 0, 0);
        }
    }

    /**
     * Gets the description of images from manifest and
     * prepares the list to contol the configuration.
     * <p>
     * The attributes are named "ImageTitle-n" and "ImageImage-n".
     * The value "n" must start at "1" and be incremented by 1.
     */
    private void setupImageList() {
        imagesNames = new Vector();
        imagesList.setCommandListener(this);

        for (int n = 1; n < 100; n++) {
            String name = parent.getAppProperty("ImageName-" + n);

            // no more images available
            if (name == null || name.length() == 0) {
                break;
            }
            String label = parent.getAppProperty("ImageTitle-" + n);

            // no lable available - use picture name instead
            if (label == null || label.length() == 0) {
                label = name;
            }
            imagesNames.addElement(name);
            imagesList.append(label, offImage);
        }
    }

    /**
     * Creates the off-screen image with specified size an color.
     */
    private Image createIndicatorImage(int w, int h, int r, int g, int b) {
        Image res = Image.createImage(w, h);
        Graphics gc = res.getGraphics();
        gc.setColor(r, g, b);
        gc.fillRect(0, 0, w, h);
        return res;
    }
} // end of class 'GUIImageServer' definition
