## La place du marché - Microservice Storage
Projet 12 - DA JAVA - Openclassrooms

Domaine : http://storage.lpdm.kybox.fr

##### Liste des urls disponibles :
| URL                                                  | Methode | Description                                                                                           |
|------------------------------------------------------|---------|-------------------------------------------------------------------------------------------------------|
| storage.lpdm.kybox.fr/                               | POST    | @RequestBody : id de l'utilisateur : { "id": Integer }                                                |
| storage.lpdm.kybox.fr/save                           | POST    | Résultat des opérations                                                                               |
| storage.lpdm.kybox.fr/test                           | GET     | Interface de test (non persistante)                                                                   |
| storage.lpdm.kybox.fr/user/{id}                      | GET     | Retourne liste des fichiers uploadés par l'utilisateur (url)                                          |
| storage.lpdm.kybox.fr/user/{userId}/delete/{fileUrl} | GET     | Supprime le fichier ayant pour URL le paramètre {fileUrl} et pour propriétaire l'identifiant {userId} |