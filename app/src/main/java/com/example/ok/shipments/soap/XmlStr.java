package com.example.ok.shipments.soap;

/**
 * Created by ${lida} on 2016/6/13.
 */
public class XmlStr {
    public final static String GETSHIPPINGMODES_TEMPLET = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <soap:Body>\n" +
            "    <GetShippingModes xmlns=\"http://www.xsjky.cn/\">\n" +
            "      <securityInfo>\n" +
            "        <UserId>UserIdValue</UserId>\n" +
            "        <ClientName>ClientNameValue</ClientName>\n" +
            "        <SessionId>SessionIdValue</SessionId>\n" +
            "        <RoleData>RoleDataValue</RoleData>\n" +
            "      </securityInfo>\n" +
            "      <getDisables>getDisablesValue</getDisables>\n" +
            "    </GetShippingModes>\n" +
            "  </soap:Body>\n" +
            "</soap:Envelope>";

        public final static String SaveDocument="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                "    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "    <soap:Body>\n" +
                "        <SaveDocument xmlns=\"http://www.xsjky.cn/\">\n" +
                "            <securityInfo>\n" +
                "                <UserId>UserIdValue</UserId>\n" +
                "                <ClientName>ClientNameValue</ClientName>\n" +
                "                <SessionId>SessionIdValue</SessionId>\n" +
                "            </securityInfo>\n" +
                "            <document>\n" +
                "                <BalanceBy>0</BalanceBy>\n" +
                "                <BalanceMode>SenderPay</BalanceMode>\n" +
                "                <BalanceTime>0001-01-01T00:00:00</BalanceTime>\n" +
                "                <Carriage>0.0</Carriage>\n" +
                "                <CarriageItems></CarriageItems>\n" +
                "                <ConsigneeAddress>\n" +
                "                    <Address>ConsigneeAddressAddressValue</Address>\n" +
                "                    <AddressId>0</AddressId>\n" +
                "                    <City>ConsigneeAddressCityValue</City>\n" +
                "                    <District>ConsigneeAddressDistrictValue</District>\n" +
                "                    <PostCode>0</PostCode>\n" +
                "                    <Province>ConsigneeAddressProvinceValue</Province>\n" +
                "                </ConsigneeAddress>\n" +
                "                <ConsigneeAreaCode>0</ConsigneeAreaCode>\n" +
                "                <ConsigneeCode>0</ConsigneeCode>\n" +
                "                <ConsigneeContactPerson>ConsigneeContactPersonValue</ConsigneeContactPerson>\n" +
                "                <ConsigneeName>ConsigneeNameValue</ConsigneeName>\n" +
                "                <ConsigneePhoneNumber>ConsigneePhoneNumberValue</ConsigneePhoneNumber>\n" +
                "                <CreateTime>CreateTimeValue</CreateTime>\n" +
                "                <Creator>UserIdValue</Creator>\n" +
                "                <DeliveredBy></DeliveredBy>\n" +
                "                <DeliveryAfterNotify>DeliveryAfterNotifyValue</DeliveryAfterNotify>\n" +
                "                <DeliveryCharge>0</DeliveryCharge>\n" +
                "                <DocumentNumber></DocumentNumber>\n" +
                "                <EditLock>false</EditLock>\n" +
                "                <EditingUser>0</EditingUser>\n" +
                "                <FromCity>FromCityValue</FromCity>\n" +
                "                <FromNetwork>\n" +
                "                </FromNetwork>\n" +
                "                <GoodsAmount>0</GoodsAmount>\n" +
                "                <InsuranceAmt>5</InsuranceAmt>\n" +
                "                <IsCarriageBalance>false</IsCarriageBalance>\n" +
                "                <IsConfirm>false</IsConfirm>\n" +
                "                <IsFinished>false</IsFinished>\n" +
                "                <LockTime>0001-01-01T00:00:00</LockTime>\n" +
                "                <MonthlyBalanceAccount>0</MonthlyBalanceAccount>\n" +
                "                <NeedDelivery>NeedDeliveryValue</NeedDelivery>\n" +
                "                <NeedInsurance>true</NeedInsurance>\n" +
                "                <NeedSignUpNotifyMessage>true</NeedSignUpNotifyMessage>\n" +
                "                <PickupBy></PickupBy>\n" +
                "                <Premium>PremiumValue</Premium>\n" +
                "                <ProductName>ProductNameValue</ProductName>\n" +
                "                <Quantity>QuantityValue</Quantity>\n" +
                "                <RecordId>0</RecordId>\n" +
                "                <Remarks></Remarks>\n" +
                "                <ShipperAddress>\n" +
                "                    <Address>ShipperAddressAddressValue</Address>\n" +
                "                    <AddressId>0</AddressId>\n" +
                "                    <City>ShipperAddressCityValue</City>\n" +
                "                    <District>ShipperAddressDistrictValue</District>\n" +
                "                    <Province>ShipperAddressProvinceValue</Province>\n" +
                "                </ShipperAddress>\n" +
                "                <ShipperAreaCode></ShipperAreaCode>\n" +
                "                <ShipperCode>0</ShipperCode>\n" +
                "                <ShipperContactName>ShipperContactNameValue</ShipperContactName>\n" +
                "                <ShipperName>ShipperNameValue</ShipperName>\n" +
                "                <ShipperPhoneNumber>ShipperPhoneNumberValue</ShipperPhoneNumber>\n" +
                "                <ShippingMode>\n" +
                "                    <IsEnabled>true</IsEnabled>\n" +
                "                    <ModeId>ModeIdValue</ModeId>\n" +
                "                    <ModeName>ModeNameValue</ModeName>\n" +
                "                </ShippingMode>\n" +
                "                <ShippingStatus>IsApplied</ShippingStatus>\n" +
                "                <ToCity></ToCity>\n" +
                "                <TotalCharge>0.0</TotalCharge>\n" +
                "                <Upstair>UpstairValue</Upstair>\n" +
                "                <Volumn>VolumnValue</Volumn>\n" +
                "                <Weight>WeightValue</Weight>\n" +
                "                <WoodenFrames />\n" +
                "            </document>\n" +
                "        </SaveDocument>\n" +
                "    </soap:Body>\n" +
                "</soap:Envelope>";
}
