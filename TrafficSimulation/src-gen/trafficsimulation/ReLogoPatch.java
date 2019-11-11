package trafficsimulation;

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import groovy.lang.Closure;
import repast.simphony.relogo.*;
import repast.simphony.relogo.ast.Diffusible;
import repast.simphony.relogo.builder.GeneratedByReLogoBuilder;
import repast.simphony.relogo.builder.ReLogoBuilderGeneratedFor;
import repast.simphony.space.SpatialException;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;

@GeneratedByReLogoBuilder
@SuppressWarnings({"unused","rawtypes","unchecked"})
public class ReLogoPatch extends BasePatch{

	/**
	 * Sprouts (makes) a number of new userTurtles and then executes a set of commands on the
	 * created userTurtles.
	 * 
	 * @param number
	 *            a number
	 * @param closure
	 *            a set of commands
	 * @return created userTurtles
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserTurtle")
	public AgentSet<trafficsimulation.relogo.UserTurtle> sproutUserTurtles(int number, Closure closure) {
		AgentSet<trafficsimulation.relogo.UserTurtle> result = new AgentSet<>();
		AgentSet<Turtle> createResult = this.sprout(number,closure,"UserTurtle");
		for (Turtle t : createResult){
			if (t instanceof trafficsimulation.relogo.UserTurtle){
				result.add((trafficsimulation.relogo.UserTurtle)t);
			}
		} 
		return result;
	}

	/**
	 * Sprouts (makes) a number of new userTurtles and then executes a set of commands on the
	 * created userTurtles.
	 * 
	 * @param number
	 *            a number
	 * @param closure
	 *            a set of commands
	 * @return created userTurtles
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserTurtle")
	public AgentSet<trafficsimulation.relogo.UserTurtle> sproutUserTurtles(int number) {
		return sproutUserTurtles(number,null);
	}

	/**
	 * Returns an agentset of userTurtles from the patch of the caller.
	 * 
	 * @return agentset of userTurtles from the caller's patch
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserTurtle")
	public AgentSet<trafficsimulation.relogo.UserTurtle> userTurtlesHere(){
	  Grid grid = getMyObserver().getGrid();
	  GridPoint gridPoint = grid.getLocation(this);
	  AgentSet<trafficsimulation.relogo.UserTurtle> result = new AgentSet<trafficsimulation.relogo.UserTurtle>();
	  for (Turtle t : Utility.getTurtlesOnGridPoint(gridPoint,getMyObserver(),"userTurtle")){
			if (t instanceof trafficsimulation.relogo.UserTurtle)
			result.add((trafficsimulation.relogo.UserTurtle)t);
		}
		return result;
	}

	/**
	 * Returns the agentset of userTurtles on the patch at the direction (ndx, ndy) from the
	 * caller.
	 * 
	 * @param nX
	 *            a number
	 * @param nY
	 *            a number
	 * @returns agentset of userTurtles at the direction (nX, nY) from the caller
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserTurtle")
	public AgentSet<trafficsimulation.relogo.UserTurtle> userTurtlesAt(Number nX, Number nY){
		double dx = nX.doubleValue();
		double dy = nY.doubleValue();
		double[] displacement = {dx,dy};

		try{
		GridPoint gridPoint = Utility.getGridPointAtDisplacement(getGridLocationAsNdPoint(),displacement,getMyObserver());
		AgentSet<trafficsimulation.relogo.UserTurtle> result = new AgentSet<trafficsimulation.relogo.UserTurtle>();						
		for (Turtle t : Utility.getTurtlesOnGridPoint(gridPoint,getMyObserver(),"userTurtle")){
			if (t instanceof trafficsimulation.relogo.UserTurtle)
			result.add((trafficsimulation.relogo.UserTurtle)t);
		}
		return result;
		}
		catch(SpatialException e){
			return new AgentSet<trafficsimulation.relogo.UserTurtle>();
		}
	}

	/**
	 * Returns an agentset of userTurtles on a given patch.
	 * 
	 * @param p
	 *            a patch
	 * @return agentset of userTurtles on patch p
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserTurtle")
	public AgentSet<trafficsimulation.relogo.UserTurtle> userTurtlesOn(Patch p){
		AgentSet<trafficsimulation.relogo.UserTurtle> result = new AgentSet<trafficsimulation.relogo.UserTurtle>();						
		for (Turtle t : Utility.getTurtlesOnGridPoint(p.getGridLocation(),getMyObserver(),"userTurtle")){
			if (t instanceof trafficsimulation.relogo.UserTurtle)
			result.add((trafficsimulation.relogo.UserTurtle)t);
		}
		return result;
	}

	/**
	 * Returns an agentset of userTurtles on the same patch as a turtle.
	 * 
	 * @param t
	 *            a turtle
	 * @return agentset of userTurtles on the same patch as turtle t
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserTurtle")
	public AgentSet<trafficsimulation.relogo.UserTurtle> userTurtlesOn(Turtle t){
		AgentSet<trafficsimulation.relogo.UserTurtle> result = new AgentSet<trafficsimulation.relogo.UserTurtle>();						
		for (Turtle tt : Utility.getTurtlesOnGridPoint(Utility.ndPointToGridPoint(t.getTurtleLocation()),getMyObserver(),"userTurtle")){
			if (tt instanceof trafficsimulation.relogo.UserTurtle)
			result.add((trafficsimulation.relogo.UserTurtle)tt);
		}
		return result;
	}

	/**
	 * Returns an agentset of userTurtles on the patches in a collection or on the patches
	 * that a collection of turtles are.
	 * 
	 * @param a
	 *            a collection
	 * @return agentset of userTurtles on the patches in collection a or on the patches
	 *         that collection a turtles are
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserTurtle")
	public AgentSet<trafficsimulation.relogo.UserTurtle> userTurtlesOn(Collection c){

		if (c == null || c.isEmpty()){
			return new AgentSet<trafficsimulation.relogo.UserTurtle>();
		}

		Set<trafficsimulation.relogo.UserTurtle> total = new HashSet<trafficsimulation.relogo.UserTurtle>();
		if (c.iterator().next() instanceof Turtle){
			for (Object o : c){
				if (o instanceof Turtle){
					Turtle t = (Turtle) o;
					total.addAll(userTurtlesOn(t));
				}
			}
		}
		else {
			for (Object o : c){
				if (o instanceof Patch){
					Patch p = (Patch) o;
					total.addAll(userTurtlesOn(p));
				}
			}
		}
		return new AgentSet<trafficsimulation.relogo.UserTurtle>(total);
	}

	/**
	 * Queries if object is a userTurtle.
	 * 
	 * @param o
	 *            an object
	 * @return true or false based on whether the object is a userTurtle
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserTurtle")
	public boolean isUserTurtleQ(Object o){
		return (o instanceof trafficsimulation.relogo.UserTurtle);
	}

	/**
	 * Returns an agentset containing all userTurtles.
	 * 
	 * @return agentset of all userTurtles
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserTurtle")
	public AgentSet<trafficsimulation.relogo.UserTurtle> userTurtles(){
		AgentSet<trafficsimulation.relogo.UserTurtle> a = new AgentSet<trafficsimulation.relogo.UserTurtle>();
		for (Object e : this.getMyObserver().getContext().getObjects(trafficsimulation.relogo.UserTurtle.class)) {
			if (e instanceof trafficsimulation.relogo.UserTurtle){
				a.add((trafficsimulation.relogo.UserTurtle)e);
			}
		}
		return a;
	}

	/**
	 * Returns the userTurtle with the given who number.
	 * 
	 * @param number
	 *            a number
	 * @return turtle number
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserTurtle")
	public trafficsimulation.relogo.UserTurtle userTurtle(Number number){
		Turtle turtle = Utility.turtleU(number.intValue(), getMyObserver());
		if (turtle instanceof trafficsimulation.relogo.UserTurtle)
			return (trafficsimulation.relogo.UserTurtle) turtle;
		return null;
	}

	/**
	 * Queries if object is a userLink.
	 * 
	 * @param o
	 *            an object
	 * @return true or false based on whether the object is a userLink
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserLink")
	public boolean isUserLinkQ(Object o){
		return (o instanceof trafficsimulation.relogo.UserLink);
	}

	/**
	 * Returns an agentset containing all userLinks.
	 * 
	 * @return agentset of all userLinks
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserLink")
	public AgentSet<trafficsimulation.relogo.UserLink> userLinks(){
		AgentSet<trafficsimulation.relogo.UserLink> a = new AgentSet<trafficsimulation.relogo.UserLink>();
		for (Object e : this.getMyObserver().getContext().getObjects(trafficsimulation.relogo.UserLink.class)) {
			if (e instanceof trafficsimulation.relogo.UserLink){
				a.add((trafficsimulation.relogo.UserLink)e);
			}
		}
		return a;
	}

	/**
	 * Returns the userLink between two turtles.
	 * 
	 * @param oneEnd
	 *            an integer
	 * @param otherEnd
	 *            an integer
	 * @return userLink between two turtles
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserLink")
	public trafficsimulation.relogo.UserLink userLink(Number oneEnd, Number otherEnd) {
		return (trafficsimulation.relogo.UserLink)(this.getMyObserver().getNetwork("UserLink").getEdge(turtle(oneEnd),turtle(otherEnd)));
	}

	/**
	 * Returns the userLink between two turtles.
	 * 
	 * @param oneEnd
	 *            a turtle
	 * @param otherEnd
	 *            a turtle
	 * @return userLink between two turtles
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserLink")
	public trafficsimulation.relogo.UserLink userLink(Turtle oneEnd, Turtle otherEnd) {
		return userLink(oneEnd.getWho(), otherEnd.getWho());
	}


}