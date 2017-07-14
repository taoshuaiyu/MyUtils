package DesignMode.Singleton;

/**
 * @author tsy
 * @Description 单例模式- 枚举方式
 *              用枚举方式实现单例模式，是目前比较推荐的。枚举方式的好处是：1、线程安全；2、防止反射出现多个实例；3、防止反序列化出现多个实例。
 * @date 17:47 2017/7/13
 */
public enum Singleton4 {
    INSTANCE;
    Singleton4() {
    }

    public Singleton4 getInstance() {
        return INSTANCE;
    }
}
