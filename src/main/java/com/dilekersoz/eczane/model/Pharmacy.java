package com.dilekersoz.eczane.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="pharmacy")

public class Pharmacy {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String address;
	private String phone;
	
	private boolean isOnDuty; // bugün nöbetçi mi?
	
	 @OneToMany(mappedBy = "pharmacy")
	    private List<Drug> drugs;
	
	public void setId(Long id) {
		this.id=id;
	}
	public Long getId() {
		return id;
	}
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return name;
	}
	public void setAddress(String address) {
		this.address=address;
	}
	public String getAddress() {
		return address;
	}
	public void setPhone(String phone) {
		this.phone=phone;
	}
	public String getPhone() {
		return phone;
	}

	public void setOnDuty(boolean onDuty) {
		this.isOnDuty=onDuty;
	}
	public boolean getOnDuty() {
		return isOnDuty;
	}
}
