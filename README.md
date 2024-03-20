# ISA Project 2023 - Group 71 - Hospital Equipment Delivery system
Backend project for course Internet software architectures.

# Overview
- Hospital Equipment Delivery backend application named [jpa-example](https://github.com/dokma11/isa-back-end-g71-2023/tree/main/jpa-example/src/main/java/rs/ac/uns/ftn/informatika/jpa): written in Java using Spring backend framework
- Proof of Concept: A [document](https://github.com/dokma11/isa-back-end-g71-2023/tree/main/ProofOfConcept) consisted of:
  - Database Schema Design: Class Diagram
  - Proposal of a strategy for data partitioning
  - Proposal of a strategy for database replication and ensuring fault tolerance
  - Proposal of a strategy for data caching
  - Proposal of a strategy for load balancing implementation
  - A suggestion of which user operations should be monitored in order to improve the system
  - A complete design drawing of the proposed architecture
  - A rough estimate for the hardware resources needed to store all data over the next 5 years
- Hospital Equipment Delivery Simulator named [rabbitmq-producer-hospital-example](https://github.com/dokma11/isa-back-end-g71-2023/tree/main/rabbitmq-producer-hospital-example/src/main/java/rs/ac/uns/ftn/informatika/rabbitmq): An application that simulates the operation of a hospital, with a possibility to create a contract that
defines the type of equipment, quantity and date of delivery. Communicates with the main backend application through a message queue
- Location Simulator named [rabbitmq-producer-example](https://github.com/dokma11/isa-back-end-g71-2023/tree/main/rabbitmq-producer-example/src/main/java/rs/ac/uns/ftn/informatika/rabbitmq): A vehicle tracking system that seamlessly integrates real-time coordination of delivery vehicles. Utilizing a message queue, the system efficiently communicates coordinates to the backend, which then relays them to the frontend using websockets. Users will experience a dynamic map interface, providing live updates on the precise movements of vehicles, enhancing operational efficiency and customer satisfaction.

# Team Members
- [Jelena Kovač RA 118/2020](https://github.com/jelenaakovacc)
- [Nina Kuzminac RA 119/2020](https://github.com/kuzminacc)
- [Vukašin Dokmanović RA 89/2020](https://github.com/dokma11)
