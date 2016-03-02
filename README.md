# card-golf
A playing card game for 2-6 players, where the goal is to get the lowest score possible.

Each player is dealt 4 cards, face down. They may only look at the value of two of the cards.
This is their "hand". They arrange the cards in a square on the table and leave them like that throughout the round.

The top card of the deck is put face up in the discard pile.

Starting from the left of the dealer, each player takes their turn. They may first take the card on top of the discard pile,
and put it in the place (face up) of one of the cards in their hand. The can only replace a face-down card in their hand.
The card from their hand then goes on the discard pile. If they don't want the top discarded card,
they can draw one card from the deck. They can place this in their hand in the same fashion.
If they don't want that card either, they must turn one of the cards in their hand face up. 

This proceeds until every player has had four turns, so every card in their hand has either been turned face up or swapped out for another.

The round is scored. Cards 2 through 10 (except 3) have their face value as their score.
Ace is one, King is zero, and Jack and Queen both worth 10.
Pairs cancel out to a score of zero between them, triples result in -1, and quads result in -5.
Threes are wild, and can be matched with any other card or set of cards.

Play continues for nine rounds. The player with the lowest cumulative score from all nine rounds is the winner.
