package com.foot.server.dao.impl;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.foot.server.beans.ContentSite;
import com.foot.server.dao.ContentSiteDAO;

@Transactional
public class ContentSiteDAOImpl implements ContentSiteDAO {

	private EntityManager _entityManager;

	@Override
	public ArrayList<ContentSite> getSites() {
		//get last added row
		String s1 = "SELECT * FROM content_site";
		Query query1 = _entityManager.createNativeQuery(s1, ContentSite.class);
		ArrayList<ContentSite> result = (ArrayList<ContentSite>) query1.getResultList();
		
		if(result.isEmpty()){
			return null;
		}
		
		return result;
	}
	
	@PersistenceContext(unitName = "entityManagerFactoryFoot")
	public void setEntityManager(EntityManager entityManager) {
		this._entityManager = entityManager;
	}
}
