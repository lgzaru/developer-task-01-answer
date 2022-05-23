package com.econetwireless.epay.api.rest.resources;

import com.econetwireless.epay.api.processors.api.EpayRequestProcessor;
import com.econetwireless.epay.api.processors.api.ReportingProcessor;
import com.econetwireless.epay.api.rest.messages.TransactionsResponse;
import com.econetwireless.utils.messages.AirtimeBalanceResponse;
import com.econetwireless.utils.messages.AirtimeTopupRequest;
import com.econetwireless.utils.messages.AirtimeTopupResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by tnyamakura on 18/3/2017.
 */
@RestController
@RequestMapping("resources/services")
public class EpayResource {

    private final EpayRequestProcessor epayRequestProcessor;
    private final  ReportingProcessor reportingProcessor;

    @Autowired
    public EpayResource (EpayRequestProcessor epayRequestProcessor, ReportingProcessor reportingProcessor ){
        this.epayRequestProcessor = epayRequestProcessor;
        this.reportingProcessor = reportingProcessor;
    }

    @GetMapping(value = "enquiries/{partnerCode}/balances/{mobileNumber}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public AirtimeBalanceResponse enquireAirtimeBalance(@PathVariable("mobileNumber") final String msisdn, @PathVariable String partnerCode) {
        return epayRequestProcessor.enquireAirtimeBalance(partnerCode, msisdn);
    }

    @PostMapping(value = "credits",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public AirtimeTopupResponse creditAirtime(@RequestBody final AirtimeTopupRequest airtimeTopupRequest) {
        return epayRequestProcessor.creditAirtime(airtimeTopupRequest);
    }

    @GetMapping(value = "transactions/{partnerCode}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public TransactionsResponse getPartnerTransactions(@PathVariable("partnerCode") final String partnerCode) {
        return reportingProcessor.getPartnerTransactions(partnerCode);
    }
}
