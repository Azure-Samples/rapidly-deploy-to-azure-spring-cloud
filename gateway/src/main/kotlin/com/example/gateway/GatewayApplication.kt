package com.example.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.route.builder.filters
import org.springframework.cloud.gateway.route.builder.routes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono

@SpringBootApplication
class GatewayApplication

fun main(args: Array<String>) {
    runApplication<GatewayApplication>(*args)
}

@Configuration
class CrmConfiguration {

    @Bean
    fun gateway(rlb: RouteLocatorBuilder) = rlb
        .routes {
            route {
                path("/proxy")
                filters {
                    setPath("/customers")
                }
                uri("lb://customers/")
            }
        }

    @Bean
    fun routes(crm: CrmClient) = router {
        GET("/cos") {
            ServerResponse.ok().body(crm.customerOrders())
        }
    }

    @Bean
    @LoadBalanced
    fun webClientBuilder(): WebClient.Builder = WebClient.builder()

    @Bean
    fun webClient(webClient: WebClient.Builder): WebClient = webClient.build()
}

data class CustomerOrders(val customer: Customer, val orders: List<Order>)

@Component
class CrmClient(private val http: WebClient) {

    fun customerOrders() = this
        .customers()
        .flatMap {
            Mono.zip(Mono.just(it), orders(it.id).collectList())
        }
        .map { CustomerOrders(it.t1, it.t2) }

    fun customers() =
        this.http.get()
            .uri("http://customers/customers")
            .retrieve()
            .bodyToFlux<Customer>()

    fun orders(customerId: Int) =
        this.http
            .get()
            .uri("http://orders/orders/{cid}", customerId)
            .retrieve()
            .bodyToFlux<Order>()
}

data class Order(val customerId: Int, val id: Int)
data class Customer(val id: Int, val name: String)