package com.example.demo.service;

import com.example.demo.domain.*;
import com.example.demo.domain.item.Book;
import com.example.demo.domain.item.Item;
import com.example.demo.repository.OrderRepository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired private EntityManager em;
    @Autowired OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 주문(){
        Member member = createMember();

        Book book = new Book();
        book.setName("바함사");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);

        //when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findById(orderId);

        assertEquals("상품 주문시 상태는 ORDER" , OrderStatus.ORDER , getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다" , 1 , getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다." , 10000 * orderCount , getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 제고가 줄어야 한다." , 8 , book.getStockQuantity());


    }



    @Test
    public void 주문취소(){
        //given
        Member member = createMember();
        Book book = createBook("에스" , 10000 , 2);
        //when
        Long target = orderService.order(member.getId() , book.getId() , 1);
        orderService.cancelOrder(target);

        //then
        Order order = orderRepository.findById(target);
        assertEquals("상태가 취소여야한다" , OrderStatus.CANCEL , order.getStatus());
        int count = book.getStockQuantity();
        assertEquals("취소가 되어서 카운트가 2여야 합니다.",count , 2);
    }

    @Test
    public void 제고수량초과(){
        Member member = createMember();

        Book book = createBook("너와함깨", 10000 , 1);

        try{
            orderService.order(member.getId(), book.getId(), 2);
            fail();
        }catch (IllegalStateException e){
            System.out.println(e+"-----------");
        }

    }

    private Book createBook(String name , int orderPrice , int quantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(orderPrice);
        book.setStockQuantity(quantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "서그내로", "123-123"));
        em.persist(member);
        return member;
    }

}