/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package processmodeling;

import java.util.TimerTask;

/**
 *
 * @author b1106
 */
public class HeartbeatValidator extends TimerTask {

    private GraphManager graphManager;

    public HeartbeatValidator(GraphManager gm) {
        graphManager = gm;
    }

    @Override
    public void run() {
        validate();
    }

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
