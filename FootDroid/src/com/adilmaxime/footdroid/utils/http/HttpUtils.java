package com.adilmaxime.footdroid.utils.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.adilmaxime.footdroid.beans.Article;

public class HttpUtils {
	
	private static HttpUtils _instance;
	
	private HttpUtils()
	{}
	
	public static HttpUtils getInstance(){
		if(_instance == null){
			_instance = new HttpUtils();
		}
		return _instance;
	}
	
	public static ArrayList<Article> getArticlesFromServer(){
		ArrayList<Article> returnList = new ArrayList<Article>();
		
		HttpClient httpclient = new DefaultHttpClient();
	    
		String url = "http://192.168.0.10:8080/foot-server/services/article/last/";
		
        //execute the method
        InputStream responseBody = null;
        HttpResponse response;
        try{
        	response = httpclient.execute(new HttpGet(url));
        	StatusLine statusLine = response.getStatusLine();
        	
        	// Get hold of the response entity
            HttpEntity entity = response.getEntity();
            responseBody = entity.getContent();
            
            DocumentBuilderFactory builderFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(responseBody);
            Element rootElement = document.getDocumentElement();
            
            NodeList articleNodes = rootElement.getElementsByTagName("articles");
            
            for(int i = 0 ; i<articleNodes.getLength() ; i++){
            	NodeList articleChildNodes = articleNodes.item(i).getChildNodes();
            	Article a = new Article();
            	for(int j = 0 ; j<articleChildNodes.getLength() ; j++){
            		Node n = articleChildNodes.item(j);
            		if(n.getNodeName().equals("content_site_id")){
						String contentSiteId = n.getTextContent();
						a.setContent_site_id(Integer.parseInt(contentSiteId));
					}
            		if(n.getNodeName().equals("description")){
						String desc = n.getTextContent();
						a.setDescription(desc);
					}
            		if(n.getNodeName().equals("id")){
						String id = n.getTextContent();
						a.setContent_site_id(Integer.parseInt(id));
					}
            		if(n.getNodeName().equals("link")){
						String link = n.getTextContent();
						a.setLink(link);
					}
            		if(n.getNodeName().equals("title")){
						String title = n.getTextContent();
						a.setTitle(title);
					}					
				}
            	returnList.add(a);
            }

        } catch (IOException ioe){
            System.err.println("Unable to connect to '" + url + "'");
        } catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return returnList;
	}
}
