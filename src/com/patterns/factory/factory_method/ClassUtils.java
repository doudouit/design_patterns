package com.patterns.factory.factory_method;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @Auther: allen
 * @Date: 2020/7/30 16:05
 * @Description:
 */
public class ClassUtils {

    // 给一个接口，给出这个接口所有的实现类
    public static List<Class> getAllClassByInterface(Class c) {
        List<Class> returnClassList = new ArrayList<>();

        // 如果不是接口，不处理
        if (c.isInterface()) {
            // 获取当前包名
            String packageName = c.getPackage().getName();
            try {
                List<Class> classes = getClasses(packageName);
                for (int i = 0; i < classes.size(); i++) {
                    // 判断是不是一个接口方法
                    if (c.isAssignableFrom(classes.get(i))) {
                        // 本身不加进去
                        if (!c.equals(classes.get(i))) {
                            returnClassList.add(classes.get(i));
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return returnClassList;
    }

    // 从一个包中找到所有的类，在jar包中不能查找
    private static List<Class> getClasses(String packageName) throws IOException, ClassNotFoundException {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace(".", "/");
        Enumeration<URL> resources = contextClassLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            dirs.add(new File(url.getFile()));
        }
        List<Class> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + "." + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }


}
