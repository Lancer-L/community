## 码匠社区
## 资料 
ctrl + 向下箭头 操作一列
ctrl + 向右箭头 移到最左边

[github 授权](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)

[okHttp](https://square.github.io/okhttp/)

[h2数据库](http://www.h2database.com/html/quickstart.html)

[整合mybatis](http://www.mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)

[spring-boot 官方参考文档](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/)

## 工具

##脚本
```sql
create table USER
(
  ID           INTEGER default (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_6D79921B_F10F_49D5_BE70_5AFA355BD1F4) auto_increment,
  ACCOUNT_ID   VARCHAR(100),
  NAME         VARCHAR(50),
  TOKEN        CHAR(36),
  GMT_CREATE   BIGINT,
  GMT_MODIFIED BIGINT,
  constraint USER_PK
    primary key (ID)
);
```