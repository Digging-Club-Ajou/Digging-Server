package server.controller.archieve;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.mapper.archive.dto.ArchiveRequest;
import server.service.archive.ArchiveRequestService;

@RequestMapping("/api")
@RestController
public class ArchiveController {

    private final ArchiveRequestService archiveRequestService;

    public ArchiveController(final ArchiveRequestService archiveRequestService) {
        this.archiveRequestService = archiveRequestService;
    }

    @PostMapping("/archive")
    public void createArchive(@Login final MemberSession memberSession,
                              @RequestBody final ArchiveRequest dto) {
        archiveRequestService.requestArchive(memberSession.id(), dto);
    }
}
