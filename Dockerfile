# Étape d'exécution
FROM openjdk:21-jdk
RUN mkdir -p /home/app
RUN mkdir -p /home/app/target
WORKDIR /home/app

COPY . /home/app/

# Exposition du port
EXPOSE 8084

# Commande de démarrage
ENTRYPOINT ["java", "-jar", "target/pigierbackend-0.0.1-SNAPSHOT.jar"]