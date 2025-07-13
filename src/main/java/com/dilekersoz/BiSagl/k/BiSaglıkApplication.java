package com.dilekersoz.BiSagl.k;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.dilekersoz.eczane") //DİĞER PAKAETTEKİNİ TANISIN DİYE YAZDIK
@EnableJpaRepositories(basePackages = "com.dilekersoz.eczane.repository") // ✨ BU SATIRI EKLE
@EntityScan(basePackages = "com.dilekersoz.eczane.model") // ✨ Entity'leri tanıt
public class BiSaglıkApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiSaglıkApplication.class, args);
	}

}
