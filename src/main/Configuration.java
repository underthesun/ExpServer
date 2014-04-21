/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 * 服务器端所需的配置类，用以启动时存储配置文件中获取到的配置信息
 * @author b1106
 */
public class Configuration {

    public int HEARTBEAT_THRESHOLD = 3000;
    public int SERVER_PORT = 6666;
    public int UI_PORT = 6667;
    public int PACK_NUM = 10;
    public int ITEM_NUM = 100;
    public String UI_ADDR = "localhost";

    public int getHEARTBEAT_THRESHOLD() {
        return HEARTBEAT_THRESHOLD;
    }

    public void setHEARTBEAT_THRESHOLD(int HEARTBEAT_THRESHOLD) {
        this.HEARTBEAT_THRESHOLD = HEARTBEAT_THRESHOLD;
    }

    public int getSERVER_PORT() {
        return SERVER_PORT;
    }

    public void setSERVER_PORT(int SERVER_PORT) {
        this.SERVER_PORT = SERVER_PORT;
    }

    public int getUI_PORT() {
        return UI_PORT;
    }

    public void setUI_PORT(int UI_PORT) {
        this.UI_PORT = UI_PORT;
    }

    public String getUI_ADDR() {
        return UI_ADDR;
    }

    public void setUI_ADDR(String UI_ADDR) {
        this.UI_ADDR = UI_ADDR;
    }

    public int getPACK_NUM() {
        return PACK_NUM;
    }

    public void setPACK_NUM(int PACK_NUM) {
        this.PACK_NUM = PACK_NUM;
    }

    public int getITEM_NUM() {
        return ITEM_NUM;
    }

    public void setITEM_NUM(int ITEM_NUM) {
        this.ITEM_NUM = ITEM_NUM;
    }
    
    
    
}
