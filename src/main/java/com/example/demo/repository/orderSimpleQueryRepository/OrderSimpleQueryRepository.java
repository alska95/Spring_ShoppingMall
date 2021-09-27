package com.example.demo.repository.orderSimpleQueryRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@AllArgsConstructor
public class OrderSimpleQueryRepository {

    final EntityManager em;
    public List<SimpleOrderDto> findOrderDtos() {
        return em.createQuery("select new com.example.demo.repository.orderSimpleQueryRepository.SimpleOrderDto(o.id, o.member.name, o.orderDate, o.status, o.member.address)" +
                " from Order o" +
                " join o.member m" +
                " join o.delivery d" , SimpleOrderDto.class).getResultList();
    } //select 절에서 딱 원하는것 member.address, member.name만 딱 조회해서 가져온다. --> DTO 바로 조회해서 성능 최척화!
    //다만 해당 DTO에 완전 종속됌. 논리적으로 계층이 깨져있다.
}
