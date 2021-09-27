package com.example.demo.api;

import com.example.demo.domain.Order;
import com.example.demo.domain.OrderItem;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> orderV1(){
        List<Order> bySearch = orderRepository.findBySearch(new OrderSearch());
        for (Order search : bySearch) {
            search.getMember().getName();
            search.getDelivery().getAddress();
            List<OrderItem> orderItems = search.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        } //lazy강제 호출
        return bySearch;
    }
}
