package ma.emsi.smartwatering.service;

import ma.emsi.smartwatering.model.mesure;
import ma.emsi.smartwatering.repository.MesureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesureService {

    private final MesureRepository mesureRepository;

    @Autowired
    public MesureService(MesureRepository mesureRepository) {
        this.mesureRepository = mesureRepository;
    }

    public List<mesure> getAllMesures() {
        return mesureRepository.findAll();
    }

    // Vous pouvez ajouter d'autres méthodes de service en fonction de vos besoins
}
