# Game of life - Version Multijoueur

Ce jeux est une évolution de l'automate cellulaire du ["jeux de la vie"" de conway](https://fr.wikipedia.org/wiki/Jeu_de_la_vie)

Cet automate n'est d'ailleurs pas un jeux, mais peut le devenir comme ici.


## Règles du GoL

- Une cellule morte possédant exactement trois voisines vivantes devient vivante (elle naît).
- Une cellule vivante possédant deux ou trois voisines vivantes le reste, sinon elle meurt.


## Version multijoueur

Chaque joueur dispose d'un nombre de cellules vivantes à répartir sur une portion de plateau (une grille).
Lorsqu'ils ont procédés, le jeux est lancé.

La résolution des itérations suit les règles du GoL, cependant certains cas nécessitent de nouvelles règles:

- Une cellule vivante ne peux changer de couleur
- On compte les voisins vivants indépendement de leur joueur pour savoir son prochain état
- Lorsqu'une cellule apparait, elle appartient au joueur qui a le plus de voisins lui appartenant
- Si lors d'une naissance, il y a égalité de nombre de voisin, la cellule sera dites Neutre, et n'appartiendra à personne


## Condition de victoire

Le dernier joueur a encore avoir des cellules vivantes gagne