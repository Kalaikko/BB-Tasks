class SampleHashMap {
    static Map insertMap(inpMap) {
        //Scanner scan = new Scanner(System.in)
        def map = inpMap
        //println("Key to insert")
        def key = null
        //println("Value to insert")
        def value = null
        map[key] = value
        return map
    }

    static Map deleteMap(inpMap) {
        Scanner scan = new Scanner(System.in)
        def map = inpMap
        println("Key to be deleted")
        def key = scan.nextLine()
        map.remove(key)
        return map
    }

    static Map copyMap(inpMap) {
        def map = inpMap
        def copyMap = [:]
        copyMap << map
        return copyMap
    }

    static void printKeys(inpMap) {
        def map = inpMap
        println(map.keySet())
    }

    static void printValues(inpMap) {
        def map = inpMap
        println(map.values())
    }

    static String findByKey(inpMap) {
        Scanner scan = new Scanner(System.in)
        def map = inpMap
        println("Enter key for value")
        def key = scan.nextLine()
        def val = map.get(key)
        print("Value for key ${key} is ")
        return val
    }

    static String findByValue(inpMap) {
        Scanner scan = new Scanner(System.in)
        def str = ""
        def map = inpMap
        println("Enter value for key")
        def val = scan.nextLine()
        map.each {
            if (it.value == val) {
                str = it.key
            }
        }
        print("Key for value ${val} is ")
        return str
    }

    static Map printMap(inpMap) {
        def map = inpMap
        return map
    }

    static Map sortMap(inpMap) {
        def map = inpMap
        def sortedMap = map.sort { a, b -> b.key <=> a.key}
        return sortedMap
    }

    static void main(String[] args) {
        Scanner scan = new Scanner(System.in)
        def hashMap = [:]
        def key = []
        def value = []
        for (i in 1..3) {
            println("Enter Key")
            key[i] = scan.nextLine()
            println("Enter Value")
            value[i] = scan.nextLine()
            hashMap.put(key[i], value[i])
        }
        for (i in 1..10) {
            println("Enter Choice")
            def n = scan.nextInt()
            switch (n) {
                case 1:
                    println(insertMap(hashMap))
                    break
                case 2:
                    println(deleteMap(hashMap))
                    break
                case 3:
                    def map = copyMap(hashMap)
                    println("Copied new map")
                    map.each {
                        println(it.key + "=" + it.value)
                    }
                    break
                case 4:
                    printKeys(hashMap)
                    break
                case 5:
                    printValues(hashMap)
                    break
                case 6:
                    println(findByKey(hashMap))
                    break
                case 7:
                    println(findByValue(hashMap))
                    break
                case 8:
                    def map = printMap(hashMap)
                    for (s in map) {
                        println("key = ${s.key}, value = ${s.value}")
                    }
                    break
                case 9:
                    println(sortMap(hashMap))
                    break
                default:
                    println("Enter Correct Choice")
                    String fileContents = new File('/home/kalaikkoss/IdeaProjects/CheckSum/src/key.txt').text
                    println(fileContents)
            }
        }
    }
}