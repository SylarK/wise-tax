package pt.amado.wisetax.interfaces.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pt.amado.wisetax.model.entities.BillingAccount;
import pt.amado.wisetax.model.entities.ChargingRequest;

import static org.mockito.Mockito.*;

class Alpha1ProcessorTest {

    @InjectMocks
    private Alpha1Processor alpha1Processor;

    @Mock
    private BillingAccount billingAccount;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Processing with roaming equals false")
    public void shouldProcessTakingIntoAccountLocalCall() {
        when(billingAccount.getBucket1()).thenReturn(0L);
        when(billingAccount.getBucket2()).thenReturn(0L);
        when(billingAccount.getBucket3()).thenReturn(0L);

        ChargingRequest chargingRequest = new ChargingRequest();
        chargingRequest.setRoaming(false);
        chargingRequest.setRsu(1);

        alpha1Processor.processRequest(billingAccount, chargingRequest);

        verify(billingAccount, times(1)).setBucket1(100);
    }

    @Test
    @DisplayName("Should increase bucket two when roaming equal true and bucket2 greater than 5 euros")
    public void shouldProcessTakingIntoAccountRoamingAndBucket2() {
        when(billingAccount.getBucket1()).thenReturn(0L);
        when(billingAccount.getBucket2()).thenReturn(600L);
        when(billingAccount.getBucket3()).thenReturn(0L);
        when(billingAccount.getCounterA()).thenReturn(11L);

        ChargingRequest chargingRequest = new ChargingRequest();
        chargingRequest.setRoaming(true);
        chargingRequest.setRsu(10);

        alpha1Processor.processRequest(billingAccount, chargingRequest);

        verify(billingAccount, times(1)).setBucket2(595);
    }

    @Test
    @DisplayName("Should increase bucket three when roaming equal true and bucket2 less than 5 euros")
    public void shouldProcessTakingIntoAccountRoamingAndBucket3() {
        when(billingAccount.getBucket1()).thenReturn(0L);
        when(billingAccount.getBucket2()).thenReturn(0L);
        when(billingAccount.getBucket3()).thenReturn(0L);
        when(billingAccount.getCounterA()).thenReturn(11L);

        ChargingRequest chargingRequest = new ChargingRequest();
        chargingRequest.setRoaming(true);
        chargingRequest.setRsu(10);

        alpha1Processor.processRequest(billingAccount, chargingRequest);

        verify(billingAccount, times(1)).setBucket3(1750);
    }

}