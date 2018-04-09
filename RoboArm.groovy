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
        def hashMap = ["Link": "Title_1,Title_2"]
        def linkList = []
        def title_1 = []
        def title_2 = []
        browser.go(stringUrl)
        browser.find("#lst-ib").value("Geb")
        browser.find("#searchform").click()
        browser.find(value: "Google Search").click()
        for (def j = 0; j < noOfPages; j++) {
            for (def i = 0; i < browser.find(".r").size(); i++) {
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
            hashMap[linkList[k]] = title_1[k] + "," + title_2[k]
        }
        println(hashMap)
        return hashMap
        browser.close()
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
                    key, value -> lines.write(key + "\t" + value + "\n")
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