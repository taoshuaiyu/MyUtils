package DesignMode.Observer;

/**
 * @author tsy
 * @Description
 * @date 16:27 2017/7/13
 */
public class TestObserver {
    public static void main(String[] args) {

        Subject subject = new CountSubject();

        Observer observer = new CountObserver();
        Observer observer1 = new CountObserver1();

        subject.registerObserver(observer);
        subject.registerObserver(observer1);
        subject.notifyObserver();
    }
}
