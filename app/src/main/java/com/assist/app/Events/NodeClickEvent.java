package com.assist.app.Events;

/**
 * Created by root on 22/9/17.
 */

public class NodeClickEvent implements NodeEvent {
    private String nodeName;
    private String nodeDescription;
    private String nodeId;

    public NodeClickEvent(String nodeName, String nodeDescription, String nodeId) {
        this.nodeName = nodeName;
        this.nodeDescription = nodeDescription;
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeDescription() {
        return nodeDescription;
    }

    public void setNodeDescription(String nodeDescription) {
        this.nodeDescription = nodeDescription;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
}
