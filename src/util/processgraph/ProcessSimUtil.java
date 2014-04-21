/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util.processgraph;

import java.util.HashSet;
import java.util.Set;
import processmodeling.Process;

/**
 * 过程Process转化为ProcessSim的工具类
 * @author b1106
 */
public class ProcessSimUtil {

    /**
     * 过程Process转化为ProcessSim的转化接口
     * @param processes Process集合
     * @return ProcessSim集合
     */
    public static Set<ProcessSim> ProcessToSim(Set<Process> processes) {
        Set<ProcessSim> processSims = new HashSet<ProcessSim>();
        for (Process p : processes) {
            ProcessSim processSim = new ProcessSim();
            processSim.setAddr(p.getAddr());
            processSim.setId(p.getId());
            processSim.setIsOnline(p.isIsOnline());
            processSim.setRegTime(p.getRegTime());
            processSim.setLastBeat(p.getLastBeat());
            processSim.setPort(p.getPort());
            processSims.add(processSim);
        }
        return processSims;
    }
}
