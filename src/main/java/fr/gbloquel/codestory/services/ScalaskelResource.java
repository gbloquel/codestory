package fr.gbloquel.codestory.services;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.common.collect.Lists;

import fr.gbloquel.codestory.scalaskel.Coin;
import fr.gbloquel.codestory.scalaskel.ScalaskelResult;

@Path("/scalaskel")
public class ScalaskelResource {

	
	@GET
	@Path("change/{value}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response change(@PathParam("value")Integer value) {
		
		
		Set<ScalaskelResult> resultList =  new CopyOnWriteArraySet<ScalaskelResult>();
		List<Coin> coins = Lists.newArrayList(Coin.BAZ, Coin.QIX, Coin.BAR, Coin.FOO);
		
		for(int i = 0; i < 4; i++) {
			computeCoins(new Integer(value), resultList, coins);
			coins.remove(0);
			
		}
		
		return Response.ok(resultList).build();
		
	}
	
	/**
	 * Compute the number of coins selon the value
	 * @param value
	 * @param coin
	 * @return the number of coins
	 */
	private int getNumberOfPieces(int value, Coin coin) {
		return value / coin.getValue(); 
	}
	
	
	/**
	 * Compute the soluces.
	 * @param value
	 * @param resultList
	 */
	private void computeCoins(int value, Set<ScalaskelResult> resultList, List<Coin> coins) {
		
		ScalaskelResult scalaskelResult= new ScalaskelResult();
		int count = 0;
		
		if(value >= Coin.BAZ.getValue() && coins.contains(Coin.BAZ)) {
			count = getNumberOfPieces(value, Coin.BAZ);
			scalaskelResult.setCountBaz(count);
			value -= count * Coin.BAZ.getValue();
		}
		
		if(value >= Coin.QIX.getValue() && coins.contains(Coin.QIX)) {
			count = getNumberOfPieces(value, Coin.QIX);
			scalaskelResult.setCountQix(count);
			value -= count * Coin.QIX.getValue();
		}
		
		if(value >= Coin.BAR.getValue() && coins.contains(Coin.BAR)) {
			count = getNumberOfPieces(value, Coin.BAR);
			scalaskelResult.setCountBar(count);
			value -= count * Coin.BAR.getValue();
		}
		
		if(value >= Coin.FOO.getValue() && coins.contains(Coin.FOO)) {
			count = getNumberOfPieces(value, Coin.FOO);
			scalaskelResult.setCountFoo(count);
			value -= count * Coin.FOO.getValue();
		}
		resultList.add(scalaskelResult);
		
	}
	
}
