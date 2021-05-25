package com.web.store.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.web.store.dao.ProductDao;
import com.web.store.model.BookBean;
import com.web.store.model.CompanyBean;
import com.web.store.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	ProductDao dao;
    
	@Autowired
	public void setDao(ProductDao dao) {
		this.dao = dao;
	}
    
    @Transactional
	@Override
	public List<BookBean> getAllProducts() {
		return dao.getAllProducts();
	}
    
    @Transactional
	@Override
	public void updateAllStocks() {
    	List<BookBean> allProducts = dao.getAllProducts();
    	for(BookBean bb : allProducts) {
    		if (bb.getStock() != null && bb.getStock() < 50) {
    			dao.updateStock(bb.getBookId(), bb.getStock() + 50);
    		}
    	}
	}
    
    @Transactional
	@Override
	public List<String> getAllCategories() {
		return dao.getAllCategories();
	}
    
    @Transactional
	@Override
	public List<BookBean> getProductsByCategory(String category) {
		return dao.getProductsByCategory(category);
	}
    
    @Transactional
	@Override
	public BookBean getProductById(int productId) {
		return dao.getProductById(productId);
	}
    @Transactional
	@Override
	public void addProduct(BookBean product) {
		dao.addProduct(product);
	}
    @Transactional
	@Override
	public CompanyBean getCompanyById(int companyId) {
		
		return dao.getCompanyById(companyId);
	}
    @Transactional
	@Override
	public List<CompanyBean> getCompanyList() {
		return dao.getCompanyList();
	}
}




