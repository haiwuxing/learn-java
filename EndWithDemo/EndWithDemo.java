public class EndWithDemo {
    public static void main(String[] args) {
        System.out.println("hello world");

        String str = "abc.CSV";
        if (str.endsWith(".csv")) {
            System.out.println("以 .csv 结尾");
        } else {
            System.out.println("不以 .csv 结尾");
        }

        if (str.toLowerCase().endsWith(".csv")) {
            System.out.println("以 .csv 结尾");
        } else {
            System.out.println("不以 .csv 结尾");
        }
    }
}