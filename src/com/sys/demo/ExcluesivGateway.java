package com.sys.demo;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 排它网关
 * @author y_zzu 2020-01-15-20:44
 */
public class ExcluesivGateway {

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
                .addClasspathResource("diagrams/exclusivGateway.bpmn")
                //设置部署的名称
                .name("排它网关")
                //设置部署的类别
                .category("测试默认路径")
                .deploy();

        System.out.println("部署的id ： "+deployment.getId());
        System.out.println("部署的名称： "+deployment.getName());
    }

    /**
     * 执行流程
     */
    @Test
    public void startProcess() {
        //启动流程，使用的是 act_re_procdef 中的 key
        String processDefikey = "exclusiveGateway";
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("config.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        //取运行时服务
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //取得流程实例,通过流程定义的key(在 .bpmn 里面表示的是 id)
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefikey);
        System.out.println("流程实例 id: "+ processInstance.getId());
        System.out.println("流程定义 id："+ processInstance.getProcessDefinitionId());
    }

    /**
     * 查询任务
     */
    @Test
    public void queryTask() {
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("config.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        //任务的办理人
//        String assignee = "前台";
//        String assignee = "后台";
//        String assignee = "会员";
//        String assignee = "用户";
        String assignee = "窗口";
        //取得任务服务
        TaskService taskService = processEngine.getTaskService();
        //创建一个任务查询对象
        TaskQuery taskQuery = taskService.createTaskQuery();
        //遍历人的任务列表
        List<Task> list = taskQuery.taskAssignee(assignee).list();
        //遍历任务列表
        if (!list.isEmpty() && list.size() > 0) {
            for (Task task : list) {
                System.out.println("任务办理人： " + task.getAssignee());
                System.out.println("任务id : " + task.getId());
                System.out.println("任务的名称: " + task.getName());
            }
        } else {
            System.out.println("数据为空！！！！！！！！！！！！！！！");

        }

    }

    /**
     * 带有条件的执行任务
     */
    @Test
    public void compileTask() {
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("config.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        //任务id，正在运行的任务列表的 id
        String taskId = "3204";
        //设置额外的条件,使用的是map 集合进行存储
        Map<String, Object> params = new HashMap<String, Object>();
        /**
         * visitor  1 表示普通用户
         *          2 表示的是vip
         *          3 表示的是后台用户
         */
        params.put("visitor", 22);
        //执行任务
        processEngine.getTaskService().complete(taskId,params);
        System.out.println("当前任务执行完毕！！！！！");
    }

    @Test
    public void complete() {
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("config.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        //任务id，正在运行的任务列表的 id
        String taskId = "3204";
        //执行任务
        processEngine.getTaskService().complete(taskId);
        System.out.println("当前任务执行完毕！！！！！");
    }


}
