//package fr.sweetiez.api.adapter.delivery.tray;
//
//import fr.sweetiez.api.core.trays.models.responses.BannerTrayResponse;
//import fr.sweetiez.api.core.trays.models.responses.DetailedTrayResponse;
//import fr.sweetiez.api.core.trays.models.responses.SimpleTrayResponse;
//import fr.sweetiez.api.core.trays.models.tray.states.Highlight;
//import fr.sweetiez.api.core.trays.services.TrayService;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Collection;
//import java.util.NoSuchElementException;
//
//public class TrayEndPoints {
//
//    private final TrayService trayService;
//
//    public TrayEndPoints(TrayService trayService) {
//        this.trayService = trayService;
//    }
//
//    public ResponseEntity<Collection<SimpleTrayResponse>> retrievePublishedTrays() {
//        var publishedSweets = trayService.retrievePublishedTrays().content()
//                .stream()
//                .map(SimpleTrayResponse::new)
//                .toList();
//
//        return ResponseEntity.ok(publishedSweets);
//    }
//
//    public ResponseEntity<DetailedTrayResponse> retrieveTrayDetails(String id) {
//        try {
//            return ResponseEntity.ok(trayService.retrieveTrayDetails(id));
//        }
//        catch (NoSuchElementException exception) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
//    public ResponseEntity<Collection<BannerTrayResponse>> retrieveBannerTrays() {
//        var bannerTrays = trayService.retrievePublishedTrays().content()
//                .stream()
//                .filter(tray -> tray.states().highlight() == Highlight.BANNER)
//                .map(BannerTrayResponse::new)
//                .toList();
//
//        return ResponseEntity.ok(bannerTrays);
//    }
//}
