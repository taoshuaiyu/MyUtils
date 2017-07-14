package DesignMode.Observer;

/**
 * @author tsy
 * @Description
 * @date 16:23 2017/7/13
 */
public class CountObserver1 implements Observer {
    @Override
    public void update(Subject subject) {
        for (int i = 5; i > 0; i--) {
            System.out.println("CountObserver1 is working. i = " + i);
        }
    }
}
