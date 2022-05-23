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
   