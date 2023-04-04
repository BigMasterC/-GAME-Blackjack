package deckOfCards;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Random;

/*
 * "Deck" class represents a standard deck of 52 cards
 */
public class Deck {

	private ArrayList<Card> deck;
	
	/*
	 * Standard constructor that initializes the "deck" instance variable
	 * with an empty ArrayList then populates that ArrayList with the 52 cards
	 * and ranks from the Card class
	 */
	public Deck() {
		deck=new ArrayList<>();
		for (Suit suit: Suit.values()) {
			for (Rank rank: Rank.values()) { 
				deck.add(new Card(rank, suit));
			}
		}
	}
	/*
	 * "shuffle" method consists of a call to the shuffle method from
	 * Java Collections that takes two arguments.
	 */

	public void shuffle(Random randomNumberGenerator) {
		Collections.shuffle(deck , randomNumberGenerator);
	}
	/*
	 * This method will remove one card from 
	 * the front of the list (index 0) and return it.
	 */
	public Card dealOneCard() {
		Card removedCard = deck.remove(0);
		return removedCard;
	}

}
