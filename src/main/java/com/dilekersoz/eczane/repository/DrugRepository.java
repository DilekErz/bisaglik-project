package com.dilekersoz.eczane.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dilekersoz.eczane.model.Drug;

//DrugRepository.java (Veri Erişim Katmanı - DAO)

public interface DrugRepository extends JpaRepository <Drug, Long> { //Spring Data JPA arayüzünü kullanarak otomatik CRUD işlemlerini sağlar.
	
	//Eczaneye göre ilaçlar:
List<Drug> findByPharmacyId(Long pharmacyId); //Bu özel metod, verilen eczane ID'sine göre o eczaneye ait ilaçları getirir.

//İsim ya da barkod numarasına göre arama:
List<Drug> findByNameContainingIgnoreCaseOrBarcodeNumberContainingIgnoreCase(String name, String barcode);

}//Burada Drug tablosu üzerinde çalışılır ve pharmacy_id alanı üzerinden filtre yapılır.
