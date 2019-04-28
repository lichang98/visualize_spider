#-*- coding:utf-8 -*-
"""
绘制食品安全时间分布图
"""
import pymongo
import re
import sys
import matplotlib.pyplot as plt
from pylab import mpl
import pylab
mpl.rcParams['font.sans-serif']=['SimHei']

out_old=sys.stdout
f_out=open('log.txt','w',encoding='utf-8')
sys.stdout=f_out

if __name__ == '__main__':
    dbclient=pymongo.MongoClient('mongodb://localhost:27017')
    news_db=dbclient['spider_news']
    news_col=news_db['news_docs'] # 保存新闻内容的集合
    # 获取所有的时间字段
    news_list=list(news_col.find())
    year_list=[]
    month_list=[]
    for item in news_list:
        release_time=item['releaseTime']
        if re.search('[0-9]{4}',release_time):
            rg=re.search('[0-9]{4}',release_time).span()
            if int(release_time[rg[0]:rg[1]]) > 2020 or int(release_time[rg[0]:rg[1]]) < 1990:
                continue
            year_list.append(release_time[rg[0]:rg[1]])
            release_time=release_time[rg[1]:]
            if re.search('[0-9]{1,2}',release_time):
                rg=re.search('[0-9]{1,2}',release_time).span()
                month=release_time[rg[0]:rg[1]]
                month_list.append(month)
            else:
                year_list.pop()
    time_freq={}
    for year,month in zip(year_list,month_list):
        tm=year+'-'+month
        if tm in time_freq.keys():
            time_freq[tm] +=1
        else:
            time_freq.update({tm:1})
    fig=plt.figure(figsize=(10,8))
    ax=fig.add_subplot(111)
    time_list=list(time_freq.keys())
    time_list.sort()
    freq_list=[]
    for tm in time_list:
        freq_list.append(time_freq[tm])
    x_ticks=[]
    for i in range(len(time_list)):
        if i % 10 == 0:
            x_ticks.append(time_list[i])
    ax.bar(time_list,freq_list)
    ax.set_xticks(x_ticks)
    pylab.xticks(rotation=45)
    ax.set_title('食品安全新闻时间分布柱状图',size=20)
    ax.set_xlabel('时间',size=14)
    ax.set_ylabel('频次',size=14)
    plt.savefig('time_distribute.jpg')
    # 重置stdout
    sys.stdout=out_old
    f_out.flush()
    f_out.close()


