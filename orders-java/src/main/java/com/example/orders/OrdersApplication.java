package com.example.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;

@SpringBootApplication
public class OrdersApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdersApplication.class, args);
	}

}

@RestController
class OrderRestController {

	@GetMapping("/orders/{cid}")
	Collection<Order> get(@PathVariable Integer cid) {
		var list = new ArrayList<Order>();
		for (var orderId = 1; orderId <= (Math.random() * 100); orderId++) {
			list.add(new Order(orderId, cid));
		}
		return list;
	}
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Order {
	private Integer id, customerId;
}