package server.service.artist;

import org.springframework.web.multipart.MultipartFile;
import server.domain.artist.ArtistInfo;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import server.domain.artist.Era;
import server.domain.genre.Genre;
import server.domain.member.vo.Gender;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = {"id","name", "nation","gender", "group", "era", "genre","popularity" };

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<ArtistInfo> csvToArtistInfos(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<ArtistInfo> artistInfoList = new ArrayList<ArtistInfo>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {

                List<Era> eras = new ArrayList<Era>();
                Era era = Era.builder().era(csvRecord.get("era")).build();
                eras.add(era);

                Genre genre = Genre.builder().build();

                ArtistInfo artistInfo =  ArtistInfo.builder()
                        .name(csvRecord.get("name"))
                        .nation(csvRecord.get("nation"))
                        .gender(Gender.getGender(csvRecord.get("gender")))
                        .group(csvRecord.get("group"))
//                        .genre(genre)
                        .popularity(Integer.parseInt(csvRecord.get("popularity")))
                        .build();

                artistInfoList.add(artistInfo);
            }

            return artistInfoList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
