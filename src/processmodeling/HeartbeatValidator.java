/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package processmodeling;

import java.util.TimerTask;

/**
 * 服务器端心跳信息检测类，对服务器端已注册的过程进行超时检测。
 *
 * @author b1106
 */
public class HeartbeatValidator extends TimerTask {

    private GraphManager graphManager;

    /**
     * 构建超时检测类实例
     * @param gm 过程图管理类实例
     */
    public HeartbeatValidator(GraphManager gm) {
        graphManager = gm;
    }

    /**
     * 以单独的线程周期性检测过程
     */
    @Override
    public void run() {
        validate();
    }

    /**
     * 超时检测接口，若节点的心跳时间超出阈值，则进行超时处理
     */
    private void validate() {
        ProcessGraph graph = graphManager.getGraph();
        for (Process process : graph.getProcessSet()) {
            if (process.isIsOnline()) {
                long lastbeat = process.getLastBeat();
                long now = System.currentTimeMillis();
                if (now - lastbeat > graphManager.getThreshold()) {
                    graphManager.processTimeout(process);
                }
            }
        }
    }

    public GraphManager getGraphManager() {
        return graphManager;
    }

    public void setGraphManager(GraphManager graphManager) {
        this.graphManager = graphManager;
    }
}
