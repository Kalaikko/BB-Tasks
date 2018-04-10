import geb.Browser

class SilverRateKal {

    /**
     *
     * @param inpFilePath
     * return
     * */
    static List readFile(inpFilePath) {
        def listData = []
        new File(inpFilePath).splitEachLine(",") {
            fields -> listData.add(fields)
        }
        listData.remove(0)
        return listData
    }
    /**
     * Function to create a list from the Scrapped data
     * @param cityId
     * @param cityName
     * @param date
     * @param s_1g
     * @param s_1kg
     * return
     */
    static createList(cityId, cityName, date, s_1g, s_1kg) {
        def list = []
        list.add(cityId)
        list.add(cityName)
        list.add(date)
        list.add(s_1g)
        list.add(s_1kg)
        return list
    }

    /**
     * Scrapping the data from page and converting to list
     * @param listData
     * @param baseUrl
     * */
    static dataFetch(listData, baseUrl) {
        def urlList = []
        def silverData = []
        for (def i = 0; i < listData.size(); i++) {
            urlList.add(baseUrl + "/" + listData[i][2])
        }
        Browser browser = new Browser()
        browser.setDriver(DriverConfig.getChromeDriver())
        for (def j = 0; j < urlList.size(); j++) {
            browser.go(urlList[j])
            def tableRow = browser.find(".table-responsive").find("tbody").find("tr")[0]
            def date = tableRow.find("td")[0].text()
            def silver_1g = tableRow.find("td")[1].text()
            silver_1g = silver_1g.replace('Rs.', '').replace(',', '')
            def silver_1kg = tableRow.find("td")[4].text()
            silver_1kg = silver_1kg.replace('Rs.', '').replace(',', '')
            silverData.add(createList(listData[j][0], listData[j][1], date, silver_1g, silver_1kg))
        }
        println(silverData)
        browser.close()
        return silverData
    }

    /**
     * Function to write the Silver list data to a CSV file
     * @param silverData
     * @param outFilePath */
    static writeToCsv(silverData, outFilePath) {
        def data = []
        data = silverData
        def folder = new File(outFilePath)
        if (!folder.exists()) {
            folder.mkdirs()
        }
        File outFile = new File(folder, "SilverOut.csv")
        outFile.withWriter {
            lines ->
                for (def k = 0; k < data.size(); k++) {
                    lines.write(data[k][0] + "," + data[k][1] + "," + data[k][2] + "," + data[k][3] + "," + data[k][4] + "\n")
                }
        }
    }
    /**
     * Main Function
     */
    static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "/home/kalaikkoss/Downloads/chromedriver")
        def inpFilePath = args[0]
        def baseUrl = args[1]
        def outFilePath = args[2]
        def listData = readFile(inpFilePath)
        def silverData = dataFetch(listData, baseUrl)
        writeToCsv(silverData, outFilePath)
    }
}
