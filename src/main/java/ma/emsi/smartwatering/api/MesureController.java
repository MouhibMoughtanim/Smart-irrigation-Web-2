package ma.emsi.smartwatering.api;
import ma.smartwatering.model.mesure;
import ma.smartwatering.service.MesureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mesures")
public class MesureController {

    private final MesureService mesureService;

    @Autowired
    public MesureController(MesureService mesureService) {
        this.mesureService = mesureService;
    }
    @GetMapping("/m")


    public ResponseEntity<List<mesure>> getAllMesures() {
        List<mesure> mesures = mesureService.getAllMesures();

        if (mesures.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(mesures);
        }
    }
}
