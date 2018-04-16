import geb.Browser

class GebFrames {

    /**
     * Function to fetch package name and package link
     */
    static fetchData() {
        Browser browser = new Browser()
        browser.setDriver(DriverConfig.chromeDriver)
        def packageList = []
        def headingList = []
        def valueMap = [:]
        def hashMap = [:]
        def heading
        browser.go("http://www.gebish.org/manual/current/api/")
        def driver = browser.driver
        driver.switchTo().frame("packageListFrame")
        for (def i = 0; i < browser.find(".indexContainer").find("li").size(); i++) {
            packageList.add(browser.find(".indexContainer").find("li")[i].text())
            browser.find(".indexContainer").find("li")[i].find("a").click()
            driver.switchTo().defaultContent()
            driver.switchTo().frame("packageFrame")
            Thread.sleep(2000)
            for (def j = 0; j < browser.find(".indexContainer").size(); j++) {
                heading = browser.find(".indexContainer")[j].find("h2").text()
                for (def k = 0; k < browser.find(".indexContainer")[j].find("li").size(); k++) {
                    headingList.add(browser.find(".indexContainer")[j].find("li")[k].find("a").text())
                }
                valueMap[heading] = headingList
                hashMap[packageList[i]] = valueMap
                headingList = []
                if (j == browser.find(".indexContainer").size() - 1) {
                    valueMap = [:]
                }
            }
            driver.switchTo().defaultContent()
            driver.switchTo().frame("packageListFrame")
        }
        println(hashMap)
        browser.close()
    }

    /**
     * Main Function
     */
    static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "/home/kalaikkoss/Downloads/chromedriver")
        fetchData()

    }
}

