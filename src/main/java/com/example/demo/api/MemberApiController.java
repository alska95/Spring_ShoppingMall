package com.example.demo.api;

import com.example.demo.domain.Member;
import com.example.demo.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    @GetMapping("/api/v1/members")
    public List<Member> membersV1(){
        return memberService.findMembers();
    } //이렇게 하면 원하는것만 노출할 수 없다 --> DTO(Response Object)의 필요.

    @GetMapping("/api/v2/members")
    public Result membersV2(){
        List<Member> members = memberService.findMembers();
        List<MemberDto> collect = members.stream().map(v -> new MemberDto(v.getName())).collect(Collectors.toList());
        return new Result(collect.size() ,collect);
    } //이름만 Result로 감싸서 노출 --> 그냥 노출시키면 단순 json배열로 변환되기 때문에 유연성이 떨어진다.

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable Long id,
                                               @RequestBody @Valid UpdateMemberRequest request){
        memberService.update(id, request.getName());
        Member mem = memberService.findOne(id);
        return new UpdateMemberResponse(mem.getId() , mem.getName());
    }

    @Data
    static class CreateMemberResponse{
        private Long id;
        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    private class CreateMemberRequest {
        private String name;
        public CreateMemberRequest(String name) {
            this.name = name;
        }
    }

    @Data
    @AllArgsConstructor
    private class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    private class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    private class Result<T> {
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    private class MemberDto {
        String name;
    }
}
