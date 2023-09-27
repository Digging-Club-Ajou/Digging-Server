package server.controller.profile;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.service.profile.ProfileCreateService;

@RequestMapping("/api")
@RestController
public class ProfileController {

    private final ProfileCreateService profileCreateService;

    public ProfileController(final ProfileCreateService profileCreateService) {
        this.profileCreateService = profileCreateService;
    }

    @PostMapping("/profiles")
    public void createProfiles(@Login final MemberSession memberSession,
                               @RequestPart final MultipartFile profileImage) {
        profileCreateService.createProfiles(memberSession.id(), profileImage);
    }
}
