From SSMS:
1. Create login user: debezium
2. Create database: debezium
3. Grant all permission to on database debezium to user debezium
4. Login as user debezium

5. Create a sample table
CREATE TABLE tab (
    id int not null primary key,
    name varchar(255)
);

6. Enable CDC on debezium database
USE debezium  
GO  
EXEC sys.sp_cdc_enable_db 
GO  

7. Enable CDC on table
USE debezium;  
GO  
EXEC sys.sp_cdc_enable_table  
@source_schema = N'dbo',  
@source_name   = N'tab',  
@role_name     = N'debezium',  
@supports_net_changes = 1;

8. From the app directory, build the app using command mvn clean install
9. Run the app using java -jar name_of_the_jar.jar
10. Run Kafdrop using following command to view incoming messages in Kafka. Kafdrop can be downloaded from here: https://github.com/obsidiandynamics/kafdrop/releases/tag/3.30.0
Use the following command to run Kafdrop:
java --add-opens=java.base/sun.nio.ch=ALL-UNNAMED -jar kafdrop-3.30.0.jar --kafka.brokerConnect=localhost:29092 --server.port=29002 --management.server.port=29002 --management.endpoints.web.base-path=/health

11. Make some DDL (INSERT/UPDATED/DELETE) over the above created table from SSMS and see from Kafdrop that, the CDC messages are coming in Kafka