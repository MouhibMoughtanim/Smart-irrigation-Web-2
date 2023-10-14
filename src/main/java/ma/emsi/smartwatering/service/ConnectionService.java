package ma.emsi.smartwatering.service;

import java.util.List;


import ma.emsi.smartwatering.model.Connection;


public interface ConnectionService {
	
	Connection saveConnection(Connection connection);
	
	List<Connection> getConnections();

	Connection getConnection(long id);

	void supprimer(long id);
}
