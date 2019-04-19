# -*- coding: utf-8 -*-
"""
Created on Fri Apr 19 08:53:41 2019

@author: 李畅
"""

import scrapy
from scrapy.loader import ItemLoader
from spider_selfdef.items import SpiderSelfdefItem
import pymongo

class NewsSpider(scrapy.Spider):
    name="news_spider" # 爬虫的名称
    mongo_client = pymongo.MongoClient('mongodb://127.0.0.1:27017/')
    spider_config_col = mongo_client['spider_news']['spider_config'] # mongodb 爬虫配置集合
    # 查询当前状态为pending 的爬虫, 选择一个启动
    for spider_config in spider_config_col.find():
        if spider_config['curStatus'] == 'pending':
            pending_spider = spider_config
            break
    web_domain_list=[]
    org_domain_list=[]
    xp_page_title_list=[]
    xp_page_url_list=[]
    xp_release_time=[]
    xp_content=[]
    xp_nextpage=[]
    for item in pending_spider["attributeParser"]:
        print("in news_spider.py: pending_spider item: " + str(item));
        item = dict(item)
        for key,val in item.items():
            if key == 'web_domain': # 当前网站的domain 例如: https://search.cctv.com
                web_domain_list.append(val)
            elif key == 'start_url': # 爬虫起始网址
                start_url = val
            elif key == 'main_domain':
                org_domain_list.append(val) # 机构域名， 例如：cctv.com cntv.cn
            elif key == 'title':   # 新闻标题xpath 解析规则
                xp_page_title_list.append(val)
            elif key == 'url':   # 新闻内容界面链接xpath 解析规则
                xp_page_url_list.append(val)
            elif key == 'releaseTime':   # 新闻发布时间xpath 解析规则
                xp_release_time.append(val)
            elif key == 'content':      # 新闻主体内容xpath 解析规则
                xp_content.append(val)
            elif key == 'next_page':     # 获取下一个新闻列表的链接的xpath 解析规则
                xp_nextpage.append(val)
    allowed_domains=org_domain_list
    start_urls=[start_url] # 起始页面链接
    pass
    
    
    def parse(self,response):
        """
        对搜索页面的得到的结果列表解析
        配置中必要的参数:
            第一层解析可以获得的参数值:
                url: 新闻内容页面的网址链接
                title: 新闻的标题
            第二层解析可以获得的参数值:
                releaseTime: 新闻发布的时间
                content: 新闻的内容主题
        用于获取必要数据的xpath 设置 解析规则为list, 包含对某个属性的不同解析方式，用以适配不同页面:
            xp_page_title_list: 用于解析当前新闻列表标题的xpath规则，返回新闻标题列表
            xp_page_url_list: 用于解析转到具体新闻内容的xpath 规则，返回url 链接
            xp_release_time: 用于解析新闻发布时间值的xpath 规则
            xp_content: 用于解析新闻主体内容的xpath规则，返回段落列表
            xp_next_page: 用于获取下一个新闻列表页面
        """
        pagelink_list = response.xpath(self.xp_page_url_list[0]).extract()
        # 给每个页面链接加上网站域名，例如https://search.cctv.com/+"?...."
        if self.web_domain_list[0][-1] != '/':
            self.web_domain_list[0] += '/'
        pagelink_list = [self.web_domain_list[0]+link for link in pagelink_list]
        pagetitle_list = response.xpath(self.xp_page_title_list[0]).extract()
        # 处理获取到的每个网页链接
        for i in range(len(pagelink_list)):
            yield scrapy.Request(pagelink_list[i],meta={'url':pagelink_list[i],
                                 'title': pagetitle_list[i]})
        # 获取下一个页面列表的链接
        next_pagelist_link = response.xpath(self.xp_nextpage[0]).extract()[0]
        if next_pagelist_link:
            yield scrapy.Request(self.web_domain_list[0] + next_pagelist_link,
                                 callback=self.parse)
        return None
    
    
    def page_parse(self,response):
        """
        第二层页面解析器
        解析新闻的内容界面， 获得新闻的发布时间以及主体内容
        """
        ld = ItemLoader(item=SpiderSelfdefItem,response=response)
        ld.add_value('url',response.meta['url'])
        ld.add_value('title',response.meta['title'])
        for xp_releasetime in self.xp_release_time:
            ld.add_xpath('releaseTime',xp_releasetime)
        for xp_cont in self.xp_content:
            ld.add_xpath('content',xp_cont)
        return ld.load_item()
