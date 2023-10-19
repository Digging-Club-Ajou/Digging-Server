package server.service.artist;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import server.domain.artist.ArtistInfo;
import server.repository.artist.ArtistInfoRepository;
import server.service.spotify.SpotifySearchArtistProdService;

import java.io.*;
import java.util.List;


@Service
public class ArtistInfoCreateService {

    private ArtistInfoRepository artistInfoRepository;
    private SpotifySearchArtistProdService spotifySearchArtistProdService;



    public ArtistInfoCreateService(final ArtistInfoRepository artistInfoRepository, final SpotifySearchArtistProdService spotifySearchArtistProdService){
        this.artistInfoRepository = artistInfoRepository;
        this.spotifySearchArtistProdService = spotifySearchArtistProdService;

    }
    @Transactional
    public void createArtistInfo(MultipartFile file) {

        try {
            List<ArtistInfo> artistInfoList = CSVHelper.csvToArtistInfos(file.getInputStream());
            artistInfoList.forEach(artistInfo -> {
                String imageURL = spotifySearchArtistProdService.searchArtist(artistInfo.getName());
                artistInfo.setImageURL(imageURL);
            });
            artistInfoRepository.saveAll(artistInfoList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }



}
