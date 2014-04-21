/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package queryprocessing;

/**
 *
 * @author b1106
 */
public class Task {

    private String taskId;
    private String taskType;
    private String taskTagId;
    private String taskContent;
    private String taskSerial;
    private String sourceAddr;
    private String sourcePort;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskTagId() {
        return taskTagId;
    }

    public void setTaskTagId(String taskTagId) {
        this.taskTagId = taskTagId;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public String getTaskSerial() {
        return taskSerial;
    }

    public void setTaskSerial(String taskSerial) {
        this.taskSerial = taskSerial;
    }

    public String getSourceAddr() {
        return sourceAddr;
    }

    public void setSourceAddr(String sourceAddr) {
        this.sourceAddr = sourceAddr;
    }

    public String getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(String sourcePort) {
        this.sourcePort = sourcePort;
    }
}
