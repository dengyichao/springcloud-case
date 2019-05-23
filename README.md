# SpringCloud文档 #


----------

- ## 1、新建Springboot 2.0.6项目 ##


     1. 打开IDEA 2017 ->File->NEW->Module
     2. 选择SpringInitializr，按提示创建好SpringBoot项目，注意，版本为2.0.6  
        ![A-1.jpg](https://github.com/dengyichao/MyPostImage/blob/master/springcloud-case/A-1.jpg?raw=true)  
	    ![A-2.jpg](https://github.com/dengyichao/MyPostImage/blob/master/springcloud-case/A-2.jpg?raw=true)  
        ![A-3.jpg](https://github.com/dengyichao/MyPostImage/blob/master/springcloud-case/A-3.jpg?raw=true) 
     3. 为什么要选择2.0.6版本(18年年末写的，当时这个算是最新版本了)  
        > 官方解释，目前，最新的2.1.0  还未跟SpringCloud完全兼容，测试时采用2.1.0版本会显示报错
     4. 项目结构简介  
        ![A-4.jpg](https://github.com/dengyichao/MyPostImage/blob/master/springcloud-case/A-4.jpg?raw=true)  
        如果是Myeclipse的话，请输入https://start.spring.io/  下载好初始项目后，用MyEclipse  import导入到该工具中即可，项目结构就是标准的Maven结构在这里不做展示

----------
        
- ## 2、创建SpringCloud服务注册中心（只需要有一个即可）  ##
    
     >以前创建的demo项目为基础，改造成新的应用das-server  
     >>1.项目配置
     >>>  ①更改pom配置
     >>>> - 添加spring-cloud.version变量，用于指定SpirngClound的版本  
     >>>> - 在dependencyManagement （依赖管理）中，我们导入了Spring Cloud 的依赖的管理。  
     >>>> - 依赖中添加spring-cloud-starter-netflix-eureka-server  
     
     >>>  ②重新编写application.properties
     >>>> - 把application.properties更改为application.yml文件  
  	 >>>  ![D-1.jpg](https://github.com/dengyichao/MyPostImage/blob/master/springcloud-case/D-1.jpg?raw=true)  
  	 
	 >>2.启动类加上@EnableEurekaServer，表示Eureka服务端  
	 >>![A-6.jpg](https://github.com/dengyichao/MyPostImage/blob/master/springcloud-case/A-6.jpg?raw=true)  
	 >>该注解就是为了激活 Eureka Server 相关的自动配置类org.springframework.cloud.netftix.eureka.server.EurekaServerAutoConfiguration
     >>3.测试服务是否启动，输入：http://localhost:1111/,如下画面证明服务端成功  
     >>![A-7.jpg](https://github.com/dengyichao/MyPostImage/blob/master/springcloud-case/A-7.jpg?raw=true) 
     
----------

- ## 3、创建SpringCloud客户端 ##
	>以前创建的demo项目为基础，改造成新的应用dyc-search
	>>1.项目配置
     >>>  ①更改pom配置
     >>>> - 添加spring-cloud.version变量，用于指定SpirngClound的版本
     >>>> - 在dependencyManagement （依赖管理）中，我们导人了Spring Cloud 的依赖的管理。 
     >>>> - 依赖中添加spring-cloud-starter-netflix-eureka-client  
  	 
	 >>2.一个最简单的Eureka Client  (在启动类中添加注解 ＠EnableDiscoveryClient)  
	 >>![A-8.jpg](https://github.com/dengyichao/MyPostImage/blob/master/springcloud-case/A-8.jpg?raw=true)  
	 >>org.springframework.cloud.client.discovery.EnableDiscoveryClient 注解，就是一个自动发现客户端的实现。
     >>3.修改项目配置  
     >>>server.port : 指定了项目端口号  
	 >>>spring.application.name : 指定了应用的名称  
	 >>>Eureka.client.service-url.defaultZone : 指明了Eureka Server位置  
	 >>>![A-9.jpg](https://github.com/dengyichao/MyPostImage/blob/master/springcloud-case/A-9.jpg?raw=true)  
	 >>4.运行与测试，启动实例，会出现如下状态，成功  
	 >>>![A-10.jpg](https://github.com/dengyichao/MyPostImage/blob/master/springcloud-case/A-10.jpg?raw=true) 

----------
- ## 4、SpringCloud  使用Feign实现服务消费者，微服务通信 ##  
	&emsp;简单说下为什么使用Feign，首先我们都知道服务消息之间的连接依赖的是HttpClient，在连接过程中依赖Ribbon去实现负载均衡，实现消息连接后，如果该微服务正处于故障或延迟的时候，若此时还不停的发送请求，最后就会因等待出现故障的依赖方响应形成任务积压，最终导致自身服务瘫痪，针对这个问题，Spring Clound Hystrix实现了断路器、线程隔离等一系列的服务保护功能，综上所述一个完整的微服务通信需要具备这三个强大的功能，而Feign整合了Spring Clound Ribbon、Spring Clound Hystrix和HttpClinet所有优点，并且除了可以提供这三个强大的功能之外，它还提供了一种声明式的web服务客户端定义方法，简介明了，所以在这里我们直接使用Feign进行开发  
    &emsp;现在我们先准备两个项目，新建SpringCloud项目dyc-calculation和改造之前的dyc-searchr项目，其中dyc-searchr需要获取dyc-calculation里的数据并把自身的数据展现出来，定义方法：searchAndCalculation，该方法分表调用了，数据搜索本身的dataList()和计算数据dyc-calculation中的dataList()方法。在这里首先把流程图画出来，方面理解下面的代码  
    ![A-11.jpg](https://github.com/dengyichao/MyPostImage/blob/master/springcloud-case/A-11.jpg?raw=true) 
	
	1.dyc-calculation    数据计算
	>以前创建的dyc-search项目为基础，改造成新的应用dyc-calculation
	>>1.项目配置
     >>> - Pom文件不变
     >>> - Application.yml文件的spring.application.name 改成 dyc-calculation,端口号改为8085，具体配置如下：  
     >>> ![A-12.jpg](https://github.com/dengyichao/MyPostImage/blob/master/springcloud-case/A-12.jpg?raw=true)   
     
    >>2.创建一个标准的cotroller层，代码如下：  
    >> ![D-2.jpg](https://github.com/dengyichao/MyPostImage/blob/master/springcloud-case/D-2.jpg?raw=true)   
	>>3.(3)启动dyc-calculation,发现服务已经启动，并进行web测试  
	>> ![A-13.jpg](https://github.com/dengyichao/MyPostImage/blob/master/springcloud-case/A-13.jpg?raw=true)   
	>> ![A-14.jpg](https://github.com/dengyichao/MyPostImage/blob/master/springcloud-case/A-14.jpg?raw=true)  

	2.dyc-search         查询微服务
	>项目加入Feign架构
	>>1.项目配置
     >>> 使用Fegin添加如下依赖  
     >>> ![D-3.jpg](https://github.com/dengyichao/MyPostImage/blob/master/springcloud-case/D-3.jpg?raw=true)  
      
    >>2.启用Feign:要启用Fegin，最简单的方式就是在应用的跟目录的Application类上添加org.springframework.cloud.openfeign.EnableFeignClients注解。  
    >>3.使用Feign：要使用Feign，首先要编写Feign请求接口  
    >>> ```@FeignClient("dyc-calculation")
    >>> public interface DycCalculationClient {
    >>>    @GetMapping("test/dataList")
    >>>     public List<String> dataList();
    >>> }```
    >>其中，@FeignClient指定了要访问的服务的名称dyc-calculation，注意这里是服务名而不是项目名。
    >>在声明了dyc-calculation接口后	，我们就能在Controller中使用该接口的实现Ctrl、Service层代码实现  
    
```
	public interface TestService {
	     public List<String> dataList();
	     public List<String> searchAndCalculation();
	}
```
      
```

	@Service
    public class TestServiceImpl implements TestService {

    @Autowired
    private DycCalculationClient dycCalculationClient;

    @Override
    public List<String> dataList() {
        List<String> list = Arrays.asList("我是数据查询微服务  001","我是数据查询微服务  002","我是数据查询微服务  003");
        return list;
    }

    @Override
    public List<String> searchAndCalculation() {
        List<String> dataList = new ArrayList<>();
        List<String> list = this.dataList();
        List<String> warMethodModelList = dycCalculationClient.dataList();
        dataList.addAll(list);
        dataList.addAll(warMethodModelList);
        /*for (String str : warMethodModelList){
            dataList.add(str);
        }
        for (String str : list){
            dataList.add(str);
        }*/
        return dataList;
    }
    }

```

dyc-search项目结构图如下  
![A-15.jpg](https://github.com/dengyichao/MyPostImage/blob/master/springcloud-case/A-15.jpg?raw=true)  
     
(4)测试结果如下  
![A-16.jpg](https://github.com/dengyichao/MyPostImage/blob/master/springcloud-case/A-16.jpg?raw=true)  

----------
- ## 5、创建dyc-web项目，使用thymeleaf模板引擎  ## 
以前创建的das-demo项目为基础，改造成新的应用dyc-web，下面是基础的思路图  
![A-17.jpg](https://github.com/dengyichao/MyPostImage/blob/master/springcloud-case/A-17.jpg?raw=true)  
1.项目配置  
	(1)更改Pom文件  
	   - 添加spring-cloud.version变量，用于指定SpirngClound的版本  
	   - 在dependencyManagement （依赖管理）中，我们导人了Spring Cloud 的依赖的管理。
	   - 依赖中添加spring-cloud-starter-netflix-eureka-client
	   - 依赖中添加spring-cloud-starter-openfeign
	   - 依赖中添加spring-boot-starter-thymeleaf
	(2)application.yml  如图
				```
				
					server:
					  port: 8091
					
					spring:
					  application:
					    name: dyc-web
					  devtools:
					      restart:
					        enabled: false
					eureka:
					  client:
					    service-url:
					      defaultZone: http://localhost:1111/eureka/
					
					ribbon:
					  ReadTimeout: 6000  # 请求处理的超时时间
					  ConnectTimeout: 6000  # 请求连接的超时时间
					  eureka:
					    enabled: true
					
					feign:
					  client:
					    config:
					      feignName:
					        connectTimeout: 5000    # 连接超时时间
					        readTimeout: 5000       # 读数据超时时间
				```

 2.编写Feign接口  

```

	package ah.dyc.client;
	
	import org.springframework.cloud.openfeign.FeignClient;
	import org.springframework.web.bind.annotation.GetMapping;
	
	import java.util.List;
	
	/**
	 * @author dengyichao
	 * @Title: DycSearchClient
	 * @Package:  ah.dyc.client
	 * @Description:
	 * @date 2019/5/22  16:09
	 */
	@FeignClient("dyc-search")
	public interface DycSearchClient {
	    @GetMapping("test/dataList")
	    public List<String> dataList();
	    @GetMapping("test/searchAndCalculation")
	    List<String> searchAndCalculation();
	}
```

3.编写ctrl层  

```
	
	package ah.dyc.controller;
	
	import ah.dyc.client.DycSearchClient;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
	import org.springframework.web.bind.annotation.RequestMapping;
	
	import java.util.List;
	
	/**
	 * @author dengyichao
	 * @Title: DycSearchCtrl
	 * @Package: ah.dyc.controller
	 * @Description:
	 * @date 2019/5/22  16:12
	 */
	@Controller
	@RequestMapping("dyc-search")
	public class DycSearchCtrl {
	
	    @Autowired
	    private DycSearchClient dycSearchClient;
	
	    @RequestMapping("/index")
	    public String index(Model model){
	        System.out.println("我进来啦~");
	        List<String> dataList = dycSearchClient.dataList();
	        model.addAttribute("dataList",dataList);
	        System.out.println("investigation-and-analysis index  jinlai le");
	        return "das-monitor/index";
	    }
	
	}

```

4.编写前端html

```

	<!DOCTYPE html>
	<html lang="en">
	<head>
	    <meta charset="UTF-8">
	    <title>我是dyc-web</title>
	</head>
	<body>
	<p>我是dyc-web项目</p>
	<div  th:each="data:${dataList}">
	    <div >
	        <p  th:text="${data}">日期</p>
	    </div>
	</div>
	</body>
	</html>


```

5.完整的dyc-web项目结构，如下：  
![A-18.jpg](https://github.com/dengyichao/MyPostImage/blob/master/springcloud-case/A-18.jpg?raw=true)  

6.测试  
![A-19.jpg](https://github.com/dengyichao/MyPostImage/blob/master/springcloud-case/A-19.jpg?raw=true)  

----------
## 6、添加spring-cloud-zuul (网关) ##

使用目的

   >用一套单一且统一的API入口点，来组合一个或多个内部API，可方便实现对平台众服务接口进行控，如对访问服务的身份认证、防数据篡改、功能调用的业务鉴权，以及应数据的脱敏、流量与并发控制，最重要的是，对我们das6微服务平台实现了良好的多项目交互以及页面整合后url的管理  
	
简介  
   > Zuul是Netfilx公司开源的一个 API 关组件，提供了认证、鉴权、限流、动态路由、监控、弹性、安全、负载均衡、协助单点压测、静态响应等边缘服务的框架  
   
Zuul的基本功能如下：  

>- 验证与安全保障：识别面向各类资源的验证要求并拒绝那些与要求不符的请求
- 审查与监控：在边缘位置追踪有意义的数据及统计结果，从而为用户带来准确的生产状态结论。
- 动态路由：以动态方式根据需要将请求路由至不同后端集群处
- 压力测试：逐渐增加指向集群的负载流量，从而计算性能水平
- 负载分配：为每一种负载类型分配对应容量，并弃用超出限定值的请求
- 静态响应处理 ：在边缘位置直接建立部分响应，从而避免其流入内部集群  

Zuul 处理每个请求的方式是针对每个请求使用一个线程来处理 通常情况下，为了提高性能，所有请求会被放到处理队列中，从线程池中选取空闲线程来处理该请求

### Das6网关的路由规则 ###

![A-20.jpg](https://github.com/dengyichao/MyPostImage/blob/master/springcloud-case/A-20.jpg?raw=true)  

### 代码配置  ###
以前创建的das-web项目为基础，改造成新的应用das-web  
(1) 项目配置  

- Pom文件，添加如下依赖  

```

	<dependency>
	    <groupId>org.springframework.cloud</groupId>
	    <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
	</dependency>

```

- application.yml，添加如下参数


```

	zuul:
	  routes:
	      dyc-search:
	        path: /dyc-search/**      # 为要拦截请求的路径；
	        serviceId: dyc-search  #为要拦截请求的路径所要映射的服务 本例将所有 /das-analysis 下的请求，都转 发到 das-analysis 应用中去
	      dyc-web:
	        path: /dyc-web/**
	        serviceId: dyc-web
	      dyc-calculation:
	        path: /dyc-calculation/**
	        serviceId: dyc-calculation	

```

(2) 在Main方法上添加@EnableZuulProxy注解  

```

	@SpringBootApplication
	@EnableDiscoveryClient
	@EnableFeignClients
	@EnableZuulProxy
	public class DycWebApplication {
	
	   public static void main(String[] args) {
	      SpringApplication.run(DycWebApplication.class, args);
	   }
	
	}

```

### 测试 ###
对各种情况下的调用均已测试完毕，除页面不能夸项目调取外，剩下均可，以下案例为dyc-web（端口号8091）在url直接调用dyc-search的接口，调用成功  
![A-21.jpg](https://github.com/dengyichao/MyPostImage/blob/master/springcloud-case/A-21.jpg?raw=true)  


----------
## 7、Das6前后端分离（SpringBoot版,并不是node.js的  公司需要） ##

### 目的 ###
>主要是为了单个微服务页面及静态开发可以集成进总的一个web项目中，方面开发人员对所属模块的开发与调试

### 简单说一下实现方式 ###
在dyc-search基础上进行改造，添加zuul组件，把多余的包和代码删除首先我们知道请求后台的url是根据Ctrl层的Request去设置的，如
![A-22.jpg](https://github.com/dengyichao/MyPostImage/blob/master/springcloud-case/A-22.jpg?raw=true)  
当我们为dyc-zuul项目添加zuul组件时，为项目进行了api管理，如上面“/test/dataList”的url请求就可以修改为“/dyc-zuul/test/dataList”，执行流程为下图  

```

	zuul:
	  host:
	    connect-timeout-millis: 50000
	    socket-timeout-millis: 50000
	  routes:
	    das-platform:
	      path: /dyc-zuul/**
	      serviceId: dyc-zuul


```  


![A-23.jpg](https://github.com/dengyichao/MyPostImage/blob/master/springcloud-case/A-23.jpg?raw=true)  

----------
## 8、关于配置  ## 
由于搭建项目所用到的配置都已经做了详细的解释，这里在列举一些常用的属性，并且由于Eureka已经闭源，在这里提供一些github上面的具体的网站可以观看详细的资料  
1. Eureka的配置： https://github.com/netflix/eureka/wiki/Configuring-Eureka    
2. Ribbon的配置： https://github.com/netflix/ribbon/wiki/Programmers-Guide    
3. Hystrix的配置： https://github.com/netflix/Hystrix/wiki/Configuration    
4. Turbine的配置： https://github.com/netflix/Turbine/wiki/Configuration-(1.x)  

### 注意事项  ###
1.服务中心有一个自我保护机制，这个机制是会统计心跳失败的比例在15分钟之内是否低于85%,如果低于的情况下，EurekaServer会将当前的实例注册信息保护起来，让这些实例不会过期，在单机调试的时候很容易有这种关闭了服务提供者但是注册中心还有的情况，所以请关闭自我保护机制eureka.server.enable-self-preservation=false，并添加清理间隔时间为：eureka.server.eviction-interval-timer-in-ms=9000（默认为60*1000太长了，不利于单机测试或者开发），而在客户端上需要设置eureka.instance.lease-renewal-interval-in-seconds=30     # 续约更新时间间隔（默认30秒）
eureka.instance.lease-expiration-duration-in-seconds=90	 # 续约到期时间（默认90秒）
eureka.client.healthcheck.enabled=true，其实以上两个配置已经可以把心跳停止的服务关掉了，但是如果你的客户端中有比如数据库，mq无法与我们的应用联通时，实际上是不能提供正常服务了，但是心跳机制还在，项目不会剔除，所以在这个时候需要加入健康机制，需要在pom文件里引入：
```

	<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-actuator</artifactId>
	</dependency>



```  

2.获取服务是服务消费者的基础，所以必须确保eureka.client.fetch-registery=true,该值默认为true

### 服务注册类配置  ###
org.springframework.cloud.netflix.eureka.EurekaClientConfigBean  查看该类可以获得比官网更详细的配置信息，这些配置都是以eureka.client为前缀
简单配置如下： 
参数名	说明  	默认值
registryFetchlntervaISeconds	从Eureka服务端获取注册信息的间隔时间单位为秒	30  
instanceInfoReplicationlntervaISeconds	更新实例信息的变化到Eureka服务端的间隔时间，单位为秒	30  
initialInstancelnfoReplicationIntervaISeconds	初始化实例信息到Eureka服务端的间隔时间，单位为秒	40     
eurekaServiceUrIPolllntervaISeconds	轮询Eureka服务端地址更改的间隔时间，单位为秒。当我们与Spring Cloud Config配合，动态刷新Eureka的serviceURL地址时需要关注该参数   	300   

eurekaServerReadTimeoutSeconds	读取Eureka Server信息的超时时间，单位为秒	8  
eurekaServerC onnectTimeoutSeconds	连接Eureka Server的超时时间，单位为秒	5  
eurekaServerTotaIConnections	从Eureka窖户端到所有Eureka服务端的连接总数	200  
eurekaServerTotaIConnectionsPerHost	从Eureka客户端到每个Eureka服务端主机的连接总数	50  
eurekaConnectionIdleTimeoutSeconds	Eureka服务端连接的空闲关闭时间，单位为秒	30  
heartbeatExecutorThreadPoolSize	心跳连接池的初始化线程数	2  
heartbeatExecutorExponentiaIBackOffBound	心跳超时重试延迟时间的最大乘数值	10  
cacheRefreshExecutorThreadPooISize	缓存刷新线程池的初始化线程数	2  
cacheRefreshExecutorExponentialBackOffBound	缓存刷新重试延迟时间的最大乘数值	10  
useDnsForFetchingServiceUrls	使用DNS来获取Eureka服务端的serviceUrl	false  
registerWithEureka	是否要将自身的实例信息注册到Eureka服务端	true  
preferSameZoneEureka	是否偏好使用处于相向Zone的Eureka服务端	true  
filterOnlyUplnstances	获取实例时是否过滤，仅保留UP状态的实例	true  
fetchRegistry	是否从Eureka服务端获取注册信息	true  
enabled	启用Eureka客户端	true  


### 服务实例类配置 ###
关于服务实例类的配置信息，我们可以通过查看org. springframework．cloud.netflix. eureka .EurekaInstanceConfigBean的源码来获取详细内容，这些配置信息都以eureka.instance为前缀，下面我们针对一些常用的配置信息做一些详细的说  
简单配置如下： 
参数名	说明 	默认值  
preferIpAddress	是否优先使用lP地址作为主机名的标识	false  
leaseRenewallntervallnSeconds	Eureka客户端向服务端发送心跳的时间间隔，单位为秒	30  
leaseExpirationDurationlnSeconds	Eureka服务端在收到最后一次心跳之后等待的时间上限单位为秒。超过该时间之后服务端会将该服务实例从服务清单中剔除，从而禁止服务调用请求被发送到该实例上	90  
nonSecurePort	非安全的通信端口号	80  
securePort	安全的通信端口号	443  
nonSecurePortEnabled	是否启用非安全的通信端口号	true  
securePortEnabled                 	是否启用安全的通信端口号	  
appname	服务名，默认取spring.application.name的配置值如果没有则为unknown	  
hostname	主机名，不配置的时候将根据操作系统的主机名来获取	  

在上面的这些配置中，除了前三个配置参数在需要的时候可以做一些调整，其他的参
数配置大多数情况下不需要进行配置，使用默认值即呵。
		
----------

## 9、项目所涉及到的技术及相关版本如下 ##
采用的技术及相关版本
* JDK 8
* IntelliJ IDEA 2017.1 x64
* Apache Maven 3.5.0
* Apache HttpClient 4.5.6
* Apache commons-pool2 2.5.0
* Thymeleaf 3.0.1.RELEASE
* Bootstrap 4.0.0-beta.2
* Spring Boot 2.0.6.RELEASE
* Spring Boot Starter Web 2.0.6.RELEASE
* Spring Boot Thymeleaf Starter 2.0.6.RELEASE
* Spring Boot Test Starter 2.0.6.RELEASE
* Spring Cloud Starter Netflix Eureka Server Finchley.SR2
* Spring Cloud Starter Netflix Eureka Client Finchley.SR2
* Spring Cloud Starter Netflix Ribbon Finchley.SR2
* Spring Cloud Starter OpenFeign Finchley.SR2