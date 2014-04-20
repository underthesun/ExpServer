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
 *
 * @author b1106
 */
public class GraphManager {

    private ProcessGraph graph;
    private long threshold;
    private HeartbeatValidator heartbeatValidator;
    private MidServer server;
    private Gson gson;

    public GraphManager(MidServer server, long threshold) {
        this.server = server;
        this.threshold = threshold;
        gson = new GsonBuilder().setPrettyPrinting().create();
        graph = ProcessGraphFactory.parseProcessGraph(new File("processgraph.json"));

        heartbeatValidator = new HeartbeatValidator(this);
        Timer timer = new Timer();
        timer.schedule(heartbeatValidator, 0, 1000);

    }

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

    public void processTimeout(Process p) {
        p.setIsOnline(false);

        System.out.println("process: " + p.getId() + " fucking timeout");
//        server.frame.unregister(p.getId());
    }

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
