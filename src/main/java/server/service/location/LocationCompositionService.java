package server.service.location;

import org.springframework.stereotype.Service;
import server.mapper.location.LocationResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationCompositionService {

    private final LocationService locationService;

    public LocationCompositionService(final LocationService locationService) {
        this.locationService = locationService;
    }

    public List<LocationResponse> findLocation(final String query, final String x, final String y, final int page){
        List<LocationResponse> locationBeforeResponses;
        List<LocationResponse> locationAfterResponses = locationService.findLocation(query, x, y, page);

        if (page > 1) {
            locationBeforeResponses = locationService.findLocation(query, x, y, page - 1);
            String beforeDistance = locationBeforeResponses.get(0).placeName();
            String afterDistance = locationAfterResponses.get(0).placeName();
            if (beforeDistance.equals(afterDistance)) {
                return new ArrayList<>();
            }
        }

        return locationAfterResponses;
    }
}
