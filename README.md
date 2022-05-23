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