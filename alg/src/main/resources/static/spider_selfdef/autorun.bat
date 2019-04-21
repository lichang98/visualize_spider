@echo off
echo %cd%
cd .\src\main\resources\static\spider_selfdef
echo %cd%
for /f %%a in ('dir /a /b') do echo %%a
scrapy crawl news_spider > spider_run.log 2>&1
