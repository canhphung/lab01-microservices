package com.example.order_service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/orders")

public class OrderController {

  private final RestClient restClient;

  public OrderController(RestClient.Builder builder) {
    this.restClient = builder
        .baseUrl("http://localhost:8081")
        .build();
  }

  @GetMapping
  public List<Map<String, Object>> getOrders() {
    return List.of(
        Map.of("id", 101, "productId", 1, "quantity", 2),
        Map.of("id", 102, "productId", 2, "quantity", 1)
    );
  }

  @GetMapping("/product/{id}")
  public ResponseEntity<String> getProduct(@PathVariable int id) {
    return restClient.get()
        .uri("/api/products/{id}", id)
        .exchange((request, response) -> {
          String body = new String(
              response.getBody().readAllBytes(),
              java.nio.charset.StandardCharsets.UTF_8
          );

          return ResponseEntity
              .status(response.getStatusCode())
              .body(body);
        });
  }
}
