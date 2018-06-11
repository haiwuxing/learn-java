public class ForeachDemo {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};

        System.out.println("----------旧方式遍历------------");
        for(int i=0; i<arr.length; i++)
        {
            System.out.println(arr[i]);
        }
        
        System.out.println("---------新方式遍历-------------");
        for(int element:arr)
        {
            System.out.println(element);
        }
    }
}