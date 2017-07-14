package DesignMode.Template;

/**
 * @author tsy
 * @Description 在CaffeineBeverage类中定义了一个名为prepareReipe()的模板方法，用来描述冲泡咖啡因饮料的过程。方法用final修饰是为了防止子类修改方法的执行顺序。
 *              CaffeineBeverage类定义了4个方法，分别是brew()、addCondiments()、boilWater()、pourInCup（）。在我们的示例中，冲泡咖啡和茶共有的过程分别是煮水
 *              boilWater()、倒进杯子里 pourInCup()。这两个共用方法选择在CaffeineBeverage类实现。
 *              Tea类、Coffee类是CaffeineBeverage类的子类。而加料 addCondiments()、浸泡
 *              brew()分别在Tea类、Coffee类中有各自不同的实现。
 * @date 9:24 2017/7/14
 */
public abstract class CaffeineBeverage {

    // 模板方法
    final void prepareReipe() {
        boilWater();
        brew();
        pourInCup();
        if (customerWantsCondiments()) {
            addCondiments();
        }
    }

    // 定义一个机构
    boolean customerWantsCondiments(){
        return true;
    }

    // 浸泡
    abstract void brew();

    // 加料
    abstract void addCondiments();

    // 煮水
    void boilWater() {
        System.out.println("Boiling water");
    }

    // 倒进杯子里
    void pourInCup() {
        System.out.println("Pouring into cup");
    }
}
