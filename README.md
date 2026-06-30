# ApacheKafka Workshop ARSW  - Juan Esteban Rodríguez

## 1. Evolution towards event-driven architectures

| Process             | Communication Type | Justification |
|---------------------|--------------------|---------------|
| Browse Products     | Synchronous        | Customers expect an immediate response to view product information, prices, and availability in real time.             |
| Create Order        | Hybrid             | The order request should be processed synchronously to confirm that it was received successfully, while follow-up tasks can be handled asynchronously through events.        |
| Validate Payment    | Synchronous                   | Payment validation requires an immediate response to determine whether the transaction is approved or rejected before continuing the purchase process.        |
| Send Notification   | Asynchronous                 |Email or SMS notifications do not need to delay the user's request. They can be sent after the main transaction has completed.         |
| Update Analytics    | Asynchronous                 | Analytics processing is not critical to the user's experience and can be performed in the background using published events.        |
| Register audit logs | Asynchronous                 |  Audit records can be stored after the transaction by consuming events, improving scalability without affecting response time.       |


## 2. Apache Kafka

### Current Configuration:
- Topic: order
- Partitions: 1
- Replication Factor: 1
- Message Key: None
- Retention: 24 hours

### Risks

| Configuration          | Risk                                                                                                       |
|------------------------|------------------------------------------------------------------------------------------------------------|
| 1 Partition            | All events are processed by a single consumer, limiting scalability and creating a performance bottleneck. |
| Replication Factor = 1 | If the broker fails, the topic and its data become unavailable, resulting in possible data loss.           |
| No message key         | Related events may not be consistently assigned to the same partition in the future, making it difficult to preserve event ordering for the same order.                                                                                                          |
| 24 hour retention      | Events are deleted after one day, reducing the ability to replay events, recover from failures, perform audits, or support analytics.                                                                                                          |

### Improvements for Production

| Configuration      | Recommendation                    | Reason                                                                            |
|--------------------|-----------------------------------|-----------------------------------------------------------------------------------|
| Partitions         | Increase to 3 or more partitions. | Improves scalability and allows multiple consumers to process events in parallel. |
| Replication Factor | Use a replication factor of 3.    | Provides fault tolerance and high availability if a broker fails.                                                                                  |
| Message Key        | Use a orderId as partition key    | Ensures that all events related to the same order are stored in the same partition, preserving their order.                                                                                  |
| Retention          | Increase retention to several days or weeks, depending on business requirements.                                  | Enables event replay, auditing, troubleshooting, and analytics.                                                                                  |

## 3. First steps for lab

### Docker
We created the docker and compose it}

![docker.png](docs%2Fdocker.png)

Then we create the topics, the messages and finally the consumers

![topics.png](docs%2Ftopics.png)

Using http://localhost:8080 we open the interfaces to check the topics 

![ui.png](docs%2Fui.png)

![topicsui.png](docs%2Ftopicsui.png)

We check the messages for each topic in JSON format

![exampleMessage.png](docs%2FexampleMessage.png)