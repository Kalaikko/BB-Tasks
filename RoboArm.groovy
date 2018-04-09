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
        def hashMap = ["Title": "Link"]
        browser.go(stringUrl)

        browser.find("#lst-ib").value("Geb\t")
        browser.find(value: "Google Search").click()
        for (def j = 0; j < noOfPages; j++) {
            for (def i = 0; i < browser.find(".r").size(); i++) {
                def title = '"' + browser.find(".r")[i].find("a").text() + '"'
                def link = '"' + browser.find(".iUh30")[i].text() + '"'
                hashMap[title] = link
            }
            browser.find(id: "navcnt").find(".fl")[j].click()
//            Thread.sleep(5000)

        }
        println(hashMap)
        browser.close()
        return (hashMap)
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
        System.setProperty("webdriver.chrome.driver" , "/home/kalaikkoss/Downloads/chromedriver")
        def scan = new Scanner(System.in)
        println("Enter no. of pages")  //Getting No. of pages from the user
        def pages = scan.nextInt()
        def map = browserData(args[0], pages)  //Passing URL as arguments
        writeToCsv(args[1], map)  //Passing Folder path as arguments
    }
}