package org.sid.billingservice;

import lombok.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor

class Bill {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	private Date billingDate;
	private Long customerId;
	@OneToMany(mappedBy = "bill")
	private Collection<ProductIem> productItems ;
}

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
class ProductIem{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long productID;
	private double price;
	private double quantity;
	@ManyToOne
	private Bill bill;

}
@RepositoryRestResource
interface  BillRepository extends JpaRepository<Bill,Long> {

}
@RepositoryRestResource
interface ProductItemRepository extends JpaRepository<ProductIem,Long> {

}
@SpringBootApplication
public class BillingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(BillRepository billRepository,ProductItemRepository productItemRepository) {
		return args -> {
			Bill bill1 = billRepository.save(new Bill(null,new Date(),1L,null));
			productItemRepository.save(new ProductIem(null,2L,800.0,89,bill1));
			productItemRepository.save(new ProductIem(null,2L,800.0,89,bill1));
			productItemRepository.save(new ProductIem(null,3L,800.0,89,bill1));

		};
	}
}
