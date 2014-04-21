/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package processmodeling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import communication.Message;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import main.MidServer;
import util.processgraph.ProcessSim;
import util.processgraph.ProcessSimUtil;

/**
 * 过程图管理类。 主要对过程图的构建和维护进行管理，并提供过程图信息访问接口。
 *
 * @see ProcessGraph
 * @see HeartbeatValidator
 * @see MidServer
 * @see Gson
 *
 * @author b1106
 */
public class GraphManager {

    private ProcessGraph graph;
    private long threshold;
    private HeartbeatValidator heartbeatValidator;
    private MidServer server;
    private Gson gson;

    /**
     * 构建过程图管理类实例
     * @param server 服务器端类实例
     * @param threshold 超时阈值
     */
    public GraphManager(MidServer server, long threshold) {
        this.server = server;
        this.threshold = threshold;
        gson = new GsonBuilder().setPrettyPrinting().create();
        graph = ProcessGraphFactory.parseProcessGraph(new File("processgraph.json"));

        heartbeatValidator = new HeartbeatValidator(this);
        Timer timer = new Timer();
        timer.schedule(heartbeatValidator, 0, 1000);

    }

    /**
     * 过程节点注册接口
     * @param pid 过程节点标识
     * @param addr 过程节点地址
     * @param port 过程节点端口号
     */
    public void register(int pid, String addr, int port) {
        Process p = graph.getProcess(pid);
        if (p != null) {
            long t = System.currentTimeMillis();
            p.setRegTime(t);
            p.setLastBeat(t);
            p.setAddr(addr);
            p.setPort(port);
            p.setIsOnline(true);
        }
//        System.out.println("reg: " + p.getId() + " addr: " + p.getAddr() + " port: " + p.getPort());
        registerConfirm(p);
//        server.frame.register(pid);
    }

    /**
     * 过程节点心跳接口
     * @param pid 过程节点标识
     * @param addr 过程节点地址
     * @param port 过程节点端口号 
     */
    public void heartbeat(int pid, String addr, int port) {
        Process p = graph.getProcess(pid);
        if (p != null) {
            if (p.isIsOnline()) {
                p.setLastBeat(System.currentTimeMillis());
                heartbeatConfirm(p);
//                System.out.println("hb: " + p.getId() + " addr: " + p.getAddr() + " port: " + p.getPort());
            } else {
                register(pid, addr, port);
            }
        }
    }

    /**
     * 过程超时处理接口
     * @param p 过程节点
     */
    public void processTimeout(Process p) {
        p.setIsOnline(false);

        System.out.println("process: " + p.getId() + " fucking timeout");
//        server.frame.unregister(p.getId());
    }

    /**
     * 过程注册回馈接口
     * @param p 过程节点
     * @see Message#REGISTER_ACK
     */
    public void registerConfirm(Process p) {

        HashMap<String, Set<ProcessSim>> linkedProcess = new HashMap<String, Set<ProcessSim>>();
        linkedProcess.put(Message.PROCESS_PRE_KEY, ProcessSimUtil.ProcessToSim(p.getPreProcessSet()));
        linkedProcess.put(Message.PROCESS_POST_KEY, ProcessSimUtil.ProcessToSim(p.getPostProcessSet()));
        Message m = new Message();
        m.setType(Message.REGISTER_ACK);
        m.setContent(gson.toJson(linkedProcess, new TypeToken<HashMap<String, Set<ProcessSim>>>() {
        }.getType()));
        server.sendMessage(p, gson.toJson(m, Message.class));
    }

    /**
     * 过程心跳回馈接口
     * @param p 过程节点
     * @see Message#HEARTBEAT_ACK
     */
    public void heartbeatConfirm(Process p) {

        HashMap<String, Set<ProcessSim>> linkedProcess = new HashMap<String, Set<ProcessSim>>();
        linkedProcess.put(Message.PROCESS_PRE_KEY, ProcessSimUtil.ProcessToSim(p.getPreProcessSet()));
        linkedProcess.put(Message.PROCESS_POST_KEY, ProcessSimUtil.ProcessToSim(p.getPostProcessSet()));

        Message m = new Message();
        m.setType(Message.HEARTBEAT_ACK);
        m.setContent(gson.toJson(linkedProcess, new TypeToken<HashMap<String, HashSet<ProcessSim>>>() {
        }.getType()));
        server.sendMessage(p, gson.toJson(m, Message.class));
    }

    public long getThreshold() {
        return threshold;
    }

    public void setThreshold(long threshold) {
        this.threshold = threshold;
    }

    public ProcessGraph getGraph() {
        return graph;
    }

    public void setGraph(ProcessGraph graph) {
        this.graph = graph;
    }

    public HeartbeatValidator getHeartbeatValidator() {
        return heartbeatValidator;
    }

    public void setHeartbeatValidator(HeartbeatValidator heartbeatValidator) {
        this.heartbeatValidator = heartbeatValidator;
    }
}
