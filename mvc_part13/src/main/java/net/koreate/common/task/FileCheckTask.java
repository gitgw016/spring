package net.koreate.common.task;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.koreate.common.dao.TaskDAO;
import net.koreate.common.utils.FileUtil;

@Component
public class FileCheckTask {
	
	@Inject
	TaskDAO dao;
	
	@Inject
	ServletContext context;
	
	@Resource(name="uploadPath")
	String uploadPath;

	// cron : unix 계열의 컴퓨터 운영체제 시간 기반 job scheduler
	// 소프트웨어 환경을 설정하고 관리하는 사람들이 고정된 시간, 날짜, 간격에 주기적으로 작업을 실행 할 수 있도록 스케줄링하기 위해 고안되고 사용
	
	//   필드이름				허용값
	// 초(second)		0 ~ 59 매초(*)
	// 분(minute)		0 ~ 59 매분(*)
	// 시(hours)			0 ~ 24 매시(*)
	// 일(day-of-month)	0 ~ 31 매일(*)
	// 월(month)			0 ~ 12 or JAN ~ DEC 매월(*)
	// 요일(day-of-week)	1 ~ 7 or SUN ~ SAT 요일무관(*)
	// 연도(year)(생략가능)	1970 ~ 2099
	
	// expr
	// * : 모든 값
	// - : 범위 지정 0-15 = 15초만
	// / : 초기값과 증가치 설정  = 0 0/5 14 * * *
	//						요일 상관x 매월 매일 14시 : 5분마다 0초에 실행
	// L : 지정할 수 있는 값중에 마지막 값 = L * * * * *
	//						매월 매일 매시 매분 59초에 실행 , L-3 = 56초에 실행 , L-3:56~59 = 56~59초에
	// W : 주중에 평일 중 가장 가까운 요일
	// # : 몇번째 무슨 요일 2#1 = 월중에 첫번째 주 월요일
	
	/*
	 * @Scheduled(cron="* * * * * *") public void testEvery() throws Exception{
	 * SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	 * System.out.println("testEvery : cron=* * * * * * "+sdf.format(new Date())); }
	 */
	
	@Scheduled(cron="0 30 * * * *")
	public void testHour() throws Exception{
		System.out.println("testHour() : cron=0 30 * * * *"); // 매월 매일 30분 0초에 수행
	}
	
	@Scheduled(cron="0 0 4 * * *")
	//@Scheduled(cron="* * * * * *")
	public void testTask() throws Exception{
		// 어제 날짜로 등록된 첨부파일 목록
		List<String> list = dao.getTrashAttach();
		System.out.println(list);
		
		 // 어제날짜 기준 연/월/일 폴더 경로 생성
		SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
		long time = 1000*60*60*24;
		Date date = new Date(System.currentTimeMillis()-time);
		String realPath = sdf.format(date).replace('/', File.separatorChar);
		System.out.println(realPath);
		
		// uploadPath realPath
		String uploadPath = context.getRealPath(File.separator+this.uploadPath);
		FileUtil.removeList(uploadPath,realPath,list);
	}
}
