/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author b1106
 */
public class Configuration {

    public int HEARTBEAT_THRESHOLD = 3000;
    public int SERVER_PORT = 6666;
    public int UI_PORT = 6667;
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
    
}
