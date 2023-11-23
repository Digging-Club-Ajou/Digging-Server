package server.mapper.play_record.dto;

import lombok.Getter;
import server.global.constant.ExceptionMessage;
import server.global.exception.BadRequestException;

import static server.global.constant.ExceptionMessage.NOT_ENOUGH_PLAY_RECORD;

@Getter
public class PlayRecordResponse {

    private String genre;
    private String artistName;
    private String songTitle;

    public void addArtistName(final String artistName) {
        this.artistName = artistName;
    }

    public void addSongTitle(final String songTitle) {
        this.songTitle = songTitle;
    }

    public void addGenre(final String genre) {
        this.genre = genre;
    }

    public void validate() {
        if (genre == null || artistName == null || songTitle == null) {
            throw new BadRequestException(NOT_ENOUGH_PLAY_RECORD.message);
        }
    }
}
