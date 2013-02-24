package com.foot.server.xml;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.foot.server.beans.Article;

public class CustomHandler extends DefaultHandler{

	private String temp;
	private Article article;
	private ArrayList<Article> articles = new ArrayList<Article>();
	
	public CustomHandler(){
		super();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(qName.equalsIgnoreCase("item")){
			article = new Article();
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(article != null){
			if (qName.equalsIgnoreCase("item")) {
	            // add it to the list
	            articles.add(article);
	            article = null;
			}else if(qName.equalsIgnoreCase("title")) {
				article.setTitle(temp);
			}else if(qName.equalsIgnoreCase("link")) {
				article.setLink(temp);
			}else if(qName.equalsIgnoreCase("description")) {
				article.setDescription(temp);
			}
		}
	}
	
	@Override
	public void characters(char[] buffer, int start, int length) throws SAXException {
		temp = new String(buffer, start, length);
	}

	public ArrayList<Article> getArticles(){
		return this.articles;
	}
}
