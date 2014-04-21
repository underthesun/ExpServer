/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util.integrity;

import java.util.ArrayList;

/**
 * 用以模拟数据完整性验证的订单信息。 订单包含了订单标识orderId, 订单所包含的物品列表items, 订单所包含的物品包列表packages
 * @author b1106
 */
public class Order {

    private String orderId;
    private ArrayList<Item> items;
    private ArrayList<Package> packages;

    public Order(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Package> getPackages() {
        return packages;
    }

    public void setPackages(ArrayList<Package> packages) {
        this.packages = packages;
    }
}
