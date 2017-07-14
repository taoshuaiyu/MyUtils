package DesignMode.Factory;

/**
 * @author tsy
 * @Description 优点：可以隐藏具体类名称，提供参数给使用者直接调用；避免直接实例化对象，无需准备构造函数参数。
 *              缺点：在增加新产品的时候，必须修改工厂类，违背了开放封闭原则。
 * @date 9:45 2017/7/14
 */
public class Test {
    public static void main(String[] args) {
        IFruit apple = FruitFactory.getFruit("apple");
        IFruit orange = FruitFactory.getFruit("orange");

        apple.get();
        orange.get();
    }
}
