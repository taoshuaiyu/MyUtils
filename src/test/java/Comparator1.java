import org.apache.commons.collections.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author tsy
 * @Description
 * @date 17:36 2017/6/12
 */
public class Comparator1 {
    public static void CollectionNine() {
        NumComparator comparator = new NumComparator();
        TreeSet<NameTag> set = new TreeSet<NameTag>(comparator);
        set.add(new NameTag("Agamemnon",300));
        set.add(new NameTag("Cato",400));
        set.add(new NameTag("Plato",100));
        set.add(new NameTag("Zeno",200));
        set.add(new NameTag("Archimedes",500));
        for(NameTag tag : set)
            System.out.println(tag.getName());
    }
    public static void main(String arg[]) throws UnsupportedEncodingException {
        CollectionNine();

    }
}
class NumComparator implements Comparator<NameTag> {
    public int compare (NameTag left,NameTag right) {
        int i= right.getAge() - left.getAge();
        return i;
    }

}
class NameTag{
    String name;
    int age;

    public NameTag(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NameTag nameTag = (NameTag) o;

        if (age != nameTag.age) return false;
        return name != null ? name.equals(nameTag.name) : nameTag.name == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        return result;
    }
}