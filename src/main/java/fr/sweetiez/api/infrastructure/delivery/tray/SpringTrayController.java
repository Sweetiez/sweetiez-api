//package fr.sweetiez.api.infrastructure.delivery.tray;
//
//import fr.sweetiez.api.adapter.delivery.sweet.SweetEndPoints;
//import fr.sweetiez.api.core.sweets.models.responses.BannerSweetResponse;
//import fr.sweetiez.api.core.sweets.models.responses.DetailedSweetResponse;
//import fr.sweetiez.api.core.sweets.models.responses.SimpleSweetResponse;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Collection;
//
//@RestController
//@RequestMapping("/trays")
//public class SpringTrayController {
//
//    private final SweetEndPoints sweetEndPoints;
//
//    public SpringTrayController(SweetEndPoints sweetEndPoints) {
//        this.sweetEndPoints = sweetEndPoints;
//    }
//
//    @GetMapping("/published")
//    public ResponseEntity<Collection<SimpleSweetResponse>> retrievePublishedSweets() {
//        return sweetEndPoints.retrievePublishedSweets();
//    }
//
//    @GetMapping("/banner")
//    public ResponseEntity<Collection<BannerSweetResponse>> retrieveBannerSweets() {
//        return sweetEndPoints.retrieveBannerSweets();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<DetailedSweetResponse> retrieveSweetDetails(@PathVariable("id") String id) {
//        return sweetEndPoints.retrieveSweetDetails(id);
//    }
//
//}
