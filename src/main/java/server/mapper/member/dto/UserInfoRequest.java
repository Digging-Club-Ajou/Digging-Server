package server.mapper.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.joda.time.DateTime;
import server.domain.member.vo.Gender;

import java.sql.Date;

public record UserInfoRequest(
        Gender gender,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date birthDate
) {
}
