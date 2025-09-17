FROM eclipse-temurin:21-jdk

# Installer fontconfig pour gérer les polices
RUN apt-get update && \
    apt-get install -y --no-install-recommends fontconfig && \
    rm -rf /var/lib/apt/lists/*

# Copier tes polices Arial Narrow dans le conteneur
COPY src/fonts/*.TTF /usr/share/fonts/truetype/arialnarrow/

# Recharger le cache des polices
RUN fc-cache -f -v

# Créer répertoire d'application
WORKDIR /home/app

# Copier ton projet (incluant target/pigierbackend-0.0.1-SNAPSHOT.jar)
COPY . /home/app/

# Exposer le port
EXPOSE 8084

# Lancer le jar
ENTRYPOINT ["java", "-jar", "target/pigierbackend-0.0.1-SNAPSHOT.jar"]
