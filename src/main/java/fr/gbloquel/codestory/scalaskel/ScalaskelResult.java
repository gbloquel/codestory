package fr.gbloquel.codestory.scalaskel;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.google.common.base.Objects;


/**
 * This class stores the number of coins. 
 * @author greg
 *
 */

@JsonSerialize(include = Inclusion.NON_DEFAULT)
public class ScalaskelResult {

	@JsonProperty("foo")
	private int countFoo;
	
	@JsonProperty("bar")
	private int countBar;
	
	@JsonProperty("qix")
	private int countQix;
	
	@JsonProperty("baz")
	private int countBaz;
	
	
	/**
	 * Default Constructor.
	 */
	public ScalaskelResult() {
		countBar = 0;
		countBaz = 0;
		countFoo = 0;
		countQix = 0;
	}


	public void setCountFoo(int countFoo) {
		this.countFoo = countFoo;
	}


	public void setCountBar(int countBar) {
		this.countBar = countBar;
	}


	public void setCountQix(int countQix) {
		this.countQix = countQix;
	}


	public void setCountBaz(int countBaz) {
		this.countBaz = countBaz;
	}
	
	public int getCountFoo() {
		return countFoo;
	}
	
	
	public int getCountBar() {
		return countBar;
	}
	
	
	public int getCountQix() {
		return countQix;
	}
	
	
	public int getCountBaz() {
		return countBaz;
	}
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
					.add("foo", countFoo)
					.add("bar", countBar)
					.add("qix", countQix)
					.add("baz", countBaz)
						.toString();
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		
		
		return (this.countBar == ((ScalaskelResult) obj).countBar)
				&& (this.countBaz == ((ScalaskelResult) obj).countBaz)
				&& (this.countFoo == ((ScalaskelResult) obj).countFoo)
				&& (this.countQix == ((ScalaskelResult) obj).countQix);
		
	}
	
}
