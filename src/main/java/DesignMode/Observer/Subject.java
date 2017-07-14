package DesignMode.Observer;

/**
 * @author tsy
 * @Description
 * @date 16:17 2017/7/13
 */
public interface Subject {

    void registerObserver(Observer observer);

    void removerObserver(Observer observer);

    void notifyObserver();
}
