package DesignMode.Adapter;

/**
 * @author tsy
 * @Description Adapter类通过继承源角色Adaptee，复用了父类method1方法，并实现Target接口中Adaptee类中没有的方法method2。从而转换为用户期待目标的接口。
 * @date 9:55 2017/7/14
 */
public class ClassAdapter extends Adaptee implements Target {
    @Override
    public void method2() {
        System.out.println("Adapter method2");
    }
}
