package DesignMode.Template;

/**
 * @author tsy
 * @Description
 * @date 9:30 2017/7/14
 */
public class Coffee extends CaffeineBeverage {
    @Override
    void brew() {
        System.out.println("Dripping Coffee through filter");
    }

    @Override
    void addCondiments() {
        System.out.println("Adding Suger and Mike");
    }
}
