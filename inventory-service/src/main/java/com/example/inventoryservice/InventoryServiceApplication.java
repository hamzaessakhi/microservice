package com.example.inventoryservice;

import lombok.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
class  Product {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private double price;
}
@RepositoryRestResource
interface ProductRepository extends JpaRepository<Product,Long> {
}

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(InventoryServiceApplication.class, args);

	}

	@Bean
	CommandLineRunner start(ProductRepository productRepository, RepositoryRestConfiguration restConfig) {
		return args ->{
			restConfig.exposeIdsFor(Product.class);
			productRepository.save(new Product(null,"Ord lenovo",34));
			productRepository.save(new Product(null,"Ord MacBook",34));
			productRepository.save(new Product(null,"Ord Hp",34));
			productRepository.findAll().forEach(System.out::println);

		};
	}
}
