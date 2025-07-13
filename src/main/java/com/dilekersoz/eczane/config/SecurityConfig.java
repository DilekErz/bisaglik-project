package com.dilekersoz.eczane.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration /*@Configuration → Spring'e "Bu bir yapılandırma sınıfı" der*/
@EnableWebSecurity /*@EnableWebSecurity → Web güvenliği etkin hale gelir*/
public class SecurityConfig {

	/*Spring Boot, Security bağımlılığı eklenince:
Otomatik bir giriş sayfası (http://localhost:8080/login) sağlar
Tüm sayfaları şifre korumalı hale getirir
Senin login.html sayfanı tanımaz
Herkese aynı kuralları uygular (admin/normal kullanıcı ayrımı yapmaz)
📌 İşte bu yüzden, bizim kendi ayarlarımızı tanımlamamız gerekir.

* SecurityConfig Neyi Sağlar?
Ne Yapıyoruz?	                             Neden Gerekli?
@Configuration	                         Bu sınıfın bir ayar (config) sınıfı olduğunu belirtir
@EnableWebSecurity	                     Web güvenliği yapılandırmasını etkinleştirir
SecurityFilterChain Bean'i tanımlıyoruz      	Giriş sayfasını, şifreli URL'leri, logout işlemini kontrol ederiz
UserDetailsService tanımı yapılabilir        	Giriş yapan kullanıcıları nereden bulacağını belirtiriz
*/
/*SecurityFilterChain Metodunu Yazmak
Bu metod sayesinde:
Hangi sayfalar korunacak?
Giriş sayfası nerede?
Giriş başarılıysa nereye gitsin?
Giriş başarısızsa ne olsun?
Çıkış işlemi nasıl olsun?
→ Tüm bu ayarları yapacağız.*/
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}/*Bu sayede, data.sql dosyasında şifreyi düz metin olarak (1234) yazabileceğiz.*/
	
	@ Autowired
	private CustomSuccessHandler customSuccessHandler; //🧠 Spring’e diyoruz ki: "Ben CustomSuccessHandler’ı kullanacağım, sen bunu tanı."
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http /*güvenlik zinciri*/
		.authorizeHttpRequests(auth ->auth
				.requestMatchers("/login","/css/**","/js/**").permitAll()
				
				
				// Her iki role de açık olanlar
			    .requestMatchers("/pharmacy/list", "/pharmacy/map", "/pharmacy/locations").hasAnyRole("ADMIN", "USER")
			    
				 // Admin'e özel işlemler
			    .requestMatchers("/pharmacy/list", "/pharmacy/add", "/pharmacy/update/**", "/pharmacy/delete/**").hasRole("ADMIN")
				.requestMatchers("/admin").hasRole("ADMIN") /*/admin sayfasına sadece ADMIN rolü olanlar gidebilsin istiyoruz.*/
				
				// User'a açık olan sadece konum bilgisi
			   // .requestMatchers("/pharmacy/map", "/pharmacy/locations").hasRole("USER")
				.requestMatchers("/user").hasRole("USER") /*/user sayfasına sadece USER rolü olanlar gidebilsin istiyoruz.*/
				.anyRequest().authenticated()
				); /*Her isteği doğrulama iste”
“Ama /login, /register gibi sayfalara herkes erişebilsin”
/login, /css/**, /js/**	Giriş ve statik dosyalar serbest
.anyRequest().authenticated()	Diğer her yer için giriş zorunlu*/
		
		http
		.formLogin(form ->form
				.loginPage("/login")//bizim login.html dosyası
				//.defaultSuccessUrl("/",true) // giriş başarılıysa yönlenecek sayfa
				.successHandler(customSuccessHandler) // Rolüne göre yönlendirme yapar(🔁 Artık giriş başarılı olunca, Spring otomatik olarak bizim yazdığımız CustomSuccessHandler sınıfını çağıracak ve kişiyi rolüne göre yönlendirecek.)
				.permitAll()
				);
		
		http
		.logout(logout ->logout
				.logoutUrl("/logout")   // isteğe bağlı: logout URL
				.logoutSuccessUrl("/login?logout") // çıkış sonrası yönlendirme
				.permitAll()
				);
	
		return http
			
				.build();
		
		/*✅ KODUN NELER YAPIYOR?
Yapılan İşlem	                 Açıklama
@Configuration	                Bu sınıf bir ayar sınıfı
@EnableWebSecurity	            Web güvenlik yapılandırması devrede
SecurityFilterChain()	        Giriş ve erişim kurallarını tanımlar
requestMatchers("/login", ...)	Giriş sayfası ve statik dosyalar herkes için açık
anyRequest().authenticated()	Diğer tüm sayfalara giriş zorunlu
.build()	                    Ayarları uygula ve sistemi başlat
loginPage("/login") →          Thymeleaf’teki login.html
defaultSuccessUrl("/", true) → Giriş başarılıysa ana sayfaya git (şimdilik /)
permitAll() →                  Bu sayfa herkese açık olmalı
/logout URL’si çağrıldığında oturum sonlandırılır
Kullanıcı login.html?logout sayfasına yönlendirilir
Sayfada çıkış mesajı görünür (th:if="${param.logout}" sayesinde)*/
	}
	
	
}
