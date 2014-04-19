/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.MidServer;

/**
 *
 * @author b1106
 */
public class CommunicationTool {

    private int serverPort;
    private MidServer server;
    private DatagramSocket dataSocket;
    private Receiver receiver;
    private Sender sender;

    public CommunicationTool(MidServer server, int port) {
        this.server = server;
        this.serverPort = port;
        try {
            dataSocket = new DatagramSocket(serverPort);
        } catch (SocketException ex) {
            Logger.getLogger(CommunicationTool.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.receiver = new Receiver(this, dataSocket);
        new Thread(receiver).start();
        this.sender = new Sender(dataSocket);
        
    }


    public void parseData(String ip, int port, String data) {
        server.parseData(ip, port, data);
    }

    public void sendMessage(String ip, int port, String data) {
        sender.sendMessage(ip, port, data);
    }
}
