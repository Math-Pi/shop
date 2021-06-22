# IT网上商城

[前台首页](http://localhost:8080/shop/login/uIndex)

[后台登录页面](http://localhost:8080/shop/login/login)	用户名/密码：admin/admin

## 配置文件

### web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee"
         xmlns:web="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
         version="2.5">
    <display-name>shop</display-name>
    <!--欢迎页面-->
    <welcome-file-list>
        <welcome-file>index.jsp</welcome-file>
    </welcome-file-list>
    <!--指定Spring配置文件位置-->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:spring/applicationContext-*.xml</param-value>
    </context-param>
    <!--指定以ContextLoaderListener方式启动Spring容器-->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    <!--编码过滤器-->
    <filter>
        <filter-name>characterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
        <init-param>
            <param-name>forceEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>characterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    <!--前端控制器-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:spring/springmvc-config.xml</param-value>
        </init-param>
    </servlet>
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

    <!--自定义过滤器-->
    <filter>
        <filter-name>SystemContextFilter</filter-name>
        <filter-class>com.cjm.filter.SystemContextFilter</filter-class>
        <!-- 初始化参数的设置 -->
        <init-param>
            <param-name>pageSize</param-name>
            <param-value>15</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>SystemContextFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    <!--错误页面-->
    <error-page>
        <error-code>404</error-code>
        <location>/error/404.jsp</location>
    </error-page>
    <error-page>
        <error-code>500</error-code>
        <location>/error/500.jsp</location>
    </error-page>
</web-app>
```

获取web.xml配置文件参数的值

```java
//从配置文件的servlet配置中，取得servlet初始化参数page-size（页面大小）
int pageSize=Integer.parseInt(this.getServletConfig().getInitParameter("pageSize"));  
//从application范围内取得page-size,application指的是ServletContext对象
this.getServletContext().getInitParameter("contextConfigLocation");
```

### applicationContext-dao.xml

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans-4.1.xsd
		http://www.springframework.org/schema/context 
		http://www.springframework.org/schema/context/spring-context-4.1.xsd">

	<!-- 读取db.properties -->
	<context:property-placeholder location="classpath:db.properties" />

	<!-- 配置数据源 -->
	<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
		<!--数据库驱动-->
		<property name="driverClassName" value="${jdbc.driver}" />
		<!--数据库连接的url-->
		<property name="url" value="${jdbc.url}" />
		<!--用户名-->
		<property name="username" value="${jdbc.username}" />
		<!--密码-->
		<property name="password" value="${jdbc.password}" />
		<!--支持的最大连接数-->
		<property name="maxActive" value="${jdbc.maxActive}" />
		<!--最大空闲连接-->
		<property name="maxIdle" value="${jdbc.maxIdle}" />
		<!--初始化连接数目 -->
		<property name="initialSize" value="${jdbc.initialSize}" />
	</bean>

	<!-- 配置MyBatis工厂sqlSessionFactory -->
	<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
		<!-- 注入数据源 -->
		<property name="dataSource" ref="dataSource" />
		<!-- 加载mybatis的全局配置文件 -->
		<property name="configLocation" value="classpath:mybatis/mybatis-config.xml" />
	</bean>

	<!-- Mapper代理开发(基于MapperScannerConfigurer) -->
	<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
		<!-- 扫描包路径，如果需要扫描多个包，中间使用半角逗号隔开 -->
		<property name="basePackage" value="com.cjm.mapper" />
		<property name="sqlSessionFactoryBeanName" value="sqlSessionFactory" />
	</bean>

</beans>
```

### applicationContext-service.xml

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:mvc="http://www.springframework.org/schema/mvc"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:aop="http://www.springframework.org/schema/aop" xmlns:tx="http://www.springframework.org/schema/tx"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
		http://www.springframework.org/schema/beans/spring-beans-4.1.xsd 
		http://www.springframework.org/schema/mvc 
		http://www.springframework.org/schema/mvc/spring-mvc-4.1.xsd 
		http://www.springframework.org/schema/context 
		http://www.springframework.org/schema/context/spring-context-4.1.xsd 
		http://www.springframework.org/schema/aop 
		http://www.springframework.org/schema/aop/spring-aop-4.1.xsd 
		http://www.springframework.org/schema/tx 
		http://www.springframework.org/schema/tx/spring-tx-4.1.xsd " default-autowire="byName">
    <!-- 商品管理的service -->
    <context:component-scan base-package="com.cjm"></context:component-scan>
    <context:annotation-config></context:annotation-config>
</beans>
```

### applicationContext-transaction.xml

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans-4.1.xsd
		http://www.springframework.org/schema/tx 
		http://www.springframework.org/schema/tx/spring-tx-4.1.xsd ">

    <!-- 事务管理器 -->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <!-- 注入数据源 -->
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!-- 通知 -->
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <tx:attributes>
            <!-- 传播行为 -->
            <tx:method name="save*" propagation="REQUIRED"/>
            <tx:method name="delete*" propagation="REQUIRED"/>
            <tx:method name="insert*" propagation="REQUIRED"/>
            <tx:method name="update*" propagation="REQUIRED"/>
            <tx:method name="find*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="get*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="select*" propagation="SUPPORTS" read-only="true"/>
        </tx:attributes>
    </tx:advice>

</beans>
```

### springmvc-config.xml

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans-4.1.xsd 
		http://www.springframework.org/schema/mvc 
		http://www.springframework.org/schema/mvc/spring-mvc-4.1.xsd 
		http://www.springframework.org/schema/context 
		http://www.springframework.org/schema/context/spring-context-4.1.xsd">

    <!-- 自动扫描包-->
    <context:component-scan base-package="com.cjm.controller" />

    <!-- 使用注解 -->
    <mvc:annotation-driven>
        <mvc:message-converters>
            <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                <property name="supportedMediaTypes">
                    <list>
                        <value>application/json;charset=UTF-8</value>
                    </list>
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>
    <mvc:resources mapping="/resource/**" location="/resource/"/>
    <!-- 配置拦截器 -->
    <mvc:interceptors>
        <mvc:interceptor>
            <mvc:mapping path="/itemCategory/**" />
            <mvc:mapping path="/user/**" />
            <mvc:mapping path="/item/**" />
            <mvc:mapping path="/itemOrder/findBySql" />
            <mvc:mapping path="/itemOrder/fh" />
            <mvc:mapping path="/news/**" />
            <mvc:mapping path="/message/**" />
            <mvc:exclude-mapping path="/item/shoplist"/>
            <mvc:exclude-mapping path="/item/view"/>
            <mvc:exclude-mapping path="/user/view"/>
            <mvc:exclude-mapping path="/news/list"/>
            <mvc:exclude-mapping path="/news/view"/>
            <mvc:exclude-mapping path="/message/add"/>
            <mvc:exclude-mapping path="/message/toAdd"/>
            <bean class="com.cjm.interceptor.AdminInterceptor" />
        </mvc:interceptor>
        <!--<mvc:interceptor>
            <mvc:mapping path="/**" />
            <bean class="com.cjm.interceptor.LoginInterceptor" />
        </mvc:interceptor>-->
    </mvc:interceptors>
	<!--文件上传-->
    <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver"/>
    <!-- 视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <!-- 配置jsp路径的前缀 -->
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <!-- 配置jsp路径的后缀 -->
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
```

### db.properties

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/shopMaven?useUnicode=true&characterEncoding=utf-8
jdbc.username=root
jdbc.password=123456
jdbc.maxActive=30
jdbc.maxIdle=10
jdbc.initialSize=5
```

### mybatis-config.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

	<!-- 配置别名 -->
	<settings>  
        <setting name="logImpl" value="LOG4J"/>  
    </settings> 
	<typeAliases>
		<!-- 批量扫描别名 -->
		<package name="com.cjm.po"/>
	</typeAliases>
 <plugins>
	    <!-- com.github.pagehelper为PageHelper类所在包名 -->
	    <plugin interceptor="com.github.pagehelper.PageHelper">
	        <!-- 4.0.0以后版本可以不设置该参数 -->
	        <property name="dialect" value="mysql"/>
	        <!-- 该参数默认为false -->
	        <!-- 设置为true时，会将RowBounds第一个参数offset当成pageNum页码使用 -->
	        <!-- 和startPage中的pageNum效果一样-->
	        <property name="offsetAsPageNum" value="true"/>
	        <!-- 该参数默认为false -->
	        <!-- 设置为true时，使用RowBounds分页会进行count查询 -->
	        <property name="rowBoundsWithCount" value="true"/>
	        <!-- 设置为true时，如果pageSize=0或者RowBounds.limit = 0就会查询出全部的结果 -->
	        <!-- （相当于没有执行分页查询，但是返回结果仍然是Page类型）-->
	        <property name="pageSizeZero" value="true"/>
	        <!-- 3.3.0版本可用 - 分页参数合理化，默认false禁用 -->
	        <!-- 启用合理化时，如果pageNum<1会查询第一页，如果pageNum>pages会查询最后一页 -->
	        <!-- 禁用合理化时，如果pageNum<1或pageNum>pages会返回空数据 -->
	        <property name="reasonable" value="true"/>
	        <!-- 3.5.0版本可用 - 为了支持startPage(Object params)方法 -->
	        <!-- 增加了一个`params`参数来配置参数映射，用于从Map或ServletRequest中取值 -->
	        <!-- 可以配置pageNum,pageSize,count,pageSizeZero,reasonable,orderBy,不配置映射的用默认值 -->
	        <!-- 不理解该含义的前提下，不要随便复制该配置 -->
	        <property name="params" value="pageNum=start;pageSize=limit;"/>
	        <!-- 支持通过Mapper接口参数来传递分页参数 -->
	        <property name="supportMethodsArguments" value="true"/>
	        <!-- always总是返回PageInfo类型,check检查返回类型是否为PageInfo,none返回Page -->
	        <property name="returnPageInfo" value="check"/>
	    </plugin>
	</plugins>
</configuration>
```



## 1.com.cjm

### base：基础类

- **BaseDao**

```java
public interface BaseDao<T>{
	//插入一个实体    
	int insert(T entity) ; 
    //根据实体主键删除一个实体
    void deleteById(Serializable id);  
}
```

- **BaseService**

```java
public interface BaseService<T> {
	int insert(T entity) ; 
    void deleteById(Serializable id);  
```

- **BaseServiceImpl**

```java
public abstract  class BaseServiceImpl<T> implements BaseService<T>{
    private BaseDao<T> baseDao;  
    public abstract BaseDao<T> getBaseDao();  
	public int insert(T entity) {
		return this.getBaseDao().insert(entity);
	}  
	public void deleteById(Serializable id) {
		this.getBaseDao().deleteById(id);
	}
}
```

- **BaseController**

```java
public class BaseController {
    //下面是判断null的操作
	public boolean isEmpty(String str) {
		return (null == str) || (str.trim().length() <= 0);
	}
    public Map<String,Object> getMap(){
		return new HashMap<String,Object>();
	}
}
```

### controller：控制类

- **CarController（用户操作）**
  - /car/exAdd：加入购物车(异步操作)-->成功(/car/findBySql)；失败(/login/uLogin)
  - /car/findBySql：转向我的购物车页面-->成功(car.jsp)；失败(/login/uLogin)
  - /car/delete：删除(异步操作)
- **CommentController（用户操作）**
  - /comment/exAdd：添加评论(异步操作)-->成功(myOrder.jsp)；失败(/login/uLogin)
- **ItemCategoryController（管理员操作）**
  - /itemCategory/findBySql：分页查询一级类别列表-->itemCategory.jsp
  - /itemCategory/add：转向到新增一级类别页面-->add.jsp
  - /itemCategory/toAdd：新增一级类别-->(/itemCategory/findBySql)
  - /itemCategory/update：转向到修改一级类别页面-->update.jsp
  - /itemCategory/toUpdate：修改一级类别-->(/itemCategory/findBySql)
  - /itemCategory/delete：删除类别-->(/itemCategory/findBySql)
  - /itemCategory/findBySql2：查看二级类别-->itemCategory2.jsp
  - /itemCategory/add2：转向到新增二级类别页面-->add2.jsp
  - /itemCategory/exAdd2 ：新增二级类别-->(/itemCategory/findBySql2)
  - /itemCategory/update2：转向到修改二级类别页面-->update2.jsp
  - /itemCategory/exUpdate2：修改二级类别-->(/itemCategory/findBySql2)
  - /itemCategory/delete2：删除二级类别-->(/itemCategory/findBySql2)
  - /itemCategory/graph：商品销售统计-->(graph.jsp)
- **ItemController**
  - /item/findBySql：分页查询商品列表-->item.jsp
  - /item/add：跳转到增加商品入页面-->add.jsp
  - /item/toAdd：增加商品-->(/item/findBySql)
  - /item/update：跳转到修改商品入页面-->update.jsp
  - /item/toUpdate：修改商品-->(/item/findBySql)
  - /item/delete：商品下架-->(/item/findBySql)
  - /item/shoplist：二级分类查询商品列表-->shoplist.jsp
  - /item/view：商品详情-->view.jsp
- **ItemOrderController**
  - /itemOrder/findBySql：订单管理列表(管理员操作)-->itemOrder.jsp
  - /itemOrder/myOrder：我的订单-->成功(myOrder.jsp)；失败(/login/uLogin)
  - /itemOrder/toAdd：购物车结算提交(/car.jsp异步操作)-->成功(页面刷新)；失败(/login/uLogin)
  - /itemOrder/qx：取消订单-->myOrder.jsp
  - /itemOrder/fh：后台发货(管理员操作)-->(/itemOrder/findBySql)
  - /itemOrder/sh：用户收货-->(/itemOrder/myOrder)
  - /itemOrder/evaluate：用户评价入口-->evaluate.jsp
- **LoginController**
  - /login/login：管理员登录-->mLogin.jsp
  - /login/toLogin：登录验证-->成功(mIndex.jsp)；失败(/login/mtuichu)
  - /login/mtuichu：管理员退出-->mLogin.jsp
  - /login/uIndex：前端首页-->uIndex.jsp
  - /login/res：用户注册-->res.jsp
  - /login/toRes：注册验证-->成功(uLogin.jsp)；失败(/login/res)
  - /login/uLogin：用户登录-->uLogin.jsp
  - /login/utoLogin：登陆验证-->成功(uIndex.jsp)；失败(/login/uLogin)
  - /login/uTui：用户退出-->uIndex.jsp
  - /login/pass：修改密码-->成功(pass.jsp)；失败(/login/uLogin)
  - /login/upass：修改密码验证(异步操作)
- **MessageController**
  - /message/add：跳转到发表留言页面-->add.jsp
  - /message/toAdd：提交留言(异步操作)-->add.jsp
  - /message/findBySql：查询留言列表-->成功(message.jsp)；失败(mLogin.jsp)
  - /message/delete：删除留言-->成功(/message/findBySql)；失败(mLogin.jsp)
- **NewsController**
  - /news/findBySql：后台公告列表-->news.jsp
  - /news/add：跳转到添加公告页面-->add.jsp
  - /news/toAdd：添加公告-->(/news/findBySql)
  - /news/update：跳转到修改公告页面-->(/news/findBySql)
  - /news/toUpdate：修改公告-->update.jsp
  - /news/delete：删除公告-->(news/findBySql)
  - /news/list：前端公告列表-->list.jsp
  - /news/view：公告详情页面-->view.jsp
- **OrderDetailController**
  - /orderDetail/ulist：订单详情(管理员操作)
- **ScController**
  - /sc/toAdd：收藏-->成功(/sc/findBySql)；失败(uLogin.jsp)
  - /sc/findBySql：收藏列表-->成功(collect.jsp)；失败(uLogin.jsp)
  - /sc/delete：取消收藏-->成功(/sc/findBySql)；失败(uLogin.jsp)
- **UeditorController**
  - /ueditor/saveFile：上传文件处理
- **UserController**
  - /user/findBySql：查看用户-->成功(user.jsp)；失败(mLogin.jsp)
  - /user/view：查看用户个人中心信息-->成功(view.jsp)；失败(/login/uLogin.jsp)
  - /user/exUpdate：修改用户信息验证-->成功(view.jsp)；失败(/login/uLogin.jsp)

### filter：过滤器

- **SystemContextFilter**

```java
public class SystemContextFilter implements Filter {
    private Integer pageSize;
    public void destroy() { }

    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {
        Integer offset = 0;
        try {
            offset = Integer.parseInt(req.getParameter("pager.offset"));
        } catch (NumberFormatException e) {
        }
        try {
            SystemContext.setOrder(req.getParameter("order"));
            SystemContext.setSort(req.getParameter("sort"));
            SystemContext.setPageOffset(offset);
            SystemContext.setPageSize(pageSize);
            SystemContext.setRealPath(((HttpServletRequest) req).getSession().getServletContext().getRealPath("/"));
            chain.doFilter(req, resp);
        } finally {
            SystemContext.removeOrder();
            SystemContext.removeSort();
            SystemContext.removePageOffset();
            SystemContext.removePageSize();
            SystemContext.removeRealPath();
        }
    }
    public void init(FilterConfig cfg) throws ServletException {
        try {
            pageSize = Integer.parseInt(cfg.getInitParameter("pageSize"));
        } catch (NumberFormatException e) {
            pageSize = 15;
        }
    }
}
```

### mapper：映射接口

- CarMapper

```java
public interface CarMapper extends BaseDao<Car> {
}
```

- CommentMapper

```java
public interface CommentMapper extends BaseDao<Comment> {
}
```

- ItemCategoryMapper

```java
public interface ItemCategoryMapper extends BaseDao<ItemCategory> {
}
```

- ItemMapper

```java
public interface ItemMapper extends BaseDao<Item> {
}
```

- ItemOrderMapper

```java
public interface ItemOrderMapper extends BaseDao<ItemOrder> {
}
```

- ManageMapper

```java
public interface ManageMapper extends BaseDao<Manage> {
}
```

- MessageMapper

```java
public interface MessageMapper extends BaseDao<Message> {
}
```

- NewsMapper

```java
public interface NewsMapper extends BaseDao<News> {
}
```

- OrderDetailMapper

```java
public interface OrderDetailMapper extends BaseDao<OrderDetail> {
}
```

- ScMapper

```java
public interface ScMapper extends BaseDao<Sc> {
}
```

- UserMapper

```java
public interface UserMapper extends BaseDao<User> {
}
```

### po：实体类

- **Car：购物车**

```java
//购物车
public class Car implements Serializable {
    private Integer id;
    //商品id
    private Integer itemId;
    //商品对象
    private Item item;
    //用户id
    private Integer userId;
    //商品数量
    private Integer num;
    //商品单价
    private double price;
    //商品总价
    private String total;
    //省略getter、setter方法
}
```

- **CarDto**

```java
public class CarDto {
    private Integer id;
    private Integer num;
    //省略getter、setter方法
}
```

- **CategoryDto：一级类目和它的二级类目列表**

```java
//一级类目和它的二级类目列表
public class CategoryDto {
    private ItemCategory father;
    private List<ItemCategory> childrens;
	//省略getter、setter方法
}
```

- **Comment：评论**

```java
//评论
public class Comment implements Serializable {
    private Integer id;
    //用户id
    private Integer userId;
    private User user;
    //商品id
    private Integer itemId;
    //内容
    private String content;
    //发布时间
    private Date addTime;
    //省略getter、setter方法
}
```

- **Item：商品**

```java
//商品
public class Item implements Serializable{
    //主键
    private Integer id;
    //名称
    private String name;
    //商品价格
    private String price;
    /折扣
    private String zk;
    //收藏数
    private Integer scNum;
    //购买数
    private Integer gmNum;
    //主图
    private String url1;
    //副图1
    private String url2;
    //副图2
    private String url3;
    //副图3
    private String url4;
    //副图4
    private String url5;
    //描述
    private String ms;
    //类别id一级
    private Integer categoryIdOne;
    private ItemCategory yiji;

    //类别id二级
    private Integer categoryIdTwo;
    private ItemCategory erji;

    //是否有效 0有效 1已删除
    private Integer isDelete;
    //评论列表
    private List<Comment> pls;
    //省略getter、setter方法
}
```

- **ItemCategory：类目**

```java
//类目
public class ItemCategory implements Serializable {
    //主键id
    private Integer id;
    //类目名称
    private String name;
    //父id
    private Integer pid;
    //是否已删除
    private Integer isDelete;
    //省略getter、setter方法
}
```

- **ItemOrder：订单**

```java
//订单
public class ItemOrder implements Serializable {
    //主键
    private Integer id;
    //商品id
    private Integer itemId;
    //购买者id
    private Integer userId;
    private User user;
    //订单号
    private String code;
    //购买时间
    private Date addTime;
    //购买数量
    private String total;
    private Integer isDelete;
    //0.新建待发货1.已取消 2已发货3.到收货4已评价
    private Integer status;
    private List<OrderDetail> details;
    //省略getter、setter方法
}
```

- **Manage：管理员**

```java
//管理员
public class Manage implements Serializable {
    private Integer id;
    //用户名
    private String userName;
    //密码
    private String passWord;
    //姓名
    private String realName;
    //省略getter、setter方法
}
```

- **Message：留言**

```java
public class Message implements Serializable {
    private Integer id;
    //姓名
    private  String name;
    //内容
    private String content;
    //手机号
    private String phone;
    //省略getter、setter方法
}
```

- **News：新闻**

```java
public class News implements Serializable {
    private Integer id;
    //标题
    private  String name;
    //内容
    private String content;
    //发布时间
    private Date addTime;
    //省略getter、setter方法
}
```

- **OrderDetail：商品详情**

```java
//订单详情
public class OrderDetail implements Serializable {
    private Integer id;
    //商品id
    private Integer itemId;
    private Item item;
    //订单id
    private Integer orderId;
    //0.未退货 1已退货
    private Integer status;
    //数量
    private Integer num;
    //小计
    private String total;
    //省略getter、setter方法
}
```

- **Sc：收藏**

```java
//收藏
public class Sc implements Serializable {
    private Integer id;
    //商品id
    private Integer itemId;
    //商品对象
    private Item item;
    //收藏者id
    private Integer userId;
    //省略getter、setter方法
}
```

- **TjDto**

```java
public class TjDto {
    private String name;
    private Integer num;
    //省略getter、setter方法
}
```

- **User：用户**

```java
//用户
public class User implements Serializable {
    //主键id
    private Integer id;
    //用户名
    private String userName;
    //密码
    private String passWord;
    //手机号
    private String phone;
    //真实姓名
    private String realName;
    //性别
    private String sex;
    //地址
    private String address;
    //电子邮箱
    private String email;
    //省略getter、setter方法
}
```

### service：服务接口

- **CarService**

```java
public interface CarService extends BaseService<Car> {
}
```

- **CommentService**

```java
public interface CommentService extends BaseService<Comment> {
}
```

- **ItemCategoryService**

```java
public interface ItemCategoryService extends BaseService<ItemCategory> {
}
```

- **ItemOrderService**

```java
public interface ItemOrderService extends BaseService<ItemOrder> {
}
```

- **ItemService**

```java
public interface ItemService extends BaseService<Item> {
}
```

- **ManageService**

```java
public interface ManageService extends BaseService<Manage> {
}
```

- **MessageService**

```java
public interface MessageService extends BaseService<Message> {
}
```

- **NewsService**

```java
public interface NewsService extends BaseService<News> {
}
```

- **OrderDetailService**

```java
public interface OrderDetailService extends BaseService<OrderDetail> {
}
```

- **ScService**

```java
public interface ScService extends BaseService<Sc> {
}
```

- **UserService**

```java
public interface UserService extends BaseService<User> {
}
```

### serviceimpl：服务接口实现类

- **CarServiceImpl**

```java
@Service
public class CarServiceImpl extends BaseServiceImpl<Car> implements CarService {
    @Autowired
    private CarMapper carMapper;
    @Override
    public BaseDao<Car> getBaseDao() {
        return carMapper;
    }
}
```

- **CommentServiceImpl**

```java
@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public BaseDao<Comment> getBaseDao() {
        return commentMapper;
    }
}
```

- **ItemCategoryServiceImpl**

```java
@Service
public class ItemCategoryServiceImpl extends BaseServiceImpl<ItemCategory> implements ItemCategoryService {
    @Autowired
    ItemCategoryMapper itemCategoryMapper;
    @Override
    public BaseDao<ItemCategory> getBaseDao() {
        return itemCategoryMapper;
    }
}
```

- **ItemOrderServiceImpl**

```java
@Service
public class ItemOrderServiceImpl extends BaseServiceImpl<ItemOrder> implements ItemOrderService {
    @Autowired
    private ItemOrderMapper itemOrderMapper;
    @Override
    public BaseDao<ItemOrder> getBaseDao() {
        return itemOrderMapper;
    }
}
```

- **ItemServiceImpl**

```java
@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {
    @Autowired
    private ItemMapper itemMapper;
    @Override
    public BaseDao<Item> getBaseDao() {
        return itemMapper;
    }
}
```

- **ManageServiceImpl**

```java
@Service
public class ManageServiceImpl extends BaseServiceImpl<Manage> implements ManageService {
    @Autowired
    ManageMapper manageMapper;
    @Override
    public BaseDao<Manage> getBaseDao() {
        return manageMapper;
    }
}
```

- **MessageServiceImpl**

```java
@Service
public class MessageServiceImpl extends BaseServiceImpl<Message> implements MessageService {
    @Autowired
    private MessageMapper messageMapper;
    @Override
    public BaseDao<Message> getBaseDao() {
        return messageMapper;
    }
}
```

- **NewsServiceImpl**

```java
@Service
public class NewsServiceImpl extends BaseServiceImpl<News> implements NewsService {
    @Autowired
    private NewsMapper newsMapper;
    @Override
    public BaseDao<News> getBaseDao() {
        return newsMapper;
    }
}
```

- **OrderDetailServiceImpl**

```java
@Service
public class OrderDetailServiceImpl extends BaseServiceImpl<OrderDetail> implements OrderDetailService {
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Override
    public BaseDao<OrderDetail> getBaseDao() {
        return orderDetailMapper;
    }
}
```

- **ScServiceImpl**

```java
@Service
public class ScServiceImpl extends BaseServiceImpl<Sc> implements ScService {
    @Autowired
    private ScMapper scMapper;
    @Override
    public BaseDao<Sc> getBaseDao() {
        return scMapper;
    }
}
```

- **UserServiceImpl**

```java
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public BaseDao<User> getBaseDao() {
        return userMapper;
    }
}
```

### utils：工具类

- Consts：定义常量

```java
public class Consts {
    /**管理员实体*/
    public static final String MANAGE = "manage";
    /**前端用户登录名*/
    public static final String USERNAME = "username";
    /**前端用户登录id*/
    public static final String USERID = "userId";
    /**json返回码名称*/
    public static final String RES = "res";
}
```

- Pager：分页

```java
public class Pager<T> {
	/**
	 * 分页的大小
	 */
	private int size;
	/**
	 * 分页的起始页
	 */
	private int offset;
	/**
	 * 总记录数
	 */
	private long total;
	/**
	 * 分页的数据
	 */
	private List<T> datas;
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<T> getDatas() {
		return datas;
	}
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
	/**
	 * 构造方法里面求数据
	 */
	public Pager(List<T> datas) {
		if(datas instanceof Page){
			Page<T> page = (Page<T>)datas;
			setOffset(page.getPageNum());
			setSize(page.getPageSize());
			setTotal(page.getTotal());
			setDatas(datas);
		}
		
	}
	public Pager() {
	}
}
```

- SystemContext

```java
public class SystemContext {
	/**
	 * 分页大小
	 */
	private static ThreadLocal<Integer> pageSize = new ThreadLocal<Integer>();
	/**
	 * 分页的起始页
	 */
	private static ThreadLocal<Integer> pageOffset = new ThreadLocal<Integer>();
	/**
	 * 列表的排序字段
	 */
	private static ThreadLocal<String> sort = new ThreadLocal<String>();
	/**
	 * 列表的排序方式
	 */
	private static ThreadLocal<String> order = new ThreadLocal<String>();
	
	private static ThreadLocal<String> realPath = new ThreadLocal<String>();
	
	
	
	public static String getRealPath() {
		return realPath.get();
	}
	public static void setRealPath(String _realPath) {
		SystemContext.realPath.set(_realPath);
	}
	public static Integer getPageSize() {
		return pageSize.get();
	}
	public static void setPageSize(Integer _pageSize) {
		pageSize.set(_pageSize);
	}
	public static Integer getPageOffset() {
		return pageOffset.get();
	}
	public static void setPageOffset(Integer _pageOffset) {
		pageOffset.set(_pageOffset);
	}
	public static String getSort() {
		return sort.get();
	}
	public static void setSort(String _sort) {
		SystemContext.sort.set(_sort);
	}
	public static String getOrder() {
		return order.get();
	}
	public static void setOrder(String _order) {
		SystemContext.order.set(_order);
	}
	
	public static void removePageSize() {
		pageSize.remove();
	}
	
	public static void removePageOffset() {
		pageOffset.remove();
	}
	
	public static void removeSort() {
		sort.remove();
	}
	
	public static void removeOrder() {
		order.remove();
	}
	
	public static void removeRealPath() {
		realPath.remove();
	}
	
}
```



- UUIDUtils

```java
public class UUIDUtils {

	private static boolean IS_THREADLOCALRANDOM_AVAILABLE = false;
	private static Random random;
	private static final long leastSigBits;
	private static final ReentrantLock lock = new ReentrantLock();
	private static long lastTime;

	static {
		try {
			IS_THREADLOCALRANDOM_AVAILABLE = null != UUIDUtils.class.getClassLoader().loadClass(
					"java.util.concurrent.ThreadLocalRandom");
		} catch (ClassNotFoundException e) {
		}

		byte[] seed = new SecureRandom().generateSeed(8);
		leastSigBits = new BigInteger(seed).longValue();
		if (!IS_THREADLOCALRANDOM_AVAILABLE) {
			random = new Random(leastSigBits);
		}
	}

	private UUIDUtils() {
	}

	/**
	 * Create a new random UUID.
	 *
	 * @return the new UUID
	 */
	public static String random() {
		byte[] randomBytes = new byte[16];
		if (IS_THREADLOCALRANDOM_AVAILABLE) {
			java.util.concurrent.ThreadLocalRandom.current().nextBytes(randomBytes);
		} else {
			random.nextBytes(randomBytes);
		}

		long mostSigBits = 0;
		for (int i = 0; i < 8; i++) {
			mostSigBits = (mostSigBits << 8) | (randomBytes[i] & 0xff);
		}
		long leastSigBits = 0;
		for (int i = 8; i < 16; i++) {
			leastSigBits = (leastSigBits << 8) | (randomBytes[i] & 0xff);
		}

		return new UUID(mostSigBits, leastSigBits).toString().replaceAll("-", "");
	}

	/**
	 * Create a new time-based UUID.
	 *
	 * @return the new UUID
	 */
	public static String create() {
		long timeMillis = (System.currentTimeMillis() * 10000) + 0x01B21DD213814000L;

		lock.lock();
		try {
			if (timeMillis > lastTime) {
				lastTime = timeMillis;
			} else {
				timeMillis = ++lastTime;
			}
		} finally {
			lock.unlock();
		}

		// time low
		long mostSigBits = timeMillis << 32;

		// time mid
		mostSigBits |= (timeMillis & 0xFFFF00000000L) >> 16;

		// time hi and version
		mostSigBits |= 0x1000 | ((timeMillis >> 48) & 0x0FFF); // version 1

		return new UUID(mostSigBits, leastSigBits).toString().replaceAll("-", "");
	}

}
```



2.mybatis

- **mybatis-config.xml**

## 3.spring

- **applicationContext-dao.xml**
- **applicationContext-service.xml**
- **applicationContext-transaction.xml**
- **springmvc-config.xml**

## 4.properties

- **db.properties**：数据库连接信息
- **log4j.properties**：日志文件

## 5.pom.xml

- 引入项目依赖包

## 6.前端（s省略）