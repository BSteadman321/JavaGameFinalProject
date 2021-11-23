import java.lang.Integer;
import java.util.Vector;
import java.awt.image.BufferedImage;
public class Card{

	private final String suit;	//suit of the card
	private final int number; //number of the card (jack = 11, queen =12, king = 13)
	private final String faceName;
	private final String ImageName;
	private final String FaceDownImageName;
	private boolean isFaceUp;
	public Card(int i, String s){
			suit = s;
			number = i;
			FaceDownImageName = new String("CardImages/back_of_card.jpg");
			//this gets the faceName which we can use for asset-getting
			switch(number){		
				case 1:
					faceName = "Ace";
					break;
				case 2:case 3:case 4:
				case 5:case 6:case 7:
				case 8:case 9:case 10:
					faceName = new String(Integer.toString(number));
					break;
				case 11:
					faceName = "Jack";
					break;
				case 12:
					faceName = "Queen";
					break;
				case 13:
					faceName = "King";
					break;
				default:
					faceName = "Error";
			}

			ImageName = new String("CardImages/" + faceName + "_of_"+ suit + ".jpg");
			isFaceUp = false;
			//System.out.println(ImageName);
	}

	//Method for finding the color of a card from its suit
	public String Color(){
		String ret;
		switch(suit){
			case "HEARTS": case "DIAMONDS":
				ret = "Red";
				break;
			case "SPADES": case "CLUBS":
				ret = "Black";
				break;
			default:
				ret = "Error has occured";
		}
		return ret;
	}

	public boolean isDifferentColorThan(Card c){
		return (!Color().equals(c.Color()));
	}
	public int getNumber(){
		return number;
	}
	public String getSuit(){
		return suit;
	}
	public String getFaceName(){
		return faceName;
	}

	//will return the string of filename to the cards image
	//if face up - returns filename to card face
	//if face down - returns filename to card back 
	public String Display(){
		if(isFaceUp)
			return (ImageName);
		return FaceDownImageName;
	}


	//
	public void setFaceUp(){
		isFaceUp = true;
	}
	public void setFaceDown(){
		isFaceUp = false;
	}
	public boolean isFaceUp(){
		return isFaceUp;
	}
	public boolean isFaceDown(){
		return !isFaceUp;
	}

	//used for getting a string of the suits as they are understood by the card class
	public static String[] ListOfSuits(){
		return new String[]{"SPADES", "CLUBS", "DIAMONDS", "HEARTS"};
	}
}

