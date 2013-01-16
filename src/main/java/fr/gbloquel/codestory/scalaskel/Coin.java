package fr.gbloquel.codestory.scalaskel;

/**
 * This class lists the differents coin.
 * @author greg
 *
 */
public enum Coin {
	FOO(1),BAR(7), QIX(11), BAZ(21);
	
	/**
	 * Value of Coin in cents.
	 */
	private int value;
	
	/**
	 * Constructor
	 * @param value the value of coin
	 */
	Coin(int valueCoin) {
		this.value = valueCoin;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}	
	
	
}
