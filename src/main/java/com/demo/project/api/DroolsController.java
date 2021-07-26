package com.demo.project.api;

import com.demo.project.entity.DroolsInsuranceInfo;
import com.demo.project.service.DroolsRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2021/7/26.
 *
 * @author xuebaopeng
 * Description
 */
@RestController
@RequestMapping("/drools")
public class DroolsController {
    @Autowired
    private DroolsRuleService ruleService;


    /**
     * 加载excel文件中的规则
     * @return
     */
    @RequestMapping("/insuranceInfoCheck")
    public Map insuranceInfoCheck(){
        Map map = new HashMap();

        //模拟数据，实际应为页面传递过来
        DroolsInsuranceInfo insuranceInfo = new DroolsInsuranceInfo();
        insuranceInfo.setParam1("picc");
        insuranceInfo.setParam4("上海");
        insuranceInfo.setParam5("101");
        insuranceInfo.setParam6("12");
        insuranceInfo.setParam7("222");
        insuranceInfo.setParam8("1");
        insuranceInfo.setParam13("3");

        try {
            List<String> list = ruleService.insuranceInfoCheck(insuranceInfo);
            if(list != null && list.size() > 0){
                map.put("checkResult",false);
                map.put("msg","准入失败");
                map.put("detail",list);
            }else{
                map.put("checkResult",true);
                map.put("msg","准入成功");
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("checkResult",false);
            map.put("msg","未知错误");
            return map;
        }
    }

    /**
     * 加载workBench中配置的规则
     * @return
     */
    @RequestMapping("/workBench")
    public Map workBench(){
        Map map = new HashMap();

        //模拟数据，实际应为页面传递过来
        DroolsInsuranceInfo insuranceInfo = new DroolsInsuranceInfo();
        insuranceInfo.setParam1("PICC");
        insuranceInfo.setParam4("上海");
        insuranceInfo.setParam5("101");
        insuranceInfo.setParam6("12");
        insuranceInfo.setParam7("222");
        insuranceInfo.setParam8("1");
        insuranceInfo.setParam13("3");

        try {
            List<String> list = ruleService.workBench(insuranceInfo);
            if(list != null && list.size() > 0){
                map.put("checkResult",false);
                map.put("msg","准入失败");
                map.put("detail",list);
            }else{
                map.put("checkResult",true);
                map.put("msg","准入成功");
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("checkResult",false);
            map.put("msg","未知错误");
            return map;
        }
    }

    /**
     * workBench+drools-server
     * 坑1：出现jackson的jar冲突问题，因为spring或者kie中用的jackson为高版本
     *      解决：exclusion掉springboot和kie-server-client中的jackson-databind，重新引入低版本的jackson-databind即可，我这里是从2.9降为2.7.3就可以了。
     * 坑2：workBench中的数据对象的包位置必须和项目中对应的数据对象的包位置一致
     *      例如：workBench中为com.myspace.mytest1.mytestmodel1   那么项目里的数据对象也应该放在这个地方，不然规则中set数据set不进去
     * @return
     */
    @RequestMapping("/workBench1")
    public String workBench1(){
        String strValue = ruleService.workBench1();
        return strValue;
    }

}
