package DesignMode.Adapter;

/**
 * @author tsy
 * @Description 利用适配器后，使用这只需要调用一个接口即可。 复用性强，复用了现有的类，无需修改源角色的类，使得目标接口和源角色解耦。
 *              更好的拓展性，一个适配器可以集成多个源角色来完成目标接口。
 *              但会使得系统之间的关系变的负责，过多的使用适配器模式，无疑增加代码阅读和理解成本。
 * @date 9:55 2017/7/14
 */
public class Adaptee {

    public void method1() {
        System.out.println("Adaptee method1");
    }
}
