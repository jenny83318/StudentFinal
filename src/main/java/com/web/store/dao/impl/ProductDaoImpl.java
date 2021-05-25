package com.web.store.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.store.dao.ProductDao;
import com.web.store.exception.ProductNotFoundException;
import com.web.store.model.BookBean;
import com.web.store.model.CompanyBean;

@Repository
public class ProductDaoImpl implements ProductDao {
	SessionFactory factory;

	@Autowired
	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookBean> getAllProducts() {
		String hql = "FROM BookBean";
		Session session = null;
		List<BookBean> list = new ArrayList<>();
		session = factory.getCurrentSession();
		list = session.createQuery(hql).getResultList();
		return list;
	}

	@SuppressWarnings("unused")
	@Override
	public void updateStock(int productId, int newQuantity) {
		String hql = "UPDATE BookBean b SET b.stock = :stock WHERE bookId = :id";
		Session session = factory.getCurrentSession();

		int n = session.createQuery(hql).setParameter("stock", newQuantity).setParameter("id", productId)
				.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllCategories() {
		String hql = "SELECT DISTINCT b.category FROM BookBean b";
		Session session = factory.getCurrentSession();
		List<String> list = new ArrayList<>();
		list = session.createQuery(hql).getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookBean> getProductsByCategory(String category) {
		String hql = "FROM BookBean bb WHERE bb.category = :category";
		List<BookBean> list = new ArrayList<>();
		Session session = factory.getCurrentSession();
		list = session.createQuery(hql).setParameter("category", category).getResultList();
		return list;
	}

	@Override
	public BookBean getProductById(int productId) {
		Session session = factory.getCurrentSession();
		BookBean bb = session.get(BookBean.class, productId);
		if (bb == null) {
			throw new ProductNotFoundException("您查詢的書籍不存在", productId);
		}
		return bb;
	}

	@Override
	public void addProduct(BookBean product) {
		Session session = factory.getCurrentSession();
		CompanyBean cb = getCompanyById(product.getCompanyId());
		product.setCompanyBean(cb);
		session.persist(product);
	}

	@Override
	public CompanyBean getCompanyById(int companyId) {
		Session session = factory.getCurrentSession();
		return session.get(CompanyBean.class, companyId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyBean> getCompanyList() {
		Session session = factory.getCurrentSession();
		String hql = "FROM CompanyBean";
		return session.createQuery(hql).getResultList();
	}
}
