import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author tsy
 * @Description java8特性 描述
 * @date 14:55 2017/6/12
 */
public class Java8 {

     public static void main(String[] args){
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.forEach(u->list.remove(u));

     }

    /**
     * 排序
     */
    public static void sort(){
        //旧排序
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");

        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return a.compareTo(b);
            }
        });

        //新排序
        Collections.sort(names,(String a,String b)->b.compareTo(a));

        names.forEach(u-> System.out.println(u));
    }

    /**
     * 函数是编程
     */
    public static void converter(){
        Converter<String, Integer> converter = (u) -> Integer.valueOf(u);
        Integer converted = converter.convert("123");
        System.out.println(converted);    // 123
    }

    /**
     * map 遍历
     */
    public static void map(){
        Map<String,String> map = new HashMap<String,String>();
        map.put("a","1");
        map.put("b","2");
        map.forEach((key,value)-> System.out.println(key));
    }
}

@FunctionalInterface
interface Converter<F, T> {
    T convert(F from);
}