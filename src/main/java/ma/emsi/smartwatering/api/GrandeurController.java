package ma.emsi.smartwatering.api;
import ma.emsi.smartwatering.model.Grandeur;
import ma.emsi.smartwatering.model.mesure;
import ma.emsi.smartwatering.service.GrandeurService;
import ma.emsi.smartwatering.service.MesureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mesures")
public class GrandeurController {

    private final MesureService mesureService;
    private final GrandeurService grandeurService;

    @Autowired
    public GrandeurController(MesureService mesureService, GrandeurService grandeurService) {
        this.mesureService = mesureService;
        this.grandeurService = grandeurService;
    }
    @GetMapping("/m/{zone_id}")


    public ResponseEntity<List<Grandeur>> getMesures(@PathVariable Long zone_id) {
        List<Grandeur> mesures = grandeurService.getGrandeurByZone(zone_id);

        if (mesures.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(mesures);
        }
    }
    @GetMapping("/m/All")
    public ResponseEntity<List<Grandeur>> getMesuresAll() {
        List<Grandeur> mesures = grandeurService.getGrandeurs();

        if (mesures.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(mesures);
        }
    }






}
