package DesignMode.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tsy
 * @Description
 * @date 16:24 2017/7/13
 */
public class CountSubject implements Subject {

    private List<Observer> observerList;

    public CountSubject() {
        observerList = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void removerObserver(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyObserver() {
        observerList.stream().forEach(o -> o.update(this));
    }
}
