package com.dilekersoz.eczane.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dilekersoz.eczane.model.CartItem;
import com.dilekersoz.eczane.model.Drug;
import com.dilekersoz.eczane.repository.DrugRepository;
import com.dilekersoz.eczane.repository.PharmacyRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/drugs") //Bu sınıf, /drug/... ile başlayan URL’leri yönetir.Örneğin: /drug/pharmacy/3 → ID’si 3 olan eczanenin ilaçlarını göster.
public class DrugController {

	@Autowired
	private DrugRepository drugRepository;
	
	//Spring bu repository’leri otomatik enjekte eder (veri çekip kaydetmek için).
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	/*@GetMapping
	public String listAllDrugs(Model model) {
		List<Drug> drugs =drugRepository.findAll();
		model.addAttribute("drugs",drugs);
		return "drug_list";}*/
				
	
	@GetMapping("/search")
	public String searchDrugs(@RequestParam("keyword") String keyword, Model model) {
		List<Drug> result = drugRepository.findByNameContainingIgnoreCaseOrBarcodeNumberContainingIgnoreCase(keyword, keyword);
		model.addAttribute("drugs",result);
		return "drug_list";
	}
	
	
	@GetMapping("/pharmacy/{id}") //URL'den veri alma isteğidir (GET). {id} → URL'den alınan eczane ID’sidir.
	public String getDrugsByPharmacy(@PathVariable("id")Long pharmacyId, Model model) {//Model model: Verileri HTML sayfasına göndermeye yarar.
		List<Drug> drugs=drugRepository.findByPharmacyId(pharmacyId); // Veritabanından pharmacyId ile ilgili tüm ilaçlar çekilir.
		model.addAttribute("drugs",drugs); //model.addAttribute("drugs", drugs) sayesinde bu veriler HTML'e gönderilir.
		return "drug_list"; //Son olarak drug_list.html isimli sayfa çalıştırılır.
	}
	
	@GetMapping("/add")
	public String showAddForm(Model model) {
	    model.addAttribute("drug", new Drug());
	    model.addAttribute("pharmacies", pharmacyRepository.findAll()); // eczane listesi dropdown için
	    return "add_drug"; // Form HTML sayfası
	}

	@PostMapping("/add")
	public String addDrug(@ModelAttribute Drug drug) {
	    drugRepository.save(drug);
	    Long pharmacyId = drug.getPharmacy().getId(); // İlacın ait olduğu eczanenin ID’si
	    return "redirect:/drugs/pharmacy/" + pharmacyId;
	} // ekledikten sonra tüm ilaç listesine yönlendir
	
	
	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
	    Drug drug = drugRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Geçersiz ilaç ID: " + id));
	    model.addAttribute("drug", drug);
	    model.addAttribute("pharmacies", pharmacyRepository.findAll());
	    return "update_drug";
	}

	@PostMapping("/update/{id}")
	public String updateDrug(@PathVariable("id") Long id, @ModelAttribute Drug updatedDrug) {
	    updatedDrug.setId(id); // ID'yi elle set ediyoruz çünkü formdan gelmiyor olabilir
	    drugRepository.save(updatedDrug);
	    return "redirect:/drugs";
	}
	
	@GetMapping("/pharmacy/{id}/drugs")
	public String listDrugsByPharmacy(@PathVariable("id") Long pharmacyId, Model model) {
	    List<Drug> drugs = drugRepository.findByPharmacyId(pharmacyId);
	    model.addAttribute("drugs", drugs);
	    return "drug_list";
	}


	@GetMapping
	public String listAllDrugs(Model model, HttpSession session) {
	    List<Drug> drugs = drugRepository.findAll();
	    model.addAttribute("drugs", drugs);

	    // Sepetteki ürün sayısını modele ekle
	    List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
	    int cartSize = (cart != null) ? cart.size() : 0;
	    model.addAttribute("cartSize", cartSize);

	    return "drug_list";
	}
	
	@GetMapping("/delete/{id}")  
	public String deleteDrug(@PathVariable Long id) {
	    drugRepository.deleteById(id);
	    return "redirect:/drugs";
	}

	
	
//	Veritabanından gelen ilaç listesi HTML sayfasına gönderiliyor.
}
