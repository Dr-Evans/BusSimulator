
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class QueryResult
{
        Line line;
        Location departure;
        Location destination;
        
        public QueryResult()
        {
        }
        
        public QueryResult(Line line, Location departure, Location destination)
        {
           this.line = line;
           this.departure = departure;
           this.destination = destination;
        }

        public String toString()
        {
        	return "[QUERY RESULT:]\n" + line + " moving from " + departure + " heading to " + destination;
        }
    
    public QueryResult search(ArrayList<BusStop> locations, ArrayList<Line> lines, Location departure, Location destination)
    {
    	
	/** YOUR SEARCH ALGORITHM WILL GO HERE */
    	//Iterate through lines - find smallest distance
    	double minDistance = Double.MAX_VALUE;
    	Line minLineFromDep = lines.get(0);
    	BusStop minBusStopFromDep = (minLineFromDep.getRoute()).get(0);
    	
    	for (int i = 0; i < lines.size(); i++){
    		ArrayList<BusStop> route = (lines.get(i)).getRoute();
    		
    		//Calculate min distances
    		double[] distances = new double[route.size()];
    		for (int j = 0; j < route.size(); j++){
    			distances[j] = departure.distanceFrom(route.get(j));
    			System.out.print("Distances from dep: " + distances[j]);
    		}
    		System.out.println();
    		
    		//Calculate min distance
    		for (int j = 0; j < distances.length; j++){
    			if (minDistance > distances[j]){
    				minDistance = distances[j];
    				minLineFromDep = lines.get(i);
    				minBusStopFromDep = route.get(j);
    				System.out.println(minDistance + "   " + minBusStopFromDep);
    			}
    		}
    		
    	}
    	
    	minDistance = Double.MAX_VALUE;
    	
    	
    	ArrayList<BusStop> route = minLineFromDep.getRoute();
    	BusStop minBusStopFromDest = route.get(0);
    	for (int i = 0; i < route.size(); i++){
    		double distance = destination.distanceFrom(route.get(i));
    		if (minDistance > distance){
    			minDistance = distance;
    			minBusStopFromDest = route.get(i);
    			System.out.println(minDistance + "   " + minBusStopFromDest);
    		}
    	}
    	System.out.println(minLineFromDep + " from " + minBusStopFromDep + " to " + minBusStopFromDest);
    	return new QueryResult(minLineFromDep, minBusStopFromDep, minBusStopFromDest);
    	/*
    	// 1) Get a list of every line object with all the bus stop objects that goes through it
    	HashMap<Line, ArrayList<BusStop>> linesWithBusStops = new HashMap();
    	
    	for (int i = 0; i < lines.size(); i++)
    	{
    		ArrayList<BusStop> busStopsOnLine = new ArrayList();
    		
    		//Iterates through all locations to build an array of all the bus stops on the line
    		for (int j = 0; j < locations.size(); j++)
    		{
    			if ((lines.get(i)).onRoute(locations.get(j)))
    			{
    				busStopsOnLine.add(locations.get(j));
    			}	
    		}
    		
    		linesWithBusStops.put(lines.get(i), busStopsOnLine);
    	}
    	
    	Set<Map.Entry<Line,ArrayList<BusStop>>> linesWithBusStopsTuple = linesWithBusStops.entrySet();
    	
    	// 2) Check for the closest two bus stops on each line to the departure and destination
    	HashMap<Line, BusStop[]> linesAndClosestBusStops = new HashMap();
    	HashMap<Line, Double> linesAndDistances = new HashMap();
    	double departureDist = (double)Double.MAX_VALUE;
    	double destinationDist = (double)Double.MAX_VALUE;
    	double totalDist = (double)Double.MAX_VALUE;
    	for (Map.Entry i : linesWithBusStopsTuple)
    	{
    		// (Min Dest from Departure, Min Dest from Destination)
    		BusStop[] closestBusStops = new BusStop[2];
    		
    		double departureCalcDist;
    		double destinationCalcDist;
    		double totalCalcDist;
    		Line selectedLine = (Line)i.getKey();
    		//System.out.println("Selected Line = " + selectedLine);
    		BusStop closestBusStop;
    		for (int j = 0; j < (linesWithBusStops.get(selectedLine)).size(); j++)
    		{
    			closestBusStop = (linesWithBusStops.get(selectedLine)).get(j);
    			//System.out.println("Selected Bus Stop = " + closestBusStop);
    			departureCalcDist = closestBusStop.distanceFrom(departure);
    			//System.out.println("Distance from departure = " + departureCalcDist);
    			destinationCalcDist = closestBusStop.distanceFrom(destination);
    			//System.out.println("Distance from destination = " + destinationCalcDist);
    			
    			
    			
    			//System.out.println("Departure Comparison: " + departureCalcDist + " < " + departureDist + " " + (departureCalcDist < departureDist));
    			if (departureCalcDist < departureDist)
    			{
    				departureDist = departureCalcDist;
    				closestBusStops[0] = new BusStop(closestBusStop.getName(), closestBusStop.getX(), closestBusStop.getY());
    				//System.out.println("Current closest bus stop to departure = " + closestBusStops[0]);
    			}
    			//System.out.println("Destination Comparison: " + destinationCalcDist + " < " + destinationDist + " " + (destinationCalcDist < destinationDist));
    			if (destinationCalcDist < destinationDist)
    			{
    				destinationDist = destinationCalcDist;
    				closestBusStops[1] = new BusStop(closestBusStop.getName(), closestBusStop.getX(), closestBusStop.getY());
    				//System.out.println("Current closest bus stop to destination = " + closestBusStops[1]);
    			}
    			
    			totalCalcDist = departureDist + destinationDist;
    			//System.out.println("Total distance from departure and destination = " + totalCalcDist);
    			//System.out.println("Total Distance Comparison: " + totalCalcDist + " < " + totalDist + " " + (totalCalcDist < totalDist));
    			if (totalCalcDist < totalDist)
    			{
    				totalDist = totalCalcDist;
    				//System.out.println("Current smallest total distance " + totalDist);
    				
    			}
    		}
    		//System.out.println("Closest Bus Stop to depature for " + selectedLine + "= " + closestBusStops[0]);
    		//System.out.println("Closest Bus Stop to destination for " + selectedLine + "= " + closestBusStops[1]);
    		//System.out.println("Total walking distance = " + totalDist);
    		linesAndClosestBusStops.put(selectedLine, closestBusStops);
			linesAndDistances.put(selectedLine, totalDist);
			
			//Reset values to determine minimum
    		departureDist = (double)Double.MAX_VALUE;
        	destinationDist = (double)Double.MAX_VALUE;
        	totalDist = (double)Double.MAX_VALUE;
    	}
    	
    	// 3) Find line with smallest distance
    	Set<Map.Entry<Line,Double>> linesAndDistancesTuples = linesAndDistances.entrySet();
    	//System.out.println("FIND LINE WITH SMALLEST DISTNACE");
    	double minDistance = (double)Double.MAX_VALUE;
    	Line minLine = null;
    	for (Map.Entry m : linesAndDistancesTuples)
    	{
    		//System.out.println((Double)m.getValue() + " < " + minDistance + " " + ((Double)m.getValue() < minDistance));
    		if((Double)m.getValue() < minDistance)
    		{
    			minDistance = (Double)m.getValue();
    			//System.out.println("Minimum Distance = " + minDistance);
    			minLine = (Line)m.getKey(); 
    			//System.out.println("Corresponding Line = " + minLine);
    		}
    	}
    	BusStop minDeparture = (linesAndClosestBusStops.get(minLine))[0];
    	BusStop minDestination = (linesAndClosestBusStops.get(minLine))[1];
    	
    	return new QueryResult(minLine, minDeparture, minDestination);
    	*/
    }

    public Line getLine(){
    	return line;
    }
    
    public Location getDestination(){
    	return destination;
    }
    
    public Location getDeparture(){
    	return departure;
    }
    
    public void setLine(Line l){
    	this.line = l;
    }
    
    public void setDestination(Location d){
    	this.destination = d;
    }
    
    public void getDeparture(Location d){
    	this.departure = d;
    }
    public static ArrayList<BusStop> filterBusStops(ArrayList<Object> list)
    {
    	ArrayList<BusStop> bsList = new ArrayList();
        for(Object o: list)
	    if (o instanceof BusStop)
		bsList.add((BusStop)o);
        return bsList; 
    }
 
    public static ArrayList<Line> filterLines(ArrayList<Object> list)
    {
    	ArrayList<Line> lineList = new ArrayList();
        for(Object o: list)
	    if (o instanceof Line)
		lineList.add((Line)o);
        return lineList; 
    }
    /*
    public static void main(String[] args) throws Exception
    {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter input file name:");
        ArrayList<Object> rtsItems = IOUtil.readBusstopAndLineInfo(input.next());
        input.nextLine();
        ArrayList<BusStop> busStops = filterBusStops(rtsItems);
        ArrayList<Line> lines = filterLines(rtsItems);
        System.out.println("Please enter the query to find the best line");
        System.out.println("that stops at the closest bus stop from the");
        System.out.print("departure location (enter as x,y):");
        input.useDelimiter("[,\\s]+");         
        Location departure = new Location("departure", input.nextInt(), input.nextInt());  
        System.out.println("and stops at a bus stop closest to");
        System.out.print("destination location (enter as x,y):");
        Location destination = new Location("destination", input.nextInt(), input.nextInt());  
        System.out.println("favoring the one that is closer to the destination point.");
        QueryResult result = search(busStops, lines, departure, destination);
        System.out.println(result); 
    }
    */
}