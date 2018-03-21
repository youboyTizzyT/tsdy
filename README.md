# tsdy 贴身导游框架
***
贴身导游架构,是采用最简单的client->netty->action->service->dao->数据库 在dao和数据库中有redis当缓存,并使用redis实现锁机制解决并发问题
连接池采用c3d0连接池,作业机制以及时间及时采用quartz框架,日志采用log4j,消息格式采用alibaba的json,tcp消息分发采用netty,数据库采用mysql
项目jar包采用maven,down代码到idea中等待jar包下载完成即可开发逻辑部分.所有框架属性全在resources文件下的配置文件中.其他详情在tsdy目录下的服务器说明书下
***
 + 2018.3.21 1.0.0 完成框架netty搭建,个部分功能的简单实现,完成基本交互.
