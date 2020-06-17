package Engine;
import Engine.GUI.BoardGUI;

public class main {
    public static long ipsBetween(String start, String end) {
        String[] arr=start.split("\\.");
        String[] arr2=end.split("\\.");
        Integer[] arr_start=new Integer[arr.length];
        Integer[] arr_end=new Integer[arr2.length];
        int sum=0;
        int count=1;
        for(int i=arr_start.length-1;i>=0;i--)
        {
            //System.out.println(arr[i]+" "+arr2[i]);
            arr_start[i]=Integer.parseInt(arr[i].trim());
            arr_end[i]=Integer.parseInt(arr2[i].trim());
            sum+=(arr_end[i]-arr_start[i])*count;
            count*=256;
        }
        return sum;
    }
    public static void main(String args[]) {
        //System.out.println(ipsBetween("10.0.0.0","10.0.0.50"));
        BoardGUI abs=BoardGUI.getInstance();


    }
}

