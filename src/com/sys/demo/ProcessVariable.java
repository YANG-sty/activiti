package com.sys.demo;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author y_zzu 2020-01-16-10:21
 */
public class ProcessVariable {
    /**
     * deploy   部署
     * startProcess 执行流程
     * setVariable 设置流程变量值
     * getVariable 查询流程变量
     * queryTask    查询任务
     * complete     执行任务
     */
    /**
     * 部署流程定义
     * 取得流程引擎，且自动创建Activiti涉及的数据库和表
     */
    @Test
    public void deploy() {
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("config.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        //获取仓库服务： 管理流程定义
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //创建一个部署的构建器
        Deployment deployment = repositoryService.createDeployment()
                //从类路径中加载资源，一次只能添加一个资源
                .addClasspathResource("diagrams/processVariable.bpmn")
                //设置部署的名称
                .name("流程变量值")
                //设置部署的类别
                .category("di_er_ban")
                .deploy();

        System.out.println("部署的id ： " + deployment.getId());
        System.out.println("部署的名称： " + deployment.getName());
    }

    /**
     * 执行流程
     */
    @Test
    public void startProcess() {
        //启动流程，使用的是 act_re_procdef 中的 key
        String processDefikey = "myProcess_1";
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("config.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        //取运行时服务
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //取得流程实例,通过流程定义的key(在 .bpmn 里面表示的是 id)
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefikey);
        System.out.println("流程实例 id: " + processInstance.getId());
        System.out.println("流程定义 id：" + processInstance.getProcessDefinitionId());
        System.out.println("流程实例的id：" + processInstance.getProcessInstanceId());
    }

    /**
     * 查询任务
     */
    @Test
    public void queryTask() {
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("config.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        //取得任务服务
        TaskService taskService = processEngine.getTaskService();
        //创建一个任务查询对象
        TaskQuery taskQuery = taskService.createTaskQuery();
        String name = "bianliang_1";
        List<Task> listName = taskQuery.taskName(name).list();
        if (!listName.isEmpty() && listName.size() > 0) {
            for (Task task : listName) {
                System.out.println("name 任务办理人： " + task.getAssignee());
                System.out.println("name 任务id : " + task.getId());
                System.out.println("name 任务的名称: " + task.getName());
            }
        } else {
            System.out.println("name 数据为空！！！！！！！！！！！！！！！");

        }


    }


    /**
     * 执行任务
     */
    @Test
    public void complete() {
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("config.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        //任务id，正在运行的任务列表的 id
        String taskId = "4502";
        //执行任务
        processEngine.getTaskService().complete(taskId);
        System.out.println("当前任务执行完毕！！！！！");
    }

    /**
     * 设置流程变量值
     */
    @Test
    public void setVariable(){
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("config.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        //任务id
        String taskId="4502";
        //采用TaskService来设置流程变量

        //1. 第一次设置流程变量
//		TaskService taskService = processEngine.getTaskService();
//		taskService.setVariable(taskId, "cost", 1000);//设置单一的变量，作用域在整个流程实例
//		taskService.setVariable(taskId, "申请时间", new Date());
//		taskService.setVariableLocal(taskId, "申请人", "何某某");//该变量只有在本任务中是有效的


        //2. 在不同的任务中设置变量
//		TaskService taskService = processEngine.getTaskService();
//		taskService.setVariable(taskId, "cost", 5000);//设置单一的变量，作用域在整个流程实例
//		taskService.setVariable(taskId, "申请时间", new Date());
//		taskService.setVariableLocal(taskId, "申请人", "李某某");//该变量只有在本任务中是有效的

        /**
         * 3. 变量支持的类型
         * - 简单的类型 ：String 、boolean、Integer、double、date
         * - 自定义对象bean
         */
        TaskService taskService = processEngine.getTaskService();
        //传递的一个自定义bean对象
        AppayBillBean appayBillBean=new AppayBillBean();
        appayBillBean.setId(2);
        appayBillBean.setCost(44400);
        appayBillBean.setDate(new Date());
        appayBillBean.setAppayPerson("冯宝宝");
        taskService.setVariable(taskId, "appayBillBean", appayBillBean);


        System.out.println("设置成功！!！！！！！！！！");

    }

    /**
     * 查询流程变量
     */
    @Test
    public void getVariable(){
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("config.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        //任务id
        String taskId="4502";
//		TaskService taskService = processEngine.getTaskService();
//		Integer cost=(Integer) taskService.getVariable(taskId, "cost");//取变量
//		Date date=(Date) taskService.getVariable(taskId, "申请时间");//取本任务中的变量
////		Date date=(Date) taskService.getVariableLocal(taskId, "申请时间");//取本任务中的变量
//		String appayPerson=(String) taskService.getVariableLocal(taskId, "申请人");//取本任务中的变量
////		String appayPerson=(String) taskService.getVariable(taskId, "申请人");//取本任务中的变量
//
//		System.out.println("金额:"+cost);
//		System.out.println("申请时间:"+date);
//		System.out.println("申请人:"+appayPerson);


        //读取实现序列化的对象变量数据
        TaskService taskService = processEngine.getTaskService();
        AppayBillBean appayBillBean=(AppayBillBean) taskService.getVariable(taskId, "appayBillBean");
        System.out.println("id: " +  appayBillBean.getId());
        System.out.println("金额: " + appayBillBean.getCost());
        System.out.println("申请人: " + appayBillBean.getAppayPerson());
        System.out.println("时间: " + appayBillBean.getDate());

    }

    //模拟流程变量设置
    @Test
    public void  getAndSetProcessVariable(){
        //有两种服务可以设置流程变量
//		TaskService taskService = processEngine.getTaskService();
//		RuntimeService runtimeService = processEngine.getRuntimeService();

        /**1.通过 runtimeService 来设置流程变量
         * exxcutionId: 执行对象
         * variableName：变量名
         * values：变量值
         */
//		runtimeService.setVariable(exxcutionId, variableName, values);
//		runtimeService.setVariableLocal(executionId, variableName, values);
        //设置本执行对象的变量 ，该变量的作用域只在当前的execution对象
//		runtimeService.setVariables(exxcutionId, variables);
        //可以设置多个变量  放在 Map<key,value>  Map<String,Object>

        /**2. 通过TaskService来设置流程变量
         * taskId：任务id
         */
//		taskService.setVariable(taskId, variableName, values);
//		taskService.setVariableLocal(taskId, variableName, values);
////		设置本执行对象的变量 ，该变量的作用域只在当前的execution对象
//		taskService.setVariables(taskId, variables); //设置的是Map<key,values>

        /**3. 当流程开始执行的时候，设置变量参数
         * processDefiKey: 流程定义的key
         * variables： 设置多个变量  Map<key,values>
         */
//		processEngine.getRuntimeService()
//		.startProcessInstanceByKey(processDefiKey, variables)

        /**4. 当执行任务时候，可以设置流程变量
         * taskId:任务id
         * variables： 设置多个变量  Map<key,values>
         */
//		processEngine.getTaskService().complete(taskId, variables);


        /** 5. 通过RuntimeService取变量值
         * exxcutionId： 执行对象
         *
         */
//		runtimeService.getVariable(exxcutionId, variableName);//取变量
//		runtimeService.getVariableLocal(exxcutionId, variableName);//取本执行对象的某个变量
//		runtimeService.getVariables(variablesName);//取当前执行对象的所有变量
        /** 6. 通过TaskService取变量值
         * TaskId： 执行对象
         *
         */
//		taskService.getVariable(taskId, variableName);//取变量
//		taskService.getVariableLocal(taskId, variableName);//取本执行对象的某个变量
//		taskService.getVariables(taskId);//取当前执行对象的所有变量
    }

}
