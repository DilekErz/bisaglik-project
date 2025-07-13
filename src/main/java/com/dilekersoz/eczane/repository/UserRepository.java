package com.dilekersoz.eczane.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dilekersoz.eczane.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByUsername(String username);

	/*Çünkü Spring, senin yazdığın interface’i otomatik olarak bir sınıfa dönüştürüp içini dolduruyor.*/
	/*Ve Spring şunları senin yerine otomatik ekliyor:
    findAll()
    findById(Long id)
    save(User user)
    deleteById(Long id)*/
	/*✅ UserRepository Interface'ini Neden Yaptık?
Spring Boot'ta veritabanıyla konuşmak için bir arayüze (interface'e) ihtiyaç vardır.

🎯 Görevi:
UserRepository, User tablosuyla ilgili tüm veritabanı işlemlerini (CRUD) otomatik yapar.

🧠 CRUD ne demek?
Kısaltma	Anlamı	                Örnek
C	       Create (Ekle)	      save(user)
R	     Read (Oku / Listele)	findAll(), findById()
U	      Update (Güncelle)	    save(user) tekrar kullanılır
D	        Delete (Sil)	     deleteById(id)

*Çünkü Spring Boot bize der ki:
“Sen sadece interface tanımla, içini ben otomatik doldururum!”
Yani biz hiçbir kod yazmadan, bu satırla tüm işlemleri kullanabiliriz: public interface UserRepository extends JpaRepository<User, Long>

🧾 Sonuç:
UserRepository, veritabanıyla haberleşen katmandır.
Spring Boot bu interface’i görüp, senin adına gerekli işlemleri otomatik yapar.
Bu sayede sen SQL yazmak zorunda kalmazsın.

 Artık sistemin kullanıcıları veritabanında saklayabilir, sorgulayabiliriz.


*/

}
