package com.econetwireless.epay.business.utils;

import com.econetwireless.in.webservice.BalanceResponse;
import com.econetwireless.in.webservice.CreditRequest;
import com.econetwireless.in.webservice.CreditResponse;
import com.econetwireless.utils.pojo.INBalanceResponse;
import com.econetwireless.utils.pojo.INCreditRequest;
import com.econetwireless.utils.pojo.INCreditResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/*
 * Created 2022/05/23 - 16:18
 * Project developer-task-01-answer
 * Author LG Zaru
 */
public class MessageConvertersFromINTest {

    private INCreditRequest inRequest;
    private INBalanceResponse inBalanceResponse;
    private INCreditResponse inCreditResponse;


    @Before
    public void setUp() {


        String msisdn = "0777000111";
        double amount = 7.0;
        String referenceNumber = "Ref Number";
        String narrative = "Successful";
        double balance = 8.0;
        String partnerCode = "P0001CODE";
        String responseCode = "200";


//In credit
        CreditRequest creditRequest = new CreditRequest();
        creditRequest.setPartnerCode(partnerCode);
        creditRequest.setMsisdn(msisdn);
        creditRequest.setAmount(amount);
        creditRequest.setReferenceNumber(referenceNumber);
        inCreditResponse = new INCreditResponse();
        inCreditResponse.setBalance(balance);
        inCreditResponse.setMsisdn(msisdn);
        inCreditResponse.setNarrative(narrative);
        inCreditResponse.setResponseCode(responseCode);
        CreditResponse creditResponse = new CreditResponse();
        creditResponse.setBalance(balance);
        creditResponse.setMsisdn(msisdn);
        creditResponse.setNarrative(narrative);
        creditResponse.setResponseCode(responseCode);
//Balance
        inBalanceResponse = new INBalanceResponse();
        inBalanceResponse.setAmount(amount);
        inBalanceResponse.setMsisdn(msisdn);
        inBalanceResponse.setNarrative(narrative);
        inBalanceResponse.setResponseCode(responseCode);
        BalanceResponse balanceResponse = new BalanceResponse();
        balanceResponse.setAmount(amount);
        balanceResponse.setMsisdn(msisdn);
        balanceResponse.setNarrative(narrative);
        balanceResponse.setResponseCode(responseCode);
        inRequest = new INCreditRequest();
        inRequest.setPartnerCode(partnerCode);
        inRequest.setMsisdn(msisdn);
        inRequest.setAmount(amount);
        inRequest.setReferenceNumber(referenceNumber);
    }

    @Test
    public void conversionOfINCreditResponseToCreditResponseShouldPassIfValidRequestIsPassed() {
        final CreditResponse response = MessageConverters.convert(inCreditResponse);
        assertNotNull(response);
        assertEquals(inCreditResponse.getBalance(), response.getBalance(), 0.01);
        assertEquals(inCreditResponse.getMsisdn(), response.getMsisdn());
        assertEquals(inCreditResponse.getNarrative(), response.getNarrative());
        assertEquals(inCreditResponse.getResponseCode(), response.getResponseCode());
    }

    @Test
    public void conversionOfINBalanceResponseToBalanceResponseShouldFailIfRequestIsNull() {
        INBalanceResponse request = null;
        final BalanceResponse response = MessageConverters.convert(request);
        assertNull(response);
    }

    @Test
    public void conversionOfINBalanceResponseToBalanceResponseShouldPassIfValidRequestIsPassed() {
        final BalanceResponse response = MessageConverters.convert(inBalanceResponse);
        assertNotNull(response);
        assertEquals(inBalanceResponse.getAmount(), response.getAmount(), 0.01);
        assertEquals(inBalanceResponse.getMsisdn(), response.getMsisdn());
        assertEquals(inBalanceResponse.getNarrative(), response.getNarrative());
        assertEquals(inBalanceResponse.getResponseCode(), response.getResponseCode());
    }

    @Test
    public void conversionOfINCreditRequestToCreditRequestShouldFailIfRequestIsNull() {
        INCreditRequest request = null;
        final CreditRequest response = MessageConverters.convert(request);
        assertNull(response);
    }

    @Test
    public void conversionOfINCreditRequestToCreditRequestShouldPassIfValidRequestIsPassed() {
        final CreditRequest response = MessageConverters.convert(inRequest);
        assertNotNull(response);
        assertEquals(inRequest.getAmount(), response.getAmount(), 0.01);
        assertEquals(inRequest.getMsisdn(), response.getMsisdn());
        assertEquals(inRequest.getPartnerCode(), response.getPartnerCode());
        assertEquals(inRequest.getReferenceNumber(), response.getReferenceNumber());
    }

    @Test
    public void conversionOfINCreditResponseToCreditResponseShouldFailIfRequestIsNull() {
        INCreditResponse request = null;
        final CreditResponse response = MessageConverters.convert(request);
        assertNull(response);
    }

}
