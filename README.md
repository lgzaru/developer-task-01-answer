## Developer Assessment Task1

* You will receive instructions from Cassava Smartech on what's required.

### Issues Found & Resolved
1. Made changes in CreditsServiceImpl class.
    - changed (.persist) and (.update) to save. There is no @PreInsert callback annotation in JPA.
    - I also changed the fields to final modifier

   ```sh
    final SubscriberRequest createdSubscriberRequest = subscriberRequestDao.persist(subscriberRequest);
    subscriberRequestDao.update(createdSubscriberRequest);

    final SubscriberRequest createdSubscriberRequest = subscriberRequestDao.save(subscriberRequest);
    subscriberRequestDao.save(createdSubscriberRequest);

2. Made changes in EnquiriesServiceImpl class
   - changed (.persist) and (.update) to save. There is no @PreInsert callback annotation in JPA.
   - I also changed the fields to final modifier
   ```sh
    final SubscriberRequest createdSubscriberRequest = subscriberRequestDao.persist(subscriberRequest);
    subscriberRequestDao.update(createdSubscriberRequest);

    final SubscriberRequest createdSubscriberRequest = subscriberRequestDao.save(subscriberRequest);
    subscriberRequestDao.save(createdSubscriberRequest);
   ```

3. Made changes in MobileNumberUtils class
   - changed field logger to static because Non-static field cannot be referenced from a static context
   ```sh
     private static final Logger LOGGER = LoggerFactory.getLogger(MobileNumberUtils.class);
   ```
   - also removed the boolean method "isInitiatorSameAsBeneficiary" because it was never used or referenced
   
4. Made changes in PartnerCodeValidatorImpl class
   - removed the super reference in PartnerCodeValidatorImpl constructor
   ```sh
   public PartnerCodeValidatorImpl(RequestPartnerDao requestPartnerDao) {
        this.requestPartnerDao = requestPartnerDao;
    }
   ```
   - changed RequestPartnerDao field to final
     private final RequestPartnerDao requestPartnerDao;
   - in method validatePartnerCode, the return value is always true, so I simplified it as follows
    ```sh
    return true;
    ```
5. Made changes to SubscriberRequest class
   - I renamed the Request to request to match the actual name of the entity
   ```sh
   @NamedQueries({@NamedQuery(name = "SubscriberRequest.findByPartnerCode", query = "select r from request r where r.partnerCode = :partnerCode order by r.dateCreated desc ")})
   ```
   - Changed the annotation  @PreInsert to @PrePersist. There is no @PreInsert callback annotation in JPA
   ```sh
    @PrePersist
   ```
   - Replaced StringBuilder to String in order to simplify the code
   ```sh
        return "SubscriberRequest{" + "id=" + id +
                ", requestType='" + requestType + '\'' +
                ", partnerCode='" + partnerCode + '\'' +
                ", msisdn='" + msisdn + '\'' +
                ", balanceBefore=" + balanceBefore +
                ", balanceAfter=" + balanceAfter +
                ", amount=" + amount +
                ", dateCreated=" + dateCreated +
                ", dateLastUpdated=" + dateLastUpdated +
                ", status='" + status + '\'' +
                ", reference='" + reference + '\'' +
                ", version=" + version +
                '}';   
   ```

6. Made changes in EpayResource class
   - I included Constructor Dependency Injection by Autowiring the constructor. I also changed the below fields to final
   ```sh
   private final EpayRequestProcessor epayRequestProcessor;
    private final  ReportingProcessor reportingProcessor;

    @Autowired
    public EpayResource (EpayRequestProcessor epayRequestProcessor, ReportingProcessor reportingProcessor ){
        this.epayRequestProcessor = epayRequestProcessor;
        this.reportingProcessor = reportingProcessor;
    }
   ```
   - Added the missing path variable in AirtimeBalanceResponse method
   - I removed final String pCode, because it wasn't reference and initialized
   - On enquireAirtimeBalance method call, i added the path variable partnerCode
   ```sh
    @GetMapping(value = "enquiries/{partnerCode}/balances/{mobileNumber}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public AirtimeBalanceResponse enquireAirtimeBalance(@PathVariable("mobileNumber") final String msisdn, @PathVariable String partnerCode) {
        return epayRequestProcessor.enquireAirtimeBalance(partnerCode, msisdn);
    } 
   ```

7. Made changes in ResponseCode class
   - I removed the Modifier 'private' because it is redundant for enum constructors
   - I added 'this' to Variable 'code' because it is assigned to itself
   - I also changed the variable code to final
   ```sh
   private final String code;
    ResponseCode(String code) {
        this.code = code;
    }
   ```

8. Made changes in IntelligentNetworkServiceImpl class
   - Changed the @BindingType(value = SOAPBinding.SOAP12HTTP_BINDING) to @BindingType(value = SOAPBinding.SOAP11HTTP_BINDING) in the IntelligentNetworkServiceImpl class.
   - The reason being that in IntelligentNetworkService.wsdl the soap namespace defined
     as:  "http://schemas.xmlsoap.org/wsdl/soap/" and it means its using SOAP v1.1 and by default its uses SOAP11HTTP_BINDING binding type.
   ```sh
   @BindingType(value = SOAPBinding.SOAP11HTTP_BINDING)
   ```

9. Made changes in RequestInterceptor class
   - Changed the and to &&
   ```sh
       @Around("execution(* com.econetwireless.epay.api.rest.resources.EpayResource.getPartnerTransactions(..)) and args(partnerCode)")

   ```
   - Changed field injection to Constructor Injection
   ```sh
    private final PartnerCodeValidator partnerCodeValidator;
   
    @Autowired
    public RequestInterceptor(PartnerCodeValidator partnerCodeValidator) {
        this.partnerCodeValidator = partnerCodeValidator;
    }
   ```
   - Defined the args
   ```sh
      argNames = "joinPoint,partnerCode,msisdn"
   ```
   