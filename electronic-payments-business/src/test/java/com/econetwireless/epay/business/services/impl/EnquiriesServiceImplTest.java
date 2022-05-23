package com.econetwireless.epay.business.services.impl;

import com.econetwireless.epay.business.integrations.api.ChargingPlatform;
import com.econetwireless.epay.business.services.api.EnquiriesService;
import com.econetwireless.epay.dao.subscriberrequest.api.SubscriberRequestDao;
import com.econetwireless.epay.domain.SubscriberRequest;
import com.econetwireless.utils.enums.ResponseCode;
import com.econetwireless.utils.messages.AirtimeBalanceResponse;
import com.econetwireless.utils.pojo.INBalanceResponse;
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
public class EnquiriesServiceImplTest {

    private String partnerCode;
    private INBalanceResponse inBalanceResponse;
    private SubscriberRequest subscriberRequest;
    private String msisdn;
    private EnquiriesService enquiriesService;


    @Mock
    private SubscriberRequestDao subscriberRequestDao;

    @Mock
    private ChargingPlatform chargingPlatform;

    @Before
    public void setUp() {
        enquiriesService = new EnquiriesServiceImpl(chargingPlatform, subscriberRequestDao);

        msisdn = "0776546345";
        partnerCode = "PARTNER-CODE";
        String requestType = "Balance Enquiry";
        subscriberRequest = new SubscriberRequest();
        subscriberRequest.setMsisdn(msisdn);
        subscriberRequest.setPartnerCode(partnerCode);
        subscriberRequest.setId(1L);
        subscriberRequest.setReference("REF-00001");
        subscriberRequest.setRequestType(requestType);
        subscriberRequest.setBalanceBefore(1);
        subscriberRequest.setAmount(2);
        subscriberRequest.setBalanceAfter(3);
        inBalanceResponse = new INBalanceResponse();
        inBalanceResponse.setResponseCode("200");
        inBalanceResponse.setAmount(2);
        inBalanceResponse.setMsisdn(msisdn);
        inBalanceResponse.setNarrative("successful");

    }

    @Test
    public void whenValidParametersArePassedBalanceEnquiryShouldPass() {
        final String message = "successful";
        when(chargingPlatform.enquireBalance(partnerCode, msisdn)).thenReturn(inBalanceResponse);
        when(subscriberRequestDao.save(any(SubscriberRequest.class))).thenReturn(subscriberRequest);
        final AirtimeBalanceResponse airtimeBalanceResponse = enquiriesService.enquire(partnerCode, msisdn);
        assertNotNull(airtimeBalanceResponse);
        assertEquals(message, airtimeBalanceResponse.getNarrative());
        assertEquals(ResponseCode.SUCCESS.getCode(), airtimeBalanceResponse.getResponseCode());
        verify(subscriberRequestDao, times(2)).save(any(SubscriberRequest.class));
        verify(chargingPlatform, times(1)).enquireBalance(partnerCode, msisdn);
    }
}
