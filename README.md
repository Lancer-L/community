## 码匠社区
## 资料 
ctrl + 向下箭头 操作一列
ctrl + 向右箭头 移到最左边

[github 授权](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)

[okHttp](https://square.github.io/okhttp/)

[h2数据库](http://www.h2database.com/html/quickstart.html)

[整合mybatis](http://www.mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)

[spring-boot 官方参考文档](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/)

[flyway 学习](https://flywaydb.org/getstarted/firststeps/maven)

[bootstrap 学习](https://v3.bootcss.com/css/)

[lombok 学习](https://www.projectlombok.org/)

[thymeleaf 学习](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#using-theach)
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
create table question
(
	id int auto_increment,
	title varchar(50),
	description text,
	gmt_create bigint,
	gmt_modified bigint,
	creator int,
	comment_count int default 0,
	view_count int default 0,
	like_count int default 0,
	tag varchar(256),
	constraint question_pk
		primary key (id)
);



```