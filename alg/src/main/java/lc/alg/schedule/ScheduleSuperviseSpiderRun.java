/**
 * 
 */
package lc.alg.schedule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.quartz.QuartzJobBean;

import lc.alg.entity.NewsDocs;
import lc.alg.entity.SpiderConfigInfo;
import lc.alg.entity.SpiderRunInfo;

/**
 * @author 李畅
 *	定时任务，固定时间间隔查询爬虫运行情况，并保存到MongoDB中
 */
public class ScheduleSuperviseSpiderRun extends QuartzJobBean{
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public static SpiderRunThread spiderRunThread=null;
	
//	/**
//	 *  java 执行系统命令
//	 * @param cmd
//	 * @throws IOException 
//	 * @throws InterruptedException 
//	 */
//	public void runShellScript(String[] cmds) throws IOException, InterruptedException {
//		Process pro = Runtime.getRuntime().exec(cmds);
//		pro.waitFor();
////		InputStream in = pro.getInputStream();
////		BufferedReader read = new BufferedReader(new InputStreamReader(in));
////		String line = null;
////		StringBuffer messages = new StringBuffer();
////		while((line = read.readLine()) != null) {
////			//System.out.println(new String(line.getBytes("GBK"),"UTF-8"));
////			line = new String(line.getBytes("GBK"),"UTF-8");
////			System.out.println(line);
////			messages.append(line);
////		}
//		System.out.println("**********ALL MESSAGE OUTPUT FINISH************");
//	}
	

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		System.out.println("定时任务.................");
		//查询当前是否有running 状态的爬虫
		if(mongoTemplate.findOne(new Query(Criteria.where("curStatus").is("running")), SpiderConfigInfo.class) == null) {
			System.out.println("当前没有running 状态的爬虫程序");
			return;
		}
		if(spiderRunThread == null) {
			Path path = Paths.get(System.getProperty("user.dir"),"src","main","resources","static","spider_selfdef","autorun.bat");
			String pathStr = path.toString();
			String[] cmds = {pathStr};
			spiderRunThread = new SpiderRunThread(cmds);
		}
		//在线程中启动爬虫
		if(!spiderRunThread.isAlive() && spiderRunThread.isStopped()) {
			spiderRunThread.start();
			spiderRunThread.setStopped(false);
		}else if(!spiderRunThread.isAlive() && spiderRunThread.isFinish()) {
			//启动后的爬虫运行已停止
			//查询当前状态为running 的爬虫，并设置为finish, 更新runlog 数据
			Query query = new Query(Criteria.where("curStatus").is("running"));
			SpiderConfigInfo spiderConfigInfo = mongoTemplate.findOne(query, SpiderConfigInfo.class);
			if(spiderConfigInfo !=null) {
				String taskName = spiderConfigInfo.getTaskName();
				Update update = new Update().set("curStatus", "finish");
				mongoTemplate.updateFirst(query, update, SpiderConfigInfo.class);
				//更新爬虫运行信息中的runlog
				Path path = Paths.get(System.getProperty("user.dir"),"src","main","resources","static","spider_selfdef","spider_run.log");
				File logFile = new File(path.toString());
				BufferedReader reader = null;
				StringBuffer strBuffer=new StringBuffer();
				try {
					reader = new BufferedReader(new FileReader(logFile));
					String line=null;
					while((line = reader.readLine()) != null) {
						strBuffer.append(line+"\r\n");
					}
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					if(reader != null)
						try {
							reader.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					e.printStackTrace();
				}
				//更新到数据库中
				update = new Update().set("runlog", strBuffer.toString());
				query = new Query(Criteria.where("taskName").is(taskName));
				mongoTemplate.updateFirst(query, update, SpiderRunInfo.class);
			}
		}
		// 获取当前爬虫的运行信息
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		String dateStr = dateFormat.format(date);
		String memUsageStr = Double.toString((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())*1.0/Runtime.getRuntime().totalMemory());
		//查询数据库，获取当前爬虫抓取的新闻的数量
		List<NewsDocs> list = mongoTemplate.findAll(NewsDocs.class);
		int getNewsCount = list.size();
		//查询当前为running状态的爬虫
		Query query = new Query(Criteria.where("curStatus").is("running"));
		SpiderConfigInfo spiderConfigInfo = mongoTemplate.findOne(query, SpiderConfigInfo.class);
		if(spiderConfigInfo != null) {
			//更新memInfo getCountInfo
			String taskName = spiderConfigInfo.getTaskName();
			query = new Query(Criteria.where("taskName").is(taskName));
			SpiderRunInfo spiderRunInfo = mongoTemplate.findOne(query, SpiderRunInfo.class);
			Map<String,String> timeMemUse = new HashMap<>();
			timeMemUse.put(dateStr, memUsageStr);
			spiderRunInfo.getMemInfo().add(timeMemUse);	//添加当前时间点的内存占用信息
			Map<String,String> timeNewsGetInfo = new HashMap<>();
			int getInfoListLen = spiderRunInfo.getGetCountInfo().size();
			if(getInfoListLen > 0) {
				Iterator<Map.Entry<String,String>> entries = spiderRunInfo.getGetCountInfo().get(getInfoListLen-1).entrySet().iterator();
				int preNewsGetCount=0;
				while(entries.hasNext()) {
					preNewsGetCount = Integer.parseInt(entries.next().getValue());
				}
				getNewsCount -= preNewsGetCount;
			}
			timeNewsGetInfo.put(dateStr, Integer.toString(getNewsCount));
			spiderRunInfo.getGetCountInfo().add(timeNewsGetInfo);
			//更新memInfo 以及getCountInfo 到数据库中
			query = new Query(Criteria.where("taskName").is(taskName));
			Update update = new Update().set("memInfo", spiderRunInfo.getMemInfo());
			mongoTemplate.updateFirst(query, update, SpiderRunInfo.class);
			update = new Update().set("getCountInfo", spiderRunInfo.getGetCountInfo());
			mongoTemplate.updateFirst(query, update, SpiderRunInfo.class);
		}
	}
}
