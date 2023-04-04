package tests;

import deckOfCards.*;
import blackjack.*;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;
import org.junit.Test;

public class PublicTests {

	@Test
	public void testDeckConstructorAndDealOneCard() {
		Deck deck = new Deck();
		for (int suitCounter = 0; suitCounter < 4; suitCounter++) {
			for (int valueCounter = 0; valueCounter < 13; valueCounter++) {
				Card card = deck.dealOneCard();
				assertEquals(card.getSuit().ordinal(), suitCounter);
				assertEquals(card.getRank().ordinal(), valueCounter);
			}
		}
	}

	/* This test will pass only if an IndexOutOfBoundsException is thrown */
	@Test (expected = IndexOutOfBoundsException.class)
	public void testDeckSize() {
		Deck deck = new Deck();
		for (int i = 0; i < 53; i++) {  // one too many -- should throw exception
			deck.dealOneCard();
		}
	}

	@Test
	public void testDeckShuffle() {
		Deck deck = new Deck();
		Random random = new Random(1234);
		deck.shuffle(random);
		assertEquals(new Card(Rank.KING, Suit.CLUBS), deck.dealOneCard());
		assertEquals(new Card(Rank.TEN, Suit.CLUBS), deck.dealOneCard());
		assertEquals(new Card(Rank.JACK, Suit.SPADES), deck.dealOneCard());
		for (int i = 0; i < 20; i++) {
			deck.dealOneCard();
		}
		assertEquals(new Card(Rank.SIX, Suit.CLUBS), deck.dealOneCard());
		assertEquals(new Card(Rank.FIVE, Suit.CLUBS), deck.dealOneCard());
		for (int i = 0; i < 24; i++) {
			deck.dealOneCard();
		}
		assertEquals(new Card(Rank.EIGHT, Suit.CLUBS), deck.dealOneCard());
		assertEquals(new Card(Rank.JACK, Suit.HEARTS), deck.dealOneCard());
		assertEquals(new Card(Rank.JACK, Suit.CLUBS), deck.dealOneCard());
	}

	@Test
	public void testGameBasics() {
		Random random = new Random(3723);
		BlackjackModel game = new BlackjackModel();
		game.createAndShuffleDeck(random);
		game.initialPlayerCards();
		game.initialDealerCards();
		game.playerTakeCard();
		game.dealerTakeCard();
		ArrayList<Card> playerCards = game.getPlayerCards();
		ArrayList<Card> dealerCards = game.getDealerCards();
		assertTrue(playerCards.get(0).equals(new Card(Rank.QUEEN, Suit.HEARTS)));
		assertTrue(playerCards.get(1).equals(new Card(Rank.SIX, Suit.DIAMONDS)));
		assertTrue(playerCards.get(2).equals(new Card(Rank.EIGHT, Suit.HEARTS)));
		assertTrue(dealerCards.get(0).equals(new Card(Rank.THREE, Suit.CLUBS)));
		assertTrue(dealerCards.get(1).equals(new Card(Rank.NINE, Suit.SPADES)));
		assertTrue(dealerCards.get(2).equals(new Card(Rank.FIVE, Suit.CLUBS)));		
	}
	@Test
	public void testPossibleHandValues() {//used for debugging and added a stopped point at line 81
		//only need to test one hand
		//include a case w/ more than one ACE
		ArrayList<Card> playerCards = new ArrayList<>();
		playerCards.add(new Card(Rank.ACE,Suit.CLUBS)); //6 or 16; normal
		playerCards.add(new Card(Rank.TWO,Suit.CLUBS));
		playerCards.add(new Card(Rank.THREE,Suit.CLUBS));
		assertTrue(BlackjackModel.possibleHandValues(playerCards).size()==2
				&& BlackjackModel.possibleHandValues(playerCards).get(0)== 6
				&& BlackjackModel.possibleHandValues(playerCards).get(1)== 16); 
		//CASE 2
		ArrayList<Card> playerCards2 = new ArrayList<>();
		playerCards2.add(new Card(Rank.ACE,Suit.CLUBS)); //5 or 15 
		playerCards2.add(new Card(Rank.ACE,Suit.CLUBS));
		playerCards2.add(new Card(Rank.THREE,Suit.CLUBS));
		assertTrue(BlackjackModel.possibleHandValues(playerCards2).size()==2
				&& BlackjackModel.possibleHandValues(playerCards2).get(0)== 5
				&& BlackjackModel.possibleHandValues(playerCards2).get(1)== 15);

		ArrayList<Card> playerCards3 = new ArrayList<>();
		playerCards3.add(new Card(Rank.ACE,Suit.CLUBS)); //21
		playerCards3.add(new Card(Rank.KING,Suit.CLUBS));
		playerCards3.add(new Card(Rank.QUEEN,Suit.CLUBS));
		assertTrue(BlackjackModel.possibleHandValues(playerCards3).size()==1 && BlackjackModel.possibleHandValues(playerCards3).get(0)== 21);

		//Case #4
		ArrayList<Card> playerCards4 = new ArrayList<>(); //25 //bust
		playerCards4.add(new Card(Rank.FIVE,Suit.CLUBS));
		playerCards4.add(new Card(Rank.KING,Suit.CLUBS));
		playerCards4.add(new Card(Rank.QUEEN,Suit.CLUBS));
		assertTrue(BlackjackModel.possibleHandValues(playerCards4).size()==1 && BlackjackModel.possibleHandValues(playerCards4).get(0)== 25 );

		//Case #5
		ArrayList<Card> playerCards5 = new ArrayList<>(); //21 Natural Blackjack
		playerCards5.add(new Card(Rank.ACE,Suit.CLUBS));
		playerCards5.add(new Card(Rank.KING,Suit.CLUBS));
		assertTrue(BlackjackModel.possibleHandValues(playerCards5).size()==2 
				&& BlackjackModel.possibleHandValues(playerCards5).get(0)== 11 
				&& BlackjackModel.possibleHandValues(playerCards5).get(1)== 21 );
		//CASE #6
		ArrayList<Card> playerCards6 = new ArrayList<>();
		playerCards6.add(new Card(Rank.ACE,Suit.CLUBS)); //3 or 13
		playerCards6.add(new Card(Rank.ACE,Suit.CLUBS));
		playerCards6.add(new Card(Rank.ACE,Suit.CLUBS));
		assertTrue(BlackjackModel.possibleHandValues(playerCards6).size()==2
				&& BlackjackModel.possibleHandValues(playerCards6).get(0)== 3
				&& BlackjackModel.possibleHandValues(playerCards6).get(1)== 13);
	}
	@Test
	public void testAssessHand() {//this is how you test it out? (is not working for Natural_BlackJack
		//only need to test one hand
		//Case #1
		ArrayList<Card> playerCards = new ArrayList<>();
		playerCards.add(new Card(Rank.ACE,Suit.CLUBS));
		playerCards.add(new Card(Rank.KING,Suit.CLUBS));
		assertTrue(BlackjackModel.assessHand(playerCards)==HandAssessment.NATURAL_BLACKJACK); 
		//Case #2
		ArrayList<Card> playerCards2 = new ArrayList<>();
		playerCards2.add(new Card(Rank.ACE,Suit.CLUBS));
		playerCards2.add(new Card(Rank.KING,Suit.CLUBS));
		playerCards2.add(new Card(Rank.QUEEN,Suit.CLUBS));
		assertTrue(BlackjackModel.assessHand(playerCards2)==HandAssessment.NORMAL);
		//Case #3
		ArrayList<Card> playerCards3 = new ArrayList<>();
		playerCards3.add(new Card(Rank.FIVE,Suit.CLUBS));
		playerCards3.add(new Card(Rank.KING,Suit.CLUBS));
		playerCards3.add(new Card(Rank.QUEEN,Suit.CLUBS));
		assertTrue(BlackjackModel.assessHand(playerCards3)==HandAssessment.BUST);
		//Case #4
		ArrayList<Card> playerCards4 = new ArrayList<>();
		playerCards4.add(new Card(Rank.FIVE,Suit.CLUBS));
		assertTrue(BlackjackModel.assessHand(playerCards4)==HandAssessment.INSUFFICIENT_CARDS);
	}
	@Test
	public void testGameAssessment() {
		//only need to test one hand
		BlackjackModel newObj = new BlackjackModel();
		ArrayList<Card> playerCards = new ArrayList<>();
		ArrayList<Card> dealerCards = new ArrayList<>();
		playerCards.add(new Card(Rank.FOUR,Suit.CLUBS)); //9
		playerCards.add(new Card(Rank.FIVE,Suit.CLUBS));
		dealerCards.add(new Card(Rank.TWO,Suit.CLUBS)); //5
		dealerCards.add(new Card(Rank.THREE,Suit.CLUBS));
		newObj.setPlayerCards(playerCards);
		newObj.setDealerCards(dealerCards);
		assertTrue(newObj.gameAssessment()==GameResult.PLAYER_WON); 
	}
	@Test
	public void testDealerShouldTakeCard() {
		//only need to test one hand
		BlackjackModel newObj = new BlackjackModel();
		ArrayList<Card> dealerCards = new ArrayList<>();
		dealerCards.add(new Card(Rank.THREE,Suit.CLUBS)); //8 OR 18
		dealerCards.add(new Card(Rank.FOUR,Suit.CLUBS));
		dealerCards.add(new Card(Rank.ACE,Suit.CLUBS));
		newObj.setDealerCards(dealerCards);
		assertTrue(newObj.dealerShouldTakeCard()==false); 

		//Case #2
		ArrayList<Card> dealerCards2 = new ArrayList<>();
		dealerCards2.add(new Card(Rank.THREE,Suit.CLUBS)); //9
		dealerCards2.add(new Card(Rank.FOUR,Suit.CLUBS));
		dealerCards2.add(new Card(Rank.TWO,Suit.CLUBS));
		newObj.setDealerCards(dealerCards2);
		assertTrue(newObj.dealerShouldTakeCard()==true); 

		//Case #3
		ArrayList<Card> dealerCards3 = new ArrayList<>();
		dealerCards3.add(new Card(Rank.ACE,Suit.CLUBS)); //7 or 17
		dealerCards3.add(new Card(Rank.FOUR,Suit.CLUBS));
		dealerCards3.add(new Card(Rank.TWO,Suit.CLUBS));
		newObj.setDealerCards(dealerCards3);
		assertTrue(newObj.dealerShouldTakeCard()==true); 

		//Case #4
		ArrayList<Card> dealerCards4 = new ArrayList<>();
		dealerCards4.add(new Card(Rank.EIGHT,Suit.CLUBS)); //17 or 27
		dealerCards4.add(new Card(Rank.EIGHT,Suit.CLUBS));
		dealerCards4.add(new Card(Rank.ACE,Suit.CLUBS));
		newObj.setDealerCards(dealerCards4);
		assertTrue(newObj.dealerShouldTakeCard()==false); 

		//Case #5
		ArrayList<Card> dealerCards5 = new ArrayList<>();
		dealerCards5.add(new Card(Rank.FIVE,Suit.CLUBS)); //17 or 27
		dealerCards5.add(new Card(Rank.KING,Suit.CLUBS));
		dealerCards5.add(new Card(Rank.TWO,Suit.CLUBS));
		newObj.setDealerCards(dealerCards5);
		assertTrue(newObj.dealerShouldTakeCard()==false); 
	}
}
