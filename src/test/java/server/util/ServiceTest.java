package server.util;

import org.springframework.beans.factory.annotation.Autowired;
import server.repository.member.MemberRepository;

@AcceptanceTest
public abstract class ServiceTest {

    @Autowired
    protected MemberRepository memberRepository;
}
