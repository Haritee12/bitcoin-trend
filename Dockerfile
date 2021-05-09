FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
COPY ./target/bitcoin-trend-0.0.1.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch bitcoin-trend-0.0.1.jar'
ENTRYPOINT ["java","-jar","bitcoin-trend-0.0.1.jar"]