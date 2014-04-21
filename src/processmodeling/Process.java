/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package processmodeling;

import java.util.HashSet;
import java.util.Set;

/**
 * 服务器端的过程节点类。
 * 存储过程节点的标识，过程节点地址信息，过程节点端口号，过程节点注册时间，过程节点心跳时间，过程节点在线标识，
 * 以及关联过程信息
 *
 * @author b1106
 */
public class Process {

    private int id;
    private String addr;
    private int port;
    private long regTime;
    private long lastBeat;
    private boolean isOnline;
    private Set<Process> preProcessSet;
    private Set<Process> postProcessSet;

    /**
     * 构建过程类实例
     * @param id 过程标识
     */
    public Process(int id) {
        this.id = id;
        this.addr = "";
        this.port = 6666;
        this.regTime = -1;
        this.lastBeat = -1;
        this.isOnline = false;
        this.preProcessSet = new HashSet<Process>();
        this.postProcessSet = new HashSet<Process>();
    }

    public long getRegTime() {
        return regTime;
    }

    public void setRegTime(long regTime) {
        this.regTime = regTime;
    }

    public void addPreProcess(Process p) {
        preProcessSet.add(p);
    }

    public Process removePreProcess(Process p) {
        Process tmp = null;
        for (Process preP : preProcessSet) {
            if (p.id == preP.id) {
                tmp = preP;
            }
        }
        return tmp;
    }

    public void addPostProcess(Process p) {
        postProcessSet.add(p);
    }

    public Process removePostProcess(Process p) {
        Process tmp = null;
        for (Process postP : postProcessSet) {
            if (p.id == postP.id) {
                tmp = postP;
            }
        }
        return tmp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public long getLastBeat() {
        return lastBeat;
    }

    public void setLastBeat(long lastBeat) {
        this.lastBeat = lastBeat;
    }

    public boolean isIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public Set<Process> getPreProcessSet() {
        return preProcessSet;
    }

    public void setPreProcessSet(Set<Process> preProcessSet) {
        this.preProcessSet = preProcessSet;
    }

    public Set<Process> getPostProcessSet() {
        return postProcessSet;
    }

    public void setPostProcessSet(Set<Process> postProcessSet) {
        this.postProcessSet = postProcessSet;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
