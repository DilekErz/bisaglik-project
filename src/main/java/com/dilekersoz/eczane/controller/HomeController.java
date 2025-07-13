package com.dilekersoz.eczane.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String homePage() {
		return "home";
	}
 
	/*giriş yaptıktan sonra yönlendirilecek bir ana sayfan ("/") yok, bu yüzden 404 hatası alıyorsun.
	 * Ana sayfayı temsil edecek bir HomeController ve home.html dosyası eklemek.
	 * girişten sonra / sayfasına yönlendirilecek ve home.html görünecek */
	
	
	
}
