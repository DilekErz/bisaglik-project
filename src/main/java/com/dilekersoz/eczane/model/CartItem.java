package com.dilekersoz.eczane.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity//veritabanında cart_item adında bir tablo oluşturur.
public class CartItem {

	@Id //bu tablonun birincil anahtarıdır (primary key)
	@GeneratedValue(strategy=GenerationType.IDENTITY) //id değeri otomatik artan olarak veritabanında oluşturulacak (1, 2, 3... gibi)
	private Long id;
	private int quantity; //Kullanıcının sepete bu ilaçtan kaç adet eklediğini tutar. (örnek: 2 kutu parol)
	
	
	@ManyToOne //Bir ilaç (Drug), birden fazla CartItem ile ilişkili olabilir.
	@JoinColumn(name="drug_id") //Veritabanındaki cart_item tablosunda drug_id adında bir sütun oluşturur. Bu sütun Drug tablosundaki id ile bağlantılı olur (foreign key)
	private Drug drug;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	
	public void setUser(User user) {
	    this.user = user;
	}

	public User getUser() {
	    return user;
	}

	

	
	public void setId(Long id) {
		this.id=id;
	}
	public Long getId() {
		return id;
	}
	
	public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {//Sepetteki miktarı almak ve değiştirmek için kullanılır.
        this.quantity = quantity;
    }

    public Drug getDrug() { //Bu CartItem nesnesine bağlı olan Drug nesnesine erişmek veya değiştirmek için kullanılır.
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }
	
}
