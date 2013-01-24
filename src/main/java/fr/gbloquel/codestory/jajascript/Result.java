package fr.gbloquel.codestory.jajascript;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

/**
 * This class contains the response.
 * 
 * @author g.bloquel
 * 
 */

public class Result {

    @JsonProperty("gain")
    private long profit;

    @JsonProperty("path")
    private List<String> flightPaths = new ArrayList<String>();

    /**
     * @return the profit
     */
    public long getProfit() {
        return profit;
    }

    public Result() {

    }

    /**
     * Update the result
     * 
     * @param result
     */
    public void update(Result result) {
        this.profit = result.getProfit();
        
        List<String> listTemp = Lists.newArrayList();
        for(String flight: result.flightPaths) {
        	listTemp.add(new String(flight));
        }
        this.getFlightPaths().clear();
        for (String flight : listTemp) {
            this.flightPaths.add(new String(flight));
        }
    }

    /**
     * @param profit
     *            the profit to set
     */
    public void setProfit(long profit) {
        this.profit = profit;
    }

    /**
     * @return the flightPaths
     */
    public List<String> getFlightPaths() {
        return flightPaths;
    }


    /**
     * @param flightPaths
     *            the flightPaths to set
     */
    public void setFlightPaths(List<String> flightPaths) {
        this.flightPaths = flightPaths;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                            .add("gain", profit)
                            .add("path", flightPaths)

                                 .toString();
    }

}
