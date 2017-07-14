package DesignMode.Factory;

/**
 * @author tsy
 * @Description
 * @date 9:44 2017/7/14
 */
public class Orange implements IFruit {
    @Override
    public void get() {
        System.out.println("I am a orange." );
    }
}