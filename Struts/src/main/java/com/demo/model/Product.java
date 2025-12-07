package com.demo.model;

import java.io.Serializable;
import java.util.Date;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String productName;
    private Double price;
    private Integer quantity;
    private Integer sold;
    private String image;
    private Date importDate;
    
    public Product() {
    }
    
    public Product(Integer id, String productName, Double price, Integer quantity, Integer sold, String image, Date importDate) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.sold = sold;
        this.image = image;
        this.importDate = importDate;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public Integer getSold() {
        return sold;
    }
    
    public void setSold(Integer sold) {
        this.sold = sold;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    public Date getImportDate() {
        return importDate;
    }
    
    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }
}
