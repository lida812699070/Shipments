package com.example.ok.shipments.urls;


import com.example.ok.shipments.BaseSettings;

/**
 * Created by OK on 2016/4/1.
 */

public class Urls {
    public static final String strUrl = "http://"+ BaseSettings.VERSIONS+".xsjky.cn/LogisticsServer.asmx/ReportCoordinate";   //查看一个人最新位置
    public static final String GetTraceDataByDocNumber = "http://"+ BaseSettings.VERSIONS+".xsjky.cn/LogisticsServer.asmx/GetTraceDataByDocNumber";   //查看一个人最新位置
    public static final String getLocationHandler="http://api.map.baidu.com/trace/v2/entity/list?ak=mu9sc5oxDdUjfobbzDI6DxyaOGAdHkPy&service_id=118436&mcode=FD:DE:70:61:35:65:48:A6:15:B5:A2:F9:55:A6:FC:A5:83:35:FF:78;cn.xsjky.android&entity_names=";
    public static final String Query = "http://"+ BaseSettings.VERSIONS+".xsjky.cn/addressbooks.asmx/Query";
    public static final String Get = "http://"+ BaseSettings.VERSIONS+".xsjky.cn/addressbooks.asmx/Get";
    public static final String ResetPassword = "http://"+ BaseSettings.VERSIONS+".xsjky.cn/securityservice.asmx/ResetPassword";
    public static final String Update = "http://"+ BaseSettings.VERSIONS+".xsjky.cn/addressbooks.asmx/Update";
    public static final String GetDefault = "http://"+ BaseSettings.VERSIONS+".xsjky.cn/addressbooks.asmx/GetDefault";
    public static final String SetDefault = "http://"+ BaseSettings.VERSIONS+".xsjky.cn/addressbooks.asmx/SetDefault";
    public static final String New = "http://"+ BaseSettings.VERSIONS+".xsjky.cn/addressbooks.asmx/New";
    public static final String Delete = "http://"+ BaseSettings.VERSIONS+".xsjky.cn/addressbooks.asmx/Delete";
    public static final String QueryDeliveryRequest = "http://"+ BaseSettings.VERSIONS+".xsjky.cn/deliveryrequest/deliveryrequestservice.asmx/QueryDeliveryRequest";
    public static final String UpdateProfile = "http://"+ BaseSettings.VERSIONS+".xsjky.cn/securityService.asmx/UpdateProfile";
    public static final String GetProfile = "http://"+ BaseSettings.VERSIONS+".xsjky.cn/securityService.asmx/GetProfile";
    public static final String DynamicLogin = "http://"+ BaseSettings.VERSIONS+".xsjky.cn/LogisticsServer.asmx/DynamicLogin";
    public static final String ApplyAuthenticode = "http://"+ BaseSettings.VERSIONS+".xsjky.cn/LogisticsServer.asmx/ApplyAuthenticode";
    public static final String GetLatestVersion = "http://"+ BaseSettings.VERSIONS+".xsjky.cn/Update/AppUpdateService.asmx/GetLatestVersion";
    public static final String GetDocumentPicturesByNumber = "http://"+ BaseSettings.VERSIONS+".xsjky.cn/LogisticsServer.asmx/GetDocumentPicturesByNumber";
    public static final String QueryDocument = "http://"+ BaseSettings.VERSIONS+".xsjky.cn/logisticsserver.asmx/QueryDocument";
    public static final String FindInProgressRequest = "http://"+ BaseSettings.VERSIONS+".xsjky.cn/DeliveryRequest/DeliveryRequestService.asmx/FindInProgressRequest";
    public static final String GetRequestHandlerCoordinate = "http://"+ BaseSettings.VERSIONS+".xsjky.cn/DeliveryRequest/DeliveryRequestService.asmx/GetRequestHandlerCoordinate";
    public static final String CancelDeliveryRequest = "http://"+ BaseSettings.VERSIONS+".xsjky.cn/DeliveryRequest/DeliveryRequestService.asmx/CancelDeliveryRequest";
    public static final String GetMarkNames = "http://"+ BaseSettings.VERSIONS+".xsjky.cn/DeliveryRequest/DeliveryRequestService.asmx/GetMarkNames";
    public static final String AppLogin = "http://"+ BaseSettings.VERSIONS+".xsjky.cn/LogisticsServer.asmx/AppLogin";
    public static final String ChangePassword= "http://"+ BaseSettings.VERSIONS+".xsjky.cn/securityservice.asmx/ChangePassword";
    public static final String RegisterUser= "http://"+ BaseSettings.VERSIONS+".xsjky.cn/securityservice.asmx/RegisterUser";
    public static final String GetCargoInfos= "http://"+ BaseSettings.VERSIONS+".xsjky.cn/DeliveryRequest/DeliveryRequestService.asmx/GetCargoInfos";
    public static final String DeliveryControlQuery= "http://"+BaseSettings.VERSIONS+".xsjky.cn/logisticsServer.asmx/DeliveryControlQuery";
    public static final String DeliveryControlApply= "http://"+BaseSettings.VERSIONS+".xsjky.cn/logisticsServer.asmx/DeliveryControlApply";
    }
