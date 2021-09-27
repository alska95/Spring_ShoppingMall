package com.example.demo.api;

import com.example.demo.domain.Address;
import com.example.demo.domain.Order;
import com.example.demo.domain.OrderItem;
import com.example.demo.domain.OrderStatus;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.OrderSearch;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


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


    @GetMapping("/api/v2/orders")
    public List<OrderDto> orderV2(){
        List<Order> orders = orderRepository.findBySearch(new OrderSearch());
        return orders.stream().map(o -> new OrderDto(o)).collect(Collectors.toList());
    }


    @Getter //properties error --> 보통 getset문제임
    private class OrderDto {

        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
//        private List<OrderItem> orderItems; //주의할 점 이런식의 조회는 OrderItem조차 Dto로 바꿔줘야한다! 많이 실수하는 부분
        private List<OrderItemDto> orderItems;

        OrderDto(Order order){
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            order.getOrderItems().stream().forEach(o->o.getItem().getName());
            orderItems = order.getOrderItems().stream().map(o->new OrderItemDto(o)).collect(Collectors.toList());
        }
    }

    @Getter
    static class OrderItemDto{

        private String itemName;
        private int orderPrice;
        private int count;

        OrderItemDto(OrderItem orderItem){
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }
}
