/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import main.Configuration;

/**
 *
 * @author b1106
 */
public class Test {

//    public static void main(String[] args) throws IOException {
//        toJson();
////        fromJson();
////        confToJson();
//
//
//    }

    public static void toJson() throws IOException {
        
//        Message m = new Message();
//        m.setType("xx");
//        TaskMessageContent mc = new TaskMessageContent();
//
//        Process p1 = new Process(1);
//        Process p2 = new Process(2);
//        Process p3 = new Process(3);
//        p2.addPreProcess(p1);
//        p2.addPostProcess(p3);
//
//        Set<Process> set = new HashSet<Process>();
//        set.add(p1);
//        set.add(p2);
//        set.add(p3);
//        mc.setProcesses(set);
//
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("xx.json")));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        m.setContent(gson.toJson(mc));
//        gson.toJson(m, Message.class, bw);
//        gson.toJson(set, new TypeToken<Set<Process>>() {}.getType(), bw);
        
        X x = new X();
        x.s = "xx";
//        x.x = 12;
        gson.toJson(x, X.class, bw);
               
        bw.flush();
        bw.close();

    }

    public static void fromJson() throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(new File("xx.json")));
        Gson gson = new GsonBuilder().create();
        X x = gson.fromJson(br, X.class);
//        Message m = gson.fromJson(br, Message.class);
//        TaskMessageContent tm = gson.fromJson(m.getContent(), TaskMessageContent.class);
//        Set<Process> set = tm.getProcesses();
//        Set<Process> set = gson.fromJson(br, new TypeToken<Set<Process>>() {
//        }.getType());
//        for (Process p : set) {
//            System.out.println("process: " + p.getId() + " pre: " + p.getPostProcessSet().size());
//        }
        
    }

    public static void confToJson() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("conf.json")));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

//        gson.toJson(gson, bw);
        Configuration p = new Configuration();
        p.setHEARTBEAT_THRESHOLD(121312);
        p.setSERVER_PORT(3232);
        gson.toJson(p, Configuration.class, bw);
        bw.flush();
        bw.close();
    }
}
