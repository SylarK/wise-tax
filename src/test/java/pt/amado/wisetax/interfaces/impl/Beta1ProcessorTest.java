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

class Beta1ProcessorTest {

    @InjectMocks
    private Beta1Processor beta1Processor;

    @Mock
    private BillingAccount billingAccount;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Processing in local mode without discounts")
    public void shouldProcessLocalWithoutDiscounts() {
        Instant constantInstant = Instant.parse("2023-10-26T00:00:00Z");

        when(billingAccount.getBucket1()).thenReturn(0L);
        when(billingAccount.getBucket2()).thenReturn(0L);
        when(billingAccount.getBucket3()).thenReturn(0L);
        when(billingAccount.getCounterA()).thenReturn(5L);

        ChargingRequest chargingRequest = new ChargingRequest();
        chargingRequest.setRoaming(false);
        chargingRequest.setRsu(1);
        chargingRequest.setCreatedAt(constantInstant);

        beta1Processor.processRequest(billingAccount, chargingRequest);

        verify(billingAccount, times(1)).setBucket1(10);
    }

    @Test
    @DisplayName("Processing in roaming mode with discounts")
    public void shouldProcessRoamingWithDiscounts() {
        Instant constantInstant = Instant.parse("2023-10-26T00:00:00Z");

        when(billingAccount.getBucket1()).thenReturn(0L);
        when(billingAccount.getBucket2()).thenReturn(5000L);
        when(billingAccount.getBucket3()).thenReturn(2000L);
        when(billingAccount.getCounterA()).thenReturn(15L);

        ChargingRequest chargingRequest = new ChargingRequest();
        chargingRequest.setRoaming(true);
        chargingRequest.setRsu(1);
        chargingRequest.setCreatedAt(constantInstant);

        beta1Processor.processRequest(billingAccount, chargingRequest);

        verify(billingAccount, times(1)).setBucket2(5017);
    }

}