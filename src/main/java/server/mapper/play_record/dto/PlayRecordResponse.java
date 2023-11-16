package server.mapper.play_record.dto;

import lombok.Getter;

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
}
