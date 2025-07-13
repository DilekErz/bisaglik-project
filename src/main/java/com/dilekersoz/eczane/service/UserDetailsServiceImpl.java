package com.dilekersoz.eczane.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.GrantedAuthority;

import com.dilekersoz.eczane.model.User; 

import com.dilekersoz.eczane.repository.UserRepository;

@Service /*Spring'e bu sınıfın servis olduğunu söyledin(Spring’in bu sınıfı otomatik tanıyabilmesi için)*/
@Transactional/*Spring Security, login olur olmaz kullanıcıyı veritabanından çekmeye çalışır.Eğer o an veritabanı bağlantısı henüz hazır değilse ya da JPA işlemi transaction içinde değilse, kullanıcıyı göremez.@Transactional bu işlemin düzgün şekilde tamamlanmasını garanti altına alır.*/
public class UserDetailsServiceImpl implements UserDetailsService{ /*implements UserDetailsService Girişte kullanıcıyı nasıl bulacağını anlattın */
	@Autowired
	private UserRepository userRepository;
	/* UserRepository interface’inin çalışan bir sınıf halini otomatik oluşturuyor
Sen sadece şunu yazarsın:
@Autowired
private UserRepository userRepository;
Spring der ki:
"Tamam, ben bu UserRepository interface'inin nasıl çalışacağını biliyorum.
Veritabanı bağlantısını ben hallederim."*/
	
	/*🔐 Giriş sistemi (authentication) için Spring Security'yi yapılandıracağız.
Spring Security, kullanıcının:
Giriş yapmasına,
Şifre kontrolü yapılmasına,
Giriş yapılmadan sistemin korunmasına olanak sağlar.

Hedefimiz:
Spring Security’ye “kullanıcıları nereden alacağını” söyleyeceğiz
Parolayı nasıl kontrol edeceğini göstereceğiz
Giriş formu ekranını yöneteceğiz

Neden class?
Çünkü:
Bu sınıfın içinde gerçek method gövdeleri olacak
Spring Security’ye “kullanıcıyı nasıl bulacağını” göstereceğiz
UserDetailsService isimli bir interface'i implement edeceğiz

implements NE DEMEK?
Java’da implements anahtar kelimesi:

"Ben bu interface’i uyguluyorum, içindeki methodları yazacağım" demektir.

🧠 2. NEDEN Kullanıyoruz?
Sen UserDetailsServiceImpl adında bir sınıf oluşturuyorsun.
Ama bu sınıf, Spring Security’nin login işlemini yapabilmesi için, kendisinden beklenen bir davranışı yerine getirmeli.

Spring, uygulamaya şunu sorar:

“Kullanıcı adıyla giriş yapılacak.
Bana bu kullanıcıyı bulacak bir method var mı?”

Spring bu methodu UserDetailsService adlı bir interface içinde tanımlar.
Biz de kendi sınıfımızla bunu "uyguluyoruz", yani:
public class UserDetailsServiceImpl implements UserDetailsService

🔁 3. Peki Spring bu sınıfa ne zaman bakar?
Uygulama çalıştığında bir kullanıcı giriş yapınca, Spring şunu der:

“Ben UserDetailsService arayüzüne bakan bir sınıf arıyorum.
Ona göre veritabanından kullanıcıyı bulayım.”

Ve senin implements UserDetailsService dediğin sınıfı bulur.
İçindeki şu methodu çalıştırır:


@Override
public UserDetails loadUserByUsername(String username)
*/
	
		

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		/*loadUserByUsername()	Sisteme giriş yapan kullanıcıyı veritabanından çekmek için(girilen kullanıcıyı veritabanında bul Eğer yoksa UsernameNotFoundException fırlat,
Varsa UserDetails tipine çevirip geri döndür.)*/
		com.dilekersoz.eczane.model.User user=userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Kullanıcı Bulunamadı:"+username));
		/*findByUsername(username): Repository'deki methodumuz   .orElseThrow(...)	Eğer kullanıcı yoksa özel hata fırlat, UsernameNotFoundException:	Spring’in beklediği hata tipi
*/
		List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole()));
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				user.getPassword(),
				authorities);
				/*ser.getRole() değeri "ADMIN" ise bu "ROLE_ADMIN" olur. Sonra bunu SimpleGrantedAuthority ile sar ve listeye al.*/
		/*⚠️ Bu new ArrayList<>()(bu satırı sildik çünkü yönetici ve kullanıcı panelini ayırabilmek için) kısmı boş yetki listesi veriyor. Spring, senin kullanıcının yetkisini bilmediği için hiçbir role sahip değil gibi davranıyor.*/
		
		/*bu method şimdilik return null yapıyor.
Bu method aslında şunu yapmalı:
“Girilen username’e göre kullanıcıyı UserRepository ile veritabanından bul,
sonra onu UserDetails tipine dönüştür ve geri döndür.”*/
	}

}
