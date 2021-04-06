package com.demo.project.ruleBase;

import com.alibaba.fastjson.JSON;
import com.demo.project.common.enums.EmDelStatus;
import com.demo.project.dao.JavaRuleDoDao;
import com.demo.project.entity.JavaRuleDo;
import com.demo.project.entity.Result;
import com.demo.project.service.CrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: xuebaopeng
 * @Date: 2021/4/2 18:16
 */
@Service
@Slf4j
public class JavaRuleService  extends CrudService<Integer, JavaRuleDo, JavaRuleDoDao> {

    @Autowired
    private JavaRuleStorage javaRuleStorage;

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
            "        System.out.println(\"我是参数22，value=\" + param1);\n" +
            "        return true;\n" +
            "    }\n" +
            "\n" +
            "    @Action\n" +
            "    public void then(@Fact(\"param2\") String param2) {\n" +
            "        System.out.println(\"我是参数2，value=\" + param2);\n" +
            "    }\n" +
            "}\n" ;
    @PostConstruct
    public void init() throws Exception {
        List<JavaRuleDo> javaRules = createJpaQuery();
        javaRules.stream()
                .forEach(javaRule -> {
                    try {
                        BaseRule rule = DynamicRuleUtils.getRuleInstance(javaRule);
                        if (!javaRuleStorage.add(javaRule.getGroupName(), rule)) {
                            log.warn("添加规则{}到容器失败！", javaRule.getName());
                            javaRule.setStatus(1);
                        }
                        log.info("添加了规则{}到容器", javaRule.getFullClassName());
                    } catch (Exception e) {
                        log.warn("添加规则{}到容器异常！", javaRule.getName());
                        javaRule.setStatus(0);
                    }
                });
    }


    public static void main(String[] args) {
        Map<String, String> stringStringMap=new HashMap<>();
        stringStringMap.put("key",demoRule);
        System.out.println(JSON.toJSONString(stringStringMap));
    }

    /**
     * 新增一个规则
     * @param javaRuleDo
     * @return
     * @throws Exception
     */
    @Transactional
    public Integer  saveJavaRule(JavaRuleDo javaRuleDo) throws Exception {
        Result<JavaRuleDo> ruleResultSet= (Result<JavaRuleDo>) DynamicRuleUtils.compiler(javaRuleDo);
        JavaRuleDo insertRule=ruleResultSet.getData();
        javaRuleDo.setByteContent(insertRule.getByteContent());
        javaRuleDo.setFullClassName(insertRule.getFullClassName());
        javaRuleDo.setGroupName(insertRule.getGroupName());
        javaRuleDo.setSimpleClassName(insertRule.getSimpleClassName());
        return insert(javaRuleDo);
    }

    @Transactional
    public int  updateJavaRule(JavaRuleDo javaRuleDo) throws Exception {
        Result<JavaRuleDo> ruleResultSet= (Result<JavaRuleDo>) DynamicRuleUtils.compiler(javaRuleDo);
        JavaRuleDo insertRule=ruleResultSet.getData();
        javaRuleDo.setByteContent(insertRule.getByteContent());
        javaRuleDo.setFullClassName(insertRule.getFullClassName());
        javaRuleDo.setGroupName(insertRule.getGroupName());
        javaRuleDo.setSimpleClassName(insertRule.getSimpleClassName());
        int update=update(javaRuleDo);
        init();
        return update;
    }
    public List<JavaRuleDo> createJpaQuery() throws Exception {
         JavaRuleDo search=new JavaRuleDo();
         search.setDelStatus(EmDelStatus.NOT_DELETE.getCode());
         return  findAll();
    }
}
