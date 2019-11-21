package compile;

import org.codehaus.janino.ClassBodyEvaluator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class JaninoCompiler {

    private static Map<String,String> map=new HashMap<>();

    static {
        //用map模拟存储的脚本，脚本可以是很复杂，这里只作简单测试
        map.put("0", "return new EacProxy(){	public String query(){		return \"xxx交通管理局子公司分部一\";	}};");
        map.put("1", "return new EacProxy(){	public String query(){		return \"sss交通管理局子公司分部二\";	}};");
        map.put("2", "return new EacProxy(){	public String query(){		return \"阿里杭州萧山一公司\";	}};");
        map.put("3", "return new EacProxy(){	public String query(){		return \"菜鸟物流下沙分仓库\";	}};");
    }
    public static String generateProxySource(String source) throws IOException {
        String template = StreamUtils.InputStreamTOString(JaninoCompiler.class.getResourceAsStream("/EacProxy.temp")
        );
        return template.replaceAll("#replaced#", source);
    }

    public static void main(String[] args) throws Exception{
        try {
            ClassBodyEvaluator ce = new ClassBodyEvaluator();//class body
            String key = String.valueOf(new Random().nextInt(4));
            String source = generateProxySource(map.get(key));
            ce.cook(source);
            Object obj = ce.getClazz().getMethod("createProxy").invoke(null);
            EacProxy ep = (EacProxy) obj;
            System.out.println("JaninoCompiler.main"+ep.query());
        }catch (Exception e){
            throw e;
        }
    }

}
