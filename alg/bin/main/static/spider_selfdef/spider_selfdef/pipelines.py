# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://doc.scrapy.org/en/latest/topics/item-pipeline.html

from scrapy.conf import settings
import pymongo

class SpiderSelfdefPipeline(object):
    
    def __init__(self):
        # 获取数据库连接信息
        host = settings['MONGODB_HOST']
        port = settings['MONGODB_PORT']
        dbname = settings['MONGODB_DBNAME']
        client = pymongo.MongoClient(host=host,port=port)
        # 定义数据库
        db = client[dbname]
        self.table = db[settings['MONGODB_TABLE']]
        self.missing_table = db[settings['MONGODB_TABLE_MISSING']]
        pass
    
    def process_item(self, item, spider):
        # 使用dict 转换item ,并插入到数据库中
        news_info = dict(item)
        # 检查title rekeaseTime content 字段是否齐全
        if 'title' not in news_info.keys() or 'releaseTime' not in  \
                    news_info.keys() or 'content' not in news_info.keys():
            missing_info={}
            missing_info.update({'url':news_info['url']})
            if 'title' not in news_info.keys():
                missing_info.update({'title':'false'})
            else:
                missing_info.update({'title':'true'})
            if 'releaseTime' not in news_info.keys():
                missing_info.update({'releaseTime':'false'})
            else:
                missing_info.update({'releaseTime':'true'})
            if 'content' not in news_info.keys():
                missing_info.update({'content':'false'})
            else:
                missing_info.update({'content':'true'})
            self.missing_table.insert(missing_info)
        else:
            self.table.insert(news_info)
        return item
