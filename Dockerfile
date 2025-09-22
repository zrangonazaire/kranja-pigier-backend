<<<<<<< Updated upstream
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
=======
# 1. Image de base : Ubuntu avec OpenJDK 21
FROM ubuntu:22.04

# 2. Éviter les prompts interactifs
ENV DEBIAN_FRONTEND=noninteractive
ENV TZ=Europe/Paris

# 3. Installer Java, fonts Microsoft, et outils nécessaires
RUN apt-get update && \
    apt-get install -y software-properties-common wget gnupg2 curl fontconfig unzip locales && \
    locale-gen fr_FR.UTF-8 && \
    apt-get install -y openjdk-21-jdk ttf-mscorefonts-installer && \
    echo "ttf-mscorefonts-installer msttcorefonts/accepted-mscorefonts-eula select true" | debconf-set-selections && \
    fc-cache -fv && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# 4. Variables d'environnement pour Java
ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
ENV PATH=$PATH:$JAVA_HOME/bin
ENV LANG=fr_FR.UTF-8

# 5. Créer les dossiers de travail
RUN mkdir -p /home/app/target
WORKDIR /home/app

# 6. Copier ton code et les dépendances
COPY . /home/app/

# 7. (Optionnel) Copier les extensions Jasper (fonts .jar) dans classpath si tu en utilises
# Exemple :
# COPY fonts/arialnarrow.jar /home/app/libs/

# 8. Exposer le port utilisé par l’application
EXPOSE 8084

# 9. Démarrer ton application Spring Boot
>>>>>>> Stashed changes
ENTRYPOINT ["java", "-jar", "target/pigierbackend-0.0.1-SNAPSHOT.jar"]
