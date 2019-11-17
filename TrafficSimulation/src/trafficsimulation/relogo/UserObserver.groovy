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
	int notUsedPatches = 0
	int howManyBusRoads = 1
	int roadsHorizontally = 2
	int roadsVertically = 2
	int howWidthRoads = 2
	int howManyCrossingWithLights = 1
	int howManyCrossingCircles = 3
	int lightTimerTicks = 10
	boolean usePedestrians = true
	HashSet<UserPatch> patchesCrossing = new HashSet<>()
	HashSet<Crossing> crossings = new HashSet<>()
	
	@Setup
	def setup(){
		// Clear environment
		clearAll()
		
		// Prepare roads
		setRoadsOnMap()
		
		// Draw zebra crossing
		if (usePedestrians) {
			drawZebraCrossing()
		}
		
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
	
	def drawZebraCrossing() {
		
	}

	def setRoadsOnMap() {
		// How many points we can use
		int wordSizeX = abs(getMinPxcor()) + getMaxPxcor() - 2 * notUsedPatches
		int wordSizeY = abs(getMinPycor()) + getMaxPycor() - 2 * notUsedPatches
		int skipX = (wordSizeX - (2 * howWidthRoads * roadsVertically)) / (roadsVertically + 1)
		int skipY = (wordSizeY - (2 * howWidthRoads * roadsHorizontally)) / (roadsHorizontally + 1)
		
		// Horizontally roads
		for(int roundNumber = 1; roundNumber <= roadsHorizontally; roundNumber++) {
			int rowIndex =  getMinPycor() + (roundNumber * skipY) + (roundNumber * 2 * howWidthRoads) - 1
			
			for (int i = getMinPxcor(); i <= getMaxPxcor(); i++) {
				for (int roadNo = 0; roadNo < howWidthRoads; roadNo++) {
					if (roadNo < howManyBusRoads) {
						markAsBusRoad(patch(i, rowIndex + roadNo))
					} else {
						markAsNormalRoad(patch(i, rowIndex + roadNo))
					}
				}
				
				for (int roadNo = 0; roadNo < howWidthRoads; roadNo++) {
					if (roadNo < (howWidthRoads - howManyBusRoads)) {
						markAsNormalRoad(patch(i, rowIndex + howWidthRoads + roadNo))
					} else {
						markAsBusRoad(patch(i, rowIndex + howWidthRoads + roadNo))
					}
				}
			}
		}
		
		// Vertically roads
		for(int roundNumber = 1; roundNumber <= roadsVertically; roundNumber++) {
			int rowIndex =  getMinPxcor() + (roundNumber * skipX) + (roundNumber * 2 * howWidthRoads) - 1
			
			for (int i = getMinPycor(); i <= getMaxPycor(); i++) {
				for (int roadNo = 0; roadNo < howWidthRoads; roadNo++) {
					// If already set patch type - we have crossing now
					if (patch(rowIndex + roadNo, i).patchType) {
						markAsCrossing(patch(rowIndex + roadNo, i))
					} else {
						if (roadNo < howManyBusRoads) {
							markAsBusRoad(patch(rowIndex + roadNo, i))
						} else {
							markAsNormalRoad(patch(rowIndex + roadNo, i))
						}
					}
				}
				
				for (int roadNo = 0; roadNo < howWidthRoads; roadNo++) {
					// If already set patch type - we have crossing now
					if (patch(rowIndex + howWidthRoads + roadNo, i).patchType) {
						markAsCrossing(patch(rowIndex + howWidthRoads + roadNo, i))
					} else {
						if (roadNo < (howWidthRoads - howManyBusRoads)) {
							markAsNormalRoad(patch(rowIndex + howWidthRoads + roadNo, i))
						} else {
							markAsBusRoad(patch(rowIndex + howWidthRoads + roadNo, i))
						}
					}
				}
			}
		}
				
		// Footpath or green lawn
		ask(patches()) {
			if (!it.patchType) {
				boolean footpath = false
				ask(neighbors4()) {
					if (it.patchType == PatchType.ROAD_SPECIAL || it.patchType == PatchType.ROAD_NORMAL)
						footpath = true
				}
				
				// Draw footpath or green lawn
				if (footpath) {
					markAsFootPath(it)
				} else {
					markAsEmptySpace(it)
				}
			}
		}
		
		// We need sort patches to use neighbors4/1 method
		List<UserPatch> list = new ArrayList<>(patchesCrossing); 
		list.sort { a, b ->
		   a.getPxcor() <=> b.getPxcor() ?: a.getPycor() <=> b.getPycor()
		}
			
		// Group patches from patchesCrossing set into crossingObject
		for (UserPatch patch in list) {
			// Check if one of my neighbors already with crossing
			for (UserPatch neighbor in patch.neighbors4()) {
				if (neighbor.crossing != null) {
					patch.crossing = neighbor.crossing
					break
				}
			}
			
			// Not set - we need prepare new crossing
			if (patch.crossing == null) {
				Crossing crossing = new Crossing()
				crossings.add(crossing)
				patch.crossing = crossing
			}
			
			// Add patch to the crossing
			patch.crossing.patches.add(patch)
		}
		
		// Set type of crossing
		int crossingNo = 0
		for(Crossing crossing in crossings) {
			if (crossingNo < howManyCrossingWithLights) {
				crossing.crossType = CrossType.TRAFFIC_WITH_LIGHTS
				crossing.lights = new TrafficLight(lightTimerTicks)
			} else {
				crossing.crossType = CrossType.TRAFFIC_CIRCLE
			}
			crossingNo++
		}
	}
	
	def markAsCrossing(UserPatch patch) {
		patch.patchType = PatchType.CROSSING;
		patch.pcolor = 28.9d
		patchesCrossing.add(patch)
	}
	
	def markAsEmptySpace(UserPatch patch) {
		patch.patchType = PatchType.EMPTY_SPACE
		patch.pcolor = green()
	}
	
	def markAsFootPath(UserPatch patch) {
		patch.patchType = PatchType.FOOTPATH
		patch.pcolor = black()
	}
	
	def markAsBusRoad(UserPatch patch) {
		patch.patchType = PatchType.ROAD_SPECIAL
		patch.pcolor = white()
	}
	
	def markAsNormalRoad(UserPatch patch) {
		patch.patchType = PatchType.ROAD_NORMAL
		patch.pcolor = 9.2d
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