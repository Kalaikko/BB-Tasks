import geb.Browser

class RoboArm {
/**
 * Function to fetch data and putting into a Map
 * @param stringUrl
 * @param noOfPages
 * @return
 */

    static Map browserData(stringUrl, noOfPages) {
        Browser browser = new Browser()
        browser.setDriver(DriverConfig.getChromeDriver())
        def hashMap = [:]
        def linkList = []
        def title_1 = []
        def title_2 = []
        def linkMap = [:]
        browser.go(stringUrl)
        browser.find("#lst-ib").value("Geb")
        browser.find("#searchform").click()
        browser.find(value: "Google Search").click()
        for (def j = 0; j < noOfPages; j++) {
            for (def i = 0; i <2; i++) {
                linkList.add(browser.find(".r")[i].find(("a")).getAttribute("href"))
                title_1.add('"' + browser.find(".r")[i].find("a").text() + '"')
            }
            if (j != noOfPages - 1) {
                browser.find(id: "navcnt").find(".fl")[j].click()
            }
//            Thread.sleep(5000)
        }
        for (def k = 0; k < linkList.size(); k++) {
            browser.go(linkList[k])
            title_2.add('"' + browser.getTitle() + '"')
            hashMap["Title_1"] = title_1[k]
            hashMap["Title_2"] = title_2[k]
            linkMap[linkList[k]] = hashMap
            hashMap=[:]
        }
        println(linkMap)
        browser.close()
        return linkMap
    }

    /**
     * Writing the map data into the csv file
     * @param stringPath
     * @param map
     * @return
     */

    static writeToCsv(stringPath, map) {
        def folder = new File(stringPath)
        if (!folder.exists()) {
            folder.mkdirs()
        }
        File outputFile = new File(folder, "Output.csv")
        outputFile.withWriter {
            lines ->
                map.each {
                    key,value -> lines.write (key + "," + value."Title_1" + "," + value."Title_2" + "\n")
                }
        }
    }

    static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "/home/kalaikkoss/Downloads/chromedriver")
        def scan = new Scanner(System.in)
        println("Enter no. of pages")  //Getting No. of pages from the user
        def pages = scan.nextInt()
        def map = browserData(args[0], pages)  //Passing URL as arguments
        writeToCsv(args[1], map)  //Passing Folder path as arguments
    }
}