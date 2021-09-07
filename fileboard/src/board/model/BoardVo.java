package board.model;

import java.sql.Timestamp;

public class BoardVo {
	private int num;
	private String writer;
	private String title;
	private String content;
	private int readCount;
	private Timestamp regdate;
	private Timestamp moddate;
	private int depth;
	private int ref;
	private String password;
	
	public BoardVo() {}
	
	public BoardVo(int num, String writer, String title, String content, int readCount, 
			Timestamp regdate, Timestamp moddate, int depth, int ref, String password) {
		super();
		this.num = num;
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.readCount = readCount;
		this.regdate = regdate;
		this.moddate = moddate;
		this.depth = depth;
		this.ref = ref;
		this.password = password;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public Timestamp getRegdate() {
		return regdate;
	}
	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}
	public Timestamp getModdate() {
		return moddate;
	}
	public void setModdate(Timestamp moddate) {
		this.moddate = moddate;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public int getRef() {
		return ref;
	}
	public void setRef(int ref) {
		this.ref = ref;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "BoardVo [num=" + num + ", writer=" + writer + ", title=" + title + ", content=" + content
				+ ", readCount=" + readCount + ", regdate=" + regdate + ", moddate=" + moddate 
				+ ", depth=" + depth + ", ref=" + ref + ", password=" + password + "]";
	}
	
	
}
