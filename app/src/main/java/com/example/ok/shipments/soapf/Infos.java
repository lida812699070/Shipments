package com.example.ok.shipments.soapf;

/**
 * Created by ${lida} on 2016/5/24.
 */
public class Infos {
        public static final String GetTraceDataByDocId="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <soap:Body>\n" +
            "    <GetTraceDataByDocId xmlns=\"http://www.xsjky.cn/\">\n" +
            "      <loginInfo>\n" +
            "        <UserId>UserIdValue</UserId>\n" +
            "        <ClientName>ClientNameValue</ClientName>\n" +
            "        <SessionId>SessionIdValue</SessionId>\n" +
            "        <RoleData>RoleDataValue</RoleData>\n" +
            "      </loginInfo>\n" +
            "      <documentId>documentIdValue</documentId>\n" +
            "    </GetTraceDataByDocId>\n" +
            "  </soap:Body>\n" +
            "</soap:Envelope>";

        public static final String QueryTraceData="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <QueryTraceData xmlns=\"http://www.xsjky.cn/\">\n" +
                "      <sessionId>sessionIdValue</sessionId>\n" +
                "      <userId>userIdValue</userId>\n" +
                "      <clientName>clientNameValue</clientName>\n" +
                "      <queryNumbers>\n" +
                "        <string>stringValue</string>\n" +
                "      </queryNumbers>\n" +
                "    </QueryTraceData>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>";
}
