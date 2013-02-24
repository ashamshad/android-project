package com.foot.server.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.foot.server.beans.ArticleList;
import com.foot.server.context.AppContext;
import com.foot.server.dao.ArticleDAO;
 
@Service("articleService")
@Path("/article/")
public class ArticeService {
 
	@GET
	@Path("/last/")
	@Produces("application/xml")
	public ArticleList getLastArticles()
	{
		ArticleDAO articleDAO;
		ApplicationContext ctx = AppContext.getApplicationContext();
		articleDAO = (ArticleDAO) ctx.getBean("articleDAO");
		ArticleList list = new ArticleList();
		list.setArticles(articleDAO.getLastArticles(5));
		return list; 
	}
}