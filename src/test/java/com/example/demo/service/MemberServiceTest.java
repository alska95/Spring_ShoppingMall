package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class MemberServiceTest {


    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Rollback(true)
    public void 회원가입(){
        //given
        Member member1 = new Member();
        member1.setName("황경하2");

        //when
        Long savedId = memberService.join(member1);
        //then
        assertEquals(member1 , memberRepository.findById(savedId));
        //넣은거랑 똑같으면 성공

    }

    @Test
    public void 중복_회원_예외(){
        Member member1 = new Member();
        member1.setName("황경하3");
        memberService.join(member1);
        Member member2 = new Member();
        member2.setName("황경하3");
        try{
            memberService.join(member2);
            fail();
        }catch (IllegalStateException e){

        }

    }

}