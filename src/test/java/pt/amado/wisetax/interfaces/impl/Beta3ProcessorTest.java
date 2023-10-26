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

class Beta3ProcessorTest {

    @InjectMocks
    private Beta3Processor beta3Processor;

    @Mock
    private BillingAccount billingAccount;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Processing in local mode during the day")
    public void shouldProcessLocalDuringDay() {
        Instant constantInstant = Instant.parse("2023-10-26T14:00:00Z");

        when(billingAccount.getBucket3()).thenReturn(0L);
        when(billingAccount.getCounterC()).thenReturn(5L);

        ChargingRequest chargingRequest = new ChargingRequest();
        chargingRequest.setRoaming(false);
        chargingRequest.setRsu(1);
        chargingRequest.setCreatedAt(constantInstant);

        beta3Processor.processRequest(billingAccount, chargingRequest);

        verify(billingAccount, times(1)).setBucket3(10);
    }

    @Test
    @DisplayName("Processing in local mode with discounts during the weekend")
    public void shouldProcessLocalWithDiscountsDuringWeekend() {
        Instant constantInstant = Instant.parse("2023-10-29T12:00:00Z");

        when(billingAccount.getBucket3()).thenReturn(2000L);
        when(billingAccount.getCounterC()).thenReturn(15L);

        ChargingRequest chargingRequest = new ChargingRequest();
        chargingRequest.setRoaming(false);
        chargingRequest.setRsu(1);
        chargingRequest.setCreatedAt(constantInstant);

        beta3Processor.processRequest(billingAccount, chargingRequest);

        verify(billingAccount, times(1)).setBucket3(2000L);
    }

}