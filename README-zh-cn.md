### Java后台管理系统                                     

​																		Forked from [Ouyangan](https://github.com/Ouyangan)

---

**项目介绍**

包含通用模块的后台管理系统,使用spring, springmvc, mybatis,shiro, swagger 等常用框架 , 主要特点:

- 用redis实现`shiroCache` ,`shiroSession`管理, 服务器重启不会影响用户状态信息 
- 灵活的权限配置方案 ,可为某一角色统一配置权限,也可为某一用户单独配置权限 ,最大化满足个性化权限需求 ,并且权限动态刷新,立即生效 ,无需重新登录
- 职位信息由 部门 ,角色,权限 组成 ,基本满足各种变态人事组织需求 ,无限制上下级目录 ,可身兼多职....
- 终端登录限制 ,自动下线同类型终端异地账号 , 强制某终端用户下线 ,禁用启动账户 .
- 统一异常处理 ,json请求返回json类型错误数据 ,普通web请求返回普通web错误页面.
- 生产,开发,本地环境分离, 便于持续集成 ,例如 `mvn clean install -Pprod` 
- 集成`springfox`文档管理 , 接口调试非常方便 , 解决文档维护痛点....
- 请求日志,开发环境错误日志输出,方便分析和调试。
- 数据字典: 查询自动走缓存...
- ip拦截

---

**系统部署**

- 创建`mysql`数据库 ,运行`sql-init`文件夹下的`backaum.sql`, 安装`Redis`(port:6379)...
- IDE导入maven工程 ,等待依赖下载 ...
- 设置访问端口任意 , 当然你可以自己申请极限验证账号绑定,然后替换调数据字典的值
- `run` , `用户名`:admin `密码`:111111 
---
### 后端技术

| 技术              | 名称                |
| ----------------- | ------------------- |
| Spring            | 容器                |
| SpringMVC         | MVC框架             |
| Mybatis           | ORM框架             |
| Apache Shiro      | 认证和授权框架      |
| Redis             | 数据缓存            |
| Mysql             | Mysql数据库         |
| Springfox-swagger | api接口文档生成工具 |


### UI效果图
![](http://ww1.sinaimg.cn/large/6135a28bgy1fya53zp1odj20ul0iajs6.jpg)
![](http://ww1.sinaimg.cn/large/6135a28bgy1fya53yus5vj21z30xz78w.jpg)
![](http://ww1.sinaimg.cn/large/6135a28bgy1fya53ywa6xj21z10y2aho.jpg)
![](http://ww1.sinaimg.cn/large/6135a28bgy1fya53yuot0j21z30p4jwa.jpg)
![](http://ww1.sinaimg.cn/large/6135a28bgy1fya53yuyj0j21xx0pvag4.jpg)
![](http://ww1.sinaimg.cn/large/6135a28bgy1fya53zbbm7j21z30sltfc.jpg)
![](http://ww1.sinaimg.cn/large/6135a28bgy1fya53zhghpj21ys0vuqcm.jpg)
![](http://ww1.sinaimg.cn/large/6135a28bgy1fya53zb6qej21wr0t3te9.jpg)
![](http://ww1.sinaimg.cn/large/6135a28bgy1fya53zcbd5j21yr0y6gsn.jpg)
![](http://ww1.sinaimg.cn/large/6135a28bgy1fya53yustaj21yd0kx78l.jpg)
![](http://ww1.sinaimg.cn/large/6135a28bgy1fya53zdvbhj21ya0u4qa8.jpg)
![](http://ww1.sinaimg.cn/large/6135a28bgy1fya53zqvdsj21cc0xsaet.jpg)
![](http://ww1.sinaimg.cn/large/6135a28bgy1fya53zorudj21bu0xvq5p.jpg)
![](http://ww1.sinaimg.cn/large/6135a28bgy1fya53zs18uj21yx0x9wld.jpg)

---

### License

- apache license 2.0



