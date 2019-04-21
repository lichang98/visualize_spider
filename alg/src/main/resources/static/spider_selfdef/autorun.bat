@echo off
echo %cd%
cd .\src\main\resources\static\spider_selfdef
echo %cd%
scrapy crawl news_spider