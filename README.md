# 仓库库管理系统后端

set on **2024.2.21**

+ 一个前后端分离项目的后端部分，实现仓库以及相应人员管理
+ [前端地址](https://github.com/Zwm-s/wms-web)

# 主要技术：
+ SSM
+ springweb
+ mybatis
+ Redis
+ SpringSecurity

# 更新日志

## 2024.2.21
第一次推送
+ 初步实现对用户管理的功能，数据库连接部分以及Dao层采用mybatis技术
+ 包括对用户的增删改查
+ 对用户的模糊查询
+ 对用户的分页查询

## 2024.2.22
第二次推送
+ 适应前端使用数据对特定用户查询，修改做出了优化
+ 添加了按照账户来查询修改
+ 保证账户的不可重复

## 2024.2.23
第三次推送
+ 增加了登录验证功能
+ 增加了jwt令牌验证功能

## 2024.2.27
第四次推送
+ 完善了跨域处理
+ 完善了用户层需求

## 2024.3.1
第五次推送
+ 整合了SpringSecurity框架来处理登录模块，后续可能会进行授权处理，这一块问题太多
+ 添加了仓库，物品的数据库的增删改查功能

## 2024.3.3
第六次推送
+ 完善了对物品的增删改查等功能
+ 完善了仓库的增删改查功能
+ 配合前端基本功能已经完成

## 2024.3.6
第七次推送
+ 结合前端上传头像完善了用户头像存储
+ 结合阿里云Oss对象存储初步完成对物品对象图片的上传以及删除

## 2024.3.10
第八次推送
+ 云存储欠费了，改为本地存储
+ 完善了登出图片存储功能

## 2024.3.10
第七次推送
+ 集成了Redis数据库
+ 完善了登出功能

## 2024.3.10
第八次推送
+ 完善了用户权限管理
