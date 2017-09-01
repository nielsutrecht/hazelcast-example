# Hazelcast Example

An example project to demonstrate Hazelcast's pub-sub mechanism. It contains two services, an event source and and event sink.

## Event Source

The Event Source service is a Spring Boot service that publishes events on the 'events' topic. It uses Spring Scheduling to
publish time events every second, minute, hour, etc. It also has two REST end-points that can be used to publish user login
and log message events.

It comes with a simple Angular 1.6 SPA that can be used to publish these logmessage and userlog events without requiring you
to use a REST client to send these events.

## Event Sink

The Event Sink service is (again) a Spring Boot service that listens on the 'events' topic and logs the events it sees. In
addition to that it also transmits these events via websockets to an Angular 1.6 SPA that can display these events.

## Building

You can build the entire project through Maven with mvn clean install

## Running

### Event Source

You can run the Application main class from your IDE or run the applications after building from the commandline:

java -jar eventsink/target/eventsink-1.0-SNAPSHOT.jar
java -jar eventsource/target/eventsource-1.0-SNAPSHOT.jar

You can then navigate to http://localhost:8080 and http://localhost:8081 to open the event source / sink applications.


