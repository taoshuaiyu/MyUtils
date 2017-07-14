package DesignMode.Singleton;

/**
 * @author tsy
 * @Description 单例模式- 双重检查锁定
 *              这里可以注意到修饰变量instance的关键字增加了volatile。这里volatile主要作用是提供内存屏障，禁止指令重排序。
 *              现有t1、t2两个线程同时访问getInstance(),假设t1、t2都执行到A处。由于有同步锁，只能有个1个线程获得锁，假如t1拥有该同步锁，t1执行到C处instace
 *              = new Singleton()。将会做如下3步骤： 1.分配内存空间 2.初始化 3.将instance指向分配内存空间
 *              正常的执行顺序应为：1->2->3。执行第3步时，这时候的instance就不再是null了。但由于指令重排序的存在，
 *              执行顺序有可能变化为：1->3->2。当执行3的时候，instance就不再是null，但初始化工作有可能还没有做完。
 *              这时候如果t2获取锁执行的话，就会直接获取有可能还没有初始化完成的instance。这样使用instance会引起程序报错。
 *              当然这也是极端情况下，我尝试几次无法捕捉重现，但并不意味着问题不存在。volatile当然还是要加的
 * @date 17:44 2017/7/13
 */
public class Singleton3 {
    private Singleton3() {

    }

    private static volatile Singleton3 instance = null;

    public static Singleton3 getInstance() {
        if (null == instance) {
            synchronized (Singleton3.class) {
                if (null == instance) {
                    instance = new Singleton3();
                }
            }
        }
        return instance;
    }
}
