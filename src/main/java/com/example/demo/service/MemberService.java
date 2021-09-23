package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository; //변경할일 없기 때문에 final

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void update(Long id, String name){
        Member byId = memberRepository.findById(id);
        byId.setName(name);

    }
    //회원가입
    public Long join(Member member){
        duplicateCheck(member);
        memberRepository.save(member);

        //중복 방지
        return member.getId();
    }

    private void duplicateCheck(Member member){
        if(!memberRepository.findByName(member.getName()).isEmpty()){
            throw new IllegalStateException(
                    "이미 존재하는 회원입니다."
            );
        }
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

    //회원전체조회
}
