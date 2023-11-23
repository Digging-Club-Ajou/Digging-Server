package server.controller.member_search;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import server.mapper.member_search.dto.MemberSearchResponses;
import server.service.member_search.MemberSearchService;

@Slf4j
@RequestMapping("/api")
@RestController
public class MemberSearchController {

    private MemberSearchService memberSearchService;

    public MemberSearchController(final MemberSearchService memberSearchService){
        this.memberSearchService =  memberSearchService;
    }

    @GetMapping("/member/search")
    public MemberSearchResponses getMemberSearchList(@RequestParam final String keyword){
        return memberSearchService.getMemberSearchList(keyword);
    }
}
