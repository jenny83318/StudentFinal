package com.web.store.model;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
// 本類別封裝單筆書籍資料

import org.springframework.web.multipart.MultipartFile;
@Entity
@Table(name="Book")
public class BookBean implements Serializable {
	@Override
	public String toString() {
		return "BookBean [bookId=" + bookId + ", title=" + title + ", author=" + author + ", price=" + price
				+ ", discount=" + discount + ", fileName=" + fileName + ", bookNo=" + bookNo + ", category=" + category
				+ ", stock=" + stock + ", companyId=" + companyId + ", companyBean=" + companyBean + ", priceStr="
				+ priceStr + "]";
	}
	private static final long serialVersionUID = 1L;
	private Integer 	bookId ;
	private String  	title;
	private String  	author;
	private Double  	price;
	private Double  	discount;
	private Blob    	coverImage;	
	private String  	fileName;
	private String  	bookNo;
	private String  	discountStr;
	private String  	category;
	private Integer  	stock;
	private Integer  	companyId;
	private  MultipartFile productImage;
	//private String  	companyName;
    private CompanyBean companyBean;
	
	public BookBean(Integer bookID, String title, String author, 
			double price, double discount, String fileName, 
			String bookNo, Blob coverImage, int companyId, String category) {
		this.bookId = bookID;
		this.title = title;
		this.author = author;
		this.price = price;
		this.discount = discount;
		this.fileName = fileName;
		this.bookNo = bookNo;
		this.coverImage = coverImage;
		this.companyId = companyId;
		this.category = category;
		this.stock = 0;
	}
	
	public BookBean(Integer bookID, String title, String author, 
			Double price, Double discount, String fileName, 
			String bookNo, Blob coverImage, Integer stock, String category, CompanyBean companyBean) {
		this.bookId = bookID;
		this.title = title;
		this.author = author;
		this.price = price;
		this.discount = discount;
		this.fileName = fileName;
		this.bookNo = bookNo;
		this.coverImage = coverImage;
		this.companyBean = companyBean;
		this.category = category;
		this.stock = stock;
	}
	
	public BookBean() {
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getBookId() {   // bookId
		return bookId;
	}
	public void setBookId(Integer bookID) {
		this.bookId = bookID;
	}
	
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="FK_CompanyBean_Id") 
	public CompanyBean getCompanyBean() {
		return companyBean;
	}

	public void setCompanyBean(CompanyBean companyBean) {
		this.companyBean = companyBean;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	private String  priceStr = null;
	@Transient
	public String getPriceStr() {
		return priceStr;
	}

	public void setPriceStr(String priceStr) {
		this.priceStr = priceStr;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
		if (priceStr == null) {
			priceStr = String.valueOf(price);
		}
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {   //0.8, 0.75
		if (discount  == null) {
			this.discount = 1.0;
			discountStr = "";
			return;
		}
		this.discount = discount;
		
		if (discount == 1) {
			discountStr = "";
		} else {
			int dnt = (int)(discount * 100);
			if (dnt % 10 == 0) {
				discountStr = (dnt / 10) + "折";
			} else {
				discountStr = " "  + dnt + "折";
			} 
			
		}
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getBookNo() {
		return bookNo;
	}
	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}
	@Transient
	public String getDiscountStr() {
		return discountStr;
	}	
	public Blob getCoverImage() {       // Binary Large OBject
		return coverImage;              // Character Large OBject
	}
	public void setCoverImage(Blob coverImage) {
		this.coverImage = coverImage;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
	@Transient
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
//	public String getCompanyName() {
//		return companyName;
//	}
//
//	public void setCompanyName(String companyName) {
//		this.companyName = companyName;
//	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	
	
	@Transient
	public MultipartFile getProductImage() {
		return productImage;
	}

	public void setProductImage(MultipartFile productImage) {
		this.productImage = productImage;
	}
}
