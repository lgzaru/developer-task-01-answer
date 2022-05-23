package com.econetwireless.epay.business.integrations.impl;

import com.econetwireless.epay.business.integrations.api.ChargingPlatform;
import com.econetwireless.epay.business.utils.MessageConverters;
import com.econetwireless.in.webservice.BalanceResponse;
import com.econetwireless.in.webservice.CreditRequest;
import com.econetwireless.in.webservice.CreditResponse;
import com.econetwireless.in.webservice.IntelligentNetworkService;
import com.econetwireless.utils.pojo.INBalanceResponse;
import com.econetwireless.utils.pojo.INCreditRequest;
import com.econetwireless.utils.pojo.INCreditResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/*
 * Created 2022/05/23 - 16:18
 * Project developer-task-01-answer
 * Author LG Zaru
 */

@RunWith(MockitoJUnitRunner.class)
public class ChargingPlatformImplTest {

    @Mock
    private IntelligentNetworkService intelligentNetworkService;

    @InjectMocks
    ChargingPlatform chargingPlatform = new ChargingPlatformImpl(intelligentNetworkService);

    private INCreditRequest inCreditRequest;
    private CreditResponse creditResponse;
    private String partnerCode;
    private String msisdn;
    private INBalanceResponse inBalanceResponse;
    private BalanceResponse balanceResponse;

    private static final String BAD_REQUEST_FAILURE_RESPONSE_CODE = "404";

    @Before
    public void setUp() {
        partnerCode = "CODE-12345";
        String responseCode = "200";
        String narrative = "Successful";
        msisdn = "0775245867";
        double amount = 10;
        String referenceNumber = "REF-12345";
        double balance = 10;

        inBalanceResponse = new INBalanceResponse();
        inBalanceResponse.setResponseCode(responseCode);
        inBalanceResponse.setNarrative(narrative);
        inBalanceResponse.setAmount(amount);
        inBalanceResponse.setMsisdn(msisdn);

        balanceResponse = new BalanceResponse();
        balanceResponse.setResponseCode(responseCode);
        balanceResponse.setNarrative(narrative);
        balanceResponse.setAmount(amount);
        balanceResponse.setMsisdn(msisdn);

        inCreditRequest = new INCreditRequest();
        inCreditRequest.setPartnerCode(partnerCode);
        inCreditRequest.setMsisdn(msisdn);
        inCreditRequest.setAmount(amount);
        inCreditRequest.setReferenceNumber(referenceNumber);

        INCreditResponse inCreditResponse = new INCreditResponse();
        inCreditResponse.setResponseCode(responseCode);
        inCreditResponse.setNarrative(narrative);
        inCreditResponse.setBalance(balance);
        inCreditResponse.setMsisdn(msisdn);

        creditResponse = MessageConverters.convert(inCreditResponse);
    }

    @Test
    public void whenFailureResponseCodeIsReceivedEnquireBalanceShouldFail() {
        balanceResponse.setResponseCode(BAD_REQUEST_FAILURE_RESPONSE_CODE);
        inBalanceResponse.setResponseCode(BAD_REQUEST_FAILURE_RESPONSE_CODE);
        when(intelligentNetworkService.enquireBalance(partnerCode, msisdn)).thenReturn(balanceResponse);
        INBalanceResponse response = chargingPlatform.enquireBalance(partnerCode, msisdn);
        assertNotNull(response);
        assertNotNull(inBalanceResponse.getResponseCode());
        assertEquals(inBalanceResponse.getResponseCode(), response.getResponseCode());
        assertNotNull(inBalanceResponse.getNarrative());
        assertEquals(inBalanceResponse.getNarrative(), response.getNarrative());
        assertTrue(response.getAmount() >= 0);
        assertEquals(inBalanceResponse.getAmount(), response.getAmount(), 0.01);
        assertNotNull(inBalanceResponse.getMsisdn());
        assertEquals(inBalanceResponse.getMsisdn(), response.getMsisdn());
        verify(intelligentNetworkService, times(1)).enquireBalance(partnerCode, msisdn);
    }

    @Test
    public void whenSuccessResponseIsReceivedSubscriberAccountShouldBeCredited () {
        when(intelligentNetworkService.creditSubscriberAccount(any(CreditRequest.class))).thenReturn(creditResponse);
        INCreditResponse response = chargingPlatform.creditSubscriberAccount(inCreditRequest);
        assertNotNull(response);
        assertNotNull(inBalanceResponse.getResponseCode());
        assertEquals(inBalanceResponse.getResponseCode(), response.getResponseCode());
        assertNotNull(inBalanceResponse.getNarrative());
        assertEquals(inBalanceResponse.getNarrative(), response.getNarrative());
        assertTrue(response.getBalance() >= 0);
        assertEquals(inBalanceResponse.getAmount(), response.getBalance(), 0.01);
        assertNotNull(inBalanceResponse.getMsisdn());
        assertEquals(inBalanceResponse.getMsisdn(), response.getMsisdn());
        verify(intelligentNetworkService, times(1)).creditSubscriberAccount(any(CreditRequest.class));
    }

    @Test
    public void whenSuccessResponseIsReceivedEnquireBalanceShouldPass () {
        when(intelligentNetworkService.enquireBalance(partnerCode, msisdn)).thenReturn(balanceResponse);
        INBalanceResponse response = chargingPlatform.enquireBalance(partnerCode, msisdn);
        assertNotNull(response);
        assertNotNull(inBalanceResponse.getResponseCode());
        assertEquals(inBalanceResponse.getResponseCode(), response.getResponseCode());
        assertNotNull(inBalanceResponse.getNarrative());
        assertEquals(inBalanceResponse.getNarrative(), response.getNarrative());
        assertTrue(response.getAmount() >= 0);
        assertEquals(inBalanceResponse.getAmount(), response.getAmount(), 0.01);
        assertNotNull(inBalanceResponse.getMsisdn());
        assertEquals(inBalanceResponse.getMsisdn(), response.getMsisdn());
        verify(intelligentNetworkService).enquireBalance(partnerCode, msisdn);

    }

}
