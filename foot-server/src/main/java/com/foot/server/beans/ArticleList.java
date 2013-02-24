package com.foot.server.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ArticleList")
public class ArticleList {

	List<Article> articles;

	public List<Article> getArticles() {
		return this.articles;
	}

	public void setArticles(List<Article> list) {
		this.articles = list;
	}
}
