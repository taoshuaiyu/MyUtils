package util;

public class EnumUtils {

    public static <E extends Enum,V> boolean contains(Class<? extends Enum> enumClass,V ele,EqualsComparator<E,V>
            comparator){
        try {
            Enum[] es = enumClass.getEnumConstants();
            for (int i = 0; i < es.length; i++) {
                Enum e = es[i];
                if(comparator.equalsTo((E)e,ele)){
                    return true;
                }
            }
        }catch (NullPointerException e){
        }
        return false;
    }

    public interface EqualsComparator<V1,V2>{

        public boolean equalsTo(V1 value1,V2 value2);

    }

    private EnumUtils() {
    }
}