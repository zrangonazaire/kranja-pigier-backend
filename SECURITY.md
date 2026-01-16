# Sécurité — Mitigation CVE-2025-10492

## Contexte
Une vulnérabilité de désérialisation Java (CVE-2025-10492) a été signalée dans **JasperReports Library** (toutes versions <= 7.0.3). Cette faille permet potentiellement une exécution de code à distance via des données sérialisées malveillantes.

## Actions appliquées
- Mitigation immédiate: ajout d'un filtre global de désérialisation dans `PigierbackendApplication.main` (utilisation de `ObjectInputFilter`) afin de rejeter les classes dangereuses (ex: `java.lang.Runtime`, `java.lang.ProcessBuilder`, `java.lang.Class`) et de refuser toute désérialisation non explicitement autorisée.
  - Fichier modifié: `src/main/java/com/pigierbackend/PigierbackendApplication.java`
- Compilation vérifiée: le build Maven réussit après la modification.

## Suivi / Actions recommandées
- Suivre le bulletin Jaspersoft (https://community.jaspersoft.com/advisories) et mettre à jour `net.sf.jasperreports:jasperreports` vers une version corrigée dès qu'elle sera publiée.
- À réception d'une version corrigée, effectuer la mise à jour de la dépendance et exécuter la suite de tests et l'analyse CVE.
- Envisager une revue de l'utilisation des fonctionnalités de JasperReports qui effectuent de la désérialisation (chargement de fichiers `.jasper` ou de flux externes) et restreindre / valider les entrées utilisateur qui pourraient être traitées par la librairie.

## Note
Cette mitigation est une mesure d'atténuation en attendant un correctif upstream. Elle réduit significativement le risque d'exploitation, mais ne remplace pas la mise à jour vers une version corrigée dès qu'elle est disponible.

