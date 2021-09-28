package com.example.demo.repository;

import com.example.demo.domain.Order;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OrderRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findById(Long id){
        return em.find(Order.class,id);
    }

    public List<Order> findBySearch(OrderSearch orderSearch){
        return em.createQuery(
                "select o from Order o join o.member m " +
                        "where m.name =: name and o.status =: status" , Order.class
        ).setParameter("name" , orderSearch.getMemberName())
                .setParameter("status" , orderSearch.getOrderStatus())
                .getResultList();
    }

    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery(
                "select o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", Order.class
        ).getResultList();
    }

    public List<Order> findAllWithItem() {
        return em.createQuery(
                "select distinct o from Order o " +
                        "join fetch o.member m " +
                        "join fetch o.delivery d " +
                        "join fetch o.orderItems oi " +
                        "join fetch oi.item i", Order.class).getResultList();

    }
    //쿼리 수십개 나가던게 단 하나로 해결!
    //걍조인을 하면 result가가 개수가 많은쪽 만큼 생성된다. --> 중복 데이터가 생성됨. distinct를 붙여서 해결하자!
    //distinct의 두가지 기능 1. 쿼리에 distinct 붙여준다(효과는 없음) 2. 중복 데이터를 걸러준다.


//    public List<Order> findAllByString(OrderSearch orderSearch) {
//        //language=JPAQL
//        String jpql = "select o From Order o join o.member m";
//        boolean isFirstCondition = true;
//        //주문 상태 검색
//        if (orderSearch.getOrderStatus() != null) {
//            if (isFirstCondition) {
//                jpql += " where";
//                isFirstCondition = false;
//            } else {
//                jpql += " and";
//            }
//            jpql += " o.status = :status";
//        }
//        //회원 이름 검색
//        if (StringUtils.hasText(orderSearch.getMemberName())) {
//            if (isFirstCondition) {
//                jpql += " where";
//                isFirstCondition = false;
//            } else {
//                jpql += " and";
//            }
//            jpql += " m.name like :name";
//        }
//        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
//                .setMaxResults(1000); //최대 1000건
//        if (orderSearch.getOrderStatus() != null) {
//            query = query.setParameter("status", orderSearch.getOrderStatus());
//        }
//        if (StringUtils.hasText(orderSearch.getMemberName())) {
//            query = query.setParameter("name", orderSearch.getMemberName());
//        }
//        return query.getResultList();
//    }
}
