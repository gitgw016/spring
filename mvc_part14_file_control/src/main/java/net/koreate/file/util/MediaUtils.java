package net.koreate.file.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;

// 이미지를 판별할 파일 타입을 저장해놓은 객체
public class MediaUtils {

	private static Map<String,MediaType> mediaMap;
	
	static {
		mediaMap = new HashMap<>();
		mediaMap.put("JPG", MediaType.IMAGE_JPEG);
		mediaMap.put("JPEG", MediaType.IMAGE_JPEG);
		mediaMap.put("GIF", MediaType.IMAGE_GIF);
		mediaMap.put("PNG", MediaType.IMAGE_PNG);
	}
	
	public static MediaType getMediaType(String ext) {
		return mediaMap.get(ext.toUpperCase());
	}
}
