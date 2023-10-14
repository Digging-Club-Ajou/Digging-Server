package server.mapper.melody_card.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MelodyCardResponse {

    private long memberId;
    private String nickname;
    private String artistName;
    private String songTitle;
    private String previewUrl;
    private String imageUrl;
    private String address;
    private String cardDescription;
    private String color;

    protected MelodyCardResponse() {
    }

    @Builder
    private MelodyCardResponse(final long memberId, final String nickname, final String artistName,
                              final String songTitle, final String previewUrl, final String address,
                              final String cardDescription, final String color, final String imageUrl) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.artistName = artistName;
        this.songTitle = songTitle;
        this.previewUrl = previewUrl;
        this.address = address;
        this.cardDescription = cardDescription;
        this.color = color;
        this.imageUrl = imageUrl;
    }

    public MelodyCardResponse updateUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}