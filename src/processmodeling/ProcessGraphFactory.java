/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package processmodeling;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.processgraph.NodePOJO;
import util.processgraph.ProcessPOJO;

/**
 *
 * @author b1106
 */
public class ProcessGraphFactory {

    public static ProcessGraph createProcessGraph() {
        ProcessGraph graph = new ProcessGraph();
        return graph;
    }

    public static ProcessGraph parseProcessGraph(File file) {
        ProcessGraph graph = new ProcessGraph();
        Set<Process> processSet = new HashSet<Process>();
        Set<Process> originProcessSet = new HashSet<Process>();
        Set<Process> terminationProcessSet = new HashSet<Process>();
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(new FileInputStream(file), "utf-8");
            Gson gson = new Gson();
            ProcessPOJO pojo = gson.fromJson(inputStreamReader, ProcessPOJO.class);
            ArrayList<NodePOJO> nodePOJO = pojo.getNodePOJO();
//        ArrayList<NodeProcessPOJO> nodeProcessPOJO = pojo.getNodeProcessPOJO();

            //generate processes
            for (NodePOJO npojo : nodePOJO) {
                Process process = new Process(npojo.getId());
                processSet.add(process);
                if (npojo.getPreNodes().length == 0) {
                    originProcessSet.add(process);
                } else if (npojo.getPostNodes().length == 0) {
                    terminationProcessSet.add(process);
                }
            }
            graph.setOriginProcessSet(originProcessSet);
            graph.setProcessSet(processSet);
            graph.setTerminationProcessSet(terminationProcessSet);

            //associate processes according to the NodePOJOs' association
            for (Process process : processSet) {
                int[] preIds = null;
                int[] postIds = null;
                for (NodePOJO npojo : nodePOJO) {
                    if (npojo.getId() == process.getId()) {
                        preIds = npojo.getPreNodes();
                        postIds = npojo.getPostNodes();
                    }
                }
                for (Process p : processSet) {
                    for (int id : preIds) {
                        if (p.getId() == id) {
                            process.addPreProcess(p);
                        }
                    }
                    for (int id : postIds) {
                        if (p.getId() == id) {
                            process.addPostProcess(p);
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ProcessGraphFactory.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                inputStreamReader.close();
            } catch (IOException ex) {
                Logger.getLogger(ProcessGraphFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return graph;
    }
}
