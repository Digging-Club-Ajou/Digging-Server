package server.service.melody_card;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import server.domain.album.Album;
import server.domain.melody_card.MelodyCard;
import server.global.exception.BadRequestException;
import server.global.exception.NotFoundException;
import server.mapper.melody_card.dto.MelodyCardRequest;
import server.mapper.melody_card.dto.MelodyCardResponse;
import server.repository.album.AlbumRepository;
import server.repository.melody_card.MelodyCardRepository;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static server.global.constant.ExceptionMessage.*;
import static server.global.constant.TextConstant.*;
import static server.global.constant.TimeConstant.ONE_HOUR;

@Profile({"prod", "dev"})
@Service
public class MelodyCardProdService {
    private MelodyCardRepository melodyCardRepository;

    public MelodyCardProdService(final MelodyCardRepository melodyCardRepository){
        this.melodyCardRepository = melodyCardRepository;
    }

    @Transactional
    public void deleteMelodyCardInfo (final Long melodyCardId) {
        melodyCardRepository.getById(melodyCardId);
        melodyCardRepository.deleteById(melodyCardId);
    }
}
