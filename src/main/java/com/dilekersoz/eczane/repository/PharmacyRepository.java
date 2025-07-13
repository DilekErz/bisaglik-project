package com.dilekersoz.eczane.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dilekersoz.eczane.model.Pharmacy;

/*PharmacyRepository sınıfını oluşturalım. Bu sınıf, eczane verilerine erişmek için Spring Data JPA’nın hazır metotlarını kullanmamızı sağlar. CRUD işlemleri için ek kod yazmak zorunda kalmayız.

*/
@Repository

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long>{
	
	List<Pharmacy> findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(String name, String address); // eczaneye göre arama kutusu için
	//Bu metot, isim ya da adres içinde geçen kelimeleri filtreler (büyük-küçük harf duyarsız).
}

