/**
 * 
 */
package lc.alg.schedule;

import java.io.IOException;

/**
 * @author 李畅
 *
 */
public class SpiderRunThread extends Thread{
	
	private String[] cmds;		//当前线程执行的命令
	private boolean stopped;	//在停止爬虫线程后，设置该标志位为true 
	private boolean finish;
	
	public SpiderRunThread(String[] cmds) {
		super();
		this.cmds = cmds;
		this.stopped=true;
		this.finish=false;
	}
	
	

	public boolean isStopped() {
		return stopped;
	}



	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}


	public boolean isFinish() {
		return finish;
	}



	public void setFinish(boolean finish) {
		this.finish = finish;
	}



	@Override
	public void run() {
		Process pro;
		System.out.println("爬虫脚本线程启动");
		try {
			pro = Runtime.getRuntime().exec(cmds);
			pro.waitFor();
			this.finish=true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("爬虫脚本线程终止!!!");
			e.printStackTrace();
		}
//		InputStream in = pro.getInputStream();
//		BufferedReader read = new BufferedReader(new InputStreamReader(in));
//		String line = null;
//		StringBuffer messages = new StringBuffer();
//		while((line = read.readLine()) != null) {
//			//System.out.println(new String(line.getBytes("GBK"),"UTF-8"));
//			line = new String(line.getBytes("GBK"),"UTF-8");
//			System.out.println(line);
//			messages.append(line);
//		}
		System.out.println("**********ALL MESSAGE OUTPUT FINISH************");
	}
}
