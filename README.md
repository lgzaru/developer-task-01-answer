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