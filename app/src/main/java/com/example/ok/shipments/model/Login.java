package com.example.ok.shipments.model;

/**
 * Created by OK on 2016/4/22.
 */
public class Login {
    private String UserId;
    private String ClientName;
    private String SessionId;
    private String RoleData;

    @Override
    public String toString() {
        return "Login{" +
                "UserId='" + UserId + '\'' +
                ", ClientName='" + ClientName + '\'' +
                ", SessionId='" + SessionId + '\'' +
                ", RoleData='" + RoleData + '\'' +
                '}';
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getRoleData() {
        return RoleData;
    }

    public void setRoleData(String roleData) {
        RoleData = roleData;
    }

    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }
}
