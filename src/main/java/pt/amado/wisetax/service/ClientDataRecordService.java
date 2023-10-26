package pt.amado.wisetax.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    void saveTransaction(BillingAccount account, ChargingRequest request, Tariff tariff) {
        clientDataRecordRepository.save(Objects.requireNonNull(generateClientDataRecord(account, request, tariff)));
    }

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

    public List<ClientDataRecord> findAllRecordsByPhoneNumber(String phoneNumber, String order) {
        if(phoneNumber.charAt(0) == ' ')
            phoneNumber = phoneNumber.replaceFirst(" ", "+");
        if ("ASC".equalsIgnoreCase(order))
            return clientDataRecordRepository.findByPhoneNumberOrderByCreatedAtAsc(phoneNumber);
        return clientDataRecordRepository.findByPhoneNumberOrderByCreatedAtDesc(phoneNumber);
    }
}