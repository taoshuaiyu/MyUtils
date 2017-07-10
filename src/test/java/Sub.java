import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static javafx.scene.input.KeyCode.F;

/**
 * @author tsy
 * @Description
 * @date 10:54 2017/7/3
 */
public class Sub extends Super {
    private final Date date;

    Sub() {
        date = new Date();
    }

    @Override
    public void overrideMe() {
        System.out.println(date);
    }

    public static void main(String[] args) throws ParseException {
        // Sub sub = new Sub();
        // sub.overrideMe();
        // System.out.println("123ABC123".matches("[a-zA-Z0-9]*"));
        Integer a = 1;
        Integer b = 2;
        BigDecimal bigDecimal = new BigDecimal(100);
        Double f1 = new BigDecimal((float) a / b).setScale(3, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal(100)).doubleValue();
        Double f2 = new BigDecimal((float) a / b).setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        Double f3 = new BigDecimal(0.5789).setScale(3, BigDecimal.ROUND_HALF_UP).multiply(bigDecimal)
                .doubleValue();
        String str = "1.0";
        System.out.println(str.matches("(([1-9][0-9])\\.([0-9]{2}))|[0]\\.([0-9]{2})"));
        System.out.println(f1);

        int i=1;
        int j=2;
        System.out.println(sum(i,j));

    }
    static int sum(int...args){
        int sum=0;
        for (int arg:args){
            sum+=arg;
        }
        return sum;
    }
}

class Super {
    public Super() {

    }

    public void overrideMe() {

    }
}

class user {
    String name;
    Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}