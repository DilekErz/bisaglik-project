package com.dilekersoz.eczane.config;

import java.io.IOException;//IOException → Web yönlendirmesi sırasında hata oluşursa kullanılacak.
import java.util.Collection;// Collection → Birden fazla yetkiyi (ROLE_ADMIN, ROLE_USER) içeren listeyi tutmak için.

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
/*🔐 Giriş yapan kullanıcının tüm bilgilerini içeren Authentication nesnesi burada işimize yarar.
Ayrıca kullanıcının rollerine GrantedAuthority sayesinde ulaşırız.*/

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
/*🔧 Bu sınıf AuthenticationSuccessHandler arayüzünü (interface) uygular.
💡 @Component → Spring bu sınıfı tanısın diye kullanılır. Böylece başka yerlerde (örneğin SecurityConfig içinde) otomatik olarak kullanılabilir.*/

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.ServletException;
/*🧱 Kullanıcının gönderdiği istek (HttpServletRequest) ve sistemin ona verdiği cevap (HttpServletResponse) ile ilgileniyoruz.*/

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler { //🔹 Diyoruz ki: “Ben giriş başarılı olunca ne yapılacağını özelleştireceğim.”

	

@Override
public void onAuthenticationSuccess(HttpServletRequest request, 
		HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException { //Kullanıcı başarılı giriş yaptığında otomatik olarak çalışan method.🔹 Authentication objesi ile kullanıcının bilgilerine ulaşırız.
	
	System.out.println("✅ Giriş başarılı, CustomSuccessHandler çalıştı!");
	
Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities(); //👥 Kullanıcının rollerini (ROLE_ADMIN, ROLE_USER, vb.) liste şeklinde alırız.
	
	//ÖNCE ADMIN KONTROL ET
System.out.println("✅ Giriş başarılı. Roller:");
for (GrantedAuthority authority : authorities) {
    System.out.println("🔸 " + authority.getAuthority());
}
	for (GrantedAuthority authority: authorities) {
		//String role= authority.getAuthority();
		// Her rolü tek tek kontrol ederiz.🧠 role değişkeni şu değeri alabilir:"ROLE_ADMIN" "ROLE_USER"

		
		if("ROLE_ADMIN".equals(authority.getAuthority())) {
			System.out.println("🟢 ROLE_ADMIN bulundu → /admin");
			response.sendRedirect("/admin");
			return;  //👉 Eğer giriş yapan kişi admin’se /admin sayfasına,
		} 
		//user yönlendirmesi
		for(GrantedAuthority auth:authorities) {
			if("ROLE_USER".equals(authority.getAuthority())) {
				System.out.println("🟢 ROLE_USER bulundu → /user");
				response.sendRedirect("/user");
				return;//👉 kullanıcıysa /user sayfasına yönlendirilir.
			}
		}
		}
	System.out.println("⚠️ Rol atanmadı → /");
	response.sendRedirect("/"); // Rol bulunamazsa ana sayfaya yönlendir

	/*✅ Nedir Bu CustomSuccessHandler?
	Spring Security’de bir kullanıcı giriş yaptıktan sonra nereye yönlendirileceğini belirlemek için AuthenticationSuccessHandler adlı bir yapıyı kullanırız.
	Senin amacın:
	👉 Admin olanı /admin sayfasına,
	👉 User olanı /user sayfasına göndermek.*/
		
	
}}