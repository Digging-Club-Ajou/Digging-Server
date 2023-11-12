package server.service.melody_card;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import server.domain.member.vo.MemberSession;
import server.mapper.melody_card.dto.MelodyCardRequest;
import server.mapper.melody_card.dto.MelodyCardResponse;

import java.util.List;

@Service
public interface MelodyCardCreateService {

    void createMelodyCard(final long memberId, final MelodyCardRequest dto, final MultipartFile melodyCardImage);

    MelodyCardResponse getMelodyCard(final long melodyCardId);

    List<MelodyCardResponse> getMelodyCards(final long albumId);

}
