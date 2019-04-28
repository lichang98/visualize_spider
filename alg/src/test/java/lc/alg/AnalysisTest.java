/**
 * 
 */
package lc.alg;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

/**
 * @author ¿Ó≥©
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AnalysisTest {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Test
	public void timeDistribute() throws IOException {
		FileWriter writer = new FileWriter(new File("log.txt"));
		List<NewsDocs> newsList = mongoTemplate.findAll(NewsDocs.class);
		for(int i=0;i<newsList.size();i++) {
			Matcher match = Pattern.compile("(\\d{4})(\\D*)(\\d{1,2})").matcher(newsList.get(i).getReleaseTime());
			if(match.find()) {
				writer.write(match.group(1)+"-"+match.group(3));
				writer.write("\n");
			}
		}
		writer.flush();
		writer.close();
	}
}
