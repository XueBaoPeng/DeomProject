package com.demo.project.service;

import com.demo.project.entity.CompileResult;
import com.demo.project.entity.Result;
import com.demo.project.ruleBase.DynamicRuleUtils;

/**
 * @Description:
 * @Author: xuebaopeng
 * @Date: 2021/4/2 18:33
 */
public class MainClass {
  static String    demoRule="package com.demo.project.ruleBase;\n" +
          "\n" +
          "import org.jeasy.rules.annotation.Action;\n" +
          "import org.jeasy.rules.annotation.Condition;\n" +
          "import org.jeasy.rules.annotation.Fact;\n" +
          "import org.jeasy.rules.annotation.Rule;\n" +
          "\n" +
          "/**\n" +
          " * @Description:\n" +
          " * @Author: xuebaopeng\n" +
          " * @Date: 2021/4/2 18:04\n" +
          " */\n" +
          "@Rule\n" +
          "public class DemoRule1  extends BaseRule{\n" +
          "\n" +
          "    @Condition\n" +
          "    public boolean when(@Fact(\"param1\") String param1) {\n" +
          "        System.out.println(\"我是参数1，value=\" + param1);\n" +
          "        return true;\n" +
          "    }\n" +
          "\n" +
          "    @Action\n" +
          "    public void then(@Fact(\"param2\") String param2) {\n" +
          "        System.out.println(\"我是参数2，value=\" + param2);\n" +
          "    }\n" +
          "}\n" ;


    public static void main(String[] args) throws Exception{
        Result<CompileResult> ruleResultSet= (Result<CompileResult>) DynamicRuleUtils.compile(demoRule);


//        try {
//            byte[] b = cc.toBytecode();
//            BaseRule baseRule=new BaseRule();
//            baseRule.setByteContent(b);
//            baseRule.setPriority(1);
//            baseRule.setFullClassName("com.demo.project.ruleBase.DemoRule1");
//            System.out.println(b);
//            cc.writeFile();
//        } catch (NotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (CannotCompileException e) {
//            e.printStackTrace();
//        }
    }
}
