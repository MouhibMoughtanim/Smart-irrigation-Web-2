package ma.emsi.smartwatering.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.emsi.smartwatering.model.AppUser;
import ma.emsi.smartwatering.model.Arrosage;
import ma.emsi.smartwatering.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{
	
	
}
	