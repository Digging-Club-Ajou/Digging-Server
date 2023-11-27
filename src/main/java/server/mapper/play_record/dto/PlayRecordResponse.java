package server.mapper.play_record.dto;

import lombok.Getter;
import server.global.exception.BadRequestException;

import static server.global.constant.ExceptionMessage.NOT_ENOUGH_PLAY_RECORD;

@Getter
public class PlayRecordResponse {

    private String genre;
    private String mostPlayedArtistName;
    private String favoriteArtistName;
    private String songTitle;

    public void addMostPlayedArtistName(final String mostPlayedArtistName) {
        this.mostPlayedArtistName = mostPlayedArtistName;
    }

    public void addFavoriteArtistName(final String favoriteArtistName) {
        this.favoriteArtistName = favoriteArtistName;
    }

    public void addSongTitle(final String songTitle) {
        this.songTitle = songTitle;
    }

    public void addGenre(final String genre) {
        this.genre = genre;
    }

    public void validate() {
        if (genre == null || mostPlayedArtistName == null || favoriteArtistName == null || songTitle == null) {
            throw new BadRequestException(NOT_ENOUGH_PLAY_RECORD.message);
        }
    }
}
