package com.example.demo;

import com.example.demo.domain.*;
import com.example.demo.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * 주문 1
 * 황경하
 *  상품 1
 *  상품 2
 *
 * */
@Component
@RequiredArgsConstructor
public class InitDb {

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;

        public void doInit(){
            Member member = new Member();
            member.setName("황경하");
            member.setAddress(new Address("수원" , "로" , "1"));
            em.persist(member);

            Book book1 = new Book();
            book1.setName("상품1");
            book1.setPrice(1000);
            book1.setStockQuantity(10);
            em.persist(book1);

            Book book2 = new Book();
            book2.setName("상품1");
            book2.setPrice(1000);
            book2.setStockQuantity(10);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1,1000 , 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book1,2000 , 2);
            List<OrderItem> tmp = new ArrayList<>();
            tmp.add(orderItem1);
            tmp.add(orderItem2);
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, tmp);
            em.persist(order);
        }
    }
}

