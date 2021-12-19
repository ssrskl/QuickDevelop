# 工程简介
## 源码地址
目前只在github上更新了，搬家到github了哦！！

后端源码：https://github.com/ssrskl/QuickDevelop

前端源码：https://gitee.com/PengGeee/forum

> 后端是新版本，前端目前是老版本，前端不适配新版本的后端。

## 项目介绍
QuickDevelop 目前规划也改变了一下，论坛的快速开发框架也会实现，后续也会完善我们的成品项目。
## 项目
QuickDevelop是一个面向全国大学生的在线社区，大学生可以选择自己的学校，以及专业等等。
可以在社区中与其他学校的朋友交流讨论。也可以在不同的版块，不同的分类中发表帖子询问或回答他人的问题等。
后续将会添加im即时通讯系统，让大家也可以在线聊天。

同样的，QuickDevelop也会完善原来的论坛快速开发框架，添加多种功能等等。

关于名字，暂定为**抚风**，后续可能会更改
## 进度
### 后端
用户，文章，分类，标签，权限控制等都已基本完成。

后续将会添加消息队列，文章收藏，用户关注等等功能。

## 主要功能

### 1. 用户的注册与登陆
登陆功能完善了跨域问题，采用拦截器解决了跨域问题，并不再使用传统的cookie，而是采用token的形式，可以在任何客户端实现保持登陆状态
详情请见[无cookie的方法](https://swamp-nitrogen-718.notion.site/cookie-cdd1ed572208401799d4c997c4bc3a37)

### 2. 全局异常处理
采用@RestControllerAdvice和@ExceptionHandler注解捕获异常，并对异常进行处理。

### 3. 面对切面编程

当当当！当然是最典型的面对AOP编程啦，上一刀，下一刀把Controller切开，主要是用来日志输出的。

### 4. 权限控制

没有采用spring官方的SpringSecurity,而是采用了sa-token。
完善了权限的架构。完善了权限的分级.权限采用用户对角色，角色对权限的方法。也对角色以及权限进行了细分。

### 5. 基本功能
包括文章的发表，以及更新，评论的发表，可以发表在不同的板块，在板块之下还有类型，可以选择板块之下的类型。同样的还有等级以及经验的功能。

### 6. 关于前端
新版本的前端暂时还没有开始制作，等放假了就开始多平台的前端开发。

### 7. 日志管理
仿造若依的日志管理,通过自定义注解,使用AOP切入,获得方法的信息等,存入数据库

### 8. 后续添加
后续添加nacos 的远程config。
添加Sentinel的限流熔断降级。

### 9. Start趋势
[![Giteye chart](https://chart.giteye.net/gitee/maoyanscsvr/quick-develop/2Z5HA39H.png)](https://giteye.net/chart/2Z5HA39H)

```
├─ sql
│ 	└─ SQL.sql                                                                      //数据库建表语句
└─ src                                                                              //源码目录
	├─ main
	│ 	├─ java
	│ 	│ 	├─ com
	│ 	│ 	│ 	└─ maoyan
	│ 	│ 	│ 		└─ quickdevelop
	│ 	│ 	│ 			├─ admin                                                     //主模块
	│ 	│ 	│ 			│ 	└─ controller
	│ 	│ 	│ 			│ 		├─ monitor
	│ 	│ 	│ 			│ 		│ 	└─ ServerController.java                         //服务器检测控制类
	│ 	│ 	│ 			│ 		├─ system                                            //系统控制器目录
	│ 	│ 	│ 			│ 		│ 	├─ dqarticle                    
	│ 	│ 	│ 			│ 		│ 	│ 	└─ DqArticleController.java                  //文章控制器
	│ 	│ 	│ 			│ 		│ 	├─ dqcomment
	│ 	│ 	│ 			│ 		│ 	│ 	└─ DqCommentController.java                  //评论控制器
	│ 	│ 	│ 			│ 		│ 	├─ DqLoginController.java                        //用户登录控制器
	│ 	│ 	│ 			│ 		│ 	├─ DqRegisterController.java                     //用户注册控制器
	│ 	│ 	│ 			│ 		│ 	├─ dqtype
	│ 	│ 	│ 			│ 		│ 	│ 	└─ DqTypeController.java                     //文章类型控制器
	│ 	│ 	│ 			│ 		│ 	├─ dquser
	│ 	│ 	│ 			│ 		│ 	│ 	└─ DqUserController.java                     //用户控制器
	│ 	│ 	│ 			│ 		│ 	└─ UploadController.java                         //文件上传控制器
	│ 	│ 	│ 			│ 		└─ TestController.java                               //测试类
	│ 	│ 	│ 			├─ common                                                    //组件目录
	│ 	│ 	│ 			│ 	├─ constant
	│ 	│ 	│ 			│ 	│ 	├─ Constants.java                                    //通用常量信息
	│ 	│ 	│ 			│ 	│ 	└─ HttpStatus.java                                   //返回的状态码
	│ 	│ 	│ 			│ 	├─ core
	│ 	│ 	│ 			│ 	│ 	├─ AjaxResult.java                                   //自定义返回格式
	│ 	│ 	│ 			│ 	│ 	├─ domain
	│ 	│ 	│ 			│ 	│ 	│ 	├─ dqabstract
	│ 	│ 	│ 			│ 	│ 	│ 	│ 	└─ DqStatusDispose.java                      //状态抽象类
	│ 	│ 	│ 			│ 	│ 	│ 	├─ DqArticle.java                                //文章实体类
	│ 	│ 	│ 			│ 	│ 	│ 	├─ DqComment.java                                //评论实体类
	│ 	│ 	│ 			│ 	│ 	│ 	├─ DqRolePermission.java                         //角色与权限对应实体类
	│ 	│ 	│ 			│ 	│ 	│ 	├─ DqType.java                                   //类型实体类
	│ 	│ 	│ 			│ 	│ 	│ 	└─ DqUser.java                                   //用户实体类
	│ 	│ 	│ 			│ 	│ 	└─ page
	│ 	│ 	│ 			│ 	│ 		└─ PageDomain.java                               //分页数据
	│ 	│ 	│ 			│ 	├─ exception
	│ 	│ 	│ 			│ 	│ 	├─ BaseException.java                                //基础异常
	│ 	│ 	│ 			│ 	│ 	├─ CustomException.java                              //自定义异常
	│ 	│ 	│ 			│ 	│ 	├─ file
	│ 	│ 	│ 			│ 	│ 	│ 	├─ FileException.java
	│ 	│ 	│ 			│ 	│ 	│ 	├─ FileNameLengthLimitExceededException.java
	│ 	│ 	│ 			│ 	│ 	│ 	├─ FileSizeLimitExceededException.java
	│ 	│ 	│ 			│ 	│ 	│ 	└─ InvalidExtensionException.java
	│ 	│ 	│ 			│ 	│ 	└─ user
	│ 	│ 	│ 			│ 	│ 		├─ CaptchaException.java
	│ 	│ 	│ 			│ 	│ 		├─ CaptchaExpireException.java
	│ 	│ 	│ 			│ 	│ 		├─ UserException.java
	│ 	│ 	│ 			│ 	│ 		└─ UserPasswordNotMatchException.java
	│ 	│ 	│ 			│ 	├─ MessageUtils.java
	│ 	│ 	│ 			│ 	├─ spring
	│ 	│ 	│ 			│ 	│ 	└─ SpringUtils.java
	│ 	│ 	│ 			│ 	├─ StringUtils.java
	│ 	│ 	│ 			│ 	└─ utils                                                   //工具类目录
	│ 	│ 	│ 			│ 		├─ Arith.java
	│ 	│ 	│ 			│ 		├─ DateUtils.java
	│ 	│ 	│ 			│ 		├─ DirectoryTreeUtil.java
	│ 	│ 	│ 			│ 		├─ DqStatusDisposrUtils.java
	│ 	│ 	│ 			│ 		├─ html
	│ 	│ 	│ 			│ 		│ 	├─ EscapeUtil.java
	│ 	│ 	│ 			│ 		│ 	└─ HTMLFilter.java
	│ 	│ 	│ 			│ 		├─ http
	│ 	│ 	│ 			│ 		│ 	├─ HttpHelper.java
	│ 	│ 	│ 			│ 		│ 	└─ HttpUtils.java
	│ 	│ 	│ 			│ 		├─ ip
	│ 	│ 	│ 			│ 		│ 	└─ IpUtils.java
	│ 	│ 	│ 			│ 		├─ MyQueryWrapper.java                                 //自定义QueryWrapper类
	│ 	│ 	│ 			│ 		├─ SaTokenUtils.java
	│ 	│ 	│ 			│ 		├─ sign
	│ 	│ 	│ 			│ 		│ 	├─ Base64.java
	│ 	│ 	│ 			│ 		│ 	└─ Md5Utils.java
	│ 	│ 	│ 			│ 		├─ text
	│ 	│ 	│ 			│ 		│ 	├─ CharsetKit.java
	│ 	│ 	│ 			│ 		│ 	├─ Convert.java
	│ 	│ 	│ 			│ 		│ 	└─ StrFormatter.java
	│ 	│ 	│ 			│ 		└─ UploadUtil.java
	│ 	│ 	│ 			├─ frameworks                                                  //框架目录
	│ 	│ 	│ 			│ 	├─ aspectj
	│ 	│ 	│ 			│ 	│ 	├─ LogAspect.java
	│ 	│ 	│ 			│ 	│ 	└─ ServiceAspect.java
	│ 	│ 	│ 			│ 	├─ config
	│ 	│ 	│ 			│ 	│ 	├─ CorsConfig.java
	│ 	│ 	│ 			│ 	│ 	├─ MybatisPlusConfig.java
	│ 	│ 	│ 			│ 	│ 	├─ SaTokenConfigure.java
	│ 	│ 	│ 			│ 	│ 	├─ SwaggerConfigure.java
	│ 	│ 	│ 			│ 	│ 	└─ UploadConfig.java
	│ 	│ 	│ 			│ 	├─ exception
	│ 	│ 	│ 			│ 	│ 	└─ GlobalExceptionHandler.java
	│ 	│ 	│ 			│ 	├─ interceptor
	│ 	│ 	│ 			│ 	└─ web
	│ 	│ 	│ 			│ 		└─ domain
	│ 	│ 	│ 			│ 			├─ server
	│ 	│ 	│ 			│ 			│ 	├─ Cpu.java
	│ 	│ 	│ 			│ 			│ 	├─ Jvm.java
	│ 	│ 	│ 			│ 			│ 	├─ Mem.java
	│ 	│ 	│ 			│ 			│ 	├─ Sys.java
	│ 	│ 	│ 			│ 			│ 	└─ SysFile.java
	│ 	│ 	│ 			│ 			└─ Server.java
	│ 	│ 	│ 			├─ QuickDevelopApplication.java
	│ 	│ 	│ 			├─ QuickDevelopApplicationRuner.java
	│ 	│ 	│ 			└─ system
	│ 	│ 	│ 				├─ domain
	│ 	│ 	│ 				│ 	├─ DqArticleVO.java
	│ 	│ 	│ 				│ 	├─ DqCommentVO.java
	│ 	│ 	│ 				│ 	├─ DqTypeVO.java
	│ 	│ 	│ 				│ 	├─ DqUserVO.java
	│ 	│ 	│ 				│ 	└─ vo
	│ 	│ 	│ 				│ 		├─ LoginVO.java
	│ 	│ 	│ 				│ 		└─ RegisterVO.java
	│ 	│ 	│ 				├─ mapper
	│ 	│ 	│ 				│ 	├─ DqArticleMapper.java
	│ 	│ 	│ 				│ 	├─ DqCommentMapper.java
	│ 	│ 	│ 				│ 	├─ DqRolePermissionMapper.java
	│ 	│ 	│ 				│ 	├─ DqTypeMapper.java
	│ 	│ 	│ 				│ 	├─ DqUserMapper.java
	│ 	│ 	│ 				│ 	└─ TestMybatis.java
	│ 	│ 	│ 				└─ service
	│ 	│ 	│ 					├─ IDqArticleService.java
	│ 	│ 	│ 					├─ IDqCommentService.java
	│ 	│ 	│ 					├─ IDqRolePermissionService.java
	│ 	│ 	│ 					├─ IDqTypeService.java
	│ 	│ 	│ 					├─ IDqUserService.java
	│ 	│ 	│ 					└─ Impl
	│ 	│ 	│ 						├─ IDqArticleServiceImpl.java
	│ 	│ 	│ 						├─ IDqCommentServiceImpl.java
	│ 	│ 	│ 						├─ IDqRolePermissionServiceImpl.java
	│ 	│ 	│ 						├─ IDqTypeServiceImpl.java
	│ 	│ 	│ 						├─ IDqUserServiceImpl.java
	│ 	│ 	│ 						└─ IStpInterfaceImpl.java
	│ 	│ 	└─ generator
	│ 	│ 		├─ domain
	│ 	│ 		│ 	└─ DqUser.java
	│ 	│ 		├─ mapper
	│ 	│ 		│ 	└─ DqUserMapper.java
	│ 	│ 		└─ service
	│ 	│ 			├─ DqUserService.java
	│ 	│ 			└─ impl
	│ 	│ 				└─ DqUserServiceImpl.java
	│ 	└─ resources
	│ 		├─ application.yml
	│ 		├─ banner.txt
	│ 		├─ i18n
	│ 		│ 	└─ messages.properties
	│ 		├─ logback.xml
	│ 		├─ mapper
	│ 		│ 	└─ DqUserMapper.xml
	│ 		├─ static
	│ 		└─ templates
	└─ test
		└─ java
			└─ com
				└─ maoyan
					└─ quickdevelop
						└─ QuickDevelopApplicationTests.java

```

