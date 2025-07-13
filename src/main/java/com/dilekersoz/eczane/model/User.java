package com.dilekersoz.eczane.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;


@Entity /*veritabanı tablosu gibi tanıyacak*/
@Table(name = "user")// sql de tablo ismi sıkıntı vermesin diye ekledim
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	/*Bu, id'nin otomatik artan olmasını sağlar (tüm veritabanlarında yaygındır).*/
	
	private Long id; /*field özellik olarak yazdım*/
	private String username;
	private String password;
	private String role;
	
	  // 🔹 JPA için zorunlu olan boş constructor
    public User() {
    }
	
	public User(Long id, String username, String password , String role) {
		this.id=id;
		this.username=username;
		this.password=password;
		this.role=role;
	}
   public void setId(Long id) {
	   this.id=id;
   }
	public Long getId() {
		return id;
	}
	public void setUsername(String username) {
		this.username=username;
	}
	public String getUsername() {
		return username;
	}
	public void setPassword(String password) {
		this.password=password;
	}
	public String getPassword() {
		return password;
	}
	public void setRole(String role) {
		this.role=role;
	}
	public String getRole() {
		return role;
	}
}
