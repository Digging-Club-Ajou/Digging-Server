package server.service.artist;

import com.amazonaws.services.s3.model.CSVInput;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.transaction.Transactional;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.step.item.ChunkProvider;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.util.FileUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import server.domain.artist.ArtistInfo;
import server.domain.artist.Era;
import server.global.exception.BadRequestException;
import server.mapper.artist.dto.ArtistInfoDto;
import server.repository.artist.ArtistInfoRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static server.global.constant.ExceptionMessage.PROFILES_SAVE_EXCEPTION;
import static server.global.constant.TextConstant.ALBUM_IMAGE;
import static server.global.constant.TextConstant.DIGGING_CLUB;

@Service
public class ArtistInfoCreateService {

    private ArtistInfoRepository artistInfoRepository;



    public ArtistInfoCreateService(final ArtistInfoRepository artistInfoRepository){
        this.artistInfoRepository = artistInfoRepository;
    }
    @Transactional
    public void createArtistInfo(MultipartFile file) {

        try {
            List<ArtistInfo> artistInfoList = CSVHelper.csvToArtistInfos(file.getInputStream());
            //todo
            //spotify api 추가
            artistInfoList.forEach(artistInfo -> {

            });
            artistInfoRepository.saveAll(artistInfoList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        items.open(new ExecutionContext());
//
//        items.getClass();

    }




}
