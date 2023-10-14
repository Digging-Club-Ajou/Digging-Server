package server.mapper.melody_card.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MelodyCardResponse {

    private long memberId;
    private String nickname;
    private String singer;
    private String songTitle;
    private String streamingLink;
    private String address;
    private String cardDescription;
    private String color;
    private String imageUrl;

    protected MelodyCardResponse() {
    }

    @Builder
    private MelodyCardResponse(final long memberId, final String nickname, final String singer,
                              final String songTitle, final String streamingLink, final String address,
                              final String cardDescription, final String color, final String imageUrl) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.singer = singer;
        this.songTitle = songTitle;
        this.streamingLink = streamingLink;
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