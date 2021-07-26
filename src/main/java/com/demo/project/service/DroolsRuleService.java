package com.demo.project.service;



import com.demo.project.entity.DroolsInsuranceInfo;
import com.demo.project.entity.DroolsMytmodel;
import com.demo.project.utils.KieSessionUtils;
import org.drools.core.io.impl.UrlResource;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 * Created on 2021/7/26.
 *
 * @author xuebaopeng
 * Description
 */
@Service
public class DroolsRuleService {
    public static final String SERVER_URL = "http://192.168.56.10:8180/kie-server/services/rest/server";//server地址
    public static final String PASSWORD = "kieserver1!";//默认密码
    public static final String USERNAME = "kieserver";//默认账号
    public static final String KIE_CONTAINER_ID = "mytest1_1.0.0-SNAPSHOT";//访问http://192.168.56.10:8180/kie-server/services/rest/server/containers获取
    public static final String KIE_SESSION_ID = "mytest1";//在workBench配置KIE_SESSION_ID
    @Value(value = "${xlsPath}")
    private String xlsPath;
    /**
     * 决策表方式
     * @param droolsInsuranceInfo
     * @return
     * @throws Exception
     */
    public List<String> insuranceInfoCheck(DroolsInsuranceInfo droolsInsuranceInfo) throws Exception{
        KieSession session = KieSessionUtils.getKieSessionFromXLS(xlsPath);
        session.getAgenda().getAgendaGroup("sign").setFocus();

        session.insert(droolsInsuranceInfo);

        List<String> listRules = new ArrayList<>();
        session.setGlobal("listRules", listRules);

        session.fireAllRules();

        return listRules;
    }

    /**
     * workBench方式，使用maven的方式解析jar包进行规则访问。
     * @param droolsInsuranceInfo
     * @return
     * @throws IOException
     */
    public List<String> workBench(DroolsInsuranceInfo droolsInsuranceInfo) throws IOException {
        //通过此URL可以访问到maven仓库中的jar包
        //URL地址构成：http://ip地址:Tomcat端口号/WorkBench工程名/maven2/坐标/版本号/xxx.jar
        String url =
                "http://localhost:8080/kie-drools-wb/maven2/com/myspace/myProject/1.0.0/myProject-1.0.0.jar";

        KieServices kieServices = KieServices.Factory.get();

        //通过Resource资源对象加载jar包
        UrlResource resource = (UrlResource) kieServices.getResources().newUrlResource(url);
        //通过Workbench提供的服务来访问maven仓库中的jar包资源，需要先进行Workbench的认证
        resource.setUsername("kie");
        resource.setPassword("kie");
        resource.setBasicAuthentication("enabled");

        //将资源转换为输入流，通过此输入流可以读取jar包数据
        InputStream inputStream = resource.getInputStream();

        //创建仓库对象，仓库对象中保存Drools的规则信息
        KieRepository repository = kieServices.getRepository();

        //通过输入流读取maven仓库中的jar包数据，包装成KieModule模块添加到仓库中
        KieModule kieModule =repository.addKieModule(kieServices.getResources().newInputStreamResource(inputStream));

        //基于KieModule模块创建容器对象，从容器中可以获取session会话
        KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());
        KieSession session = kieContainer.newKieSession();
        session.getAgenda().getAgendaGroup("sign").setFocus();
        session.insert(droolsInsuranceInfo);

        List<String> listRules = new ArrayList<>();
        session.setGlobal("listRules", listRules);

        session.fireAllRules();

        return listRules;
    }

    /**
     * workBench+kie-server方式。
     * @return
     */
    public String workBench1() {
// KisService 配置信息设置
        KieServicesConfiguration kieServicesConfiguration;
        kieServicesConfiguration = KieServicesFactory.newRestConfiguration(SERVER_URL, USERNAME, PASSWORD, 10000L);
        kieServicesConfiguration.setMarshallingFormat(MarshallingFormat.JSON);

        // 创建规则服务客户端
        KieServicesClient kieServicesClient = KieServicesFactory.newKieServicesClient(kieServicesConfiguration);
        RuleServicesClient ruleServicesClient = kieServicesClient.getServicesClient(RuleServicesClient.class);

        // 规则输入条件
        DroolsMytmodel mytest = new DroolsMytmodel();
        mytest.setSex(0);

        // 命令定义，包含插入数据，执行规则
        KieCommands kieCommands = KieServices.Factory.get().getCommands();
        List<Command<?>> commands = new LinkedList<>();
        commands.add(kieCommands.newInsert(mytest, "mytestmodel"));
        commands.add(kieCommands.newFireAllRules());
        ServiceResponse<ExecutionResults> results = ruleServicesClient.executeCommandsWithResults(KIE_CONTAINER_ID,
                kieCommands.newBatchExecution(commands, KIE_SESSION_ID));

        // 返回值读取
        DroolsMytmodel value = (DroolsMytmodel) results.getResult().getValue("mytestmodel");
        return value.toString();

    }
}
