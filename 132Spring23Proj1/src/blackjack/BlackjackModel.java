package blackjack;

import java.util.ArrayList;
import java.util.Random;

import deckOfCards.*;
/*
 * The purpose of the "BlackjackModel" class is to layout the rules of the game
 * using the two possible hands (dealer's Cards and the player's Cards) to determine the result of the game.
 */

public class BlackjackModel {

	private ArrayList<Card> dealerCards; //an ArrayList representing the dealer's cards
	private ArrayList<Card> playerCards; //an ArrayList representing the player's cards
	private Deck deck; //a Deck variable representing the entire Deck of cards for the game

	/**
	 * A simple getter for the Dealer's Cards
	 * @return returning a shallow copy of the dealer's cards
	 */
	public ArrayList<Card> getDealerCards() { //I would have to make a shallow copy since the item is immutable
		ArrayList<Card> copyOfDealerCards = new ArrayList<>(dealerCards); 
		//#SHALLOW COPY because the cards are enumerated types and are therefore immutable
		
		// Note to self // Benefit: Allows you to save memory space on the Stack

		return copyOfDealerCards;
	}
	/**
	 * A simple getter for the Player's Cards
	 * @return returning a shallow copy of the player's cards
	 */
	public ArrayList<Card> getPlayerCards() {
		ArrayList<Card> copyOfPlayerCards = new ArrayList<>(playerCards);
		//Shallow copy of the playerCards
		return copyOfPlayerCards;
	}
	
	/**
	 * @param "cards" is an ArrayList of Cards
	 * A simple setter to set the private instance variables that initializes the 
	 * current object "dealer cards" with a shallow copy of the parameter "cards"
	 */

	public void setDealerCards(ArrayList<Card> cards) {
		this.dealerCards = new ArrayList<>(cards); 
		
		/*when setting an ArrayList that is an instance variable, 
		 * pass in the parameter name and assign it to the current object
		 * 
		 */
	}
	
	/**
	 * @param "cards" is an ArrayList of Cards
	 * A simple setter to set the private instance variables that initializes the 
	 * current object "player cards" with a shallow copy of the parameter "cards"
	 */
	public void setPlayerCards(ArrayList<Card> cards) {
		this.playerCards = new ArrayList<>(cards);
	}
	
	/**
	 * @param random; a variable of type Random that randomly generates a random number
	 * "createAndShuffleDeck" method assigns a new instance of the Deck class to the current object "deck" variable 
	 * and then proceeds to shuffle the deck by passing in the parameter "random"
	 */

	public void createAndShuffleDeck(Random random) {
		deck = new Deck(); //"new instance of the Deck class"

		/* shuffles the deck, passing the parameter, "random," 
		 * along to the deck's shuffle method.*/
		deck.shuffle(random);
	}
	/*
	 * Instantiates the "dealerCards" current object with an empty deck
	 * The "for" loop deals two cards to the dealer
	 */
	public void initialDealerCards() {
		dealerCards = new ArrayList<>();
		for (int i = 0; i <2; i++) {
			dealerCards.add(deck.dealOneCard()); 
			//removes one card (each time) and returns that card and adds it to the ArrayList
		}
	}
	/*
	 * Instantiates the "playerCards" current object with an empty deck
	 * The "for" loop deals two cards to the player
	 */
	public void initialPlayerCards() {
		playerCards = new ArrayList<>();
		for (int i = 0; i <2; i++) {
			playerCards.add(deck.dealOneCard()); 
			//removes one card (each time) and returns that card and adds it to the ArrayList
		}
	}
	
	/*
	 * "playerTakeCard" method (below) deals one card from the deck
	 * and adds it to the player's hand by making a call to the "dealOneCard"
	 * method in the Deck class
	 */

	public void playerTakeCard() {
		playerCards.add(deck.dealOneCard());
	}
	
	/*
	 * "dealerTakeCard" method (below) deals one card from the deck
	 * and adds it to the dealer's hand by making a call to the "dealOneCard"
	 * method in the Deck class
	 */
	public void dealerTakeCard() {
		dealerCards.add(deck.dealOneCard());	
	}

	//THIS IS WHERE THE PUBIC TESTS END //
	
	/**
	 * @param "hand" represents either the dealer or player's current hand
	 * "possibleHandValues" method evaluates the hand passed in through in the parameter
	 * and returns a very short ArrayList that could contain either one or two Integers
	 * that represent the values that could be assigned to that hand.
	 * 
	 * (We assume that the hand passed in consists of at least 2 cards)
	 * 
	 * @return returning an ArrayList of one or two intergers
	 */

	public static ArrayList<Integer> possibleHandValues(ArrayList<Card> hand){ //seems like it works C:
		int totalOfValues = 0; 
		//helper variable to count the total of the card values (in a hand) without an Ace
		int totalOfValuesWithAce = 0;
		//helper variable to count the total of the card values (in the specified hand) with an Ace present in the hand
		int numOfAces=0;
		//helper variable meant to keep track of the # of Aces.
		ArrayList<Integer> value = new ArrayList<>();
		for (Card card: hand) {
			totalOfValues += card.getRank().getValue();
			//adds the value of the card to the totalOfValues helper variable
			if (card.getRank() != Rank.ACE) {
				if (totalOfValues > 0) {
					//first we get the rank of the card and then its value
					continue;
				}
				if (totalOfValues > 21 || totalOfValuesWithAce > 21) {
					//return just a single value, the smaller one
					totalOfValues -= 10;
				}
			}else if (card.getRank() == Rank.ACE) {
				if (totalOfValuesWithAce < 10) { 
					//will ensure I return two values	
					totalOfValuesWithAce+=10;
					continue;
				}
				if (totalOfValues > 21 || totalOfValuesWithAce > 21) {
					//will ensure I return just a single value, the smaller one
					totalOfValues -= 11;
				}

			}
		}
		for(Card card: hand) {
			if (totalOfValuesWithAce <= 20) {
				if (card.getRank() == Rank.ACE && numOfAces < 1) {
					if(totalOfValuesWithAce <= 21) {
						totalOfValuesWithAce+=totalOfValues; //adds the totalOfValues to totalOfValuesWithAce
						numOfAces++; //increases numOfAces by "1" so that the "if" statement does not return true
					}
				}
			}
		}

		value.add(totalOfValues); //adding the integer value of "totalOfValues" to the ArrayList
		
		/*if there is only one Ace and the totalOfValuesWith Ace is less than or equal to 21, then add the 2nd
		 * index
		 */
		if (numOfAces == 1 && totalOfValuesWithAce <= 21) {
			value.add(totalOfValuesWithAce);
		}

		return value;
	}
	// be more detail in the descriptions below
	/**
	 * @param "hand" represents either the dealer or player's current hand
	 * 
	 * "assessHand" method will assess the hand and will return one of the four
	 * HandAssessment constants (found in the enum "HandAssessment")
	 * 
	 * @return returns the result of the hand based on the cards in the
	 * respective hand
	 */

	public static HandAssessment assessHand(ArrayList<Card> hand) {

		if (hand == null || hand.size() < 2) {
			return HandAssessment.INSUFFICIENT_CARDS;
		}else if(BlackjackModel.possibleHandValues(hand).size()==2 && 
				BlackjackModel.possibleHandValues(hand).get(1)==21) {
			return HandAssessment.NATURAL_BLACKJACK;
		}else if (BlackjackModel.possibleHandValues(hand).get(0)>21) {
			return HandAssessment.BUST;
		}
		return HandAssessment.NORMAL;

	}
	/**
	 * "gameAssessment" method looks at both the player's and the dealer's cards to determine the outcome of the game
	 * 
	 * @return returns one of the GameResult constants found in the enum "GameResult"
	 * 
	 */


	public GameResult gameAssessment() { //hint: use the assessHand() method use the method 
		if((BlackjackModel.assessHand(playerCards)==HandAssessment.NATURAL_BLACKJACK)&&
				BlackjackModel.assessHand(dealerCards)!=HandAssessment.NATURAL_BLACKJACK) {
			return GameResult.NATURAL_BLACKJACK;
		}else if((BlackjackModel.assessHand(playerCards)==HandAssessment.NATURAL_BLACKJACK)&&
				(BlackjackModel.assessHand(dealerCards)==HandAssessment.NATURAL_BLACKJACK)) {
			return GameResult.PUSH;
		}else if ((BlackjackModel.assessHand(dealerCards)==HandAssessment.NATURAL_BLACKJACK)&& 
				BlackjackModel.possibleHandValues(playerCards).get(0) == 21) {
			return GameResult.PUSH;
		}else if(BlackjackModel.assessHand(playerCards)==HandAssessment.BUST) {
			return GameResult.PLAYER_LOST;
		}else if((BlackjackModel.assessHand(dealerCards)==HandAssessment.BUST)&& 
				(BlackjackModel.assessHand(playerCards)!=HandAssessment.BUST)) { //LAST STATEMENT
			return GameResult.PLAYER_WON;
		}

		if ((BlackjackModel.assessHand(dealerCards)!= HandAssessment.BUST)&& 
				(BlackjackModel.assessHand(playerCards)!= HandAssessment.BUST)){
			if((BlackjackModel.possibleHandValues(dealerCards).size()== 1 && 
					BlackjackModel.possibleHandValues(playerCards).get(0) > 
			BlackjackModel.possibleHandValues(dealerCards).get(0)) || 
					(BlackjackModel.possibleHandValues(dealerCards).size()== 2 && 
					BlackjackModel.possibleHandValues(playerCards).get(0) > 
			BlackjackModel.possibleHandValues(dealerCards).get(0)) ) { 
				//*****MAY NEED TO ACCOUNT FOR AN ACE********
				//comparing the first element in the ArrayList (which should be the value)
				return GameResult.PLAYER_WON;
			}else if (BlackjackModel.possibleHandValues(playerCards).get(0) <
					BlackjackModel.possibleHandValues(dealerCards).get(0)) {
				return GameResult.PLAYER_LOST;
			}else {
				return GameResult.PUSH; 
				//check this out to see if I can move it back and make it a regular else in this giant "if" statement
			}
		}
		return null; 

	}
	/**
	 * "dealerShouldTakeCard" method looks at the dealer's cards to determine if the dealer
	 * can take another card during their turn returning true if the dealer should take another card
	 * 
	 * @return
	 */
	public boolean dealerShouldTakeCard() {
		if((BlackjackModel.possibleHandValues(dealerCards).size()== 1 && 
				BlackjackModel.possibleHandValues(dealerCards).get(0)<=16) ||
				(BlackjackModel.possibleHandValues(dealerCards).size() == 2 && 
				BlackjackModel.possibleHandValues(dealerCards).get(1)<=16) ) {
			return true;
		}else if( (BlackjackModel.possibleHandValues(dealerCards).size() == 1 && 
				BlackjackModel.possibleHandValues(dealerCards).get(0)>=18) ||
				(BlackjackModel.possibleHandValues(dealerCards).size() == 2 && 
				BlackjackModel.possibleHandValues(dealerCards).get(1)>=18)) {
			return false;
		}else if(BlackjackModel.possibleHandValues(dealerCards).get(0)==7 &&
				BlackjackModel.possibleHandValues(dealerCards).get(1)==17){
			return true;
		}else if(BlackjackModel.possibleHandValues(dealerCards).get(0)==17) {
			return false;
		}else {
			return false;
		}
	}






}
