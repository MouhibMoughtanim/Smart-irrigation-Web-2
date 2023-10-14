package ma.emsi.smartwatering.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.emsi.smartwatering.model.Connection;
import ma.emsi.smartwatering.repository.ConnectionRepository;

@Service
public class ConnectionServiceImpl implements ConnectionService{

	@Autowired
	private ConnectionRepository connectionRepo;
	
	@Override
	public Connection saveConnection(Connection connection) {
		return connectionRepo.save(connection);
	}

	@Override
	public List<Connection> getConnections() {
		return connectionRepo.findAll();
	}

	@Override
	public Connection getConnection(long id) {
		return connectionRepo.getById(id);
	}

	@Override
	public void supprimer(long id) {
		connectionRepo.deleteById(id);
	}

}
