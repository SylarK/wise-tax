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

class Alpha2ProcessorTest {

    @InjectMocks
    private Alpha2Processor alpha2Processor;

    @Mock
    private BillingAccount billingAccount;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Processing when counterB is greater than 10")
    public void shouldProcessTakingIntoAccountDiscountBasedOnCounter() {
        Instant constantInstant = Instant.parse("2023-10-26T00:00:00Z");

        when(billingAccount.getBucket1()).thenReturn(0L);
        when(billingAccount.getBucket2()).thenReturn(0L);
        when(billingAccount.getBucket3()).thenReturn(0L);
        when(billingAccount.getCounterB()).thenReturn(15L);

        ChargingRequest chargingRequest = new ChargingRequest();
        chargingRequest.setRoaming(false);
        chargingRequest.setRsu(1);
        chargingRequest.setCreatedAt(constantInstant);

        alpha2Processor.processRequest(billingAccount, chargingRequest);

        verify(billingAccount, times(1)).setBucket3(4);
    }

    @Test
    @DisplayName("Processing when bucket2 is greater than 15 euros")
    public void shouldProcessTakingIntoAccountDiscountBucket2() {
        Instant constantInstant = Instant.parse("2023-10-26T00:00:00Z");
        when(billingAccount.getBucket1()).thenReturn(0L);
        when(billingAccount.getBucket2()).thenReturn(0L);
        when(billingAccount.getBucket3()).thenReturn(2000L);
        when(billingAccount.getCounterB()).thenReturn(5L);

        ChargingRequest chargingRequest = new ChargingRequest();
        chargingRequest.setRoaming(false);
        chargingRequest.setRsu(1);
        chargingRequest.setCreatedAt(constantInstant);

        alpha2Processor.processRequest(billingAccount, chargingRequest);

        verify(billingAccount, times(1)).setBucket3(2025);
    }
}