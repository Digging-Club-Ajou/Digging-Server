package server.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import server.repository.member.MemberRepository;

@Profile("test")
@AcceptanceTest
public abstract class ServiceTest {

    @Autowired
    protected MemberRepository memberRepository;
}
