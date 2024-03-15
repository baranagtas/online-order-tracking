# Proje Adı: Online Sipariş Takip Sistemi

## Amaç

Bu proje, müşterilerin çeşitli ürünleri sipariş edebilecekleri ve siparişlerini takip edebilecekleri bir sistem geliştirmeyi amaçlamaktadır.

## Kapsam

- Uygulama, Java programlama dili ve Spring Boot Framework kullanılarak geliştirilmelidir ve REST API'yi kullanmalıdır.
- Sistem, müşterilerin önceden kaydedilmiş ürünleri sipariş etmelerine ve sipariş işlemlerini kolaylaştırmalarına izin vermelidir.
- Geliştirilen proje GitHub'da paylaşılmalıdır.
- UI (kullanıcı arayüzü) yazılması beklenmemektedir.

## Gereksinimler

1. Müşteri İşlemleri:
   - Kayıt olma
   - Giriş yapma

2. Yetkilendirme ve Kimlik Doğrulama:
   - Temel Yetkilendirme token'ları ile yapılandırılmalıdır.
   - Uç noktalara yalnızca bu token'lar kullanılarak erişilebilmelidir.

3. Ürün Yönetimi:
   - Kategori ve fiyat gibi özniteliklere göre ürünleri filtreleme ve arama
   - Stok kontrolü
   - Yeni siparişlerin alınması ve stok güncellemesi

4. Sipariş İşlemleri:
   - Siparişin durumunu takip etme
   - Siparişin iptal edilmesi

## Değerlendirme Kriterleri

- Maven projesi olarak geliştirilmelidir.
- Docker kullanılarak araçlar kurulmalıdır.
- Postman koleksiyonu veya Swagger API belgeleri hazırlanmalıdır.
- JUnit ile birim testleri yazılmalıdır.
- Nesne yönelimli programlama prensiplerine uygun olmalıdır.
- Kod kalitesi ve okunabilirlik önemlidir (Sınıf, değişken isimleri, kod yorumları, modül, paket ve klasör isimleri vb.).

## Kurulum ve Çalıştırma

1. Projenin klonlanması:

   ```bash
   git clone https://github.com/kullanici/proje.git
