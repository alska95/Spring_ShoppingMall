package com.example.demo.api;

import com.example.demo.domain.Order;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
