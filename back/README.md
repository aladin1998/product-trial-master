### Backend Ecommerce

Ceci est un projet Spring Boot pour la gestion des produits dans le cadre d'un test à passer chez ALTEN

### Prérequis

- Java 21 ou supérieur 
- Maven 3.6.3 ou supérieur
- Docker

### Setup

1. Clonez le repository:

```sh
git clone <repository-url>
cd back/back-products
```

2. Buildez le projet:

```sh
./mvnw clean install
```

3. Exécutez l'application :

```sh
./mvnw spring-boot:run
```
### Tests manuels

### Utilisation de la base de données H2

Le projet est configuré pour utiliser une base de données H2 en mémoire pour le développement. La console H2 est activée pour un accès facile.

#### Accéder à la console H2

1. Démarrez l'application.
2. Ouvrez votre navigateur web et allez à `http://localhost:8080/h2-console`.
3. Utilisez les cridentials suivants pour se connecter :
   - **JDBC URL**: `jdbc:h2:mem:testdb`
   - **Username**: `sa`
   - **Password**: `password`

### Tester avec Postman

Récupérez le fichier JSON contenant les requêtes à tester depuis postman/collections.

1. Tout d'abord, il faut envoyer la requête POST:/auth/account pour créer un utilisateur.

2. Ensuite, récupérez le token JWT en exécutant la requête POST:/auth/token.

3. Créez un produit en exécutant la requête POST:/products.

4. Ajoutez le produit au panier d'achat en exécutant la requête POST:/cart/add.

5. Supprimez le produit du panier en exécutant la requête DELETE:/cart/remove/1.

6. Ajoutez le produit à la liste d'envies en exécutant la requête POST:/wishlist/add.

7. Supprimez le produit de la liste d'envies en exécutant la requête DELETE:/wishlist/remove/1.


## Tests automatisés - Tests d'intégration

Effectuez le test d'intégration en remplaçant dev par test (profil) dans le fichier de configuration application.properties.

Ensuite, tapez la commande suivante :

```sh
./mvnw test

```


### Swagger UI

Le projet inclut Swagger UI pour la documentation API. Vous pouvez y accéder à `http://localhost:8080/swagger-ui/index.html#/`.