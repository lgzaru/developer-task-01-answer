package com.econetwireless.epay.business.services.impl;

import com.econetwireless.epay.business.integrations.api.ChargingPlatform;
import com.econetwireless.epay.business.services.api.CreditsService;
import com.econetwireless.epay.dao.subscriberrequest.api.SubscriberRequestDao;
import com.econetwireless.epay.domain.SubscriberRequest;
import com.econetwireless.utils.enums.ResponseCode;
import com.econetwireless.utils.messages.AirtimeTopupRequest;
import com.econetwireless.utils.messages.AirtimeTopupResponse;
import com.econetwireless.utils.pojo.INCreditRequest;
import com.econetwireless.utils.pojo.INCreditResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/*
 * Created 2022/05/23 - 16:18
 * Project developer-task-01-answer
 * Author LG Zaru
 */
@RunWith(MockitoJUnitRunner.class)
public class CreditServiceImplTest {


    private CreditsService creditsService;
    private INCreditResponse inCreditResponse;
    private SubscriberRequest subscriberRequest;
    private AirtimeTopupRequest airtimeTopupRequest;

    private static final String SUCCESS_RESPONSE_CODE = "200";
    private static final String TOP_UP_SUCCESS_NARRATIVE = "successful";

    @Before
    public void setUp() {
        creditsService = new CreditsServiceImpl(chargingPlatform, subscriberRequestDao);
        String msisdn = "0774987456";
        double amount = 10;
        String partnerCode = "recharge";
        String referenceNumber = "REF-38383";
        String requestType = "Topup";

        subscriberRequest = new SubscriberRequest();
        subscriberRequest.setMsisdn(msisdn);
        subscriberRequest.setPartnerCode(partnerCode);
        subscriberRequest.setRequestType(requestType);
        subscriberRequest.setBalanceBefore(1);
        subscriberRequest.setAmount(2);
        subscriberRequest.setBalanceAfter(3);
        subscriberRequest.setReference(referenceNumber);

        airtimeTopupRequest = new AirtimeTopupRequest();
        airtimeTopupRequest.setMsisdn(msisdn);
        airtimeTopupRequest.setAmount(amount);
        airtimeTopupRequest.setPartnerCode(partnerCode);
        airtimeTopupRequest.setReferenceNumber(referenceNumber);

        INCreditRequest inCreditRequest = new INCreditRequest();
        inCreditRequest.setAmount(amount);
        inCreditRequest.setMsisdn(msisdn);
        inCreditRequest.setPartnerCode(partnerCode);
        inCreditRequest.setReferenceNumber(referenceNumber);

        inCreditResponse = new INCreditResponse();
        inCreditResponse.setBalance(6.0);
        inCreditResponse.setMsisdn(msisdn);
        inCreditResponse.setNarrative(TOP_UP_SUCCESS_NARRATIVE);
        inCreditResponse.setResponseCode(SUCCESS_RESPONSE_CODE);
    }

    @Mock
    private SubscriberRequestDao subscriberRequestDao;

    @Mock
    private ChargingPlatform chargingPlatform;

    @Test
    public void whenValidParametersArePassedAirtimeCreditShouldPass () {
        final String message = "successful";
        inCreditResponse.setResponseCode("200");
        inCreditResponse.setNarrative(message);
        when(subscriberRequestDao.save(any(SubscriberRequest.class))).thenReturn(subscriberRequest);
        when(chargingPlatform.creditSubscriberAccount(any(INCreditRequest.class))).thenReturn(inCreditResponse);
        final AirtimeTopupResponse airtimeTopupResponse = creditsService.credit(airtimeTopupRequest);
        assertNotNull(airtimeTopupResponse);
        assertEquals(ResponseCode.SUCCESS.getCode(), airtimeTopupResponse.getResponseCode());
        verify(subscriberRequestDao, times(2)).save(any(SubscriberRequest.class));
        verify(chargingPlatform, times(1)).creditSubscriberAccount(any(INCreditRequest.class));
    }

    @Test
    public void credit() {
        final String message = "not reachable";
        inCreditResponse.setResponseCode("500");
        inCreditResponse.setNarrative(message);
        when(subscriberRequestDao.save(any(SubscriberRequest.class))).thenReturn(subscriberRequest);
        when(chargingPlatform.creditSubscriberAccount(any(INCreditRequest.class))).thenReturn(inCreditResponse);
        final AirtimeTopupResponse airtimeTopupResponse = creditsService.credit(airtimeTopupRequest);
        assertNotNull(airtimeTopupResponse);
        assertEquals(message, airtimeTopupResponse.getNarrative());
        assertEquals(ResponseCode.FAILED.getCode(), airtimeTopupResponse.getResponseCode());
        verify(subscriberRequestDao, times(2)).save(any(SubscriberRequest.class));
        verify(chargingPlatform, times(1)).creditSubscriberAccount(any(INCreditRequest.class));
    }
}
