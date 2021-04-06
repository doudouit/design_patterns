package com.patterns.singleton.staticinnerclass5;

import java.io.*;
import java.lang.reflect.Constructor;

/**
 * @Auther: allen
 * @Date: 2020/7/25 10:32
 * @Description: 静态内部类只会被加载一次，所以这种写法是安全的
 * <p>
 * 既能保证延时加载，又能保证线程安全。
 *
 * 注：以上5种单例模式都需要额外的工作（Serializable、transient、readResolve()）来实现序列化，否则每次反序列化一个序列化对象时，
 *     都会创建一个新的实例
 */
public class Singleton implements Serializable {
    private static final long serialVersionUID = -5859610580892670748L;

    private static class Holder {
        private static volatile Singleton singleton = new Singleton();
    }

    private Singleton() {
    }

    public static Singleton getInstance() {
        return Holder.singleton;
    }

    // 防止序列化生成一个新的对象 方法调用栈：readObject—>readObject0—>readOrdinaryObject 中指出如果实现readResolve方法，直接返回原对象
    public Object readResolve(){
        return Singleton.getInstance();
    }


//    《effective java》中只简单的提了几句话：“享有特权的客户端可以借助AccessibleObject.setAccessible方法，
//    ①通过反射机制调用私有构造器。如果需要低于这种攻击，可以修改构造器，让它在被要求创建第二个实例的抛出异常
//    ②要被反序列化恢复时，会生成一个新的对象，此对象和原来的对象具有一模一样的状态，但归根结底是两个对象。
    public static void main(String[] args) throws Exception {
        // 1.序列化方式

        // 序列化
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("test"));
        objectOutputStream.writeObject(Singleton.getInstance());
        objectOutputStream.close();

        // 反序列化 方法调用栈：readObject—>readObject0—>readOrdinaryObject，如果原对象实现序列化接口，就new一个新的对象返回
        File file = new File("test");
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
        Singleton singleton = (Singleton) objectInputStream.readObject();
        // 比较反序列化对象与元对象是否相同
        System.out.println(singleton == Singleton.getInstance());
        objectInputStream.close();
        file.delete();



        // 2.反射方式
        Singleton singletonR = Singleton.getInstance();
        Constructor<Singleton> constructor = Singleton.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Singleton newSingleton = constructor.newInstance();
        System.out.println(singletonR == newSingleton);
    }
}
