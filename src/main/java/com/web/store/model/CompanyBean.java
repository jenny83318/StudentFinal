package com.web.store.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
// 本類別封裝單筆出版社資料
@Entity
@Table(name="BookCompany")
public class CompanyBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id ;
	private String  name;
	private String  address;
	private String  url;
	private Set<BookBean> books = new LinkedHashSet<>();

	
	public CompanyBean(Integer id, String name, String address, String url) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.url = url;
	}
	public CompanyBean() {
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {   // id
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@OneToMany(mappedBy="companyBean")
	public Set<BookBean> getBooks() {
		return books;
	}
	public void setBooks(Set<BookBean> books) {
		this.books = books;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}	
	
}