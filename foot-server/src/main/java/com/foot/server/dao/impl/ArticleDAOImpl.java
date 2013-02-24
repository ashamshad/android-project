package com.foot.server.dao.impl;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.foot.server.beans.Article;
import com.foot.server.beans.ContentSite;
import com.foot.server.context.AppContext;
import com.foot.server.dao.ArticleDAO;
import com.foot.server.dao.ContentSiteDAO;

@Transactional
public class ArticleDAOImpl implements ArticleDAO {

	private EntityManager _entityManager;
	
	@Override
	public void addArticle(Article a) {
		_entityManager.persist(a);
	}

	@Override
	public ArrayList<Article> getLastArticles(int numberOfArticlePerSite) {
		ArrayList<Article> returnList = new ArrayList<Article>();
		
		//get sites
		ContentSiteDAO csiteDAO;
		ApplicationContext ctx = AppContext.getApplicationContext();
		csiteDAO = (ContentSiteDAO) ctx.getBean("contentSiteDAO");
		ArrayList<ContentSite> sites = csiteDAO.getSites();
		
		for (ContentSite site : sites) {
			//get last added rows
			String s1 = "SELECT * FROM article WHERE content_site_id="+site.getId()
					+" ORDER BY id ASC LIMIT " + numberOfArticlePerSite;
			Query query1 = _entityManager.createNativeQuery(s1, Article.class);
			ArrayList<Article> result = (ArrayList<Article>) query1.getResultList();
			for (Article a : result) {
				returnList.add(a);
			}
		}
				
		return returnList;
	}

	@PersistenceContext(unitName = "entityManagerFactoryFoot")
	public void setEntityManager(EntityManager entityManager) {
		this._entityManager = entityManager;
	}
}
