package com.demo.project.ruleBase;

import com.demo.project.entity.CompileResult;
import com.demo.project.entity.JavaRuleDo;
import com.demo.project.entity.Result;
import lombok.extern.slf4j.Slf4j;

import javax.tools.*;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.CharBuffer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**https://blog.csdn.net/qq_31142553/article/details/85013989
 * @Description:
 * @Author: xuebaopeng
 * @Date: 2021/4/2 16:42
 */
@Slf4j
public class DynamicRuleUtils {
    // 编译版本
    private static final String TARGET_CLASS_VERSION = "1.8";
    private JavaRuleStorage javaRuleStorage=new MapJavaRuleStorage();

    public static Result<CompileResult> compile(String javaSrc) throws RuntimeException{
        Pattern pattern = Pattern.compile("public\\s+class\\s+(\\w+)");
        Matcher matcher = pattern.matcher(javaSrc);
        if (matcher.find()) {
            return compile(matcher.group(1) + ".java", javaSrc);
        }
        return Result.error("找不到类名称！");
    }


    public static Result<CompileResult> compile(String javaName, String javaSrc) throws RuntimeException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager stdManager = compiler.getStandardFileManager(null, null, null);
        try(MemoryJavaFileManager manager = new MemoryJavaFileManager(stdManager)) {
            JavaFileObject javaFileObject = MemoryJavaFileManager.makeStringSource(javaName, javaSrc);
            DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();
            List<String> options = new ArrayList<>();
            options.add("-target");
            options.add(TARGET_CLASS_VERSION);
            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, collector, options, null,
                    Arrays.asList(javaFileObject));
            if (task.call()) {
                return Result.success(CompileResult.builder()
                        .mainClassFileName(javaName)
                        .byteCode(manager.getClassBytes())
                        .build());
            }
            String errorMessage = collector.getDiagnostics().stream()
                    .map(diagnostics -> diagnostics.toString())
                    .reduce("", (s1, s2) -> s1 + "\n" +s2);
            return Result.error(errorMessage);
        }catch (IOException e){
            log.error("编译出错啦！", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * JavaFileManager that keeps compiled .class bytes in memory.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    static final class MemoryJavaFileManager extends ForwardingJavaFileManager {
        /**
         * Java source file extension.
         */
        private final static String EXT = ".java";
        private Map<String, byte[]> classBytes;

        public MemoryJavaFileManager(JavaFileManager fileManager) {
            super(fileManager);
            classBytes = new HashMap<String, byte[]>();
        }

        public Map<String, byte[]> getClassBytes() {
            return classBytes;
        }

        @Override
        public void close() throws IOException {
            classBytes = new HashMap<String, byte[]>();
        }

        @Override
        public void flush() throws IOException {
        }

        /**
         * A file object used to represent Java source coming from a string.
         */
        private static class StringInputBuffer extends SimpleJavaFileObject {
            final String code;

            StringInputBuffer(String name, String code) {
                super(toURI(name), Kind.SOURCE);
                this.code = code;
            }

            public CharBuffer getCharContent(boolean ignoreEncodingErrors) {
                return CharBuffer.wrap(code);
            }

            @SuppressWarnings("unused")
            public Reader openReader() {
                return new StringReader(code);
            }
        }

        /**
         * A file object that stores Java bytecode into the classBytes map.
         */
        private class ClassOutputBuffer extends SimpleJavaFileObject {
            private String name;

            ClassOutputBuffer(String name) {
                super(toURI(name), Kind.CLASS);
                this.name = name;
            }

            public OutputStream openOutputStream() {
                return new FilterOutputStream(new ByteArrayOutputStream()) {
                    public void close() throws IOException {
                        out.close();
                        ByteArrayOutputStream bos = (ByteArrayOutputStream) out;
                        classBytes.put(name, bos.toByteArray());
                    }
                };
            }
        }

        @Override
        public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location, String className,
                                                   JavaFileObject.Kind kind, FileObject sibling) throws IOException {
            if (kind == JavaFileObject.Kind.CLASS) {
                return new ClassOutputBuffer(className);
            } else {
                return super.getJavaFileForOutput(location, className, kind, sibling);
            }
        }

        static JavaFileObject makeStringSource(String name, String code) {
            return new StringInputBuffer(name, code);
        }

        static URI toURI(String name) {
            File file = new File(name);
            if (file.exists()) {
                return file.toURI();
            } else {
                try {
                    final StringBuilder newUri = new StringBuilder();
                    newUri.append("mfm:///");
                    newUri.append(name.replace('.', '/'));
                    if (name.endsWith(EXT))
                        newUri.replace(newUri.length() - EXT.length(), newUri.length(), EXT);
                    return URI.create(newUri.toString());
                } catch (Exception exp) {
                    return URI.create("mfm:///com/sun/script/java/java_source");
                }
            }
        }
    }

    /*
     * a temp memory class loader
     */
    private static class MemoryClassLoader extends URLClassLoader {
        Map<String, byte[]> classBytes = new HashMap<String, byte[]>();
        public MemoryClassLoader(Map<String, byte[]> classBytes) {
            super(new URL[0], MemoryClassLoader.class.getClassLoader());
            this.classBytes.putAll(classBytes);
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            byte[] buffer=classBytes.get(name);
            if (buffer==null){
                return super.findClass(name);
            }
            classBytes.remove(name);
            return defineClass(name,buffer,0,buffer.length);
        }
    }


    /**
     * 从JavaRuleDO获取规则Class对象
     * @param javaRule
     * @return
     * @throws Exception
     */
    public static Class<?> getRuleClass(JavaRuleDo javaRule) throws Exception{
        Map<String, byte[]> bytecode = new HashMap<>();
        String fullName = Objects.requireNonNull(javaRule.getFullClassName(), "全类名不能为空！");
        bytecode.put(fullName, javaRule.getByteContent());
        try (MemoryClassLoader classLoader = new MemoryClassLoader(bytecode)) {
            return classLoader.findClass(fullName);
        } catch (Exception e) {
            log.error("加载类{}异常！", fullName);
            throw e;
        }
    }


    /**
     * 将编译的信息设置到实体对象
     * @param entity
     * @return
     */
    public static Result<JavaRuleDo> compiler(JavaRuleDo entity) {
        Result<?> result = DynamicRuleUtils.compile(entity.getSrcCode());
        if (result.isError()) {
            return (Result<JavaRuleDo>) result;
        }
        CompileResult compileResult = (CompileResult) result.getData();
        for (String classFullName : compileResult.getByteCode().keySet()) {
            int lastIndex = classFullName.lastIndexOf(".");
            String simpleName = lastIndex != -1 ? classFullName.substring(lastIndex + 1) : classFullName,
                    fileName = compileResult.getMainClassFileName();
            // 只要最外层的类
            if (fileName.startsWith(simpleName)) {
                entity.setFileName(fileName);
                entity.setFullClassName(classFullName);
                entity.setSimpleClassName(simpleName);
                entity.setByteContent(compileResult.getByteCode().get(classFullName));
                return Result.success(entity);
            }
        }
        return Result.error("没有找到最外层类！");
    }

    /**
     * 添加规则到容器
     * @param entity
     * @return
     */
    private Result<JavaRuleDo> addRuleToStorage(JavaRuleDo entity) {
        try {
            BaseRule rule = DynamicRuleUtils.getRuleInstance(entity);
            return javaRuleStorage.add(entity.getGroupName(), rule) ? Result.success(entity)
                    : Result.error("添加规则到容器失败！");
        } catch (Exception e) {
            log.error("添加规则{}到容器异常！", entity.getName(), e);
            return Result.error("添加规则到容器异常！");
        }
    }
    /**
     * 添加规则到容器
     * @param entity
     * @return
     */
    private Result removeRuleToStorage(JavaRuleDo entity) {
        String groupName = entity.getGroupName();
        try {
            BaseRule rule = DynamicRuleUtils.getRuleInstance(entity);
            if (javaRuleStorage.contains(groupName, rule) && !javaRuleStorage.remove(groupName, rule)) {
                return Result.error("从容器移除规则失败！");
            }
        } catch (Exception e) {
            log.error("从容器移除规则{}异常！", entity.getName(), e);
            return Result.error("从容器移除规则异常！");
        }
        return null;
    }

    /**
     * 从JavaRuleDO获取规则实例对象
     * @param javaRule
     * @return
     * @throws Exception
     */
    public static BaseRule getRuleInstance(JavaRuleDo javaRule) throws Exception{
        try {
            BaseRule rule = (BaseRule) getRuleClass(javaRule).newInstance();
            // 设置优先级
            rule.setPriority(javaRule.getSort());
            return rule;
        } catch (Exception e) {
            log.error("从JavaRuleDO获取规则实例异常！", e);
            throw e;
        }
    }

}
