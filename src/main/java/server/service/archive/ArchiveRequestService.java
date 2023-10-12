package server.service.archive;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import server.mapper.archive.dto.ArchiveRequest;

@Service
public class ArchiveRequestService {

    private final RestTemplate restTemplate;
    private final ArchiveCreateService archiveCreateService;

    public ArchiveRequestService(final RestTemplate restTemplate,
                                 final ArchiveCreateService archiveCreateService) {
        this.restTemplate = restTemplate;
        this.archiveCreateService = archiveCreateService;
    }

    public void requestArchive(final long memberId, final ArchiveRequest dto) {
        archiveCreateService.createArchive(memberId, dto);

        // todo RestTemplate 요청
    }
}
