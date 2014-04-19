/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util.processgraph;

import java.util.HashSet;
import processmodeling.Process;

/**
 *
 * @author b1106
 */
public class ProcessSimUtil {

    public static HashSet<ProcessSim> ProcessToSim(HashSet<Process> processes) {
        HashSet<ProcessSim> processSims = new HashSet<ProcessSim>();
        for (Process p : processes) {
            ProcessSim processSim = new ProcessSim();
            processSim.setAddr(p.getAddr());
            processSim.setId(p.getId());
            processSim.setIsOnline(p.isIsOnline());
            processSim.setLastBeat(p.getLastBeat());
            processSim.setPort(p.getPort());
            processSims.add(processSim);
        }
        return processSims;
    }
}
