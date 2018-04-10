import groovy.json.JsonSlurper

class InputParsing {

    /**
    * Parsing XML File
    * */
    static Map mapXml(inpString) {
        def hashMap = [:]
        def objXml = new XmlParser().parseText(inpString)
        def statusCode = objXml.Status?.StatusCode?.text()
        def premium = objXml.DATA?.PREMIUM?.text()?.toDouble()
        def message = objXml.Status?.Message?.text()
        def quoteId = objXml.DATA?.QUOTE_ID?.text()
        def totalODp = objXml.DATA?.OD_PREMIUM?.TOTAL_OD_PREMIUM?.text()?.toDouble()
        def totalLP = objXml.DATA?.LIABILITY?.TOTAL_LIABILITY_PREMIUM?.text()?.toDouble()
        if (statusCode == "S-0002" && premium > 0) {
            hashMap["STATUS"] = "SUCCESS"
            hashMap["Error"] = ""
        } else {
            hashMap["STATUS"] = "FAILURE"
            hashMap."ERROR" = message
        }
        hashMap.put("PREMIUM_WITHOUT_TAX", (totalODp + totalLP))
        hashMap."ID" = quoteId
        println(hashMap)
        return hashMap
    }

    /**
     * Parsing JSON File
     */
    static Map mapJson(inpString) {
        def hashMap = [:]
        def map =[:]
        def list = []
        def objJson = new JsonSlurper().parseText(inpString)
        def status = objJson.response?.txnStatus?.toString()
        def desc = objJson.response?.statusDesc?.toString()
        def Id = objJson.response?.txnRefId?.toString()
        if (status == "SUCCESS") {
            hashMap."STATUS" = "SUCCESS"
            hashMap."ERROR" = ""
        } else {
            hashMap."STATUS" = "FAILURE"
            hashMap."ERROR" = desc
        }
        hashMap."ID" = Id
        for (int i = 0; i < objJson.response?.cartDetails?.size(); i++) {
            def tidValue = objJson.response?.cartDetails[i]?.tranId
            def sidValue = objJson.response?.cartDetails[i]?.schemeRefId
            map["TRAN_ID"] = tidValue
            map["SCHEME_REF_ID"] = sidValue
            list.add(map)
            map = [:]
        }
        hashMap["DETAILS"] = list
        println(hashMap)
        return hashMap
    }

    static void main(String[] args) {
        def fileContents = new File(args[0]).text.trim()
        def folder = new File(args[1])
        if (!folder.exists()) {
            folder.mkdirs()
        }
        if (fileContents.getAt(0) == "<") {

            def map = mapXml(fileContents)
            def mapStr = map.toMapString()
            new File(folder, "response1a.txt").write(mapStr)
        } else if ((fileContents.getAt(0) == "{") || (fileContents.getAt(0) == "[")) {
            def map = mapJson(fileContents)
            def mapStr = map.toMapString()
            new File(folder, "response1b.txt").write(mapStr)
        } else {
            def map = [:]
            map."STATUS" = "FAILURE"
            map."ERROR" = "NO RESPONSE RECEIVED"
        }
    }
}