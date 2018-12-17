### Java background management system

**[中文README🇨🇳](https://github.com/Chankin026/BackAuM/blob/master/README-zh-cn.md)**    <a href="https://github.com/Ouyangan" style="text-align: right">Forked from Ouyangan</a>
---

**Project Introduction**

This is a background management system that includes common modules, using Spring, SpringMVC, MyBatis, Shiro, Redis and other common frameworks. Main features:

- Implement `shiroCache`, `shiroSession` management with redis, server restart does not affect user status information
- Flexible permission configuration scheme, which can configure permissions for a certain role, or configure permissions for a user individually, maximizing the need for personalized permissions, and dynamic refresh of permissions, effective immediately, no need to log in again
- Position information consists of departments, roles, and responsibilities. It basically meets the needs of various perverted personnel organizations. There is no limit to the upper and lower levels of the catalogue.
- Terminal login restriction, automatically offline the same type of terminal remote account, forcing a terminal user to go offline, disable the startup account.
- Uniform exception handling, json requests return json type error data, normal web requests return normal web error pages.
- Production, development, separation of the local environment, easy integration, such as `mvn clean install -Pprod`
- Integrated `springfox` document management, interface debugging is very convenient, solve the problem of document maintenance pain....
- Request logs, development environment error log output for easy analysis and debugging.
- Data Dictionary: Query Automatically Cache...
- ip interception

---

**Getting started**

- Create `mysql` database, run `backaum.sql` in the `sql-init` folder, install `Redis`(port:6379)...
- IDE imports maven project, waiting for dependency download...
- Set the access port to any, of course, you can apply for the limit verification account binding yourself, and then replace the value of the tune data dictionary.
- `run`, `username`:admin `password`:111111

---
### FrameWork
- Spring         
- SpringMVC    
- Mybatis       
- Apache Shiro    
- Redis           
- Mysql           
- Springfox-swagger 
---
### Preview
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



