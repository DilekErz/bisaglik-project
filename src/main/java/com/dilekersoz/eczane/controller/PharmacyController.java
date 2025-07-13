package com.dilekersoz.eczane.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dilekersoz.eczane.model.Pharmacy;
import com.dilekersoz.eczane.repository.PharmacyRepository;

@Controller
@RequestMapping("/pharmacy")

public class PharmacyController {
@Autowired
private PharmacyRepository pharmacyRepository;
//Spring’e diyoruz ki: "Sen bu repository’yi otomatik olarak enjekte et."Böylece veritabanı işlemlerini kullanabiliriz.

//TÜM ECZANELERİ LİSTELE
@GetMapping("/list")
public String listPharmacies(Model model) {
	
	List<Pharmacy> pharmacies=pharmacyRepository.findAll();
	model.addAttribute("pharmacies",pharmacies);
	return "pharmacy_list"; // Thymeleaf template: pharmacy_list.html
	
	/* /pharmacy/list adresine gelindiğinde:
findAll() metodu tüm eczaneleri veritabanından çeker.
model.addAttribute(...) ile HTML sayfasına gönderilir.
pharmacy_list.html adlı sayfa döndürülür (Thymeleaf).*/
}

//EKLEME FORMUNU GÖNDER
@GetMapping("/add")
public String showAddForm(Model model) {
	model.addAttribute("pharmacy",new Pharmacy());
	return "add_pharmacy";
	
	/* /pharmacy/add adresi çağrıldığında:
Yeni bir Pharmacy nesnesi oluşturulur (form boş başlar).
add_pharmacy.html adlı form sayfası gösterilir.*/
}

//YENİ ECZANEYİ KAYDET
@PostMapping("/add")
public String addPharmacy(@ModelAttribute Pharmacy pharmacy) {
	pharmacyRepository.save(pharmacy);
	return "redirect:/pharmacy/list";
	
	/*add_pharmacy.html formundan gelen bilgilerle:
@ModelAttribute formdan gelen input'ları Pharmacy nesnesine bağlar.
save(...) ile veritabanına kaydedilir.
İşlem sonrası /pharmacy/list sayfasına yönlendirilir.*/
	
}

//Güncelleme formunu göster
@GetMapping("/update/{id}")
public String showUpdateForm(@PathVariable Long id, Model model) {
	Optional<Pharmacy> pharmacy=pharmacyRepository.findById(id);
	if(pharmacy.isPresent()) {
		model.addAttribute("pharmacy",pharmacy.get());
		return "update_pharmacy";  // Thymeleaf template: update_pharmacy.html
	}
	else {
		return "redirect:/pharmacy/list";
	}
	
	
	/*/pharmacy/update/3 gibi bir istek geldiğinde:
findById(id) ile ilgili eczane bulunur.
Eğer varsa form sayfasına gönderilir (update_pharmacy.html).
Yoksa listeye yönlendirilir.*/
}

//GÜNCELLENEN ECZANEYİ KAYDET
@PostMapping("/update")
public String updatePharmacy(@ModelAttribute Pharmacy pharmacy) {
pharmacyRepository.save(pharmacy);
return "redirect:/pharmacy/list";

/*Güncelleme formu submit edildiğinde:
Pharmacy nesnesi otomatik olarak alınır.
save(...) metodu günceller (id varsa Spring otomatik update eder).
Listeye döner.*/
}

//ECZANEYİ SİLL
@GetMapping("/delete/{id}")
public String deletePharmacy(@PathVariable Long id) {
	pharmacyRepository.deleteById(id);
	return "redirect:/pharmacy/list";
	
	/*/pharmacy/delete/3 gibi bir istek geldiğinde:
İlgili eczane silinir.
Listeye yönlendirilir.

*/
	
}
//GOOGLE MAP İÇİN

@GetMapping("/map")
public String showPharmacy(Model model) {
	List <Pharmacy> pharmacies=pharmacyRepository.findAll();
	model.addAttribute("pharmacies",pharmacies);
	return "pharmacy_map";
}

@GetMapping("/search")
public String searchPharmacies(@RequestParam("keyword") String keyword,Model model) {
	List<Pharmacy> result=pharmacyRepository.findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(keyword, keyword);
	model.addAttribute("pharmacies",result);
	return "pharmacy_list";
}


}
