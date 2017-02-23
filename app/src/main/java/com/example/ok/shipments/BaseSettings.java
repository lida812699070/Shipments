package com.example.ok.shipments;

public class BaseSettings {
    public static final String EXTRA_BLUETOOTH_DEVICE_ADDRESS = "Bluetooth Device Adrress";
    public static final String EXTRA_BLUETOOTH_DEVICE_NAME = "Bluetooth Device Name";
    public static final String plaseUrl = "http://api.map.baidu.com/place/v2/search?query=";
    public static final String plaseUrlParameter = "&page_size=6&page_num=0&scope=1&region=深圳&output=json&ak=sfuQIH09bvPcDELFp0vIkyQ0nGRd07Tw";
    public final static String XML_DEFALUT_HEAD = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
            + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
            + "<soap:Body>";
    public final static String XML_DEFALUT_FOOT = "</soap:Body></soap:Envelope>";
    public final static String CLIENT_NAME = "XinShiJi.Mobile.Android.Customer";
    public final static String securityKey = "c40e6714cefd48caaa0bbe13180c983d";
    public final static String RegisterUsersecurityKey = "CFD6FDF7-61D9-4060-B968-DC372AA388C4";
    public final static String ResetPasswordsecurityKey = "bb13d541-90f5-4fd0-b01f-43c407711795";
    public final static String VERSIONS = "webservice";
    //web-service
    public static String WEBSERVICE_URL = "http://" + VERSIONS + ".xsjky.cn/LogisticsServer.asmx";
    //public static String WEBSERVICE_URL = "http://develope.xsjky.cn/LogisticsServer.asmx";
    //public final static String WEBSERVICE_URL = "http://webservice.xsjky.cn/LogisticsServer.asmx";
    public final static String WEBSERVICE_NAMESPACE = "http://www.xsjky.cn/";

    //登录请求模板
    public final static String LOGIN_ACTION = WEBSERVICE_NAMESPACE + "AppLogin";
    public final static String LOGIN_TEMPLET = XML_DEFALUT_HEAD
            + "<Login xmlns=\"" + WEBSERVICE_NAMESPACE + "\">"
            + "<loginName>${loginName}</loginName>"
            + "<password>${password}</password>"
            + "<clientName>${clientName}</clientName>"
            + "<clientVersion>${clientVersion}</clientVersion>"
            + "<DeviceId>${DeviceId}</DeviceId>"
            + "</Login>"
            + XML_DEFALUT_FOOT;

    //员工请求模板
    public final static String GETLOGINEMPLOYEE_ACTION = WEBSERVICE_NAMESPACE + "GetLoginEmployee";

    public final static String GETLOGINEMPLOYEE_TEMPLET = XML_DEFALUT_HEAD
            + "<GetLoginEmployee xmlns=\"" + WEBSERVICE_NAMESPACE + "\">"
            + "<loginInfo>"
            + "<UserId>${userId}</UserId>"
            + "<ClientName>" + CLIENT_NAME + "</ClientName>"
            + "<SessionId>${sessionId}</SessionId>"
            + "</loginInfo>"
            + "</GetLoginEmployee>"
            + XML_DEFALUT_FOOT;

    //获取客户模板
    public final static String GETCUSTOMERS_ACTION = WEBSERVICE_NAMESPACE + "GetCustomers";
    public final static String GETCUSTOMERS_TEMPLET = XML_DEFALUT_HEAD
            + "<GetCustomers xmlns=\"" + WEBSERVICE_NAMESPACE + "\">"
            + "<securityInfo>"
            + "<UserId>${userId}</UserId>"
            + "<ClientName>" + CLIENT_NAME + "</ClientName>"
            + "<SessionId>${sessionId}</SessionId>"
            + "</securityInfo>"
            + "<sortInfo>"
            + "<SortProperty>"
            + "<PropertyName>CustomerName</PropertyName>"
            + "<Direction>Ascending</Direction>"
            + "</SortProperty>"
            + "<SortProperty>"
            + "<PropertyName>CustomerId</PropertyName>"
            + "<Direction>Descending</Direction>"
            + "</SortProperty>"
            + "</sortInfo>"
            + "<pageNumber>1</pageNumber>"
            + "<pageSize>200</pageSize>"
            + "</GetCustomers>"
            + XML_DEFALUT_FOOT;

    //新建运单模板
    public final static String NEWDOCUMENT_ACTION = WEBSERVICE_NAMESPACE + "NewDocument";
    public final static String NEWDOCUMEN_TEMPLET = XML_DEFALUT_HEAD
            + "<NewDocument xmlns=\"" + WEBSERVICE_NAMESPACE + "\">"
            + "<securityInfo>"
            + "<UserId>${userId}</UserId>"
            + "<ClientName>" + CLIENT_NAME + "</ClientName>"
            + "<SessionId>${sessionId}</SessionId>"
            + "</securityInfo>"
            + "</NewDocument>"
            + XML_DEFALUT_FOOT;

    //运输方式
    public final static String GETSHIPPINGMODES_ACTION = WEBSERVICE_NAMESPACE + "GetShippingModes";
    public final static String GETSHIPPINGMODES_TEMPLET = XML_DEFALUT_HEAD
            + "<GetShippingModes xmlns=\"" + WEBSERVICE_NAMESPACE + "\">"
            + "<securityInfo>"
            + "<UserId>${userId}</UserId>"
            + "<ClientName>" + CLIENT_NAME + "</ClientName>"
            + "<SessionId>${sessionId}</SessionId>"
            + "</securityInfo>"
            + "</GetShippingModes>"
            + XML_DEFALUT_FOOT;

    //运单号
    public final static String GETDOCUMENTNUMBER_ACTION = WEBSERVICE_NAMESPACE + "GetDocumentNumber";
    public final static String GETDOCUMENTNUMBER_TEMPLET = XML_DEFALUT_HEAD
            + "<GetDocumentNumber xmlns=\"" + WEBSERVICE_NAMESPACE + "\">"
            + "<loginInfo>"
            + "<UserId>${userId}</UserId>"
            + "<ClientName>" + CLIENT_NAME + "</ClientName>"
            + "<SessionId>${sessionId}</SessionId>"
            + "</loginInfo>"
            + "<fromNetwork>"
            + "<NetworkId>${fromNetwork.networkId}</NetworkId>"
            + "<NetworkCode>${fromNetwork.networkCode}</NetworkCode>"
            + "<NetworkName>${fromNetwork.networkName}</NetworkName>"
            + "<ParentId>${fromNetwork.parentId}</ParentId>"
            + "<Remarks>${fromNetwork.remarks}</Remarks>"
            + "<ManagerId>${fromNetwork.managerId}</ManagerId>"
            + "<IsDisabled>${fromNetwork.isDisabled}</IsDisabled>"
            + "<Tel>${fromNetwork.tel}</Tel>"
            + "<Fax>${fromNetwork.fax}</Fax>"
            + "<PinYinCode>${fromNetwork.pinYinCode}</PinYinCode>"
            + "<WuBiCode>${fromNetwork.wuBiCode}</WuBiCode>"
            + "</fromNetwork>"
            + "</GetDocumentNumber>"
            + XML_DEFALUT_FOOT;

    //网点GetNetworks
    public final static String GETNETWORKS_ACTION = WEBSERVICE_NAMESPACE + "GetNetworks";
    public final static String GETNETWORKS_TEMPLET = XML_DEFALUT_HEAD
            + "<GetNetworks xmlns=\"" + WEBSERVICE_NAMESPACE + "\">"
            + "<securityInfo>"
            + "<UserId>${userId}</UserId>"
            + "<ClientName>" + CLIENT_NAME + "</ClientName>"
            + "<SessionId>${sessionId}</SessionId>"
            + "</securityInfo>"
            + "</GetNetworks>"
            + XML_DEFALUT_FOOT;

    //保存运单
    public final static String SAVEDOCUMENT_ACTION = WEBSERVICE_NAMESPACE + "SaveDocument";
    public final static String SAVEDOCUMENT_TEMPLET = XML_DEFALUT_HEAD
            + "<SaveDocument xmlns=\"" + WEBSERVICE_NAMESPACE + "\">"
            + "<securityInfo>"
            + "<UserId>${userId}</UserId>"
            + "<ClientName>" + CLIENT_NAME + "</ClientName>"
            + "<SessionId>${sessionId}</SessionId>"
            + "</securityInfo>"
            + "${document}"
            + "</SaveDocument>"
            + XML_DEFALUT_FOOT;

    //待收货
    public final static String GETAPPLYDOCUMENTCOUNT_ACTION = WEBSERVICE_NAMESPACE + "GetApplyDocumentCount";
    public final static String GETAPPLYDOCUMENTCOUNT_TEMPLET = XML_DEFALUT_HEAD
            + "<GetApplyDocumentCount xmlns=\"" + WEBSERVICE_NAMESPACE + "\">"
            + "<loginInfo>"
            + "<UserId>${userId}</UserId>"
            + "<ClientName>" + CLIENT_NAME + "</ClientName>"
            + "<SessionId>${sessionId}</SessionId>"
            + "</loginInfo>"
            + "</GetApplyDocumentCount>"
            + XML_DEFALUT_FOOT;

    public final static String GETAPPLYDOCUMENTS_ACTION = WEBSERVICE_NAMESPACE + "GetApplyDocuments";
    public final static String GETAPPLYDOCUMENTS_TEMPLET = XML_DEFALUT_HEAD
            + "<GetApplyDocuments xmlns=\"" + WEBSERVICE_NAMESPACE + "\">"
            + "<loginInfo>"
            + "<UserId>${userId}</UserId>"
            + "<ClientName>" + CLIENT_NAME + "</ClientName>"
            + "<SessionId>${sessionId}</SessionId>"
            + "</loginInfo>"
            + "</GetApplyDocuments>"
            + XML_DEFALUT_FOOT;

    //客户收件人列表
    public final static String GETCUSTOMERRECEIVERS_ACTION = WEBSERVICE_NAMESPACE + "GetCustomerReceivers";
    public final static String GETCUSTOMERRECEIVERS_TEMPLET = XML_DEFALUT_HEAD
            + "<GetCustomerReceivers xmlns=\"" + WEBSERVICE_NAMESPACE + "\">"
            + "<securityInfo>"
            + "<UserId>${userId}</UserId>"
            + "<ClientName>" + CLIENT_NAME + "</ClientName>"
            + "<SessionId>${sessionId}</SessionId>"
            + "</securityInfo>"
            + "<customerId>${customerId}</customerId>"
            + "<pageNuber>0</pageNuber>"
            + "<pageSize>100</pageSize>"
            + "</GetCustomerReceivers>"
            + XML_DEFALUT_FOOT;

    public final static String FindCustomerReceiver_ACTION = WEBSERVICE_NAMESPACE + "FindCustomerReceiver";
    public final static String FindCustomerReceiver_TEMPLET = XML_DEFALUT_HEAD
            + "<FindCustomerReceiver xmlns=\"" + WEBSERVICE_NAMESPACE + "\">"
            + "<loginInfo>"
            + "<UserId>${userId}</UserId>"
            + "<ClientName>" + CLIENT_NAME + "</ClientName>"
            + "<SessionId>${sessionId}</SessionId>"
            + "<RoleData>0</RoleData>"
            + "</loginInfo>"
            + "<customerId>${customerId}</customerId>"
            + "<toCity>${toCity}</toCity>"
            + "</FindCustomerReceiver>"
            + XML_DEFALUT_FOOT;
    //确认运单
    public final static String SETISCONFIRM_ACTION = WEBSERVICE_NAMESPACE + "SetIsConfirm";
    public final static String SETISCONFIRM_TEMPLET = XML_DEFALUT_HEAD
            + "<SetIsConfirm xmlns=\"" + WEBSERVICE_NAMESPACE + "\">"
            + "<loginInfo>"
            + "<UserId>${userId}</UserId>"
            + "<ClientName>" + CLIENT_NAME + "</ClientName>"
            + "<SessionId>${sessionId}</SessionId>"
            + "</loginInfo>"
            + "<documentId>${documentId}</documentId>"
            + "<returnUpdateRecord>false</returnUpdateRecord>"
            + "</SetIsConfirm>"
            + XML_DEFALUT_FOOT;

    //确认收货
    public final static String SETISRECEIVED_ACTION = WEBSERVICE_NAMESPACE + "SetIsReceived";
    public final static String SETISRECEIVED_TEMPLET = XML_DEFALUT_HEAD
            + "<SetIsReceived xmlns=\"" + WEBSERVICE_NAMESPACE + "\">"
            + "<loginInfo>"
            + "<UserId>${userId}</UserId>"
            + "<ClientName>" + CLIENT_NAME + "</ClientName>"
            + "<SessionId>${sessionId}</SessionId>"
            + "</loginInfo>"
            + "<documentId>${documentId}</documentId>"
            + "<returnUpdateRecord>false</returnUpdateRecord>"
            + "</SetIsReceived>"
            + XML_DEFALUT_FOOT;

    //待派单数量
    public final static String GETDELIVERABLEDOCUMENTCOUNT_ACTION = WEBSERVICE_NAMESPACE + "GetDeliverableDocumentCount";
    public final static String GETDELIVERABLEDOCUMENTCOUNT_TEMPLET = XML_DEFALUT_HEAD
            + "<GetDeliverableDocumentCount xmlns=\"" + WEBSERVICE_NAMESPACE + "\">"
            + "<loginInfo>"
            + "<UserId>${userId}</UserId>"
            + "<ClientName>" + CLIENT_NAME + "</ClientName>"
            + "<SessionId>${sessionId}</SessionId>"
            + "</loginInfo>"
            + "</GetDeliverableDocumentCount>"
            + XML_DEFALUT_FOOT;

    //待派件详细列表
    public final static String GETDELIVERABLEDOCUMENT_ACTION = WEBSERVICE_NAMESPACE + "GetDeliverableDocuments";
    public final static String GETDELIVERABLEDOCUMENT_TEMPLET = XML_DEFALUT_HEAD
            + "<GetDeliverableDocuments xmlns=\"" + WEBSERVICE_NAMESPACE + "\">"
            + "<loginInfo>"
            + "<UserId>${userId}</UserId>"
            + "<ClientName>" + CLIENT_NAME + "</ClientName>"
            + "<SessionId>${sessionId}</SessionId>"
            + "</loginInfo>"
            + "<pageNumber>1</pageNuber>"
            + "<pageSize>10</pageSize>"
            + "</GetDeliverableDocuments>"
            + XML_DEFALUT_FOOT;

    //生成HandOverRecord记录
    public final static String NewHandoverRecord_ACTION = WEBSERVICE_NAMESPACE + "NewHandoverRecord";
    public final static String NewHandoverRecord_TEMPLET = XML_DEFALUT_HEAD
            + "<NewHandoverRecord xmlns=\"" + WEBSERVICE_NAMESPACE + "\">"
            + "<loginInfo>"
            + "<UserId>${userId}</UserId>"
            + "<ClientName>" + CLIENT_NAME + "</ClientName>"
            + "<SessionId>${sessionId}</SessionId>"
            + "</loginInfo>"
            + "</NewHandoverRecord>"
            + XML_DEFALUT_FOOT;

    //获取批量转出列表
    public final static String GetEmployeeDeliverableDocuments_ACTION = WEBSERVICE_NAMESPACE + "GetEmployeeDeliverableDocuments";
    public final static String GetEmployeeDeliverableDocuments_TEMPLET = XML_DEFALUT_HEAD
            + "<GetEmployeeDeliverableDocuments xmlns=\"" + WEBSERVICE_NAMESPACE + "\">"
            + "<loginInfo>"
            + "<UserId>${userId}</UserId>"
            + "<ClientName>" + CLIENT_NAME + "</ClientName>"
            + "<SessionId>${sessionId}</SessionId>"
            + "</loginInfo>"
            + "</GetEmployeeDeliverableDocuments>"
            + XML_DEFALUT_FOOT;

    //打印确认
    public final static String AfterPrintCargoLabel_ACTION = WEBSERVICE_NAMESPACE + "AfterPrintCargoLabel";
    public final static String AfterPrintCargoLabel_TEMPLET = XML_DEFALUT_HEAD
            + "<AfterPrintCargoLabel xmlns=\"" + WEBSERVICE_NAMESPACE + "\">"
            + "<loginInfo>"
            + "<UserId>${userId}</UserId>"
            + "<ClientName>" + CLIENT_NAME + "</ClientName>"
            + "<SessionId>${sessionId}</SessionId>"
            + "</loginInfo>"
            + "<documentId>${documentId}</documentId>"
            + "</AfterPrintCargoLabel>"
            + XML_DEFALUT_FOOT;

    //查找联系人
    public final static String FindContacts_ACTION = WEBSERVICE_NAMESPACE + "FindContacts";
    public final static String FindContacts_TEMPLET = XML_DEFALUT_HEAD
            + "<FindContacts xmlns=\"" + WEBSERVICE_NAMESPACE + "\">"
            + "<loginInfo>"
            + "<UserId>${userId}</UserId>"
            + "<ClientName>" + CLIENT_NAME + "</ClientName>"
            + "<SessionId>${sessionId}</SessionId>"
            + "<RoleData>0</RoleData>"
            + "</loginInfo>"
            + "<findValue>${findValue}</findValue>"
            + "<findCity>${findCity}</findCity>"
            + "</FindContacts>"
            + XML_DEFALUT_FOOT;


    //批量转出数量
    public final static String GetTruckDocumentCount_ACTION = WEBSERVICE_NAMESPACE + "GetTruckDocumentCount";
    public final static String GetTruckDocumentCount_TEMPLET = XML_DEFALUT_HEAD
            + "<GetTruckDocumentCount xmlns=\"" + WEBSERVICE_NAMESPACE + "\">"
            + "<loginInfo>"
            + "<UserId>${userId}</UserId>"
            + "<ClientName>" + CLIENT_NAME + "</ClientName>"
            + "<SessionId>${sessionId}</SessionId>"
            + "<RoleData>0</RoleData>"
            + "</loginInfo>"
            + "<truckNumber>${truckNumber}</truckNumber>"
            + "</GetTruckDocumentCount>"
            + XML_DEFALUT_FOOT;

    //批量转出详情
    public final static String GetTruckDocuments_ACTION = WEBSERVICE_NAMESPACE + "GetTruckDocuments";
    public final static String GetTruckDocuments_TEMPLET = XML_DEFALUT_HEAD
            + "<GetTruckDocuments xmlns=\"" + WEBSERVICE_NAMESPACE + "\">"
            + "<loginInfo>"
            + "<UserId>${userId}</UserId>"
            + "<ClientName>" + CLIENT_NAME + "</ClientName>"
            + "<SessionId>${sessionId}</SessionId>"
            + "<RoleData>0</RoleData>"
            + "</loginInfo>"
            + "<truckNumber>${truckNumber}</truckNumber>"
            + "<pageNumber>0</pageNumber>"
            + " <pageSize>0</pageSize>"
            + "</GetTruckDocuments>"
            + XML_DEFALUT_FOOT;
    public final static String UpdateCargoInfos = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <soap:Body>\n" +
            "    <UpdateCargoInfos xmlns=\"http://www.xsjky.cn/\">\n" +
            "      <sessionId>sessionIdValue</sessionId>\n" +
            "      <userId>userIdValue</userId>\n" +
            "      <clientName>clientNameValue</clientName>\n" +
            "      <requestId>requestIdValue</requestId>\n" +
            "      <cargos>\n" +
            "cargosValues\n" +
            "      </cargos>\n" +
            "    </UpdateCargoInfos>\n" +
            "  </soap:Body>\n" +
            "</soap:Envelope>";
    public final static String NewRequest = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <soap:Body>\n" +
            "    <NewRequest xmlns=\"http://www.xsjky.cn/\">\n" +
            "      <sessionId>sessionIdValue</sessionId>\n" +
            "      <userId>userIdValue</userId>\n" +
            "      <shipperName>ShipperNameValue</shipperName>\n" +
            "      <clientName>clientNameValue</clientName>\n" +
            "      <address>addressValue</address>\n" +
            "      <longitude>longitudeValue</longitude>\n" +
            "      <latitude>latitudeValue</latitude>\n" +
            "      <contactPerson>contactPersonValue</contactPerson>\n" +
            "      <contactPhone>contactPhoneValue</contactPhone>\n" +
            "      <appointmentTime>appointmentTimeValue</appointmentTime>\n" +
            "      <cargoWeight>cargoWeightValue</cargoWeight>\n" +
            "      <cargoVolumn>cargoVolumnValue</cargoVolumn>\n" +
            "      <toCity>toCityValue</toCity>\n" +
            "      <remarks>remarksValue</remarks>\n" +
            "      <requestMarks>\n" +
            "requestMarksValue" +
            "      </requestMarks>\n" +
            "    </NewRequest>\n" +
            "  </soap:Body>\n" +
            "</soap:Envelope>";

    public final static String NewDocument = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <soap:Body>\n" +
            "    <NewDocumentResponse xmlns=\"http://www.xsjky.cn/\">\n" +
            "      <NewDocumentResult>\n" +
            "        <Value>\n" +
            "          <RecordId>int</RecordId>\n" +
            "          <DocumentNumber>string</DocumentNumber>\n" +
            "          <ShipperCode>int</ShipperCode>\n" +
            "          <ShipperName>string</ShipperName>\n" +
            "          <ShipperContactName>string</ShipperContactName>\n" +
            "          <ShipperAddress>\n" +
            "            <AddressId>int</AddressId>\n" +
            "            <Province>string</Province>\n" +
            "            <City>string</City>\n" +
            "            <District>string</District>\n" +
            "            <Street>string</Street>\n" +
            "            <Address>string</Address>\n" +
            "            <Longitude>decimal</Longitude>\n" +
            "            <Latitude>decimal</Latitude>\n" +
            "            <PostCode>string</PostCode>\n" +
            "          </ShipperAddress>\n" +
            "          <ShipperAreaCode>string</ShipperAreaCode>\n" +
            "          <ShipperPhoneNumber>string</ShipperPhoneNumber>\n" +
            "          <NeedSignUpNotifyMessage>boolean</NeedSignUpNotifyMessage>\n" +
            "          <SignUpTime>dateTime</SignUpTime>\n" +
            "          <Signner>string</Signner>\n" +
            "          <ConsigneeCode>int</ConsigneeCode>\n" +
            "          <ConsigneeName>string</ConsigneeName>\n" +
            "          <ConsigneeContactPerson>string</ConsigneeContactPerson>\n" +
            "          <ConsigneeAddress>\n" +
            "            <AddressId>int</AddressId>\n" +
            "            <Province>string</Province>\n" +
            "            <City>string</City>\n" +
            "            <District>string</District>\n" +
            "            <Street>string</Street>\n" +
            "            <Address>string</Address>\n" +
            "            <Longitude>decimal</Longitude>\n" +
            "            <Latitude>decimal</Latitude>\n" +
            "            <PostCode>string</PostCode>\n" +
            "          </ConsigneeAddress>\n" +
            "          <ConsigneeAreaCode>string</ConsigneeAreaCode>\n" +
            "          <ConsigneePhoneNumber>string</ConsigneePhoneNumber>\n" +
            "          <NeedDelivery>boolean</NeedDelivery>\n" +
            "          <Upstair>boolean</Upstair>\n" +
            "          <DeliveryCharge>int</DeliveryCharge>\n" +
            "          <FromCity>string</FromCity>\n" +
            "          <ToCity>string</ToCity>\n" +
            "          <ShippingMode>\n" +
            "            <ModeId>int</ModeId>\n" +
            "            <ModeName>string</ModeName>\n" +
            "            <IsEnabled>boolean</IsEnabled>\n" +
            "          </ShippingMode>\n" +
            "          <Weight>decimal</Weight>\n" +
            "          <Volumn>decimal</Volumn>\n" +
            "          <ProductName>string</ProductName>\n" +
            "          <Quantity>int</Quantity>\n" +
            "          <Carriage>decimal</Carriage>\n" +
            "          <TotalCharge>decimal</TotalCharge>\n" +
            "          <CarriageItems>\n" +
            "            <ShipperCost xsi:nil=\"true\" />\n" +
            "            <ShipperCost xsi:nil=\"true\" />\n" +
            "          </CarriageItems>\n" +
            "          <BalanceMode>SenderPay or ReceiverPay or Monthly</BalanceMode>\n" +
            "          <MonthlyBalanceAccount>int</MonthlyBalanceAccount>\n" +
            "          <NeedInsurance>boolean</NeedInsurance>\n" +
            "          <InsuranceAmt>int</InsuranceAmt>\n" +
            "          <Premium>decimal</Premium>\n" +
            "          <GoodsAmount>int</GoodsAmount>\n" +
            "          <Remarks>string</Remarks>\n" +
            "          <PickupBy>string</PickupBy>\n" +
            "          <DeliveredBy>string</DeliveredBy>\n" +
            "          <CreateTime>dateTime</CreateTime>\n" +
            "          <Creator>int</Creator>\n" +
            "          <FromNetwork>\n" +
            "            <NetworkId>int</NetworkId>\n" +
            "            <NetworkCode>string</NetworkCode>\n" +
            "            <NetworkName>string</NetworkName>\n" +
            "            <CityName>string</CityName>\n" +
            "            <ParentId>int</ParentId>\n" +
            "            <Remarks>string</Remarks>\n" +
            "            <ManagerId>int</ManagerId>\n" +
            "            <IsDisabled>boolean</IsDisabled>\n" +
            "            <Tel>string</Tel>\n" +
            "            <Fax>string</Fax>\n" +
            "            <PinYinCode>string</PinYinCode>\n" +
            "            <WuBiCode>string</WuBiCode>\n" +
            "          </FromNetwork>\n" +
            "          <ToNetwork>\n" +
            "            <NetworkId>int</NetworkId>\n" +
            "            <NetworkCode>string</NetworkCode>\n" +
            "            <NetworkName>string</NetworkName>\n" +
            "            <CityName>string</CityName>\n" +
            "            <ParentId>int</ParentId>\n" +
            "            <Remarks>string</Remarks>\n" +
            "            <ManagerId>int</ManagerId>\n" +
            "            <IsDisabled>boolean</IsDisabled>\n" +
            "            <Tel>string</Tel>\n" +
            "            <Fax>string</Fax>\n" +
            "            <PinYinCode>string</PinYinCode>\n" +
            "            <WuBiCode>string</WuBiCode>\n" +
            "          </ToNetwork>\n" +
            "          <IsCarriageBalance>boolean</IsCarriageBalance>\n" +
            "          <BalanceTime>dateTime</BalanceTime>\n" +
            "          <BalanceBy>int</BalanceBy>\n" +
            "          <EditLock>boolean</EditLock>\n" +
            "          <EditingUser>int</EditingUser>\n" +
            "          <LockTime>dateTime</LockTime>\n" +
            "          <ShippingStatus>IsApplied or ReceiverAssigned or IsReceived or IsStowageParts or IsStowage or IsShipping or IsArrived or IsDelivering or IsSignUp</ShippingStatus>\n" +
            "          <IsConfirm>boolean</IsConfirm>\n" +
            "          <DocumentState>string</DocumentState>\n" +
            "          <Location>string</Location>\n" +
            "          <WoodenFrames>\n" +
            "            <WoodenFrame xsi:nil=\"true\" />\n" +
            "            <WoodenFrame xsi:nil=\"true\" />\n" +
            "          </WoodenFrames>\n" +
            "          <DeliveryAfterNotify>boolean</DeliveryAfterNotify>\n" +
            "          <AllowDelivery>boolean</AllowDelivery>\n" +
            "          <NotifyTime>dateTime</NotifyTime>\n" +
            "          <Notifier>int</Notifier>\n" +
            "        </Value>\n" +
            "      </NewDocumentResult>\n" +
            "    </NewDocumentResponse>\n" +
            "  </soap:Body>\n" +
            "</soap:Envelope>";
    public final static String APP_NAME = "shipments.apk";
    public final static String APP = "shipments";
}
