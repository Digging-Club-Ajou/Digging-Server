package server.mapper.music_recommendation.dto;

import server.domain.member.vo.Gender;

import java.util.Date;
import java.util.List;

public record AIResponse(
        long memberId,
        List<String> artistNames,
        List<String> genres,
        Gender gender,
        Date birthDate
) {
}
