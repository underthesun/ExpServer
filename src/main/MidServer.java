/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import communication.CommunicationTool;
import communication.Message;
import integrityvalidating.IntegrityController;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import processmodeling.GraphManager;
import util.Configurator;
import processmodeling.Process;
import util.processgraph.ProcessSim;
import util.processgraph.ProcessSimUtil;

/**
 * 服务器端类，包括了过程建模，数据完整性验证和分布式查询处理等各项功能
 *
 * @author b1106
 * @see GraphManager
 * @see CommunicationTool
 * @see Configuration
 * @see Configurator
 * @see IntegrityController
 * @see Gson
 */
public class MidServer {

    private GraphManager graphManager;
    private CommunicationTool communicationTool;
    private Gson gson;
    private Configuration configuration;
    private IntegrityController controller;

    /**
     * 构造服务器端类实例
     */
    public MidServer() {
        configuration = Configurator.parseConfiguration(new File("server_conf.json"));
        graphManager = new GraphManager(this, configuration.HEARTBEAT_THRESHOLD);
        graphManager.setThreshold(configuration.getHEARTBEAT_THRESHOLD());

        communicationTool = new CommunicationTool(this, configuration.getSERVER_PORT());

        gson = new GsonBuilder().setPrettyPrinting().create();

        controller = new IntegrityController(this);
    }

    /**
     * 数据解析接口。 根据数据的类型，调用不同的组件进行处理。 包括了节点注册信息，心跳信息，完整性验证启动信息，过程信息获取，退出信息等
     *
     * @param ip 数据地址
     * @param port 数据端口
     * @param data 数据
     * @see Message
     */
    public void parseData(String addr, int port, String data) {
        Message m = gson.fromJson(data, Message.class);
        String mType = m.getType();
        if (mType.equals(Message.REGISTER)) {
            graphManager.register(m.getId(), addr, port);
//            sendMessageToUI(m);
        } else if (mType.equals(Message.HEARTBEAT)) {
            graphManager.heartbeat(m.getId(), addr, port);
            sendMessageToUI(m);
        } else if (mType.equals(Message.INT_START)) {
            controller.startSim();
        } else if (mType.equals(Message.PROCESS_ONLINE)) {
            echoProcessOnline();
        } else if (mType.equals(Message.KILL)) {
            sendKillMessage();
            System.out.println("kill");
            System.exit(0);
        } else if (mType.equals(Message.TASK)) {
        } else if (mType.equals(Message.TASK_RESULT)) {
        }
    }

    /**
     * 数据发送接口。通过CommunicationTool提供的数据发送接口发送数据
     *
     * @param ip 数据地址
     * @param port 数据端口
     * @param data 数据
     * @see CommunicationTool#sendMessage(java.lang.String, int,
     * java.lang.String)
     */
    public void sendMessage(String ip, int port, String data) {
        communicationTool.sendMessage(ip, port, data);
    }

    /**
     * 数据发送接口。通过CommunicationTool提供的数据发送接口发送数据
     * @param p 目的过程
     * @param data 数据
     * @see CommunicationTool#sendMessage(java.lang.String, int, java.lang.String) 
     */
    public void sendMessage(Process p, String data) {
        communicationTool.sendMessage(p.getAddr(), p.getPort(), data);
    }

    /**
     * 数据回馈接口。通过CommunicationTool提供的数据发送接口回馈数据至监控端
     * @param m 消息
     */
    public void sendMessageToUI(Message m) {
        communicationTool.sendMessage(configuration.UI_ADDR, configuration.UI_PORT, gson.toJson(m, Message.class));
    }

    /**
     * 回馈在线过程信息至监控端
     */
    public void echoProcessOnline() {
        Set<Process> processes = new HashSet<Process>();
        for (Process p : graphManager.getGraph().getProcessSet()) {
            if (p.isIsOnline()) {
                processes.add(p);
            }
        }
        Message message = new Message();
        message.setType(Message.PROCESS_ONLINE);
        message.setContent(gson.toJson(ProcessSimUtil.ProcessToSim(processes), new TypeToken<Set<ProcessSim>>() {
        }.getType()));
        sendMessageToUI(message);
    }

    /**
     * 发送推出命令至各个过程节点
     */
    public void sendKillMessage() {
        Message m = new Message();
        m.setType(Message.KILL);
        for (Process p : graphManager.getGraph().getProcessSet()) {
            if (p.isIsOnline()) {
                sendMessage(p, gson.toJson(m, Message.class));
            }
        }
    }

    public GraphManager getGraphManager() {
        return graphManager;
    }

    public void setGraphManager(GraphManager graphManager) {
        this.graphManager = graphManager;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
