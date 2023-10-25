package pt.amado.wisetax.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.amado.wisetax.model.Buckets;
import pt.amado.wisetax.model.Counters;
import pt.amado.wisetax.model.entities.BillingAccount;
import pt.amado.wisetax.model.entities.ChargingRequest;
import pt.amado.wisetax.model.entities.mdb.ClientDataRecord;
import pt.amado.wisetax.model.enums.Tariff;
import pt.amado.wisetax.repository.mdb.ClientDataRecordRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClientDataRecordService {

    ClientDataRecordRepository clientDataRecordRepository;

    void saveTransaction(BillingAccount account, ChargingRequest request, Tariff tariff) {
        clientDataRecordRepository.save(Objects.requireNonNull(generateClientDataRecord(account, request, tariff)));
    }

    private ClientDataRecord generateClientDataRecord(BillingAccount account, ChargingRequest request, Tariff tariff) {
        return ClientDataRecord.builder()
                .buckets(new Buckets(account))
                .counters(new Counters(account))
                .createdAt(request.getCreatedAt())
                .phoneNumber(account.getPhoneNumber())
                .tariff(tariff)
                .chargingRequest(request)
                .build();
    }

}
