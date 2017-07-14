package DesignMode.Template;

/**
 * @author tsy
 * @Description
 * @date 9:31 2017/7/14
 */
public class Test {
    public static void main(String[] args){
        Tea tea=new Tea("n");
        tea.prepareReipe();

        System.out.println("***********");
        Coffee coffee=new Coffee();
        coffee.prepareReipe();
    }
}
