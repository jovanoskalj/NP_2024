package aud3.cards;

import javax.sound.midi.Soundbank;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Deck {
    private  PlayingCard[] cards;
    private boolean[] isDealt;
    private  int dealtTotal;
    public Deck(){
        cards = new  PlayingCard[52];
        isDealt= new boolean[52];
        dealtTotal=0;
        for (int i=0;i<PlayingCardType.values().length;i++){
            for (int j=0;j<13;j++){
                cards[i*13+j]=new PlayingCard(j+2,PlayingCardType.values()[i]);
            }
        }
    }

    public PlayingCard[] getCards() {
        return cards;
    }

    public void setCards(PlayingCard[] cards) {
        this.cards = cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Deck)) return false;
        Deck deck = (Deck) o;
        return Arrays.equals(cards, deck.cards);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(cards);
    }

    @Override
    public String toString() {
      StringBuilder s= new StringBuilder();
      for(PlayingCard card:cards){
          s.append(card.toString());
          s.append("\n");
      }
              return s.toString();
    }
    public boolean hasCardsLeft(){
        return (cards.length-dealtTotal)>0;
    }
    public PlayingCard[] shuffle(){

        //Collections
        List<PlayingCard> playingCardList = Arrays.asList(cards);
        Collections.shuffle(playingCardList);
        return  cards;

    }
    public  PlayingCard dealCard(){
        if(!hasCardsLeft()){
            return null;
        }
        int card=(int)(Math.random() *52);
        if(!isDealt[card]){
            isDealt[card]=true;
            dealtTotal++;
            return  cards[card];
        }
       return  dealCard();
    }

    public static void main(String[] args) {
        Deck deck1 = new Deck();
        System.out.println(deck1);

        deck1.shuffle();
        System.out.println(deck1);
        PlayingCard card;
        while((card=deck1.dealCard())!=null){
            System.out.println(card);
        }
        Deck deck2 = new Deck();
        System.out.println(deck2);
        deck2.shuffle();
        deck2.dealCard();
    }
}
