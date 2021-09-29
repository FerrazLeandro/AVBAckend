package br.com.tech4me.produtosms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProdutosMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProdutosMsApplication.class, args);
	}

}
