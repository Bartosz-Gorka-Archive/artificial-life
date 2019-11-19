package trafficsimulation;

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import groovy.lang.Closure;
import repast.simphony.relogo.*;
import repast.simphony.relogo.builder.GeneratedByReLogoBuilder;
import repast.simphony.relogo.builder.ReLogoBuilderGeneratedFor;
import repast.simphony.space.SpatialException;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;

@GeneratedByReLogoBuilder
@SuppressWarnings({"unused","rawtypes","unchecked"})
public class ReLogoTurtle extends BaseTurtle{

	/**
	 * Makes a number of new userTurtles and then executes a set of commands on the
	 * created userTurtles.
	 * 
	 * @param number
	 *            a number
	 * @param closure
	 *            a set of commands
	 * @return created userTurtles
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserTurtle")
	public AgentSet<trafficsimulation.relogo.UserTurtle> hatchUserTurtles(int number, Closure closure) {
		AgentSet<trafficsimulation.relogo.UserTurtle> result = new AgentSet<>();
		AgentSet<Turtle> createResult = this.hatch(number,closure,"UserTurtle");
		for (Turtle t : createResult){
			if (t instanceof trafficsimulation.relogo.UserTurtle){
				result.add((trafficsimulation.relogo.UserTurtle)t);
			}
		} 
		return result;
	}

	/**
	 * Makes a number of new userTurtles and then executes a set of commands on the
	 * created userTurtles.
	 * 
	 * @param number
	 *            a number
	 * @param closure
	 *            a set of commands
	 * @return created userTurtles
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserTurtle")
	public AgentSet<trafficsimulation.relogo.UserTurtle> hatchUserTurtles(int number) {
		return hatchUserTurtles(number,null);
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
		GridPoint gridPoint = Utility.getGridPointAtDisplacement(getTurtleLocation(),displacement,getMyObserver());
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
	 * Returns the value of the patchType variable of the underlying patch.
	 * 
	 * @return patchType of type trafficsimulation.relogo.PatchType
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserPatch")
	public trafficsimulation.relogo.PatchType getPatchType(){
		trafficsimulation.relogo.UserPatch p = (trafficsimulation.relogo.UserPatch)patchHere();
		return p.patchType;
	}


	/**
	 * Sets the value of patchType of the underlying patch.
	 * 
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserPatch")
	public void setPatchType(trafficsimulation.relogo.PatchType value){
		trafficsimulation.relogo.UserPatch p = (trafficsimulation.relogo.UserPatch)patchHere();
		p.patchType = value;
	}

	/**
	 * Returns the value of the crossing variable of the underlying patch.
	 * 
	 * @return crossing of type trafficsimulation.relogo.Crossing
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserPatch")
	public trafficsimulation.relogo.Crossing getCrossing(){
		trafficsimulation.relogo.UserPatch p = (trafficsimulation.relogo.UserPatch)patchHere();
		return p.crossing;
	}


	/**
	 * Sets the value of crossing of the underlying patch.
	 * 
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserPatch")
	public void setCrossing(trafficsimulation.relogo.Crossing value){
		trafficsimulation.relogo.UserPatch p = (trafficsimulation.relogo.UserPatch)patchHere();
		p.crossing = value;
	}

	/**
	 * Returns the value of the pedestianOnZebra variable of the underlying patch.
	 * 
	 * @return pedestianOnZebra of type boolean
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserPatch")
	public boolean getPedestianOnZebra(){
		trafficsimulation.relogo.UserPatch p = (trafficsimulation.relogo.UserPatch)patchHere();
		return p.pedestianOnZebra;
	}


	/**
	 * Sets the value of pedestianOnZebra of the underlying patch.
	 * 
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserPatch")
	public void setPedestianOnZebra(boolean value){
		trafficsimulation.relogo.UserPatch p = (trafficsimulation.relogo.UserPatch)patchHere();
		p.pedestianOnZebra = value;
	}

	/**
	 * Returns the value of the roadNo variable of the underlying patch.
	 * 
	 * @return roadNo of type int
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserPatch")
	public int getRoadNo(){
		trafficsimulation.relogo.UserPatch p = (trafficsimulation.relogo.UserPatch)patchHere();
		return p.roadNo;
	}


	/**
	 * Sets the value of roadNo of the underlying patch.
	 * 
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserPatch")
	public void setRoadNo(int value){
		trafficsimulation.relogo.UserPatch p = (trafficsimulation.relogo.UserPatch)patchHere();
		p.roadNo = value;
	}

	/**
	 * Returns the value of the actionRule variable of the underlying patch.
	 * 
	 * @return actionRule of type trafficsimulation.relogo.ActionRule
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserPatch")
	public trafficsimulation.relogo.ActionRule getActionRule(){
		trafficsimulation.relogo.UserPatch p = (trafficsimulation.relogo.UserPatch)patchHere();
		return p.actionRule;
	}


	/**
	 * Sets the value of actionRule of the underlying patch.
	 * 
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserPatch")
	public void setActionRule(trafficsimulation.relogo.ActionRule value){
		trafficsimulation.relogo.UserPatch p = (trafficsimulation.relogo.UserPatch)patchHere();
		p.actionRule = value;
	}

	/**
	 * Makes a directed userLink from a turtle to the caller then executes a set of
	 * commands on the created userLink.
	 * 
	 * @param t
	 *            a turtle
	 * @param closure
	 *            a set of commands
	 * @return created userLink
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserLink")
	public trafficsimulation.relogo.UserLink createUserLinkFrom(Turtle t, Closure closure){
		trafficsimulation.relogo.UserLink link = (trafficsimulation.relogo.UserLink)this.getMyObserver().getNetwork("UserLink").addEdge(t,this);
		if (closure != null){
			this.ask(link,closure);
		}
		return link;
	}

	/**
	 * Makes a directed userLink from a turtle to the caller.
	 * 
	 * @param t
	 *            a turtle
	 * @return created userLink
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserLink")
	public trafficsimulation.relogo.UserLink createUserLinkFrom(Turtle t){
			return createUserLinkFrom(t,null);
	}

	/**
	 * Makes directed userLinks from a collection to the caller then executes a set
	 * of commands on the created userLinks.
	 * 
	 * @param a
	 *            a collection
	 * @param closure
	 *            a set of commands
	 * @return created userLinks
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserLink")
	public AgentSet<trafficsimulation.relogo.UserLink> createUserLinksFrom(Collection<? extends Turtle> a, Closure closure){
		AgentSet<trafficsimulation.relogo.UserLink> links = new AgentSet<trafficsimulation.relogo.UserLink>();
		for(Turtle t : a){
			links.add((trafficsimulation.relogo.UserLink)this.getMyObserver().getNetwork("UserLink").addEdge(t,this));
		}
		if (closure != null){
			this.ask(links,closure);
		}
		return links;
	}

	/**
	 * Makes directed userLinks from a collection to the caller.
	 * 
	 * @param a
	 *            a collection
	 * @return created userLinks
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserLink")
	public AgentSet<trafficsimulation.relogo.UserLink> createUserLinksFrom(Collection<? extends Turtle> a){
		return createUserLinksFrom(a,null);
	}

	/**
	 * Makes a directed userLink to a turtle from the caller then executes a set of
	 * commands on the created userLink.
	 * 
	 * @param t
	 *            a turtle
	 * @param closure
	 *            a set of commands
	 * @return created userLink
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserLink")
	public trafficsimulation.relogo.UserLink createUserLinkTo(Turtle t, Closure closure){
		trafficsimulation.relogo.UserLink link = (trafficsimulation.relogo.UserLink)this.getMyObserver().getNetwork("UserLink").addEdge(this,t);
		if (closure != null){
			this.ask(link,closure);
		}
		return link;
	}

	/**
	 * Makes a directed userLink to a turtle from the caller.
	 * 
	 * @param t
	 *            a turtle
	 * @return created userLink
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserLink")
	public trafficsimulation.relogo.UserLink createUserLinkTo(Turtle t){
			return createUserLinkTo(t,null);
	}

	/**
	 * Makes directed userLinks to a collection from the caller then executes a set
	 * of commands on the created userLinks.
	 * 
	 * @param a
	 *            a collection
	 * @param closure
	 *            a set of commands
	 * @return created userLinks
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserLink")
	public AgentSet<trafficsimulation.relogo.UserLink> createUserLinksTo(Collection<? extends Turtle> a, Closure closure){
		AgentSet<trafficsimulation.relogo.UserLink> links = new AgentSet<trafficsimulation.relogo.UserLink>();
		for(Object t : a){
			if (t instanceof Turtle){
				links.add((trafficsimulation.relogo.UserLink)this.getMyObserver().getNetwork("UserLink").addEdge(this,(Turtle)t));
			}
		}
		if (closure != null){
			this.ask(links,closure);
		}
		return links;
	}

	/**
	 * Makes directed userLinks to a collection from the caller.
	 * 
	 * @param a
	 *            a collection
	 * @return created userLinks
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserLink")
	public AgentSet<trafficsimulation.relogo.UserLink> createUserLinksTo(Collection<? extends Turtle> a){
		return createUserLinksTo(a,null);
	}

	/**
	 * Queries if there is a directed userLink from a turtle to the caller.
	 * 
	 * @param t
	 *            a turtle
	 * @return true or false based on whether there is a directed userLink from
	 *         turtle t to the caller
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserLink")
	public boolean inUserLinkNeighborQ(Turtle t){
		return this.getMyObserver().getNetwork("UserLink").isPredecessor(t, this);
	}

	/**
	 * Returns the agentset with directed userLinks to the caller.
	 * 
	 * @return agentset with directed userLinks to the caller
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserLink")
	public AgentSet inUserLinkNeighbors(){
		AgentSet result = new AgentSet();
		for(Object o : this.getMyObserver().getNetwork("UserLink").getPredecessors(this)){
			result.add(o);
		}
		return result;
	}

	/**
	 * Returns the directed userLink from a turtle to the caller.
	 * 
	 * @param t
	 *            a turtle
	 * @return directed userLink from turtle t to the caller
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserLink")
	public trafficsimulation.relogo.UserLink inUserLinkFrom(Turtle t){
		return (trafficsimulation.relogo.UserLink)this.getMyObserver().getNetwork("UserLink").getEdge(t,this);
	}

	/**
	 * Returns an agentset of directed userLinks from other turtles to the caller.
	 * 
	 * @return agentset of directed userLinks from other turtles to the caller
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserLink")
	public AgentSet<trafficsimulation.relogo.UserLink> myInUserLinks(){
		AgentSet<trafficsimulation.relogo.UserLink> result = new AgentSet<trafficsimulation.relogo.UserLink>();
		for(Object o :  this.getMyObserver().getNetwork("UserLink").getInEdges(this)){
			if (o instanceof trafficsimulation.relogo.UserLink){
				result.add((trafficsimulation.relogo.UserLink) o);
			}
		}
		return result;
	}

	/**
	 * Returns an agentset of directed userLinks to other turtles from the caller.
	 * 
	 * @return agentset of directed userLinks to other turtles from the caller
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserLink")
	public AgentSet<trafficsimulation.relogo.UserLink> myOutUserLinks(){
		AgentSet<trafficsimulation.relogo.UserLink> result = new AgentSet<trafficsimulation.relogo.UserLink>();
		for(Object o :  this.getMyObserver().getNetwork("UserLink").getOutEdges(this)){
			if (o instanceof trafficsimulation.relogo.UserLink){
				result.add((trafficsimulation.relogo.UserLink) o);
			}
		}
		return result;
	}

	/**
	 * Queries if there is a directed userLink to a turtle from the caller.
	 * 
	 * @param t
	 *            a turtle
	 * @return true or false based on whether there is a directed userLink to
	 *         turtle t from the caller
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserLink")
	public boolean outUserLinkNeighborQ(Turtle t){
		return this.getMyObserver().getNetwork("UserLink").isPredecessor(this, t);
	}

	/**
	 * Returns the agentset with directed userLinks from the caller.
	 * 
	 * @return agentset with directed userLinks from the caller
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserLink")
	public AgentSet outUserLinkNeighbors(){
		AgentSet result = new AgentSet();
		for(Object o : this.getMyObserver().getNetwork("UserLink").getSuccessors(this)){
			result.add(o);
		}
		return result;
	}

	/**
	 * Returns the directed userLink to a turtle from the caller.
	 * 
	 * @param t
	 *            a turtle
	 * @return directed userLink to turtle t from the caller
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserLink")
	public trafficsimulation.relogo.UserLink outUserLinkTo(Turtle t){
		return (trafficsimulation.relogo.UserLink)this.getMyObserver().getNetwork("UserLink").getEdge(this, t);
	}

	/**
	 * Reports true if there is a userLink connecting t and the caller.
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserLink")
	public boolean userLinkNeighborQ(Turtle t){
		return this.getMyObserver().getNetwork("UserLink").isAdjacent(this, t);
	}

	/**
	 * Returns the agentset of all turtles found at the other end of
	 * userLinks connected to the calling turtle.
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserLink")
	public AgentSet userLinkNeighbors(){
		AgentSet result = new AgentSet();
		for(Object o : this.getMyObserver().getNetwork("UserLink").getAdjacent(this)){
			result.add(o);
		}
		return result;
	}

	/**
	 * Returns an agentset of the caller's userLinks.
	 * 
	 * @return agentset of the caller's userLinks
	 */
	@ReLogoBuilderGeneratedFor("trafficsimulation.relogo.UserLink")
	public AgentSet<trafficsimulation.relogo.UserLink> myUserLinks(){
		AgentSet<trafficsimulation.relogo.UserLink> result = new AgentSet<trafficsimulation.relogo.UserLink>();
		for(Object o : this.getMyObserver().getNetwork("UserLink").getEdges(this)){
			if (o instanceof trafficsimulation.relogo.UserLink){
				result.add((trafficsimulation.relogo.UserLink)o);
			}
		}
		return result;
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