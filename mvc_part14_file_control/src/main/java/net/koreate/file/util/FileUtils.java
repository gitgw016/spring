package net.koreate.file.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.util.FileCopyUtils;

public class FileUtils {

	// 파일 업로드 및 업로드된 파일 이름 반환
	public static String uploadFile(String originalName, String uploadPath, byte[] fileData) throws Exception{
		String uploadFileName = "";
		UUID uid = UUID.randomUUID();
		String savedName = uid.toString().replace("-","");
		savedName+="_"+originalName.replace("_"," ");
		// 날짜 형식의 저장할 경로 생성 및 리턴
		String datePath = calcPath(uploadPath);
		File file = new File(uploadPath+datePath,savedName);
		FileCopyUtils.copy(fileData, file);
		
		// 업로드된 파일의 확장자
		String formatName = originalName.substring(originalName.lastIndexOf(".")+1);
		if(MediaUtils.getMediaType(formatName) != null) {
			// 이미지 파일이면 썸네일 파일 생성
			uploadFileName = makeThumbnail(uploadPath, datePath, savedName);
		} else {
			// 일반 파일이면 운영체제 경로에서 URL 경로로 구분자 변경
			uploadFileName = makeFileName(uploadPath, datePath, savedName);
		}
		System.out.println(savedName);
		System.out.println("uploadFileName : "+uploadFileName);
		return uploadFileName;
	}
	
	// 썸네일 이미지 생성
	private static String makeThumbnail(String uploadPath, String path, String savedName) throws Exception{
		String name = "";
		File file = new File(uploadPath+path,savedName);
		// 지정된 위치의 이미지 정보를 BufferedImage 타입으로 반환
		BufferedImage image = ImageIO.read(file);
		// scalr 객체를 이용해서 원본 이미지 복제
		// 복제시 크기 지정								// 고정 크기에 따른 상대 크기 (자동비율)	높이 100px 고정
		BufferedImage sourceImage = Scalr.resize(image, Scalr.Method.AUTOMATIC,Scalr.Mode.FIT_TO_HEIGHT,100);
		String thumbnailImage = uploadPath+path+File.separator+"s_"+savedName;
		File file1 = new File(thumbnailImage);
		// 확장자 이름
		String formatName = savedName.substring(savedName.lastIndexOf(".")+1);
		// BufferedImage 출력
		ImageIO.write(sourceImage, formatName, file1);
		name = thumbnailImage.substring(uploadPath.length()).replace(File.separatorChar, '/');
		return name;
	}
	
	// 운영체제의 구분자를 브라우저가 인식할 수 있는 경로로 변경하여 반환
	public static String makeFileName(String uploadPath, String path, String savedName){
		String fileName = "";
		String name = uploadPath+path+File.separator+savedName;
		fileName = name.substring(uploadPath.length()).replace(File.separatorChar, '/');
		System.out.println("fileName : "+fileName);
		return fileName;
	}
	
	// 날짜에 맞게 폴더경로 생성
	// \\upload\\2022\\02\\07
	public static String calcPath(String uploadPath) {
		String datePath = "";
		Calendar cal = Calendar.getInstance();
		String yearPath = File.separator+cal.get(Calendar.YEAR);
		String monthPath = yearPath + File.separator+new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1);
		datePath = monthPath + File.separator+new DecimalFormat("00").format(cal.get(Calendar.DATE));
		mkDir(uploadPath,yearPath,monthPath,datePath);
		return datePath;
	}
	
	// 날짜 형식의 디렉토리 생성
	public static void mkDir(String uploadPath, String... path) {
		
		if(new File(uploadPath+path[path.length-1]).exists()) {
			return;
		}
		
		for(String p : path) {
			String mkDir = uploadPath+p;
			File file = new File(mkDir);
			if(!file.exists()) {
				file.mkdir();
			}
		}
	}
	// 파일 삭제 요청 처리
	public static boolean deleteFile(String uploadPath, String fileName) {
		boolean isDeleted = false;
		// 이미지일때는 썸네일도 삭제
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		if(MediaUtils.getMediaType(formatName) != null) {
			// 이미지 파일
			String name = fileName.replace("s_", "");
			isDeleted = new File(uploadPath+(name).replace('/', File.separatorChar)).delete();
		}
		isDeleted = new File(uploadPath+(fileName).replace('/', File.separatorChar)).delete();
		return isDeleted;
	}
}
