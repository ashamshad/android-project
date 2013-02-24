package com.foot.server.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.foot.server.beans.Article;
import com.foot.server.beans.ContentSite;
import com.foot.server.context.AppContext;
import com.foot.server.dao.ArticleDAO;
import com.foot.server.dao.ContentSiteDAO;
 
@Service("contentService")
@Path("/content/")
public class ContentService {
 
	@GET
	@Path("/update/")
	public void updateContent()
	{
		//create a singular HttpClient object
        HttpClient client = new HttpClient();

        //establish a connection within 5 seconds
        client.getHttpConnectionManager().
            getParams().setConnectionTimeout(5000);

        ContentSiteDAO contentSiteDAO;
		ApplicationContext ctx = AppContext.getApplicationContext();
		contentSiteDAO = (ContentSiteDAO) ctx.getBean("contentSiteDAO");
		        
		for (ContentSite contentSite : contentSiteDAO.getSites()) {
			processContentSite(client, contentSite);	
		}
        
	}

	private void processContentSite(HttpClient client, ContentSite contentSite) {

		ArticleDAO articleDAO;
		ApplicationContext ctx = AppContext.getApplicationContext();
		articleDAO = (ArticleDAO) ctx.getBean("articleDAO");
		
        //create a method object
        GetMethod method = new GetMethod(contentSite.getUrl());
        method.setFollowRedirects(true);

        //execute the method
        InputStream responseBody = null;
        try{
            client.executeMethod(method);
            responseBody = method.getResponseBodyAsStream();
            
            DocumentBuilderFactory builderFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(responseBody);
            Element rootElement = document.getDocumentElement();
            
            NodeList items = rootElement.getElementsByTagName("item");
            ArrayList<Article> articles = new ArrayList<Article>();
            for(int i = 0 ; i<items.getLength() ; i++){
            	NodeList itemChildNodes = items.item(i).getChildNodes();
            	Article a = new Article();
            	for(int j = 0 ; j<itemChildNodes.getLength() ; j++){
            		Node n = itemChildNodes.item(j);
					if(n.getNodeName().equals("title")){
						String title = n.getTextContent();
						a.setTitle(title);
					}
					if(n.getNodeName().equals("link")){
						String link = n.getTextContent();
						a.setLink(link);
					}
					if(n.getNodeName().equals("description")){
						String desc = n.getTextContent();
						a.setDescription(desc);
					}
				}
            	a.setContent_site_id(contentSite.getId());
            	articleDAO.addArticle(a);
            }
            
            //Create a "parser factory" for creating SAX parsers
//            SAXParserFactory spfac = SAXParserFactory.newInstance();
//
//            //Now use the parser factory to create a SAXParser object
//            SAXParser sp = spfac.newSAXParser();
//
//            //Create an instance of this class; it defines all the handler methods
//            CustomHandler handler = new CustomHandler();
//
//            //Finally, tell the parser to parse the input and notify the handler
//            sp.parse(responseBody, handler);
//           
            //handler.readList();
//            for (Article article : handler.getArticles()) {
//            	ArticleDAO articleDAO;
//        		ApplicationContext ctx = AppContext.getApplicationContext();
//        		articleDAO = (ArticleDAO) ctx.getBean("articleDAO");
//        		article.setContent_site_id(contentSite.getId());
//        		articleDAO.addArticle(article);
//			}


        } catch (HttpException he) {
            System.err.println("Http error connecting to '" + contentSite + "'");
            System.err.println(he.getMessage());
        } catch (IOException ioe){
            System.err.println("Unable to connect to '" + contentSite + "'");
        } catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}