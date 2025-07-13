package com.dilekersoz.eczane.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	@GetMapping("/login")
	public String loginPage() {
		return "login";   // login.html sayfasını döndür
    
	}/*Bu sınıf, tarayıcıdan /login isteği geldiğinde Spring'in login.html dosyasını açmasını sağlar. Eğer yazmazsak, Spring Security login sayfasını bulamaz ve yönlendirme döngüsüne girer.

✅ LoginController Ne İşe Yarar?
Görev	                 Açıklama
@GetMapping("/login")	/login adresine gelen GET isteklerine cevap verir.
return "login";	        src/main/resources/templates/login.html dosyasını kullanıcıya gösterir.
@Controller           	Spring'e "Bu sınıf bir web controller" demektir.*/

	/*Bu sınıf sayesinde kullanıcı /login sayfasına gidebiliyor ve login.html dosyan açılıyor.*/
}
