class XMLParsing {
    static def xmlFile(inpString) {
        def responseMap = [:]
        if (inpString == null) {
            responseMap."Error_Code" = "PAYLOAD_ERROR"
            responseMap."Error_Bucket" = "PARTNER_WEBSERVICE_TIMEOUT"
            responseMap."Error_Message" = "Partner Webservice call failed due to timeout. Kindly resubmit the application after sometime"
        } else {
            def parseMap = StringToMapConverter.buildMapFromXml(inpString)
            def processingStatus = parseMap?.response?.data?.ProcessingStatus
            def statusCode = parseMap?.response?.data?.StatusCode
            def message = parseMap?.response?.data?.Messages?.Message
            if (processingStatus == "1" && statusCode == "120") {
                responseMap."Error_Code" = "SUCCESS"
                responseMap."Error_Bucket" = "AIP_REFERRED"
            } else if (processingStatus == "1" && statusCode == "170") {
                responseMap."Error_Code" = "SUCCESS"
                responseMap."Error_Bucket" = "AIP_APPROVED"
            } else if (processingStatus == "2") {
                responseMap."Error_Code" = "SUCCESS"
                responseMap."Error_Bucket" = "AIP_APPROVED "
            } else if (processingStatus == "3" && statusCode == "156" && message == "MSG_QDE_CC11 - PAN Status from NSDL is invalid") {
                responseMap."Error_Code" = "PAYLOAD_ERROR"
                responseMap."Error_Bucket" = "VALIDATION_ERROR"
                responseMap."Error_Message" = message
            } else if (processingStatus == "3" && statusCode == "156" && message == "MSG_QDE_CC12 - PAN Status from NSDL is valid but name match logic failed") {
                responseMap."Error_Code" = "PAYLOAD_ERROR"
                responseMap."Error_Bucket" = "VALIDATION_ERROR"
                responseMap."Error_Message" = message
            } else if (processingStatus == "3" && statusCode == "156" && message == "Relevant Error message will be given by partner") {
                responseMap."Error_Code" = "PAYLOAD_ERROR"
                responseMap."Error_Bucket" = "VALIDATION_ERROR"
                responseMap."Error_Message" = message
            } else if (processingStatus == "4" && statusCode == "142") {
                responseMap."Error_Code" = "SUCCESS"
                responseMap."Error_Bucket" = "AIP_PENDING_AT_PARTNER"
            } else if (processingStatus == "5" && statusCode == "155") {
                responseMap."Error_Code" = "SUCCESS"
                responseMap."Error_Bucket" = "AIP_PENDING_AT_PARTNER"
            } else if (processingStatus == "6" && statusCode == "157") {
                responseMap."Error_Code" = "SUCCESS"
                responseMap."Error_Bucket" = "AIP_PENDING_AT_PARTNER"
            } else if (processingStatus == "7" && statusCode == "FD") {
                responseMap."Error_Code" = "BUSINESS_ERROR"
                responseMap."Error_Bucket" = "AIP_REJECTED"
            } else if (processingStatus == "1" && statusCode == "118") {
                responseMap."Error_Code" = "SUCCESS"
                responseMap."Error_Bucket" = "AIP_REFERRED"
            } else {
                responseMap."Error_Code" = "PAYLOAD_ERROR"
                responseMap."Error_Bucket" = "UNKNOWN_ERROR"
                responseMap."Error_Message" = "UNKNOWN_ERROR"
            }
        }
        return responseMap
    }

    static void main(String[] args) {
        def xmlString = """<xml>
                            <response>
                                <data>
                                    <ProcessingStatus>3</ProcessingStatus>
                                    <StatusCode>156</StatusCode>
                                    <Messages>
                                        <Message>MSG_QDE_CC12 - PAN Status from NSDL is valid but name match logic failed</Message>
                                    </Messages>
                                </data>
                            </response>
                         </xml>"""
        println(xmlFile(xmlString))

    }
}