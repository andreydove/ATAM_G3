import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Lesson3_2 {
    public static void printArray(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    public static void printArrayUsingStream(List<String> list) {
        try {
            (list.stream().map(item -> item.split("")).collect(Collectors.toList()))
                    .stream().forEach(el -> Arrays.stream(el).forEach(System.out::print));
        }catch (NullPointerException n){
            System.out.println("Массив не определён. Пожалуйста проверте корректность вводимых значений");
        }catch (Throwable a){
            System.out.println("Произошла неизвесная ошибка. Подробнее: " + a.getMessage());
        }
    }
}
