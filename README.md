# 基于Spring Boot、Scrapy 的爬虫配置与管理
---
## 系统结构
该系统主要包括用户和爬虫的配置与管理。爬虫部分包括新爬虫的配置与启动、爬虫的监测、爬虫配置的修改与重启以及对爬取到的新闻进行管理与导出下载功能\
![系统结构图](https://github.com/lichang98/visualize_spider/blob/master/images/%E7%BD%91%E7%AB%99%E7%BB%93%E6%9E%84%E5%9B%BE.PNG)
### 主要功能部分简介
1. 爬虫的配置与启动、监测，配置修改与重启\
1.1 爬虫的配置与启动\
 在爬虫启动界面输入目标网址以及任务名称，并在下方配置每个属性的提取规则，之后就可以启动爬虫了\
 属性的提取规则使用XPATH，相关的属性的说明以及使用样例可以通过点击配置帮助查看到\
 该部分以及爬虫配置修改部分均外链了一个在线XPATH测试网站，可以在该网站上测试XPATH表达式是否书写正确\
 ![新爬虫的启动](https://github.com/lichang98/visualize_spider/blob/master/images/%E6%96%B0%E7%88%AC%E8%99%AB%E5%90%AF%E5%8A%A8.PNG)\
1.2 爬虫的监测\
 为了能够获取到爬虫的运行情况，在爬虫监测部分设置了内存、爬取新闻数量、运行日志、缺失字段记录共四个方面的监测\
 通过使用Quartz在后台编写一个定时任务，每隔10分钟获取当前系统的内存情况以及新闻数量的变化，并将监测记录写入到数据库集合中，前端使用百度的Echarts绘制折线图展示\
 由于后台是通过在线程中运行系统脚本（windows批处理），并在脚本中启动爬虫并重定向输出，因此日志文件需要在爬虫结束后才能得到\
 **Windows下运行已测试过，但是Linux下还未测试**\
 由于爬取的网站中网页的风格与结构可能不同，因此在配置新闻标题、主体内容等字段时可能会在爬取某些页面时没有提取到数据。因此添加对缺失字段的监测，从而能够通过查看相应网站的结构补充添加对应属性的提取规则\
 ![爬虫内存监测](https://github.com/lichang98/visualize_spider/blob/master/images/%E7%88%AC%E8%99%AB%E7%9B%91%E6%B5%8B.PNG)\
 *爬虫内存监测*\
 ![爬虫爬取新闻监测](https://github.com/lichang98/visualize_spider/blob/master/images/%E7%88%AC%E8%99%AB%E7%9B%91%E6%B5%8B2.PNG)\
 *新闻数量监测* \
 ![爬虫运行日志](https://github.com/lichang98/visualize_spider/blob/master/images/%E7%88%AC%E8%99%AB%E7%9B%91%E6%B5%8B3.PNG)\
 *运行日志查看* \
 ![缺失字段查看](https://github.com/lichang98/visualize_spider/blob/master/images/%E7%88%AC%E8%99%AB%E7%9B%91%E6%B5%8B4.PNG)\
 *缺失记录查看*\
 1.3 爬虫配置的修改与重启\
  在爬虫监测界面，点击爬虫配置修改按钮后，跳转到爬虫配置修改界面。下方会显示爬虫任务名称、爬取网站的网址以及之前配置的属性。可以在此页面添加或删除爬虫属性\
  ![爬虫配置修改](https://github.com/lichang98/visualize_spider/blob/master/images/%E7%88%AC%E8%99%AB%E9%85%8D%E7%BD%AE%E4%BF%AE%E6%94%B9.PNG)\
  *爬虫配置修改*\
  相关的参数说明以及配置样例可以在配置说明界面查看\
  ![配置说明](https://github.com/lichang98/visualize_spider/blob/master/images/%E7%88%AC%E8%99%AB%E9%85%8D%E7%BD%AE%E8%AF%B4%E6%98%8E.PNG)\
  *爬虫配置说明*\
  为了保证XPATH提取规则的正确性，在配置界面添加了一个外部网站链接。可以在该网站上针对待爬取的页面测试XPATH表达式，在下方可以看到使用该表达式进行提取的输出结果\
  ![在线XPATH测试](https://github.com/lichang98/visualize_spider/blob/master/images/%E5%9C%A8%E7%BA%BFXPATH%E6%B5%8B%E8%AF%95.PNG)
  *在线XPATH测试*\
2. 新闻数据管理\
 由于爬虫获取的新闻数量较多，因此采用分页显示的方式。并且只显示网址、发布时间等文字较少的信息。新闻的正文内容只在点击浏览时才会从后台获取\
![异步加载](https://github.com/lichang98/visualize_spider/blob/master/images/ajax%E5%BC%82%E6%AD%A5%E6%95%B0%E6%8D%AE%E5%8A%A0%E8%BD%BD.PNG)\
*新闻数据异步加载*\
![新闻记录分页](https://github.com/lichang98/visualize_spider/blob/master/images/%E6%96%B0%E9%97%BB%E6%95%B0%E6%8D%AE%E6%B5%8F%E8%A7%88.PNG)\
*新闻记录分页显示*\
![新闻内容浏览](https://github.com/lichang98/visualize_spider/blob/master/images/%E6%96%B0%E9%97%BB%E5%86%85%E5%AE%B9%E6%B5%8F%E8%A7%88.PNG)\
*新闻正文内容浏览*\
后台会先从数据库导出新闻的正文内容，之后将导出的文本文件打包压缩，并提供下载\
![新闻压缩文件下载](https://github.com/lichang98/visualize_spider/blob/master/images/%E6%96%B0%E9%97%BB%E6%95%B0%E6%8D%AE%E5%AF%BC%E5%87%BA.PNG)
*新闻压缩文件下载*
