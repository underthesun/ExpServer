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
 * 服务器端对底层通信进行封装的工具类
 *
 * @author b1106
 */
public class CommunicationTool {

    private int serverPort;
    private MidServer server;
    private DatagramSocket dataSocket;
    private Receiver receiver;
    private Sender sender;

    /**
     * 构造通信工具实例。 根据MidServer传递的端口号构造UDP套接字，构造Receiver和Sender的实例用以收发数据包
     *
     * @param server 服务器端实例
     * @param port 端口号
     */
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

    /**
     * 通信工具类数据解析接口，通过调用MidServer的数据解析方法parseData对数据进行解析
     *
     * @param ip 数据包地址
     * @param port 数据包端口号
     * @param data 数据
     * @see MidServer#sendMessage(java.lang.String, int, java.lang.String) 
     */
    public void parseData(String ip, int port, String data) {
        server.parseData(ip, port, data);
    }

    /**
     * 通信工具数据发送接口，通过调用Sender的信息发送方法sendMessage发送数据
     *
     * @param ip 数据包地址
     * @param port 数据包端口号
     * @param data 数据
     * @see Sender#sendMessage(java.lang.String, int, java.lang.String)
     */
    public void sendMessage(String ip, int port, String data) {
        sender.sendMessage(ip, port, data);
    }
}
