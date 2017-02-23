package com.example.ok.shipments.model;

/**
 * Created by ${lida} on 2016/6/13.
 */
public class ShippingMode {
    private String ModeId;
    private String ModeName;
    private String IsEnabled;

    @Override
    public String toString() {
        return "ShippingMode{" +
                "ModeId='" + ModeId + '\'' +
                ", ModeName='" + ModeName + '\'' +
                ", IsEnabled='" + IsEnabled + '\'' +
                '}';
    }

    public String getIsEnabled() {
        return IsEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        IsEnabled = isEnabled;
    }

    public String getModeName() {
        return ModeName;
    }

    public void setModeName(String modeName) {
        ModeName = modeName;
    }

    public String getModeId() {
        return ModeId;
    }

    public void setModeId(String modeId) {
        ModeId = modeId;
    }
}
