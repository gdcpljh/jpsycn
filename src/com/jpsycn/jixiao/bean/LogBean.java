package com.jpsycn.jixiao.bean;
import java.util.Date;


public class LogBean {

	private String content;
	private Date date;
	private Date writeTime;
	private String status;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getWriteTime() {
		return writeTime;
	}
	public void setWriteTime(Date writeTime) {
		this.writeTime = writeTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "LogBean [content=" + content + ", date=" + date
				+ ", writeTime=" + writeTime + ", status=" + status + "]";
	}
	
	
}
