# automatisation-test

Membre du groupe : Lucas Belin, Loïc Robin, Martin Lefebvre, Quentin Chéruel

Description : Nous avons repris notre projet MSPR Go-Sécuri et nous y avons implémenté les tests, nous avons modifié la pipeline pour qu'elle réponde à la demande, 
nous avons mis en place un Jenkins connecter à un serveur Nexus afin de déployer les snapshots et releases, de plus nous avons aussi connecté un serveur sonarqube 
afin de conserver une qualité de code optimal.
Notre pipeline va vérifier les versions java et maven ensuite elle va executer les tests unitaires, vérifier le taux de coverage via sonarqube, créer un jar, récupérer les infos du pom.xml, build le projet 
afin de générer un fichier .jar ensuite elle va publier sur nexus.
Le projet est package sous un docker compose afin qu'il soit un même network.

Difficultés rencontrés :
La communication entre nexus et jenkins, ansi que la communication entre jenkins et sonarqube qui nous a occuper quasiment deux jours.

Résolution des problèmes rencontrés :
Nous avons résolu nos problème via de nombreuse recherche sur internet en parcourant diverse tuto, vidéo, blog, stackoverflow et nous avons aussi fait appel à ChatGpt.
