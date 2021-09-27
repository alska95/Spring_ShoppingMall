package com.example.demo.repository.orderSimpleQueryRepository;

import com.example.demo.domain.Address;
import com.example.demo.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimpleOrderDto {
//    public SimpleOrderDto(Order order) {
//        orderId = order.getId();
//        name = order.getMember().getName(); // Member LazyLoad해온다. n+1문제 발생(조회 대상 n개보다 쿼리가 하나 더 나가는 현상)
//        orderDate = order.getOrderDate();
//        orderStatus = order.getStatus();
//        address = order.getMember().getAddress();
//    }

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    public SimpleOrderDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }

}
