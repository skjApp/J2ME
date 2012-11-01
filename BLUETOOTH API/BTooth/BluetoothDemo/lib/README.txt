            JSR082 API (BLUETOOTH) DEMONSTRATION MIDLETS

--------------------------------------------------------------------------------
1. Introduction

   The BluetoothDemo consist in two MIDlets:

   a) Bluetooth Demo - allows to exchange pictures between phones.
      It's intended to be run on two or more phones.

   b) Bluetooth Print Image Demo - demonstrates the communication between
      J2ME phone and some J2SE Bluetooth service.

--------------------------------------------------------------------------------
2. Bluetooth Demo

   2.1 The demo consist in 2 parts - client and server. You may run several
       clients or servers if you want.

   2.2 If you run the server, the bluetooth notifier is created (so you may be
       asked with security question) and it is accepting the clients
       already. The corresponding service record contains the attribute with
       the published images information.

       By default no images are published!

       Use "Publish image" and "Remove image" commands in MIDlet menu to
       add/remove the corresponding images information to/from service record.

       The published images are highlighted in the selection list.

   2.3 If you run the client and bluetooth system is initialized successfully,
       you see the "Ready for images search!" message. Select "Find" command
       to start a search.

       In fact, such a search includes both device and service
       discovering. The device search is done for PREKNOWN and CACHED devices
       too.

       You may cancel the device/service search - then you get the main client
       display.

       Select the image you want to download and choose "Load" command. You
       may cancel the image download. After you review the image, go back to
       found images list to choose another image to be download or to return
       back to new images each.

   2.4 The server is created with the URL, that indicates each connected
       client should be AUTHORIZED.

       I.e. when client is downloading the image, the corresponding server
       phone shows user the security dialog with remote device friendly name
       (if it is available) and bluetooth address.

   2.5 If several servers published the same images, the "union" list is shown
       on the client as a search result. This application hides the
       information from user what server (bluetooth device) publish these
       images.

       Still, all of the available sources (servers) for the image are stored
       in the client application. When user chooses to download image, the
       first source is attempted. In case of I/O error, next source is used
       and so on.

       After the images search and before the image download the server may
       shutdown or just "Remove image" from published list. In this case the
       client fails to download image from this server.

--------------------------------------------------------------------------------
3. Bluetooth Print Image Demo

   3.1 This demo shows the way to print the picture from J2ME (phone) to
       printer.

       NOTE: this demo does NOT use the Print Profile. A simple data exchange
       protocol over bluetooth is implemented in this demo, then printer is
       accessed though standard J2SE API (java.awt.print, javax.print).

   3.2 The J2SE part of the demo is located in 'j2seBluetoothDemo'
       subdirectory. See the corresponding README for J2SE part to be able to
       build and run this part.

   3.3 The J2ME part of demo is run in Emulator phone. It's a bluetooth
       client.

       First, the list of available images is shown. One may select the image
       to be printed. After the image has been selected, the available print
       services search is started.

       The print services search is actually the device/service discovering
       process. If the required services are found, the list of available
       bluetooth devices is shown (the bluetooth addresses are used here).

       Choose the printer device to send the image to - then J2SE service
       print it.

   3.3 The J2SE part (server) does not contain the demo specific controlling
       GUI.

       First, the standard Print Settings panel is shown. Choose the printer
       settings you want to use and press "Print" button.

       Then the "Page Setup" panel is shown. Choose the settings you want to
       use and press "Ok".

       Note, that printer/page settings appears at very beginning of J2SE part
       start one time only. No way to reconfigure these settings later.

       Finally, the "Print Monitor" is shown. At this moment the bluetooth
       system is initialized, the corresponding server is created and it is
       put to be accepting client. This panel shows the most recent download
       image to be printed. You may exit this panel only which means you exit
       J2SE application.

--------------------------------------------------------------------------------
Tue May 18 15:22:19 2004
