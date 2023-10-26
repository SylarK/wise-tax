package pt.amado.wisetax.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.amado.wisetax.exception.UserNotFoundException;
import pt.amado.wisetax.model.entities.BillingAccount;
import pt.amado.wisetax.model.entities.ChargingRequest;
import pt.amado.wisetax.model.entities.mdb.ClientDataRecord;
import pt.amado.wisetax.model.enums.Tariff;
import pt.amado.wisetax.repository.mdb.ClientDataRecordRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClientDataRecordService {

    private final ClientDataRecordRepository clientDataRecordRepository;

    /**
     * Saves a transaction record in the database based on the provided BillingAccount, ChargingRequest, and Tariff.
     *
     * @param account The BillingAccount associated with the transaction.
     * @param request The ChargingRequest related to the transaction.
     * @param tariff The Tariff associated with the transaction.
     */
    void saveTransaction(BillingAccount account, ChargingRequest request, Tariff tariff) {
        clientDataRecordRepository.save(Objects.requireNonNull(generateClientDataRecord(account, request, tariff)));
    }

    /**
     * Generates a new ClientDataRecord based on the provided BillingAccount, ChargingRequest, and Tariff.
     *
     * @param account The BillingAccount data to be included in the record.
     * @param request The ChargingRequest data to be included in the record.
     * @param tariff The Tariff associated with the record.
     * @return The generated ClientDataRecord.
     */
    private ClientDataRecord generateClientDataRecord(BillingAccount account, ChargingRequest request, Tariff tariff) {
        return ClientDataRecord.builder()
                .bucket1(account.getBucket1())
                .bucket2(account.getBucket2())
                .bucket3(account.getBucket3())
                .counterA(account.getCounterA())
                .counterB(account.getCounterB())
                .counterC(account.getCounterC())
                .createdAt(request.getCreatedAt())
                .phoneNumber(account.getPhoneNumber())
                .tariff(tariff)
                .chargingRequest(request)
                .build();
    }

    /**
     * Finds and retrieves all ClientDataRecord entries in the database associated with a provided phone number.
     *
     * @param phoneNumber The phone number to search for associated records.
     * @param order The order in which to retrieve the records (ASC for ascending, DESC for descending).
     * @return A list of ClientDataRecord entries matching the phone number, sorted as specified.
     */
    public List<ClientDataRecord> findAllRecordsByPhoneNumber(String phoneNumber, String order) throws UserNotFoundException {
        if(phoneNumber.charAt(0) == ' ')
            phoneNumber = phoneNumber.replaceFirst(" ", "+");

        List<ClientDataRecord> result;
        result = "ASC".equalsIgnoreCase(order) ? clientDataRecordRepository.findByPhoneNumberOrderByCreatedAtAsc(phoneNumber) : clientDataRecordRepository.findByPhoneNumberOrderByCreatedAtDesc(phoneNumber);

        if(result.isEmpty())
            throw new UserNotFoundException("There is no user linked to the following phone number : " + phoneNumber);
        return result;
    }
}
