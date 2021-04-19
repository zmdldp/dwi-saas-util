
# saas-util 简介
`saas-util` 是 [saas-cloud]的核心工具包，开发宗旨是兼顾 SpringBoot 和 SpringCloud 项目的公共工具类。

# saas-util 结构
- saas-annotation
    - 通用注解
- saas-boot
    - SpringBoot通用配置:序列化规则, 全局异常
    - 通用Controller/Service/Mapper父类
- saas-cache-starter
    - redis和caffeine二次封装
- saas-cloud-starter
    - spring cloud通用配置
    - 自定义负载均衡
    - 全局Fallback处理器
    - 远程调用请求头和上下文传递
- saas-core
    - 核心基础工具类 
- saas-databases
    - 数据源配置
    - 通用字段:id,createTime,createBy,updateTime,updateBy 自动填充
    - 数据源动态切换, SCHEMA动态修改, Sql动态添加租户字段
    - 数据权限拦截器
- saas-dependencies
    - 自定义pom, 管理所有项目依赖版本
- saas-dozer-starter
    - bean转换配置
- saas-echo-starter
    - 远程数据自动注入
- saas-jwt-starter
    - Jwt自动生成
    - 解析工具类
- saas-log-starter
    - 操作日志记录
    - logBack日志模板
    - MDC传递参数
- saas-mq-starter
    - 消息队列
- saas-security-starter
    - 全局认证拦截器,将请求头中的用户信息封装到LocalThread
    - 当前用户身份自动注入
    - URI权限拦截
- saas-swagger2-starter
    - swagger2自动配置
- saas-uid
    - 全局唯一标识
- saas-validator-starter
    - 后端表单验证规则自动获取
- saas-xss-starter
    - 过滤器,序列化规则双重过滤参数
- saas-zipkin-client-starter

## saas-util 功能
- Mvc封装： 通用的 Controller、Service、Mapper、全局异常、全局序列化、反序列化规则
- SpringCloud封装：请求头传递、调用日志、灰度、统一配置编码解码规则等
- 数据回显：优雅解决 跨库表关联字段回显、跨服务字段回显
- 持久层增强：增强MybatisPlus Wrapper操作类、数据权限、自定义类型处理器
- 枚举、字典等字段统一传参、回显格式： 解决前端即要使用编码，有要回显中文名的场景。
- 在线文档：对swagger、knife4j二次封装，实现配置即文档。
- 前后端表单统一校验：还在为前端写一次校验规则，后端写一次校验规则而痛苦不堪？ 本组件将后端配置的jsr校验规则返回给前端，前端通过全局js，实现统一的校验规则。
- 缓存：封装redis缓存、二级缓存等，实现动态启用/禁用redis
- XSS： 对表单参数、json参数进行xss处理
- 统一的操作日志： AOP方式优雅记录操作日志
- 轻量级接口权限
