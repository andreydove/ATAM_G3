import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Lesson3 {
    public String[] arr = new String[]{"Kyiv","New York","London","Berlin","Paris","Madrid","Rome"};

    public List<String> returnList(String[] array){
        int i = 0;
        List<String> list = new ArrayList<String>();
        while(i!=6){
            i++;
            if (i == 3){
                continue;
            }
            list.add(array[i]);
        }
        return list;
    }
}


