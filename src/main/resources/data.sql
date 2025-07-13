/*Diyelim ki veritabanındaki user tablosunda şu sütunlar var:
id	username	password	role
Şimdi şifreyi şifrelenmiş (encoded) olarak eklemeliyiz çünkü Spring Security düz şifre kabul etmez.
Şimdilik en kolayı: NoOpPasswordEncoder kullanmak (şifreyi düz yazmamıza izin verir)
Onun için önce SecurityConfig içine bir PasswordEncoder bean’i eklemeliyiz.*/

INSERT IGNORE INTO user(username,password,role) VALUES('TestUser','1234','USER');
/*user tablosu senin User entity'inden otomatik oluşmuş olmalı (@Entity ile)*/
INSERT IGNORE INTO user(username, password, role) VALUES ('SinemErz','Se2005', 'USER');
/* IGNORE Sayesinde Eğer user tablosunda username = 'admin' zaten varsa,
Ve username alanı UNIQUE olarak tanımlanmışsa,
Normalde INSERT komutu hata verirdi, ama INSERT IGNORE kullanırsan hata vermez, 
sadece o satırı eklemeden geçer.
YANİ KISACA (UYGULAMA HER BAŞLADIĞINDA EĞER ADMİN YOKSA EKLER VARSA HATA VERMEZ,ATLAR)

IGNORE YAPMADAN ÖNDE DE MYSQL DE ALTER TABLE user ADD CONSTRAINT uc_username UNIQUE (username); :
Veritabanındaki user tablosuna UNIQUE constraint (benzersizlik kuralı) eklemelisin*/
 
 INSERT IGNORE INTO user(username,password,role) VALUES ('DilekErz','De2003','ADMIN');
 INSERT IGNORE INTO user(username,password,role) VALUES ('Emircan','Emir07','USER');
