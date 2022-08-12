package com.example.demo;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication extends RouteBuilder {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DemoApplication.class, args);
        Main main = new Main();
        main.configure().addRoutesBuilder(new DemoApplication());
        main.run(args);
    }

    @Override
    public void configure() throws Exception {
        from("debezium-sqlserver:dbz-test-1?"
                + "databaseHostname=localhost"
                + "&databasePort=1433"
                + "&databaseDbname=debezium"
                + "&databaseUser=debezium"
                + "&databasePassword=dbz"
                + "&databaseServerName=MSSQLSERVER"
                + "&databaseHistoryFileFilename=/tmp/dbhistory.dat"
                + "&tableIncludeList=dbo.tab"
                + "&offsetStorageFileName=/tmp/offset.dat"
                + "&pollIntervalMs=5000"
                )
                .log(LoggingLevel.INFO, "Incoming message ${body} with headers ${headers}")
                .process(new MessageFormatter())
                .to("kafka:test?brokers=localhost:29092");
    }
}
