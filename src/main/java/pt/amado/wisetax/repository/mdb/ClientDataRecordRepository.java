package pt.amado.wisetax.repository.mdb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pt.amado.wisetax.model.entities.mdb.ClientDataRecord;

@Repository
public interface ClientDataRecordRepository extends MongoRepository<ClientDataRecord, String> {
}
