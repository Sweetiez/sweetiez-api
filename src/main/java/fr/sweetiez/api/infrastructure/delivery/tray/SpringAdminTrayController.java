//package fr.sweetiez.api.infrastructure.delivery.tray;
//
//import fr.sweetiez.api.adapter.delivery.tray.AdminTrayEndPoints;
//import fr.sweetiez.api.core.sweets.models.requests.UpdateSweetRequest;
//import fr.sweetiez.api.core.sweets.models.responses.AdminDetailedSweetResponse;
//import fr.sweetiez.api.core.sweets.models.responses.SimpleSweetResponse;
//import fr.sweetiez.api.core.trays.models.requests.*;
//import fr.sweetiez.api.core.trays.models.responses.AdminDetailedTrayResponse;
//import fr.sweetiez.api.core.trays.models.responses.AdminTraySimpleResponse;
//import fr.sweetiez.api.core.trays.models.responses.SimpleTrayResponse;
//import fr.sweetiez.api.core.trays.models.tray.Tray;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.Collection;
//
//@RestController
//@RequestMapping("/admin/trays")
//public class SpringAdminTrayController {
//
//    private final AdminTrayEndPoints trayEndPoints;
//
//    public SpringAdminTrayController(AdminTrayEndPoints trayEndPoints) {
//        this.trayEndPoints = trayEndPoints;
//    }
//
//    @PostMapping
//    public ResponseEntity<Object> create(@RequestBody CreateTrayRequest request) {
//        return trayEndPoints.create(request);
//    }
//
//    @GetMapping()
//    public ResponseEntity<Collection<AdminTraySimpleResponse>> adminRetrieveAllTrays() {
//        return trayEndPoints.retrieveAllTrays();
//    }
//
//    @PutMapping("/publish")
//    public ResponseEntity<Tray> publish(@RequestBody PublishTrayRequest request) {
//        return trayEndPoints.publish(request);
//    }
//
//    @DeleteMapping("/publish")
//    public ResponseEntity<Tray> unpublish(@RequestBody UnpublishTrayRequest request) {
//        return trayEndPoints.unpublish(request);
//    }
//
//    @PostMapping("/{id}/image")
//    public ResponseEntity<SimpleTrayResponse> addImage(@PathVariable("id") String id, @RequestParam MultipartFile image) {
//        return trayEndPoints.addImage(id, image);
//    }
//
//    @DeleteMapping("/{id}/image")
//    public ResponseEntity<SimpleTrayResponse> deleteImage(@PathVariable("id") String id, @RequestBody DeleteImageRequest request) {
//        return trayEndPoints.deleteImage(id, request);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<AdminDetailedTrayResponse> adminRetrieveTrayDetails(@PathVariable("id") String id) {
//        return trayEndPoints.adminRetrieveTrayDetails(id);
//    }
//
//    @PutMapping()
//    public ResponseEntity<AdminDetailedTrayResponse> adminUpdateTray(@RequestBody UpdateTrayRequest request) {
//        return trayEndPoints.adminUpdateSweetDetails(request);
//    }
//
//}
