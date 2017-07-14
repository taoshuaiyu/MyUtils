package DesignMode.Template;

/**
 * @author tsy
 * @Description
 * @date 9:28 2017/7/14
 */
public class Tea extends CaffeineBeverage {

    private String msg;

    public Tea(String msg) {
        this.msg = msg;
    }

    @Override
    void brew() {
        System.out.println("Stepping the tea");
    }

    @Override
    void addCondiments() {
        System.out.println("Adding Lemon");
    }

    boolean customerWantsCondiments() {
        return "y".equals(this.msg);
    }
}
