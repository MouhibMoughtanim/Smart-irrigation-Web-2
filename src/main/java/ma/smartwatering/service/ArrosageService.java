package ma.smartwatering.service;

import ma.smartwatering.model.Arrosage;

public interface ArrosageService {

	public Arrosage saveArrosage(Arrosage arossage);
	public Arrosage getArrosage (int id);

}
