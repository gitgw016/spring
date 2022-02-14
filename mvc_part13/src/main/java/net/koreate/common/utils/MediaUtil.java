package net.koreate.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;

public class MediaUtil {
	
	private static Map<String,MediaType> mediaType;
	
	// class 설계정보가 method 영역에 저장된 직후 실행
	static {
		mediaType = new HashMap<>();
		mediaType.put("JPEG", MediaType.IMAGE_JPEG);
		mediaType.put("JPG", MediaType.IMAGE_JPEG);
		mediaType.put("PNG", MediaType.IMAGE_PNG);
		mediaType.put("GIF", MediaType.IMAGE_GIF);
	}
	
	// 파일 확장명을 전달받아 일치하는 미디어 타입을 반환
	public static MediaType getMediaType(String ext) {
		return mediaType.get(ext.toUpperCase());
	}
}
