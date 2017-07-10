/**
 * @author tsy
 * @Description
 * @date 14:02 2017/7/3
 */
public enum Enmu {
    ONE("1", "one"), TWO("2", "two");
    private String code;
    private String value;

    Enmu(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
class test1{
    public static final String BAR = "-";
    public static void main(String[] args){
        System.out.println(Enmu.valueOf("ONE"));
    }
}