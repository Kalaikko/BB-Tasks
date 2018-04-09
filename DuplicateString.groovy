class DuplicateString {
    static void main(String[] args) {
        Scanner scan = new Scanner(System.in)
        def list = []
        def listSize = 8
        for (int l = 0; l < listSize; l++) {
            list[l] = scan.nextLine()
        }
        for (int j = 0; j < listSize; j++) {
            for (int k = 0; k < list.size(); k++) {
                if (list[k] == list[k + 1]) {
                    list.remove(k + 1)
                    list.remove(k)
                }
            }
        }
        println(list)
    }
}
