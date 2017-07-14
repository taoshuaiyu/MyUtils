package DesignMode.Factory;

/**
 * @author tsy
 * @Description
 * @date 9:43 2017/7/14
 */
public class FruitFactory {
    public static IFruit getFruit(String type) {
        IFruit ifruit = null;
        if ("apple".equals(type)) {
            ifruit = new Apple();
        } else if ("orange".equals(type)) {
            ifruit = new Orange();
        }
        return ifruit;
    }
}
