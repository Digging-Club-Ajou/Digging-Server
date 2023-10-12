package server.controller.location;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.mapper.location.LocationResponse;
import server.mapper.location.LocationResult;
import server.service.location.LocationProdService;
import server.service.location.LocationService;

import java.util.List;

@RequestMapping("/api")
@RestController
public class LocationController {

    private final LocationService locationService;

    public LocationController(final LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/location")
    public LocationResult findLocation(@RequestParam final String query,
                                       @RequestParam final String x,
                                       @RequestParam final String y) {
        List<LocationResponse> locationResponses = locationService.findLocation(query, x, y);
        return new LocationResult(locationResponses);
    }
}
