# language: fr
Fonctionnalité: Enregistrer un bagage

Scénario:  Enregistrer un bagage
  Étant donné un passager Bob ayant une réservation en classe économique sur le vol AC-1234
  Et qu'il a déjà un bagage enregistré respectant les normes
  Quand il enregistre le bagage suivant :
  | type    | poids | taille |
  | checked | 30kg  | 100cm  |
  Alors son total a payer est de 50$