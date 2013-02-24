package com.foot.server.dao;

import java.util.ArrayList;

import com.foot.server.beans.Article;

public interface ArticleDAO {

	public void addArticle(Article a);
	
	public ArrayList<Article> getLastArticles(int numberOfArticle);
}
