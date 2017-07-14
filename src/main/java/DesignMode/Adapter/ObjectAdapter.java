package DesignMode.Adapter;

/**
 * @author tsy
 * @Description Adapter类直接实现了Target接口。Adapter类拥有一个Adaptee类的对象，将method1方法的实现委托给该对象实现。
 * @date 10:03 2017/7/14
 */
public class ObjectAdapter implements Target {

    private Adaptee adaptee;

    public ObjectAdapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void method1() {
        adaptee.method1();
    }

    @Override
    public void method2() {
        System.out.println("Adapter method2");
    }
}
