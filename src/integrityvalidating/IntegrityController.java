/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrityvalidating;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import communication.Message;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.MidServer;
import processmodeling.Process;
import util.integrity.Item;
import util.integrity.Order;
import util.integrity.Package;

/**
 *
 * @author b1106
 */
public class IntegrityController {

    private MidServer server;
    private int packNum = 10;
    private int itemNum = 100;
    private ArrayList<Order> orders;
    private Set<Process> originProcesses;
    private Gson gson;

    public IntegrityController(MidServer server) {
        this.server = server;
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void startSim() {
//        System.out.println("start fucking int");
        initData();
        int cnt = 0;
        // send order information
        for (Process p : originProcesses) {
            Message m = new Message();
            m.setType(Message.ORDER);
            m.setContent(gson.toJson(orders.get(cnt), Order.class));
            server.sendMessage(p, gson.toJson(m, Message.class));
            cnt++;
        }
        //send actual data
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            int cnt = 0;
                            for (Process p : originProcesses) {
                                Message m = new Message();
                                m.setType(Message.DATA);
                                m.setContent(gson.toJson(orders.get(cnt), Order.class));
                                server.sendMessage(p, gson.toJson(m, Message.class));
                                cnt++;
                            }
                        } catch (InterruptedException ex) {
                            Logger.getLogger(IntegrityController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }).start();
    }

    private void initData() {
        orders = new ArrayList<Order>();
        originProcesses = server.getGraphManager().getGraph().getOriginProcessSet();
        ArrayList<Item> items = new ArrayList<Item>();
        ArrayList<Package> packages = new ArrayList<Package>();

        //set items' attrs
        long time = System.currentTimeMillis();
        for (int i = 0; i < itemNum; i++) {
            if (i < packNum) {
                Package p = new Package(i);
                p.setLastTimeChecked(time);
                packages.add(p);
            } else {
                Item item = new Item(i);
                item.setPackageId(i % packNum);
                item.setLastTimeChecked(time);
                items.add(item);
            }
        }
        //set contained items for each package
        for (Package pack : packages) {
            ArrayList<Item> itemsContained = new ArrayList<Item>();
            for (Item item : items) {
                if (item.getPackageId() == pack.getId()) {
                    itemsContained.add(item);
                }
            }
            pack.setItems(itemsContained);
        }

        int proNum = originProcesses.size();
        for (int i = 0; i < proNum; i++) {
            Order order = new Order("origin" + i);
            ArrayList<Package> packList = new ArrayList<Package>();
            for (Package pack : packages) {
                if (pack.getId() % proNum == i) {
                    packList.add(pack);
                }
            }
            ArrayList<Item> itemList = new ArrayList<Item>();
            for (Package pack : packList) {
                itemList.addAll(pack.getItems());
            }
            order.setItems(itemList);
            order.setPackages(packList);
            orders.add(order);
        }
    }  
    
}
