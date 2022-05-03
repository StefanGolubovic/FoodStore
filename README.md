# FoodStore

FoodStore is a Spring Boot web application, supporting following functionalities:
* storing Food Stores data into ElasticSearch DB
* searching Food Stores by all fields
* searching Food Stores by latitude and longitude

## Prerequisites & Tools

Elasticsearch version 7.15.1. [download link](https://www.elastic.co/downloads/past-releases/elasticsearch-7-15-1)
Running ElasticSearch database server on Windows:


SpringBoot version 2.6.7

Java version 8

## Running the application
* go to bin folder and run Command Prompt there
* Inside Command Prompt run ```> elasticsearch.bat``` <br>
* Clone the project using ```> git clone https://github.com/StefanGolubovic/FoodStore.git```.
* Open it in one of the IDEs. I used Intellij IDEA.
* You should have Java 8 sdk set up. (For IDEA use: File > Project Structure > Project > SDK (1.8 Oracle OpenJDK version 1.8.0_202). <br>
* Gradle should then build the project and pull all neccessary dependencies and you're ready to go.

### Loader component
Loader component reads CSV file with Food Stores information inside it and stores the data into ElasticSearch database in food-store-index index.<br>
It should be ran only once when you start the application. For that purpose [@PostConstruct](https://github.com/StefanGolubovic/FoodStore/blob/5d48ac9ced4a7b4c8f89b134725c3065379ab679/foodstore/foodstore/src/main/java/com/realstaq/foodstore/loader/Loader.java#L32) annotation is used. <br>
When you run the application again you should comment out that annotation.

### API
There are two endpoints:
* localhost:8080/api/v1/food-store/
* localhost:8080/api/v1/food-store/{lat}/{lon}

Postman collection is provided for API calls.



