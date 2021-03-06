package com.example.demo.api;

import com.example.demo.domain.Address;
import com.example.demo.domain.Order;
import com.example.demo.domain.OrderStatus;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.OrderSearch;
import com.example.demo.repository.orderSimpleQueryRepository.OrderSimpleQueryRepository;
import com.example.demo.repository.orderSimpleQueryRepository.SimpleOrderDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * order
 * order-->member (Many to one)
 * order-->delivery (one to one)
 *  to one 성능 최적화
 * */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1(){
        List<Order> all = orderRepository.findBySearch(new OrderSearch());
        for(Order order: all){
            order.getMember().getName(); //Lazy 강제 초기화
            order.getDelivery().getAddress();
        }
        return all;
    }

    //쿼리가 무한대로 나감 왜??
    //order에서 member로 가고 member에 가니까 order이 있음 --> 무한루프
    //해결하기위해 jsonignore, hibernate5module추가해서 해결 가능하긴함
    //다만 엔티티를 직접 노출하기때문에 이 방법은 사용하지 말자

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDtoV1> orderV2(){
        List<Order> orders = orderRepository.findBySearch(new OrderSearch());
        return orders.stream().map(v-> new SimpleOrderDtoV1(v)).collect(Collectors.toList());
    }
    //Order 통째로 넘겨서 레이지 로딩 해도 쿼리가 너무 많이 나간다. 매핑할때 전부 쿼리가 나감.

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDtoV1> orderV3(){
        List<Order> orders = orderRepository.findAllWithMemberDelivery(); //fetch를 사용함 --> 다끌고옴.
        return orders.stream().map(v-> new SimpleOrderDtoV1(v)).collect(Collectors.toList());
    }

    @GetMapping("/api/v4/simple-orders")
    public List<SimpleOrderDto> orderV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }


    @Data
    public class SimpleOrderDtoV1 {
        public SimpleOrderDtoV1(Order order) {
            orderId = order.getId();
            name = order.getMember().getName(); // Member LazyLoad해온다. n+1문제 발생(조회 대상 n개보다 쿼리가 하나 더 나가는 현상)
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getMember().getAddress();
        }

        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
    }
}
