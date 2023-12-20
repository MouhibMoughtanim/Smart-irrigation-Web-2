package ma.smartwatering.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.smartwatering.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{
	
	
}
	