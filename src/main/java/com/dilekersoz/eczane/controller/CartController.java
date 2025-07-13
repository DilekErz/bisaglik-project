package com.dilekersoz.eczane.controller;

import java.security.Principal;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dilekersoz.eczane.model.CartItem;
import com.dilekersoz.eczane.model.Drug;
import com.dilekersoz.eczane.model.User;


import com.dilekersoz.eczane.repository.CartItemRepository;
import com.dilekersoz.eczane.repository.DrugRepository;
import com.dilekersoz.eczane.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private DrugRepository drugRepository;
	
	@Autowired
	private UserRepository userRepository;

	
	 // Sepet listesini göster
	
	@GetMapping 
	public String viewCart(Model model) {
		 List<CartItem> cartItems = cartItemRepository.findAll();
	        model.addAttribute("cartItems", cartItems);
	        return "cart_list";
	}
	
	//  Sepete ilaç ekle
    @PostMapping("/add")
    public String addToCart(@RequestParam Long drugId, @RequestParam int quantity) {
        Drug drug = drugRepository.findById(drugId).orElseThrow(() -> new IllegalArgumentException("İlaç bulunamadı"));

        CartItem item = new CartItem();
        item.setDrug(drug);
        item.setQuantity(quantity);

        cartItemRepository.save(item);

        return "redirect:/cart";
    }

    //  Sepetten bir öğeyi sil
    @GetMapping("/delete/{id}")
    public String deleteFromCart(@PathVariable Long id) {
        cartItemRepository.deleteById(id);
        return "redirect:/cart";
    }
    
    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        model.addAttribute("cartItems", cart);
        return "cart";
    }
    
    
    @GetMapping("/payment")
    public String paymentPage(Model model) {
        
        return "payment"; // payment.html'ye yönlendir
    }
    
    @GetMapping("/payment-success")
    public String paymentSuccessPage() {
        return "payment_success";
    }

    
    
    @PostMapping("/add/{drugId}")
    public String addToCart(@PathVariable Long drugId, 
                            @RequestParam("quantity") int quantity, 
                            @RequestParam("pharmacyId") Long pharmacyId,
                            Principal principal,
                            RedirectAttributes redirectAttributes)
    {
        User user = userRepository.findByUsername(principal.getName()).orElse(null);
        Drug drug = drugRepository.findById(drugId).orElse(null);

        if (user != null && drug != null) {
            CartItem existingItem = cartItemRepository.findByUserAndDrug(user, drug).orElse(null);

            if (existingItem != null) {
                existingItem.setQuantity(existingItem.getQuantity() + quantity);
            } else {
                existingItem = new CartItem();
                existingItem.setUser(user);
                existingItem.setDrug(drug);
                existingItem.setQuantity(quantity);
            }
            cartItemRepository.save(existingItem);
            
            // ✅ Flash mesajı ekle
            redirectAttributes.addFlashAttribute("message", drug.getName() + " İlaç sepete eklendi.");
        }
     //  İlgili eczanenin ilaç listesine geri dön
        return "redirect:/drugs/pharmacy/" + pharmacyId;
      //  return "redirect:/drugs/pharmacy/" + drug.getPharmacy().getId(); //sadece ilgili eczanenin ilaçları listelenecek

    }
   /* @PostMapping("/confirm-payment")
    public String confirmPayment(@RequestParam String name,
                                 @RequestParam String cardNumber,
                                 @RequestParam String expiryDate,
                                 @RequestParam String cvv,
                                 RedirectAttributes redirectAttributes) {
        // Burada ödeme işlemini simüle edebilirsin
        redirectAttributes.addFlashAttribute("message", "Ödeme başarıyla tamamlandı!");
        return "redirect:/drugs"; // veya başka bir sayfa }*/
    
    @PostMapping("/confirm-payment")
    public String confirmPayment(@RequestParam String name,
                                 @RequestParam String cardNumber,
                                 @RequestParam String expiryDate,
                                 @RequestParam String cvv,
                                 RedirectAttributes redirectAttributes) {
        // Gerçek ödeme işlemi yapılacaksa burada gerçekleştirilir
        redirectAttributes.addFlashAttribute("message", "Ödeme başarıyla tamamlandı!");
        return "payment_success"; // payment_success.html doğrudan açılır // ödeme başarılı sayfasına yönlendirme
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long drugId,
                            @RequestParam int quantity,
                            @RequestParam Long pharmacyId,
                            HttpSession session) {
        
        // Geri dönüş için sakla
        session.setAttribute("lastPharmacyId", pharmacyId);

        return "redirect:/cart/view";
    }


}
