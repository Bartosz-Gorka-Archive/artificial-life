package trafficsimulation.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;
import repast.simphony.relogo.schedule.Go;
import repast.simphony.relogo.schedule.Setup;
import trafficsimulation.ReLogoObserver;

class UserObserver extends ReLogoObserver{
	// How many patches we will ignore when build map
	int notUsedPatches = 2
	// Draw special bus road?
	boolean useBusRoad = false
	int roadsHorizontally = 4
	int roadsVertically = 3
	
	@Setup
	def setup(){
		// Clear environment
		clearAll()
		
		// Prepare roads
		setRoadsOnMap()
		
		createTurtles(10) {
			setxy(randomXcor(), randomYcor())
			if (Math.random() >= 0.5) {
				markAsBus(it)
			} else {
				markAsCar(it)
			}	
		}
	}
		
	 /**
		@Go
		def go(){
			ask(turtles()){
				left(random(90))
				right(random(90))
				forward(random(10))
			}
		}

	 */
	def setRoadsOnMap() {
		// How many points we can use
		int wordSizeX = abs(getMinPxcor()) + getMaxPxcor() - 2 * notUsedPatches
		int wordSizeY = abs(getMinPycor()) + getMaxPycor() - 2 * notUsedPatches
		int skipX = (wordSizeX - (2 * 2 * roadsVertically)) / (roadsVertically + 1)
		int skipY = (wordSizeY - (2 * 2 * roadsHorizontally)) / (roadsHorizontally + 1)
		
		// Set all patches to green
		ask(patches()) {
			pcolor = green()
		}
		
		// Horizontally roads
		for(int roundNumber = 1; roundNumber <= roadsHorizontally; roundNumber++) {
			int rowIndex =  getMinPycor()  + roundNumber * skipY + roundNumber * 4 - 1
			
			for (int i = getMinPxcor(); i <= getMaxPxcor(); i++) {
				if (useBusRoad) {
					markAsBusRoad(patch(i, rowIndex))
					markAsBusRoad(patch(i, rowIndex + 3))
				} else {
					markAsNormalRoad(patch(i, rowIndex))
					markAsNormalRoad(patch(i, rowIndex + 3))
				}
				markAsNormalRoad(patch(i, rowIndex + 1))
				markAsNormalRoad(patch(i, rowIndex + 2))
			}
		}
		
		// Vertically roads
		for(int roundNumber = 1; roundNumber <= roadsVertically; roundNumber++) {
			int rowIndex =  getMinPxcor()  + roundNumber * skipX + roundNumber * 4 - 1
			
			for (int i = getMinPycor(); i <= getMaxPycor(); i++) {
				if (useBusRoad) {
					markAsBusRoad(patch(rowIndex, i))
					markAsBusRoad(patch(rowIndex + 3, i))
				} else {
					markAsNormalRoad(patch(rowIndex, i))
					markAsNormalRoad(patch(rowIndex + 3, i))
				}
				markAsNormalRoad(patch(rowIndex + 1, i))
				markAsNormalRoad(patch(rowIndex + 2, i))
			}
		}
		
		// Footpath or green lawn
		ask(patches()) {
			if (it.roadType != RoadType.SPECIAL && it.roadType != RoadType.NORMAL) {
				// Check is footpath?
				boolean footpath = false
				ask(neighbors4()) {
					if (it.roadType == RoadType.SPECIAL || it.roadType == RoadType.NORMAL)
						footpath = true
				}
				
				// Draw footpath or green lawn
				if (footpath) {
					pcolor = black()
				} else {
					pcolor = green()
				}
			}
		}
	}
	
	def markAsBusRoad(UserPatch patch) {
		patch.roadType = RoadType.SPECIAL
		patch.pcolor = white()
	}
	
	def markAsNormalRoad(UserPatch patch) {
		patch.roadType = RoadType.NORMAL
		patch.pcolor = 9.5d
	}
	
	def markAsBus(UserTurtle turtle) {
		turtle.vehicleType = VehicleType.BUS
		turtle.setShape("truck")
	}
	
	def markAsCar(UserTurtle turtle) {
		turtle.vehicleType = VehicleType.CAR
		turtle.setShape("car")
	}
}