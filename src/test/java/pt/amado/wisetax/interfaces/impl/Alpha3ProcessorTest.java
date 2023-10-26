package pt.amado.wisetax.interfaces.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pt.amado.wisetax.model.entities.BillingAccount;
import pt.amado.wisetax.model.entities.ChargingRequest;

import java.time.Instant;

import static org.mockito.Mockito.*;

class Alpha3ProcessorTest {

    @InjectMocks
    private Alpha3Processor alpha3Processor;

    @Mock
    private BillingAccount billingAccount;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Processing with counter C greater than discount threshold")
    public void shouldProcessWithCounterCDiscount() {
        Instant constantInstant = Instant.parse("2023-10-26T00:00:00Z");

        when(billingAccount.getBucket3()).thenReturn(0L);
        when(billingAccount.getCounterC()).thenReturn(15L);

        ChargingRequest chargingRequest = new ChargingRequest();
        chargingRequest.setCreatedAt(constantInstant);
        chargingRequest.setRsu(1);

        alpha3Processor.processRequest(billingAccount, chargingRequest);

        verify(billingAccount, times(1)).setBucket3(80);
    }

    @Test
    @DisplayName("Processing with balance in bucket3 greater than discount threshold")
    public void shouldProcessWithBucket3Discount() {
        Instant constantInstant = Instant.parse("2023-10-26T00:00:00Z");

        when(billingAccount.getBucket3()).thenReturn(2000L);
        when(billingAccount.getCounterC()).thenReturn(5L);

        ChargingRequest chargingRequest = new ChargingRequest();
        chargingRequest.setCreatedAt(constantInstant);
        chargingRequest.setRsu(1);

        alpha3Processor.processRequest(billingAccount, chargingRequest);

        verify(billingAccount, times(1)).setBucket3(2095);
    }

}