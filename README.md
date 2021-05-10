# bitcoin-trend
This JAVA console program prompts the user to enter a currency code and displays the current rate of bitcoin as well as the minimum and maximum rates for the past 30 days for the currency entered.

* docker image available here: https://hub.docker.com/r/haritee/bitcoin-trend

## How to execute the application?

#### create .jar file:

* clone the repository

* Generate .jar file
```
mvn clean package
```
#### Execute using command line:

* Run from path where .jar file is placed (target folder): 
```
java -jar bitcoin-trend-0.0.1.jar
```

#### Execute using Docker

* Pull docker image from Dockerhub:
```
docker pull haritee/bitcoin-trend:v1.0
```

* Run Docker image from /bitcoin-trend folder:
```
docker run -i -t haritee/bitcoin-trend:v1.0
```
