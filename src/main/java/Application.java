import java.util.ArrayList;

public class Application {
    public static void main(String[] args){
        //Lesson3_2.printArray(Lesson3.returnList(Lesson3.arr));
        //second homework below
        Lesson3 data = new Lesson3();
        String[] arr = data.arr;
        Lesson3_2 print = new Lesson3_2();
        print.printArrayUsingStream(null);
        print.printArrayUsingStream(data.returnList(arr));
        print.printArrayUsingStream(new ArrayList<String>());
    }
}
