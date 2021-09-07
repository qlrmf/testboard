package attach.model;

public class AttachVo {
	private int num;
	private String fileName;
	private int bNum;
	private long fileSize;
	private String contentType;
	
	public AttachVo() {}
	
	public AttachVo(int num, String fileName, int bNum, long fileSize, String contentType) {
		super();
		this.num = num;
		this.fileName = fileName;
		this.bNum = bNum;
		this.fileSize = fileSize;
		this.contentType = contentType;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getbNum() {
		return bNum;
	}
	public void setbNum(int bNum) {
		this.bNum = bNum;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public String toString() {
		return "AttachVo [num=" + num + ", fileName=" + fileName + ", bNum=" + bNum + ", fileSize=" + fileSize
				+ ", contentType=" + contentType + "]";
	}
	
	
}
