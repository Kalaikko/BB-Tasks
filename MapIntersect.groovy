class MapIntersect {
    static void main(String[] args) {
        def testMap_1 = [API_CODE: "B02D", AMOUNT: 20000, PERCENTAGE: 0, PRODUCT_ID: 12103164,
                         BANK_ID : 700, MIN_AMOUNT: 500, TEST_ID: 4566, ERROR_TEXT: "Contact TI team"]
        def testMap_2 = [API_CODE: "CODF", AMOUNT: 3000, PERCENTAGE: 20, PRODUCT_ID: 3045]
        def testMap_3 = [:]
        def keyList_1 = testMap_1.keySet()
        def keyList_2 = testMap_2.keySet()
        Scanner scan = new Scanner(System.in)
        def flag = scan.nextInt()
        if (flag == 1) {
            for (int i = 0; i < keyList_1.size(); i++) {
                if (keyList_2[i] == keyList_1[i]) {
                    testMap_3[keyList_1[i]] = testMap_2.get(keyList_2[i])
                } else {
                    testMap_3[keyList_1[i]] = testMap_1.get(keyList_1[i])
                }
            }
            println(testMap_3)
        } else if (flag == 0) {
            println("Flag is not Passed")
        } else {
            println("Flag is neither 0 nor 1")
        }
    }
}
