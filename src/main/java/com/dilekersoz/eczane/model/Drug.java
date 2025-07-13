package com.dilekersoz.eczane.model;

import org.hibernate.internal.build.AllowSysOut;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity //Bu sınıfın veritabanında bir tablo olduğunu belirtir(örneğin: drug tablosu).
public class Drug {
	
	@Id //Bu alanın (id) birincil anahtar (primary key) olduğunu belirtir.
	@GeneratedValue(strategy=GenerationType.IDENTITY) // id otomatik olarak artar (1,2,3,... şeklinde).
	private Long id;
	
	private String name;
	private String barcodeNumber;
	private int stock;
	private double price;
	
	@ManyToOne //Her ilaç bir eczaneye bağlıdır ama bir eczanede birden fazla ilaç olabilir.
	@JoinColumn(name="pharmacy_id") //Bu ilişkiyi veritabanında pharmacy_id adlı sütun üzerinden kurar.
	private Pharmacy pharmacy;
	//Drug → Pharmacy tablosuna bağlıdır. Yani her ilaç bir eczaneye aittir.


	
	public void setId(Long id) {
		this.id=id;
	}
	public Long getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	public String getName(){
		return name;
	}
	
	public void setBarcodeNumber(String barcodeNumber) {
		this.barcodeNumber=barcodeNumber;
	}
	public String getBarcodeNumber() {
		return barcodeNumber;
	}
	public void setStock(int stock) {
		this.stock=stock;
	}
	public int getStock() {
		return stock;
	}
	public void setPharmacy(Pharmacy pharmacy) {
	    this.pharmacy = pharmacy;
	}
	public Pharmacy getPharmacy() {
	    return pharmacy;
	}
	public void setPrice(double price) {
		this.price=price;
	}
	public double getPrice() {
		return price; 
	}
	

}
