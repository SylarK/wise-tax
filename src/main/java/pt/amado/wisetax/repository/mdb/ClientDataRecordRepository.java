package pt.amado.wisetax.repository.mdb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pt.amado.wisetax.model.entities.mdb.ClientDataRecord;

import java.util.List;

@Repository
public interface ClientDataRecordRepository extends MongoRepository<ClientDataRecord, String> {

    List<ClientDataRecord> findByPhoneNumberOrderByCreatedAtAsc(String phoneNumber);

    List<ClientDataRecord> findByPhoneNumberOrderByCreatedAtDesc(String phoneNumber);

}
