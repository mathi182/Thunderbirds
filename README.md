# Thunderbird

[![Build Status](https://travis-ci.com/GLO4002UL/ul-glo4002-a16-equipe5.svg?token=929Wmi9HboocHyKUmiTr&branch=master)](https://travis-ci.com/GLO4002UL/ul-glo4002-a16-equipe5)

ul-glo4002-a16-equipe5 created agentId GitHub Classroom

- Alexandre Brillant (ID Github : alexbrillant - Email : abrillant23@gmail.com)
- Alexis Lessard ( ID Github : AlexisLessard - Email : alexislessard95@gmail.com)
- Antoine Beaulieu Lessard (ID Github : litelite - Email : antoine222_@hotmail.com)
- Alexandre Rivest (ID Github : tiaaaa123 - Email : rivest123@hotmail.com)
- Jean-Alexandre Beaumont (ID Github : Enteris - Email : jean-alexandre.beaumont.1@ulaval.ca)
- Myriam Moar (ID Github : mr0ar - Email : myriam.moar@gmail.com)
- Maxime Trottier ( ID Github : trottierm - Email : maxime.trottier@outlook.com)
- Mathieu Boily (ID Github : mathi182 - Email : mathiboily@hotmail.com)

##User Stories

- Réservation: Événement - nouvelle réservation (Terminé)
- Réservation: Obtenir l'information sur une réservation (Terminé)
- Checkin: Enregistrement par un agent (Terminé)
- Checkin: Enregistrement en ligne (Terminé)
- Siège: Assignation du siège d'un passager (aléatoire) (Terminé)
- Bagage enregistré: Classe économique (Terminé)
- Bagages: Obtenir la liste par passager (En cours, 90% complété)
- Siège: Assigner le siège le moins cher (Terminé)
- Siège: Assigner le siège en fonction du dégagement (Terminé)


###Known Issues
Les story de sièges ont un bug qui fait retourner des erreur 500 lors de l'appel.

##Utilisation

###Exécution via Maven

Pour lancer les serveurs (par défaut sur les ports 8787 pour reservation et 8888 pour boarding) :
```
projet/$ mvn exec:java -pl reservation
projet/$ mvn exec:java -pl boarding
```

###Configuration des ports

Il est possible de paramétrer le port pour chacun des services (dans le module app):
- `-Dreservation.port=8888`
- `-Dboarding.port=8888`

###Tests

Pour effectuer les tests, il suffit de faire:
```
projet/$ mvn test : pour rouler les tests unitaires de tous les modules
projet/$ mvn integration-test : pour rouler les autres niveaux de test de tous les modules
```

##Normes

###Git

1. Suivre les directives du [wiki](http://ulaval.qualitelogicielle.ca/wiki/documentation/gestion-equipes/flot-travail-git)
2. Ne jamais pusher sur la branche `dev` directement. Puller le `dev` et créer un merge request.
3. Ne jamais accepter son propre merge request.

###Test

* Le nom des fonctions devront suivre cette structure : given*_when*_should*.
* Toujours séparer les sections du test d'une ligne vide, meme si la section ne contient qu'une ligne.

###Code
* Le code doit être écrit en anglais (incluant commentaire)
