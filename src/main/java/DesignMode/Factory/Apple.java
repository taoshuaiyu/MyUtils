package DesignMode.Factory;

/**
 * @author tsy
 * @Description
 * @date 9:42 2017/7/14
 */
public class Apple implements IFruit {
    @Override
    public void get() {
        System.out.println("I am a apple");
    }
}