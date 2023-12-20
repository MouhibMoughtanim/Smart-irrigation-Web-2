package ma.smartwatering.service;

import java.util.List;

import ma.smartwatering.model.Connection;
import ma.smartwatering.repository.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectionServiceImpl implements ConnectionService{


	private final ConnectionRepository connectionRepo;
	@Autowired
	public ConnectionServiceImpl(ConnectionRepository connectionRepo) {
		this.connectionRepo = connectionRepo;
	}

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
