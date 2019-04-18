> 这边主要是进行一个登录认证用户名密码和验证码的操作，由于是初学者遇到了挺多问题

> 为了调试方便，密码明文存储了

- 问题1：是行内通用解决方案吗?
```text
security依靠spring做链路过滤的控制，处理用户登录逻辑的过滤器为UsernamePasswordAuthenticationFilter，那我们这个验证码应该也需要一个过滤器去认证

过滤器需要每一次登录请求都进行验证，不通过就不需要去用户名密码认证了，浪费资源

shiro因为不用依靠spring包办，就灵活很多，一个登录接口把表单提交，然后对传入的参数先做验证码的校验，在做用户名密码的login认证

在这个点上，我看到了spring security虽然依附spring做了很多依赖注入和对象管理，但是灵活程度确实受限一点，需要根据它的逻辑做适配
```

- 问题2：springboot 1.5.x 和springboot 2.x的页面请求有细微差异? 
```text
这个是没有差别的，就是一开始没有把css这些资源免认证过滤，导致资源被重定向，所以导致login页面被访问两次，页面样式也加载不出来

正常请求情况下，就是除了开始请求的地址被重定向到登陆页面，其他资源响应应该是200

```

- 问题3：页面访问404问题
```text
这个还是鄙人才疏学浅乱来的问题，本以为放在resources下面的资源肯定可以被访问，可是并不符合spring boot的要求

默认静态资源路径：
Spring Boot 对静态资源映射提供了默认配置，静态资源路径都是在classpath中
classpath:/static
classpath:/public
classpath:/resources
classpath:/META-INF/resources

新增静态资源路径：
# 静态文件请求匹配方式
spring.mvc.static-path-pattern=/**
# 修改默认的静态寻址资源目录 多个使用逗号分隔
spring.resources.static-locations = classpath:/META-INF/resources/,classpath:/resources/,classpath:/static/,classpath:/public/,classpath:/itstyle/

自定义静态资源外部路径映射:

方法一：简单粗暴自定义
# 定义了一个可以随意变更的路径
image.upload.path=/home/file/image

在全局的配置中自定义
    @Value("${image.upload.path}")
    private String uploadPath;
    
   @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/uploads/**").addResourceLocations( "file:"+uploadPath);
    }


方法二：不推荐 --- springboot的默认配置失效
# 静态文件请求匹配方式
spring.mvc.static-path-pattern=/upload/**
# 修改默认的静态寻址资源目录 多个使用逗号分隔
#spring.resources.static-locations = classpath:/META-INF/resources/,classpath:/resources/,classpath:/static/,classpath:/public/,classpath:/itstyle/
spring.resources.static-locations=file:${image.upload.path}
```