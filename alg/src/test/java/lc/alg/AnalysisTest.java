/**
 * 
 */
package lc.alg;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import lc.alg.entity.NewsDocs;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * @author ¿Ó≥©
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AnalysisTest {

	@Autowired
	private MongoTemplate mongoTemplate;
	
//	@Test
//	public void timeDistribute() throws IOException {
//		FileWriter writer = new FileWriter(new File("log.txt"));
//		List<NewsDocs> newsList = mongoTemplate.findAll(NewsDocs.class);
//		for(int i=0;i<newsList.size();i++) {
//			Matcher match = Pattern.compile("(\\d{4})(\\D*)(\\d{1,2})").matcher(newsList.get(i).getReleaseTime());
//			if(match.find()) {
//				writer.write(match.group(1)+"-"+match.group(3));
//				writer.write("\n");
//			}
//		}
//		writer.flush();
//		writer.close();
//	}
	
	@Test
	public void zipTest() throws IOException, ZipException {
		ZipParameters zipParameters = new ZipParameters();
		zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		zipParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		File file = new File(Paths.get(System.getProperty("user.dir"),"src","main","resources","static","news_txt.zip").toString());
		if(file.exists()) {
			file.delete();
		}
		ZipFile zipFile=new ZipFile(Paths.get(System.getProperty("user.dir"),"src","main","resources","static","news_txt.zip").toString());
		zipFile.addFolder(Paths.get(System.getProperty("user.dir"),"src","main","resources","static","text").toString(), zipParameters);
		
	}
}
