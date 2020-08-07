# Client pour le cifrage/ decifrage des messages text

## Instruction d’utilisation du client - Nelly Alandou, Evgenii Munin
Pour effectuer la communication entre le client et le serveur suivre les commandes suivantes.
Commencer avec un serveur vide.
Pour compiler le client, ouvrir terminal, aller dans le répertoire src de projet-NellyAlandou-
EvgeniiMunin et taper javac client/TCPClient.java puis java client/TCPClient

### 1. Pour établir une connexion :
i. Lancer le serveur Serverv2.jar en utilisant le terminal avec la commande : java -jar Serverv2.jar;
ii. Executer la classe principale TCPCLient depuis Eclipse sinon en exécutant depuis le terminal
ajouter en options « -v <chemin relatif de data_user à où vous vous trouver>
iii.Dans la console Eclipse écrire la commande HELLO.

### 2. Pour obtenir la liste de documents contenus dans le serveur taper la commande LIST;

### 3. Pour ajouter le document avec le text clair.
i. Insérer la commande ADD;
ii. Attention : Si le dossier user est vide(ie pas de fichier .txt ajouté par l’utilisateur),
vous pouvez uniquement ajouter le texte en le tapant dans la ligne de commande.
• À partir de la liste de fichiers texte contenant dans le dossier (src/data_user) choisir un qui
contient votre texte clair;
• A partir de la ligne de commande, en tapant directement le texte dans la ligne de commande ;
terminer par la dernière ligne «  ***  »
iii.Choisir le type de chiffrage : CAESAR, ATBASH, ROT13, VIGENERE, KEYWORD;
• Si vous avez choisi CAESAR, taper la clé de type int;
• Si vous avez choisi VIGENERE ou KEYWORD, taper la clé de type String;
• Si vous avez choisi ATBASH ou ROT13, la clé n’est pas nécessaire.

### 4. Si le serveur n’est pas vide vous pouvez supprimer le documents de serveur. Pour cela taper la
commande REMOVE et choisir le documents d’après son ID donné de coté gauche du signe « = » dans
la liste.

### 5. Si le serveur n’est pas vide vous pouvez écrire le document avec le texte déchiffré dans un fichier.
Pour cela :
i. Insérer la commande GET;
ii. Choisir le document d’après son ID (entier du coté gauche du signe « = » dans la liste qui
s’affiche);
iii.Écrire le nom de fichier texte, par exemple out.txt. La version déchiffrée de ce document sera
écrire dans le fichier out.txt

### 6. Pour fermer la connexion :
i. Taper la commande BYE. Cette commande correspond à fermeture de socket du client.
ii. Si vous voulez rétablir la connexion vous pouvez répondre NO à question «Do you want to
disconnect for good or not » . Après ça le programme revient au point 1.
iii.Si vous êtes sur que vous vous voulez fermer la connexion taper YES / Attention!!! : après cette
commande, le serveur sera vidé.
