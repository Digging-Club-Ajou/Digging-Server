package server.service.location;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import server.mapper.location.LocationResponse;

import java.util.ArrayList;
import java.util.List;

// Test Double
@Profile("test")
@Service
public class LocationTestService implements LocationService {

    // API 문서화 용도
    @Override
    public List<LocationResponse> findLocation(final String query, final String x,
                                               final String y, final int page) {
        LocationResponse locationResponse1 =
                new LocationResponse("아주대학교 중앙도서관", "140m");
        LocationResponse locationResponse2 =
                new LocationResponse("KB국민은행 365코너 아주대학교(도서관)", "148m");
        LocationResponse locationResponse3 =
                new LocationResponse("유신고등학교 도서관", "198m");
        LocationResponse locationResponse4 =
                new LocationResponse("아주대학교 법학전문도서관", "397m");
        LocationResponse locationResponse5 =
                new LocationResponse("물근원마을문고", "596m");

        List<LocationResponse> locationResponses = new ArrayList<>();
        locationResponses.add(locationResponse1);
        locationResponses.add(locationResponse2);
        locationResponses.add(locationResponse3);
        locationResponses.add(locationResponse4);
        locationResponses.add(locationResponse5);

        return locationResponses;
    }
}
