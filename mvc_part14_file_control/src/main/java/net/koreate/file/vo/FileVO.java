package net.koreate.file.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class FileVO {

	private String auth;
	private String content;
	private List<MultipartFile> files;
	private MultipartFile file;
	
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<MultipartFile> getFiles() {
		return files;
	}
	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	@Override
	public String toString() {
		return "FileVO [auth=" + auth + ", content=" + content + ", files=" + files + ", file=" + file + "]";
	}
	
	
}
