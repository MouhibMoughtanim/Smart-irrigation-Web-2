package ma.emsi.smartwatering.service;

import ma.emsi.smartwatering.model.Arrosage;

public interface ArrosageService {

	public Arrosage saveArrosage(Arrosage arossage);
	public Arrosage getArrosage (int id);

}
