# Tensura Slime

Tensura'dan esinlenen, Minecraft Java Edition **1.21.1 + Forge** için geliştirilen bir modun ilk prototipi.

## Gereksinimler
- Java 21 (JDK)
- Minecraft Java Edition 1.21.1
- Minecraft Forge 1.21.1 (52.x)
- Gradle 8.8

## İlk prototipte bulunanlar
- Oyuncu başına sunucuda saklanan mana (başlangıçta 100)
- Büyük Bilge tarzı durum mesajları
- Yetenek öğrenme komutu
- Mana harcayan ilk yetenek prototipi

## Komutlar
Komutları hilelerin açık olduğu tek oyunculu dünyada veya uygun izinlerle sunucuda kullan:

- `/tensura status` — mana ve yetenek durumunu gösterir.
- `/tensura learn mana_pulse` — Mana Darbesi yeteneğini öğrenir.
- `/tensura cast mana_pulse` — 20 mana harcayıp geçici hız etkisi verir.

**Not:** Mana Darbesi henüz gerçek bir mermi/saldırı değil; ilk kod prototipinde geçici hız etkisi kullanır. Sonraki sürümde gerçek mana saldırısı, slime formu, yetenek ağacı ve çok oyunculu denge geliştirilecek.

## Derleme
Java 21 kurulu bir ortamda Gradle 8.8 ile proje kök dizininde:

```sh
gradle build
```

Başarılı derlemeden sonra mod JAR dosyası `build/libs/` klasöründe oluşur. Oluşan JAR'ı Minecraft Forge 1.21.1 kurulumunun `mods` klasörüne koy.

GitHub Actions derleme işi de her push sonrasında JAR üretmeyi dener. İş başarılıysa Actions sekmesindeki ilgili çalışmanın **Artifacts** bölümünden JAR alınabilir.

## Durum
Bu, ilk oynanabilir prototipin başlangıcıdır; henüz derlenip test edildiği doğrulanmış nihai sürüm değildir. Sorunları ve yeni yetenekleri adım adım düzelteceğiz.
