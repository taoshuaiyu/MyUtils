import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author tsy
 * @Description
 * @date 14:25 2017/7/17
 */
public class DiskClassLoader extends ClassLoader {

    private String mLibPath;

    public DiskClassLoader(String path) {
        mLibPath = path;
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String filename = getFileName(name);
        File file = new File(mLibPath, filename);
        try {
            FileInputStream is = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len = 0;
            while ((len = is.read()) != -1) {
                bos.write(len);
            }

            byte[] data = bos.toByteArray();
            is.close();
            bos.close();

            return defineClass(name, data, 0, data.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    // 获得要加载的class文件名
    private String getFileName(String name) {
        int index = name.lastIndexOf(".");
        if (-1 == index) {
            return name + ".class";
        } else {
            return name.substring(index) + ".class";
        }
    }

    public static void main(String[] args){
        //创建自定义classLoader对象
        DiskClassLoader diskLoader = new DiskClassLoader("D:\\lib");
        try {
            Class c=diskLoader.loadClass("Test");
            if (null !=c){
                Object obj=c.newInstance();
                Method method=c.getDeclaredMethod("say",null);
                //通过反射调用Test类的say方法
                method.invoke(obj,null);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
