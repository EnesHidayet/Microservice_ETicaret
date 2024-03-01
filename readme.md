# KURULUMLAR VE PROJE TEKNOLOJİLERİ

## Docker üzerinde postgreSQL kurulumu
        Uygulamamızda Auth servis üzerinde kullanıcı oturum açma işlemlerini ve kayıtlarını ilişkisel bir 
    veritabanında tutuyoruz. Veritabanı olarak PostgreSQL kullanıyoruz. postgreSQL i docker üzerinde çalıştırmak
    için aşağıdaki kodu kullanabilirsiniz.
```bash
  docker run --name postgreSQL -e POSTGRES_PASSWORD=root -p 5432:5432 -d postgres
```

## Docker üzerinden mongoDB çalıştırmak
    MongoDB kurulumu yaparken yetkili kullanıcı bilgilerinin girilmesi gereklidir. Bu bilgileri imajların
    Ortam Değişkenlerine atayarak yapabiliyorsunuz. Docker bu tarz bilgileri ekleyebilmeniz için size
    ek parametreler sunmaktadır.
    EK BİLGİ:
    Docker üzerinde bir imaj eklemek istiyorsak -> docker pull [IMAGE_NAME]
    Docker üzerinde bir imajı çalıştırmak istiyorsak -> docker run [IMAGE_NAME]
    Burada önemli bir nokta vardır. docker run eğer ortamda ilgili imaj yok ise önce imajı indirir sonra çalıştırır
    yani docker run yapmak için önce imajı pull etmenize gerek yoktur.
    Aşağıdaki kod çalışan bir mongoDB oluşturucaktır.
```bash
    docker run --name java13MongoDB -d -e "MONGO_INITDB_ROOT_USERNAME=admin" -e "MONGO_INITDB_ROOT_PASSWORD=root" -p 27017:27017 mongo:jammy
```

    MongoDB yi yönetebilmek için bir araca ihtiyacımız var.Bu aracın adı -> MongoDB Compass tool.Bu aracı indirip 
    kurmamız gereklidir.

    Compass kurulumu bittikten sonra, açılan pencerede "New Connection +" butonuna tıklıyorsunuz.Ekranın ortasında
    "> Advanced connection options" butonuna tıklayarak detaylı bağlantı ayarlarını yapıyoruz.
    1- Açılan ekranda "Host" kısmına veritabanınızın ip adresini ve port numarasını giriyorsunuz. Yerel bilgisayarınız
    için kullanılacak ise ya da docker desktop üzerinde ise (localhost:27017) şeklinde yazıyoruz.
    2- Authantication kısmına geçiş yaparak kurulum sırasında girdiğiniz kullanıcı adı ve şifreyi yazıyorsunuz. Docker
    run komutu ile çalıştırdı iseniz -e ile giriş yaptığınız bilgileri yazınız. (admin - root)

    NOT:  MongoDB yi ilk kurulumları ve kullanımları için admin kullanıcı ile işlemleri yapabilirsiniz. Ancak,
    veritabanlarını yönetmek ve işlemek için kullanmayınız.Her DB için ayrı kullanıcı ve yetkiler oluşturunuz
    root kullanıcısı ve şifreleri sadece ilk kurulum için kullanılmalı ve tekrar kulanılmamalıdır. Sadece gerekli 
    olduğu durumlarda müdehale için kullanınız.
    Gerekli dökümantasyonlara: www.mongodb.com/docs/manual/ ulaşabilirsiniz.

    Yetkilendirme işlemleri
    1- MONGOSH a tıklayıp açıyorsunuz
    2- Açılan kısımda test> şeklinde bir yer göreceksiniz, öncelikle test DB den kendi DB nize geçmek için
    use [DB_ADI] yazıp enter a basınız.
    Örn:
    use UserProfile
    swtiched to db UserProfile
    UserProfile> şeklinde bir görüntü elde edeceksiniz.
    3- burada kullanıcı oluşturmak için gerekli komutları giriyoruz.
    db.createUser({
    user: "bilgeUser",
    pwd: "bilgeUser*",
    roles: ["readWrite","dbAdmin"]
    })
```
    db.createUser({user: "bilgeUser",pwd: "bilgeUser*",roles: ["readWrite","dbAdmin"]})
```

## Docker üzerinde Redis Single node oluşturmak
```bash
    docker run --name java13-redis -p 6379:6379 -d redis
```

```bash
    docker run  --name redis-gui -d -p 8001:8001 redislabs/redisinsight:1.14.0
```

## Redis Spring Boot Configuration
    İlgili bağımlılık eklenir
    DİKKAT!!!!!!
    Redis Repository olarak kullanılabileceği gibi, Cache olarakta kullanılabilir, Bu nedenle Sping te Cache i
    ve Redis Repository aktif etmek için gerekli annotasyonları config e eklemeniz uygun olacaktır.

```java
@Configuration
@EnableRedisRepositories
@EnableCaching
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {

        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", 6379));
    }
}
```

        Rediste cacahe oluşturmak için, istediğiniz methodun üzerinde @Cachable anatasyonunu ekliyorsunuz
    böylelikle bu method a girilen değerler için bir Key oluşturuluyor ve return değeri redis üzerinde
    cache lenmiş oluyor

    DİKKAT!!!! Spring Boot üzerinde alınan Cache lerin temizlenmesi
    1-  Objects.requireNonNull(cacheManager.getCache("user-find-all")).clear();
    bu işlem bir key e sahip olmayan cache leri temizlemek için kullanılır.
    2- Objects.requireNonNull(cacheManager.getCache("user-find-all")).evict([KEY]);
    bu işlem dışarıdan değer alan bir methodun cache lenmiş datalarını özel olarak silmek 
    için kullanılır.
    @Cachable("find-by-ad")
    findByAd("muhammet") -> Redis => find-by-ad::muhammet
    clear cache -> getCache("find-by-ad").evict("muhammet");
    Eğer bellir bir cache in tamamını temizlemek isterseniz, 1. maddeyi kullanın.

## Elestic Search Kurulumu ve Kullanımı
```bash
    docker network create java13-network
```

```bash
    docker run -d --name elasticsearch --net java13-network -p 9200:9200 -p 9300:9300 -e "xpack.security.enabled=false" -e "xpack.security.transport.ssl.enabled=false" -e "discovery.type=single-node" -e "ELASTIC_USERNAME=admin"  -e "ELASTIC_PASSWORD=root" -e "ES_JAVA_OPTS=-Xms512m -Xmx1024m" elasticsearch:8.12.1
```

```bash
    docker run -d --name kibana --net java13-network -p 5601:5601 kibana:8.12.1
```

    DİKKAT!!!!! 
    ElasticSearch sürümler ile spring sürümleri arasında bir uyum olası gerekli. Çünkü eski sürümleri kullanabilmek için
    belli spring boot sürümlerini kullanmanız gereklidir.

    Spring Boot ile kulanmak için önce bağımlılık ekliyoruz.

    ilgili elasticsearch e bağlanmak için gerekli olan bağlantı configlerini application.yml içine yazıyoruz.
    
```yml
spring:
  elasticsearch:
    uris: http://localhost:9200
    username: admin
    password: root
```

## RabbitMQ Kurulum ve Kullanım
    RabbitMQ iki port ile çalışır. 5672, 15672 bu portlardan;
    1- 5672 olan port Rest isteklerini işlemek için kullanılır.Bu nedenle Spring Boot bu porta bağlanır.
    2- 15672 olan port arayüz webUI kısmıdır.Yönetim ekranı burasıdır.

```bash
   docker run -d --name java13-rabbit -p 5672:5672 -p 15672:15672 -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=root rabbitmq:3-management
```

    Spring Boot ile kullanım için;
    implemantation 'org.springframework.boot:spring-boot-starter-amqp:3.2.2'
    
    DİKKAT!!!!!
    RabbitMQ Deserializable işleminde getirilen yeni güvenlik config nedeniyle şu ENV nin eklenmesi
    gereklidir. "SPRING_AMQP_DESERIALIZATION_TRUST_ALL=true"
    Bu environment ı  eklemek için user microservisin main class üzerine sağ tıklayarak
    run modify configuration diyerek environment variable eklemeniz gerekmektedir.


## Servisler arası iletişim

## Aplication.yml bilgisini config serverdan almak ve configure etmek.

    Application Properties (yml) için gerek gerekli configler.
    https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html

## Docker İmage oluşturma
    İmage oluşturmak için
    - İlk olarak gradle ile ilgili modeül ün buidl işlemi yapılır.
    - İkinci aşamada gradle ile builddependents yapılır.
    - Sonra Eğe konum olarak ilgili modülün dizini içinde deil isek, terminal ekranından
    ilgili modeülün dizinine geçiş yaparız. Bunu yapmak için kullanabileceğiniz komutlar
    > cd ..  bir üst dizine geçer.
    > cd <modul_adi> ilgili modülün içine giriş yapar.
    - Terminal ekranında aşağıda bulunan ilgili modül için docker build komutu çalıştırılır.
    - Son olarak docker desktop üzerinden docker hub e push işlemi yapılır.

    docker build -t <HUB_REPOSITORY_NAME/IMAGE NAME:VERSION> .

    DİKKAT!!!! MacOS M Chipset kullananlar özellikle platform belirtmelidirler.
    ( docker build --platform linux/amd64 -t eneshidayet/config-service:v.0.1 .)

    Örn -> docker build -t eneshidayet/auth-service:v.0.3 .
    Örn -> docker build -t eneshidayet/config-service:v.0.1 .
    Örn -> docker build -t eneshidayet/user-service:v.0.9 .

    Örn MacOS -> docker build --platform linux/amd64 -t eneshidayet/auth-service:v.0.2 .
    
## Kubernate POD
        Pod, Nodes içinde yer alan sanal Pc lerdir. İçerisinde image ya da image lar barındırabilir. Bir Yaşam döngüsü vardır.
    bu nedenle başlar, işlemleri yürütür, bir süre sonra kaybolur. Bu nedenle bir pod restart olsa bile aynı şekilde
    kalmaz yani bir pod yeniden başlamaz yeniden doğar. Bu nedenle her yeni başlamada yeni bir pod oluşur ve ip adresi
    değişir.
    - Pod lar yeniden doğduğu için içinde barındırdığı bilgiler silinir.
    -Bağlantılar var ise kaybolur.

        Bir pod DB olarak kullanılıyor ise içinde tuttuğu tüm bilgiler restart ettiğinde kaybolur. Peki çözüm nedir?
    Her Pc nin bir harddiski vardır ve içinde bulunur, ancak kubernetes yapısında harddisk olarak adlandırdığımız 
    bileşenlere karşılık gelen Volume kavramı bir pod un içinden kubernetes cluster içine alınabilir. Böylece pod
    ayağa kalkarken kendisine tahsis edilen Volume a bağlanarak verilerini oradan çekebilir.
    

