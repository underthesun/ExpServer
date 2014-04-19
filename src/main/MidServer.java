/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import communication.CommunicationTool;
import communication.Message;
import integrityvalidating.IntegrityController;
import java.io.File;
import processmodeling.GraphManager;
import util.Configurator;
import processmodeling.Process;
import test.TestServer;

/**
 *
 * @author b1106
 */
public class MidServer {

    public TestServer frame;
    private GraphManager graphManager;
    private CommunicationTool communicationTool;
    private Gson gson;
    private Configuration configuration;
    private IntegrityController controller;

    public MidServer() {
        configuration = Configurator.parseConfiguration(new File("server_conf.json"));
        graphManager = new GraphManager(this, configuration.HEARTBEAT_THRESHOLD);
        graphManager.setThreshold(configuration.getHEARTBEAT_THRESHOLD());

        communicationTool = new CommunicationTool(this, configuration.getSERVER_PORT());

        gson = new GsonBuilder().setPrettyPrinting().create();

        controller = new IntegrityController(this);
    }

    public void parseData(String addr, int port, String data) {
        Message m = gson.fromJson(data, Message.class);
        String mType = m.getType();
        if (mType.equals(Message.REGISTER)) {
            graphManager.register(m.getId(), addr, port);
            infoUI(m);
        } else if (mType.equals(Message.HEARTBEAT)) {
            graphManager.heartbeat(m.getId(), addr, port);
            infoUI(m);
        } else if (mType.equals(Message.INT_START)) {
            controller.startSim();
        }
    }

    public void sendMessage(String ip, int port, String data) {
        communicationTool.sendMessage(ip, port, data);
    }

    public void sendMessage(Process p, String data) {
        communicationTool.sendMessage(p.getAddr(), p.getPort(), data);
    }

    public void infoUI(Message m) {
        communicationTool.sendMessage(configuration.UI_ADDR, configuration.UI_PORT, gson.toJson(m, Message.class));
    }

    public GraphManager getGraphManager() {
        return graphManager;
    }

    public void setGraphManager(GraphManager graphManager) {
        this.graphManager = graphManager;
    }
}
