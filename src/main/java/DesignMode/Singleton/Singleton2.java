package DesignMode.Singleton;

/**
 * @author tsy
 * @Description 单例模式- 懒汉模式
 * @date 17:42 2017/7/13
 */
public class Singleton2 {

    private Singleton2() {

    }

    private static Singleton2 instance = null;

    public static Singleton2 getInstance() {
        if (null == instance) {
            instance = new Singleton2();
        }
        return instance;
    }
}
