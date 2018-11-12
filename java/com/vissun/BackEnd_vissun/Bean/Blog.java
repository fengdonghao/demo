package com.vissun.BackEnd_vissun.Bean;



import javax.persistence.*;
import java.io.Serializable;

/**
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in9:12 2018/5/10
 */
@Entity
public class Blog implements Serializable{
	@Id
	@GeneratedValue
	private Integer id;

	private String author;

	private Integer readNum;

	private Integer commentNum;

	private String title;

	private String time;


	private String content;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name=" content", columnDefinition="CLOB", nullable=true)
	public String getContent() {
		return content;
	}
	public Integer getId() {
		return id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getReadNum() {
		return readNum;
	}

	public void setReadNum(Integer readNum) {
		this.readNum = readNum;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}


	public void setContent(String content) {
		this.content = content;
	}
}
