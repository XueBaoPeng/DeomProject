package com.demo.project.utils;

import org.codehaus.commons.compiler.CompilerFactoryFactory;
import org.codehaus.commons.compiler.ICompiler;
import org.codehaus.commons.compiler.util.ResourceFinderClassLoader;
import org.codehaus.commons.compiler.util.resource.MapResourceCreator;
import org.codehaus.commons.compiler.util.resource.MapResourceFinder;
import org.codehaus.commons.compiler.util.resource.StringResource;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: xuebaopeng
 * @Date: 2021/4/6 9:55
 */
public class HotClassesPool {

    private final static Map<String, Class<?>> pool = new HashMap<>(1024);

    private static ICompiler compiler;

    static {
        try {
            compiler = CompilerFactoryFactory.getDefaultCompilerFactory().newCompiler();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void put(String fullName, String code) throws Exception {
        put(new StringResource[]{new StringResource(fullName, code)});
    }

    public static void put(StringResource[] sources) throws Exception {

        // Store generated .class files in a Map:
        Map<String, byte[]> classes = new HashMap<String, byte[]>();
        compiler.setClassFileCreator(new MapResourceCreator(classes));

        // Now compile two units from strings:
        compiler.compile(sources);

        // Set up a class loader that uses the generated classes.
        ClassLoader cl = new ResourceFinderClassLoader(
                new MapResourceFinder(classes),    // resourceFinder
                ClassLoader.getSystemClassLoader() // parent
        );

        for (StringResource source : sources) {
            pool.put(source.getFileName(), cl.loadClass(source.getFileName()));
        }

    }

    public static Class<?> load(String classFullName) {
        return pool.get(classFullName);
    }


    public static void remove(String fileName) {
        pool.remove(fileName);
    }
}
