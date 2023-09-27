package server.domain.member.persist;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.domain.BaseTimeEntity;
import server.domain.member.vo.Gender;
import server.domain.member.vo.LoginType;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String username;

    private String nickname;

    private String loginId;

    private String password;

    private String phoneNumber;

    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Builder
    private Member(final String username, final String nickname, final String loginId, final String password,
                  final String phoneNumber, final String email, final Gender gender, final LoginType loginType) {
        this.username = username;
        this.nickname = nickname;
        this.loginId = loginId;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.loginType = loginType;
    }

    public void createNickname(final String nickname) {
        this.nickname = nickname;
    }
}
