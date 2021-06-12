package com.example.demo.controller;

import com.example.demo.domain.Address;
import com.example.demo.domain.Member;
import com.example.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.PersistenceContext;

@Controller
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/members/new")
    public String goToRegisterNewMember(){

        return "/member/memberRegister";
    }

    @PostMapping("/members/new")
    public String registerNewMember(MemberForm memberForm){
        Member member = new Member();
        member.setAddress(new Address(memberForm.getCity(),memberForm.getStreet(),memberForm.getZipcode()));
        member.setName(memberForm.getName());
        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members/list")
    public String MemberList(Model model){
        model.addAttribute("members" , memberService.findMembers());
        return "/member/memberList";
    }

    @GetMapping("/members/goHome")
    public String goHome(){
        return "redirect:/";
    }
}
