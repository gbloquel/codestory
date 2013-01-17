package fr.gbloquel.codestory.services;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.gbloquel.codestory.scalaskel.Coin;
import fr.gbloquel.codestory.scalaskel.ScalaskelResult;

@Path("/scalaskel")
public class ScalaskelResource {

	@GET
	@Path("change/{value}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response change(@PathParam("value") Integer value) {
		Set<ScalaskelResult> resultList = new CopyOnWriteArraySet<ScalaskelResult>();
		swappingBazQixBarFoo(value, resultList);
		return Response.ok(resultList).build();
	}

	public void swappingBazQixBarFoo(int value, Set<ScalaskelResult> resultList) {
		int countBaz = getNumberOfPieces(value, Coin.BAZ);
		
		for(int i = 0; i <= countBaz; i++) {			 
			swappingQixBarFoo(value - (i*Coin.BAZ.getValue()), resultList, new ScalaskelResult(0, 0, 0, i));
		}
	}
	
	

	private void swappingQixBarFoo(int value, Set<ScalaskelResult> resultList, ScalaskelResult result) {
		
		int countQix = getNumberOfPieces(value, Coin.QIX);
		
		for(int i = 0; i <= countQix; i++){
			ScalaskelResult resultQix = new ScalaskelResult();
			resultQix.setCountQix(i);
			resultQix.setCountBaz(result.getCountBaz());
			swappingBarFoo(value - (i*Coin.QIX.getValue()), resultList,resultQix);
		}
	}
	
	
	private void swappingBarFoo(int value, Set<ScalaskelResult> resultList, ScalaskelResult resultQixBaz) {
		
		int countBar = getNumberOfPieces(value, Coin.BAR);
		
		for (int i = 0; i <= countBar; i++) {
			ScalaskelResult result = new ScalaskelResult();
			result.setCountQix(resultQixBaz.getCountQix());
			result.setCountBaz(resultQixBaz.getCountBaz());
			result.setCountBar(i);
			swappingFoo(value - (i*Coin.BAR.getValue()), result);
			resultList.add(result);
		}

	}

	private void swappingFoo(int value, ScalaskelResult result) {
		result.setCountFoo(getNumberOfPieces(value, Coin.FOO));
	}

	/**
	 * Compute the number of coins selon the value
	 * 
	 * @param value
	 * @param coin
	 * @return the number of coins
	 */
	private int getNumberOfPieces(int value, Coin coin) {
		return value / coin.getValue();
	}

}
