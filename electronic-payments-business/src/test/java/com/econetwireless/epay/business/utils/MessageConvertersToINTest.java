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
public class MessageConvertersToINTest {

    private CreditRequest creditRequest;
    private BalanceResponse balanceResponse;
    private CreditResponse creditResponse;

    @Before
    public void initialize() {

        String responseCode = "400";
        String narrative = "successful";
        String partnerCode = "P0001 Code";
        String msisdn = "0773456344";
        double amount = 20.0;
        String referenceNumber = "REF-0001";
        double balance = 12.0;

//credit
        INCreditResponse inCreditResponse = new INCreditResponse();
        inCreditResponse.setBalance(balance);
        inCreditResponse.setMsisdn(msisdn);
        inCreditResponse.setNarrative(narrative);
        inCreditResponse.setResponseCode(responseCode);
        creditResponse = new CreditResponse();
        creditResponse.setBalance(balance);
        creditResponse.setMsisdn(msisdn);
        creditResponse.setNarrative(narrative);
        creditResponse.setResponseCode(responseCode);
        creditRequest = new CreditRequest();
        creditRequest.setPartnerCode(partnerCode);
        creditRequest.setMsisdn(msisdn);
        creditRequest.setAmount(amount);
        creditRequest.setReferenceNumber(referenceNumber);
        INCreditRequest inRequest = new INCreditRequest();
        inRequest.setPartnerCode(partnerCode);
        inRequest.setMsisdn(msisdn);
        inRequest.setAmount(amount);
        inRequest.setReferenceNumber(referenceNumber);
//balance
        INBalanceResponse inBalanceResponse = new INBalanceResponse();
        inBalanceResponse.setAmount(amount);
        inBalanceResponse.setMsisdn(msisdn);
        inBalanceResponse.setNarrative(narrative);
        inBalanceResponse.setResponseCode(responseCode);
        balanceResponse = new BalanceResponse();
        balanceResponse.setAmount(amount);
        balanceResponse.setMsisdn(msisdn);
        balanceResponse.setNarrative(narrative);
        balanceResponse.setResponseCode(responseCode);
    }

    @Test
    public void conversionOfCreditRequestToINCreditRequestShouldFailIfRequestIsNull() {
        CreditRequest request = null;
        final INCreditRequest response = MessageConverters.convert(request);
        assertNull(response);
    }

    @Test
    public void conversionOfCreditRequestToINCreditRequestShouldPassIfValidRequestIsPassed() {
        final INCreditRequest response = MessageConverters.convert(creditRequest);
        assertNotNull(response);
        assertEquals(creditRequest.getAmount(), response.getAmount(), 0.01);
        assertEquals(creditRequest.getMsisdn(), response.getMsisdn());
        assertEquals(creditRequest.getPartnerCode(), response.getPartnerCode());
        assertEquals(creditRequest.getReferenceNumber(), response.getReferenceNumber());
    }

    @Test
    public void conversionOfBalanceResponseToINBalanceResponseShouldFailIfRequestIsNull() {
        BalanceResponse request = null;
        final INBalanceResponse response = MessageConverters.convert(request);
        assertNull(response);
    }

    @Test
    public void conversionOfBalanceResponseToINBalanceResponseShouldPassIfValidRequestIsPassed() {
        final INBalanceResponse response = MessageConverters.convert(balanceResponse);
        assertNotNull(response);
        assertEquals(balanceResponse.getAmount(), response.getAmount(), 0.01);
        assertEquals(balanceResponse.getMsisdn(), response.getMsisdn());
        assertEquals(balanceResponse.getNarrative(), response.getNarrative());
        assertEquals(balanceResponse.getResponseCode(), response.getResponseCode());
    }

    @Test
    public void conversionOfCreditResponseToINCreditResponseShouldFailIfRequestIsNull () {
        CreditResponse creditResponse = null;
        final INCreditResponse inCreditResponse = MessageConverters.convert(creditResponse);
        assertNull(inCreditResponse);
    }

    @Test
    public void conversionOfCreditResponseToINCreditResponseShouldPassIfValidRequestIsPassed() {
        final INCreditResponse response = MessageConverters.convert(creditResponse);
        assertNotNull(response);
        assertEquals(creditResponse.getBalance(), response.getBalance(), 0.01);
        assertEquals(creditResponse.getMsisdn(), response.getMsisdn());
        assertEquals(creditResponse.getNarrative(), response.getNarrative());
        assertEquals(creditResponse.getResponseCode(), response.getResponseCode());
    }
}
