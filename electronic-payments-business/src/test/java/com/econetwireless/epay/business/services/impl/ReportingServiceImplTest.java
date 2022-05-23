package com.econetwireless.epay.business.services.impl;

import com.econetwireless.epay.business.services.api.ReportingService;
import com.econetwireless.epay.dao.subscriberrequest.api.SubscriberRequestDao;
import com.econetwireless.epay.domain.SubscriberRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/*
 * Created 2022/05/23 - 16:18
 * Project developer-task-01-answer
 * Author LG Zaru
 */
@RunWith(MockitoJUnitRunner.class)
public class ReportingServiceImplTest {

    @Mock
    SubscriberRequestDao subscriberRequestDao;
    private List<SubscriberRequest> subscriberRequests;
    private ReportingService reportingService;
    private String partnerCode;

    @Before
    public void setUp() {
        String requestType = "Report";
        partnerCode = "P00001 CODE";
        String msisdn = "0778654763";
        subscriberRequests = new ArrayList<>();
        reportingService = new ReportingServiceImpl(subscriberRequestDao);
        SubscriberRequest subscriberRequestOne = new SubscriberRequest();
        subscriberRequestOne.setReference("REF-00001");
        subscriberRequestOne.setId(1L);
        subscriberRequestOne.setPartnerCode(partnerCode);
        subscriberRequestOne.setRequestType(requestType);
        subscriberRequestOne.setBalanceBefore(1);
        subscriberRequestOne.setAmount(2);
        subscriberRequestOne.setMsisdn(msisdn);
        subscriberRequestOne.setBalanceAfter(3);
        subscriberRequestOne.setMsisdn(msisdn);
        subscriberRequestOne.setBalanceAfter(3);
        subscriberRequests.add(subscriberRequestOne);
    }

    @Test
    public void whenValidPartnerCodeIsPassedShouldReturnSubscriberRequests() {
        when(subscriberRequestDao.findByPartnerCode(partnerCode)).thenReturn(subscriberRequests);
        List<SubscriberRequest> reportResponse = reportingService.findSubscriberRequestsByPartnerCode(partnerCode);
        assertNotNull(reportResponse);
    }
}
