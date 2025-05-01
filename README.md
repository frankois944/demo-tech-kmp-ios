# Retours


Bonjour,

Quelques retours suite à la réalisation de ce test.

J'ai réalisé la partie obligatoire en profitant pour utiliser Kotlin multiplatform.

## Partie 1
- Le ViewModel a été réalisé en KMP (compatible iOS, Android, ...)
    Les Tests unitaires sont dans le dossier *commonMain* de la librarie *Shared*
- *DestinationFetchingService* (et ses modèles) ont été déplacés et convertis en Kotlin, tout en gardant leur particularité.
- J'ai gardé pour la couche UIKit, tout en gardant son esprit, bien que tout devrait être à refaire (la migration en *constraints*)
    Il y a quelques découpages bienvenus du code pour le rendre plus digeste
- Quelques commentaires dans le code ont été ajouté

## Partie 2
- J'ai utilisé *UserDefaults* dans la librairie KMP pour la persistance.
- L'UI est synchronisé avec la mise à jour de l'historique
- J'ai fait juste un petit test centré sur cette nouvelle feature, le test du viewModel est plus intéressant.

## Les Règles
- Limite les dépendances externes et justifie leur utilisation.
    Pas de dépendance côté iOS, mais les indispensables sont du côté KMP
- Essaie de faire des commits régulièrement (par exemple étape par étape).
    Désolé, je n'ai pas fait de commits réguliers. 
- Le code de `DestinationFetchingService` ne devra pas avoir changé.
    J'ai converti la classe en Kotlin.

## Introduction
### Philosophie

Chez Evaneos, nous accordons beaucoup d'importance à la **qualité du code et à la qualité de l'expérience utilisateur** de nos produits.

Nous souhaitons à travers ce test **voir tes capacités en développement**. La maintenabilité et la clarté de ton code sont pour nous des éléments très important. N'hésite donc pas à faire attention aux détails et à être **rigoureux**.

Lors de la restitution on s'intéressera **aux raisons de tes décisions et aux discussions que nous pourrons avoir autour**.
### Le test

Dans ce test, tu vas reprendre du code.

Il s'agit d'une petite app qui comporte une vue qui affiche des destinations provenant d'une fake API qui ne fonctionne pas très bien ;)
Lorsque l'utilisateur sélectionne une destination, une vue détail est présentée et elle contient une webview.

Ce test comporte 2 parties. Une première partie de refactorisation du code existant. Dans la seconde tu vas devoir ajouter une fonctionnalité.

Pour faire ce test, tu peux **forker ce repo** et faire l'implémentation de ton coté, tu nous enverras ensuite l'URL de ton repo.

N'hésite pas à nous solliciter si tu as des questions.

## 1. Refactorer le code existant

La première partie du test consiste à refactorer le code existant en MVVM et à adopter des bonnes pratiques.

- Nous ne voulons pas voir de completion block. Utilise Concurrency et/ou Combine.
- Lors de la restitution le code de `DestinationFetchingService` ne devra pas avoir été changé. C'est le seul code autorisé à utiliser des completion blocks ;)

## 2. Ajouter la fonctionnalité "Destinations récentes"

Nous souhaitons ajouter une fonctionnalité qui sauvegarde les destinations précedemment consultées selon [cette maquette](https://www.figma.com/file/4yIJXkSfo9xACHgG2KN0Yu/App%2FTestMobileDestinationGuide?node-id=632%3A2212).

- Pour stocker ces valeurs tu peux utiliser UserDefaults.
- Si la UI est réactive c'est un plus.
- Nous aimerions voir quelques tests que tu juges utiles. Si tu peux faire cette partie en TTD c'est un plus.

## Règles
- Limite les dépendances externes et justifie leur utilisation.
- Essaie de faire des commits régulièrement (par exemple étape par étape).
- Le code de `DestinationFetchingService` ne devra pas avoir changé.

## Bonus : UI/UX

**Cette partie est facultative.**
Si tu as du temps et que tu souhaites nous montrer davantage de choses, tu peux améliorer l'expérience avec par exemple un loader, un meilleur affichage des erreurs, ou faire de la UI (UIKit ou SwiftUI) complètement ou partiellement pour la vue détail. Tu peux t'inspirer de [cette maquette](https://www.figma.com/file/4yIJXkSfo9xACHgG2KN0Yu/App%2FTestMobileDestinationGuide?node-id=2%3A233).

Si tu ne fais pas cette partie ce n'est pas éliminatoire. Nous pourrons aussi simplement discuter de ce que tu aurais vu ou pas, quelle approche etc.

### Bonne chance !
