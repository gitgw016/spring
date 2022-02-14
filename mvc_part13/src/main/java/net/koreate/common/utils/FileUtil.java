package net.koreate.common.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

// File upload, download, delete
public class FileUtil {

	// 한개의 파일 정보를 넘겨받아 파일을 업로드한 후 업로드된 파일의 정보를 문자열로 반환
	public static String uploadFile(String uploadPath, MultipartFile file) throws Exception{
		String uploadFolder = getFolder();
		String originalName = file.getOriginalFilename();
		// 파일 중복을 막기 위해 원본 파일 이름을 수정
		originalName = originalName.replace("_", " ");
		UUID uid = UUID.randomUUID();
		String savedName = uid.toString().replace("-", "")+"_"+originalName;
		System.out.println("uploadFile savedName : "+savedName);
		
		// resources/upload/yyyy/MM/dd 폴더 생성
		File uploadFolderFile = new File(uploadPath,uploadFolder);
		if(!uploadFolderFile.exists()) {
			uploadFolderFile.mkdirs();
		}
		
		// 파일 업로드
		File upload = new File(uploadPath+File.separator+uploadFolder,savedName);
		byte[] bytes = file.getBytes();
		FileCopyUtils.copy(bytes, upload);
		return makeFileUploadName(uploadPath, uploadFolder, savedName);
	}
	
	// 이미지일 경우 썸네일 이미지 생성 / 생성완료 후 URL브라우저가 인식할 수 있는 경로로 변경 후 반환
	public static String makeFileUploadName(String uploadPath, String uploadFolder, String savedName) throws Exception {
		String ext = savedName.substring(savedName.lastIndexOf(".")+1);
		String fileName = uploadPath+File.separator+uploadFolder+File.separator+savedName;
		if(MediaUtil.getMediaType(ext) != null) {
			System.out.println("IMAGE 들어옴");
			
			File file = new File(fileName);
			// 원본 이미지 파일 정보
			BufferedImage fileImage = ImageIO.read(file);
			BufferedImage copyImage = Scalr.resize(fileImage, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT,100);
			fileName = uploadPath+File.separator+uploadFolder+File.separator+"s_"+savedName;
			file = new File(fileName);
			ImageIO.write(copyImage, ext, file);
		}
		return fileName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}
	
	// 현재 날짜를 기준으로 폴더트리의 정보를 문자열로 반환
	public static String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
		String str = sdf.format(new Date());
		System.out.println(str);
		return str.replace("-", File.separator);
	}

	// 요청받은 위치의 파일 데이터 반환
	public static byte[] displayFile(String uploadPath, String fileName) {
		InputStream in = null;
		String path = uploadPath+(fileName).replace('/', File.separatorChar);
		byte[] bytes = null;
		try {
			in = new FileInputStream(path);
			int size = in.available();
			System.out.println("입력 스트림에 연결된 파일 크기  : "+size+"byte");
			bytes = new byte[size];
			for(int i=0; i<bytes.length; i++) {
				bytes[i] = (byte)in.read();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				in.close();
			} catch (IOException e) {}
		}
		return bytes;
	}
	// 요청받은 파일의 header 정보 반환
	// image일 경우 - 원본 이미지 정보
	// 일반 파일인 경우 - 다운로드 정보
	public static HttpHeaders getHeader(String fileName) throws Exception {
		HttpHeaders header = new HttpHeaders();
		String ext = fileName.substring(fileName.lastIndexOf(".")+1);
		MediaType mType = MediaUtil.getMediaType(ext);
		if(mType != null) {
			System.out.println("IMAGE 간다");
			header.setContentType(mType);
		}else {
			// 다운로드
			fileName = fileName.substring(fileName.lastIndexOf("_")+1);
			fileName = new String(fileName.getBytes("utf-8"),"ISO-8859-1");
			// application/octet-stream : 해석할 수 없는 2진 데이터
			// 브라우저는 이 컨텐츠 타입을 전달 받으면 다운로드 후 기능이 수행될 수 있도록 함
			header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			header.add("Content-disposition", "attachment;fileName=\""+fileName+"\"");
		}
		
		return header;
	}

	// 첨부파일 삭제 처리
	public static boolean deleteFile(String uploadPath, String fileName) {
		String ext = fileName.substring(fileName.lastIndexOf(".")+1);
		// 일반 파일, 썸네일 이미지 삭제
		boolean isDeleted = new File(uploadPath+(fileName).replace('/', File.separatorChar)).delete();
		if(MediaUtil.getMediaType(ext) != null) {
			// 원본 이미지도 삭제
			String name = fileName.replace("s_", "");
			name = name.replace('/', File.separatorChar);
			isDeleted = new File(uploadPath+name).delete();
		}
		return isDeleted;
	}

	// task에서 테이블에 존재하지 않는 파일 목록 삭제
	public static void removeList(String uploadPath, String realPath, List<String> list) {
		// 삭제할 파일 목록
		List<String> removeList = new ArrayList<>();
		
		// 비교할 폴더 정보
		File file = new File(uploadPath,realPath);
		System.out.println(file.getPath());
		if(file.exists()) {
			System.out.println("폴더 있다");
			List<File> files = Arrays.asList(file.listFiles());
			for(File f : files) {
				String fileName = f.getName();
				realPath = realPath.replace(File.separatorChar, '/');
				String removeFilePath = realPath+fileName;
				String thumbnail = realPath+"s_"+fileName;
				System.out.println("removeFilePath : " + removeFilePath);
				System.out.println("thumbnail : "+thumbnail);
				
				if(!list.contains(removeFilePath) && !list.contains(thumbnail)) {
					removeList.add(removeFilePath);
				}
			}
			// 삭제할 파일 목록 확인
			for(String s : removeList) {
				System.out.println("removeFile : "+s);
				new File(uploadPath+(s).replace('/', File.separatorChar)).delete();
			}
		}
	}
}
