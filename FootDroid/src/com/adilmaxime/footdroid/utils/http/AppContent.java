package com.adilmaxime.footdroid.utils.http;

import java.util.ArrayList;

import com.adilmaxime.footdroid.beans.Article;

public class AppContent {
	
	private ArrayList<Article> articles = new ArrayList<Article>();
	private static AppContent _instance;
	
	private AppContent()
	{}
	
	public static AppContent getInstance(){
		if(_instance == null){
			_instance = new AppContent();
		}
		return _instance;
	}
	
	public ArrayList<Article> getArticles(){
		return articles;
	}
	
	public void setArticles(ArrayList<Article> list){
		articles = list;
	}
	
	public Article findArticleById(Long id){
		for (Article a : articles) {
			if(a.getId() == id){
				return a;
			}
		}
		return null;
	}
}
