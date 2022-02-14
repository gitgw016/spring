package net.koreate.common;

import java.io.File;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import net.koreate.common.dao.TaskDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:/context/**/*-context.xml"})
@WebAppConfiguration
public class FileTaskTest {

	@Inject
	ServletContext context;
	
	@Resource(name="uploadPath")
	String uploadPath;
	
	@Inject
	TaskDAO td;
	
	@Test
	public void test1() throws Exception{
		System.out.println(context);
		System.out.println(uploadPath);
		uploadPath = File.separator+uploadPath;
		String realPath = context.getRealPath(uploadPath);
		System.out.println(realPath);
	}
	
	@Test
	public void test2() throws Exception{
		String path = td.getPath();
		System.out.println("test2 : "+path);
		String projectPath = context.getRealPath(File.separator+uploadPath);
		System.out.println("projectPath : "+projectPath);
		String realPath = path.replace('/', File.separatorChar);
		File file = new File(projectPath,realPath);
		System.out.println("file Path :"+file.getPath());
		System.out.println("file Path :"+file.getAbsolutePath());
		if(file.exists()) {
			// 폴더 존재
			// File객체에 지정된 위치의 파일과 디렉토리 정보를 배열로 전달
			File[] files = file.listFiles();
			for(File f : files) {
				System.out.println(f.getName());
			}
		}else {
			System.out.println("폴더 없다");
		}
	}
}
