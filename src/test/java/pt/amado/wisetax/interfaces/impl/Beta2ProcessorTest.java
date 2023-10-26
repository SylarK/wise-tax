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

class Beta2ProcessorTest {

    @InjectMocks
    private Beta2Processor beta2Processor;

    @Mock
    private BillingAccount billingAccount;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Processing in local mode without discounts during the day")
    public void shouldProcessLocalWithoutDiscountsDuringDay() {
        Instant constantInstant = Instant.parse("2023-10-26T12:00:00Z");

        when(billingAccount.getBucket2()).thenReturn(0L);
        when(billingAccount.getCounterB()).thenReturn(5L);

        ChargingRequest chargingRequest = new ChargingRequest();
        chargingRequest.setRoaming(false);
        chargingRequest.setRsu(1);
        chargingRequest.setCreatedAt(constantInstant);

        beta2Processor.processRequest(billingAccount, chargingRequest);

        verify(billingAccount, times(1)).setBucket2(5);
    }

    @Test
    @DisplayName("Processing in local mode with discounts during the night")
    public void shouldProcessLocalWithDiscountsDuringNight() {
        Instant constantInstant = Instant.parse("2023-10-26T02:00:00Z");

        when(billingAccount.getBucket2()).thenReturn(2000L);
        when(billingAccount.getCounterB()).thenReturn(15L);

        ChargingRequest chargingRequest = new ChargingRequest();
        chargingRequest.setRoaming(false);
        chargingRequest.setRsu(1);
        chargingRequest.setCreatedAt(constantInstant);

        beta2Processor.processRequest(billingAccount, chargingRequest);

        verify(billingAccount, times(1)).setBucket2(2000);
    }

}