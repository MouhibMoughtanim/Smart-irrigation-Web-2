package ma.smartwatering.service;

import java.util.List;


import ma.smartwatering.model.Connection;


public interface ConnectionService {
	
	Connection saveConnection(Connection connection);
	
	List<Connection> getConnections();

	Connection getConnection(long id);

	void supprimer(long id);
}
