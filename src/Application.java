import java.util.ArrayList;

public class Application {
    public static void main(String[] args){
        //Lesson3_2.printArray(Lesson3.returnList(Lesson3.arr));
        //second homework below
        Lesson3_2.printArrayUsingStream(null);
        Lesson3_2.printArrayUsingStream(Lesson3.returnList(Lesson3.arr));
        Lesson3_2.printArrayUsingStream(new ArrayList<String>());
    }
}
