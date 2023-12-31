package server.service.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import server.domain.notification.vo.FcmMessage;
import server.domain.notification.vo.Message;
import server.domain.notification.vo.Notification;
import server.global.exception.BadRequestException;

import java.io.IOException;

import static server.global.constant.ExceptionMessage.NOTIFICATION_BAD_REQUEST;
import static server.global.constant.ExceptionMessage.NOTIFICATION_MESSAGE_BAD_REQUEST;
import static server.global.constant.NotificationConstant.*;

@Service
public class FirebaseCloudMessageService {

    private final ObjectMapper objectMapper;

    public FirebaseCloudMessageService(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void sendMessageTo(final String targetToken, final String title, final String body) {
        String message = makeMessage(targetToken, title, body);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get(MEDIA_TYPE_JSON_UTF_8));

        Request request;
        request = new Request.Builder()
                .url(NOTIFICATION_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, BEARER + targetToken)
                .addHeader(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON_UTF_8)
                .build();

        try {
            Response response = client.newCall(request)
                    .execute();
        } catch (IOException e) {
            throw new BadRequestException(NOTIFICATION_BAD_REQUEST.message);
        }
    }

    private String makeMessage(final String targetToken, final String title, final String body) {

        Notification notification = new Notification(title, body, null);
        Message message = new Message(notification, targetToken);
        FcmMessage fcmMessage = new FcmMessage(false, message);

        try {
            return objectMapper.writeValueAsString(fcmMessage);
        } catch (JsonProcessingException e) {
            throw new BadRequestException(NOTIFICATION_MESSAGE_BAD_REQUEST.message);
        }
    }
}
