package com.bitacademy.mysite.vo;

public class BoardVo {
	private Long no;
	private String title;
	private String content;
	private Long hit;
	private String regDate;
	private Long depth;
	// join
	private String writer;
	private Long writerNo;

	
	public Long getWriterNo() {
		return writerNo;
	}

	public void setWriterNo(Long writerNo) {
		this.writerNo = writerNo;
	}

	public Long getDepth() {
		return depth;
	}

	public void setDepth(Long depth) {
		this.depth = depth;
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
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

	public Long getHit() {
		return hit;
	}

	public void setHit(Long hit) {
		this.hit = hit;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", hit=" + hit + ", regDate="
				+ regDate + ", writer=" + writer + "]";
	}
	
	
	
}
