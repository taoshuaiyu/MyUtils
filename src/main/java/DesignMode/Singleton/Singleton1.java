package DesignMode.Singleton;

/**
 * @author tsy
 * @Description 单例模式-饿汉模式
 * @date 17:41 2017/7/13
 */
public class Singleton1 {

    private Singleton1() {

    }

    private static Singleton1 instance = new Singleton1();

    public static Singleton1 getInstance() {
        return instance;
    }
}
