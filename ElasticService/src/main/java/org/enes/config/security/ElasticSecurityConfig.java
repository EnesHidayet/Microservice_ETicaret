package org.enes.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@Slf4j
public class ElasticSecurityConfig {
    /**
     * Spring security ortamda gelen istekleri işlemek yani filtrelemek için SecurtiyFilterChain e ihtiyaç
     * duyar, eğer bunu siz sağlamaz iseniz zaten kendisi bir tane yaratır ve sistemi bunun üzerinden
     * yönetir.
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        /**
         * Bu alan tüm güveblik işlemlerinin yönetildiği kısımdır. Burada hangi isteklerin kontrol edileceği
         * hangi isteklerin herkese açık olacağı belirlenir.
         */
        /**
         * Spring boot 3.x öncesi config
         */
//        httpSecurity.authorizeRequests()
//                .requestMatchers("/swagger-ui/**","/v3/api-docs/**").permitAll()
//                        .anyRequest()
//                                .authenticated();
//
//        httpSecurity.formLogin();

        /**
         * Spring Boot 3.X sonrası config
         * Sprign Security gelen istekleri ayrıştırmak ve yönetmek için HttpSecurity içinde
         * belli methodlarla işlemler yapar.
         * 1- requestMatchers -> gelen isteklerden filtrelenecek alanları eklemek için kullanılır.
         * 2- permitAll -> belirlenen istekleri erişime aç.
         * 3- anyRequest -> olabilecek tüm istekler tüm end-point kullanımları anlamına gelir.
         * DİKKAT burada konu alan kendinden önce belirlenen end-pointler dışında kalanları dahil etmemektedir.
         * 4- authenticated -> belirlenen isteklere erişmek için oturum açmayı zorunlu kıl.
         */

        httpSecurity.authorizeHttpRequests(req->
            req.requestMatchers("/swagger-ui/**", "/v3/api-docs/**","/dev/v1/elastic/user/**")
                    .permitAll()
                        .anyRequest()
                            .authenticated()
        );
        /**
         * _csrf kullanımı genel olarak MVC ve Web projelerinde kullanılır.
         *
         * WAF -> Web Application Firewall
         * genellikle, api gateway üzerinde aktif edilir ve saldırıları engellemek için kullanılır.
         *
         * CSRF restAPI kullanımlarında kapatılır. Çünkü istek atmak için bir sayfaya erişmemize gerek yoktur.
         * Direkt olarak bir end-point e erişim sağlıyoruz.
         */

        //Spring boot 3.x öncesi
//        httpSecurity.csrf().disable();

        //Spring boot 3.x sonrası
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        log.info("*********** Tüm istekler buradan geçecek. ***********");
        httpSecurity.addFilterBefore(null,null);
        return httpSecurity.build();
    }
}
