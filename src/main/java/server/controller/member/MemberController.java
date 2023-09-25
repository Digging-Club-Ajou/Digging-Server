package server.controller.member;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.mapper.member.dto.MemberSignupRequest;
import server.mapper.member.dto.NicknameRequest;
import server.service.member.MemberSignupService;
import server.service.member.NicknameCreateService;
import server.service.member.ProfilesSaveService;

import java.io.IOException;

@RequestMapping("/api")
@RestController
public class MemberController {

    private final MemberSignupService memberSignupService;
    private final NicknameCreateService nicknameCreateService;

    private final ProfilesSaveService profilesSaveService;
    private final AmazonS3 amazonS3Client;

    public MemberController(final MemberSignupService memberSignupService,
                            final NicknameCreateService nicknameCreateService,
                            final ProfilesSaveService profilesSaveService,
                            final AmazonS3 amazonS3Client) {
        this.memberSignupService = memberSignupService;
        this.nicknameCreateService = nicknameCreateService;
        this.profilesSaveService = profilesSaveService;
        this.amazonS3Client = amazonS3Client;
    }

    @PostMapping("/signup")
    public void signup(@RequestBody final MemberSignupRequest dto) {
        memberSignupService.signup(dto);
    }

    @PostMapping("/nickname")
    public void createNickname(@Login final MemberSession memberSession,
                               @RequestBody @Valid final NicknameRequest dto) {
        nicknameCreateService.createNickname(memberSession.id(), dto);
    }

    @PostMapping("/profiles")
    public void createProfiles(@Login final MemberSession memberSession,
                               @RequestPart final MultipartFile multipartFile) {
        profilesSaveService.saveProfiles(memberSession.id(), multipartFile, amazonS3Client);
    }
}
