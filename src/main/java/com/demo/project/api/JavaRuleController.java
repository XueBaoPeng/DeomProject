package com.demo.project.api;

import com.demo.project.common.enums.EmDelStatus;
import com.demo.project.entity.JavaRuleDo;
import com.demo.project.entity.Result;
import com.demo.project.ruleBase.DynamicRuleManager;
import com.demo.project.ruleBase.JavaRuleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Description:
 * @Author: xuebaopeng
 * @Date: 2021/4/6 10:54
 */
@RestController()
@RequestMapping(value = "/rule", method = { RequestMethod.POST, RequestMethod.GET })
public class JavaRuleController extends BaseController{

    @Autowired
    DynamicRuleManager dynamicRuleManager;
     @Autowired
    JavaRuleService javaRuleService;

    @RequestMapping("/query/list")
    @ApiOperation(value = "查询规则集合",notes = "返回的规则集合")
    public Result list(@RequestBody JavaRuleDo memberInfo) throws Exception {
        // 默认查询没有删除的用户
        memberInfo.setDelStatus(EmDelStatus.NOT_DELETE.getCode());
        return result(javaRuleService.find(memberInfo));
    }

    /**
     * 保存（含id为新增，不含id为修改）
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    @ApiOperation(value = "新增一个规则",notes = "新增之后返回对象")
    public Result save(@RequestBody JavaRuleDo entity) throws Exception {
        return result(javaRuleService.saveJavaRule(entity));
    }

    @RequestMapping("/modify")
    @ApiOperation(value = "修改一个规则",notes = "修改后的返回值")
    public Result modify(@RequestBody JavaRuleDo entity) throws Exception {
        return result(javaRuleService.updateJavaRule(entity));
    }

    @GetMapping("/query")
    public String rule(@RequestParam(value = "name",defaultValue = "World")String name) throws Exception {
        DynamicRuleManager.Builder builder = dynamicRuleManager.builder()
                .setParameter("param1", "Hello")
                .setParameter("param2", "World")
                .registerAllRules()
                .run();
        String param1 = builder.getParameter("param1", String.class);
        String param2 = builder.getParameter("param2", String.class);
        System.out.println(param1 + " " + param2);
        return String.format("Hello %s!",param1 + " " + param2);
    }


}
