package com.thaonx4.server;

public class Client {

    private String clientPackageName;
    private String clientProcessId;
    private String clientData;
    private String ipcMethod;

    public Client(String clientPackageName, String clientProcessId, String clientData, String ipcMethod) {
        this.clientPackageName = clientPackageName;
        this.clientProcessId = clientProcessId;
        this.clientData = clientData;
        this.ipcMethod = ipcMethod;
    }

    public String getClientPackageName() {
        return clientPackageName;
    }

    public void setClientPackageName(String clientPackageName) {
        this.clientPackageName = clientPackageName;
    }

    public String getClientProcessId() {
        return clientProcessId;
    }

    public void setClientProcessId(String clientProcessId) {
        this.clientProcessId = clientProcessId;
    }

    public String getClientData() {
        return clientData;
    }

    public void setClientData(String clientData) {
        this.clientData = clientData;
    }

    public String getIpcMethod() {
        return ipcMethod;
    }

    public void setIpcMethod(String ipcMethod) {
        this.ipcMethod = ipcMethod;
    }
}
