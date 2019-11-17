package trafficsimulation.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;

import repast.simphony.engine.environment.RunEnvironment
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
	HashSet<Integer> notAllowedX = new HashSet<>()
	HashSet<Integer> notAllowedY = new HashSet<>()
	
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
	}
		
	@Go
	def go(){
		if (patch(-6, -24).turtlesHere().size() < 1) {
			createTurtles(1) { UserTurtle turtle ->
				// TODO add turtles dynamically in different locations
				turtle.setxy(-6, -24)
				
				// TODO set angle
				turtle.setHeading(0)
				
				// TODO change it to passengers model
				if (Math.random() >= 0.5) {
					markAsBus(turtle)
				} else {
					markAsCar(turtle)
				}
				
				// TODO dynamic
				turtle.destinationX = -6
				turtle.destinationY = 24
			}
		}
		
		ask(turtles()) { UserTurtle turtle ->
			turtle.go()
		}
		
		if (RunEnvironment.getInstance().getCurrentSchedule().getTickCount() % 7 == 0) {
			ask(patches()) { UserPatch patch ->
				if (patch.patchType == PatchType.ZEBRA) {
					patch.pedestianOnZebra = !patch.pedestianOnZebra
				}
			}
		}
	}
	
	def drawZebraCrossing() {
		for(int i = 0; i < 4; i++) {
			notAllowedX.add(getMaxPxcor() - i)
			notAllowedX.add(getMinPxcor() + i)
			notAllowedY.add(getMaxPycor() - i)
			notAllowedY.add(getMinPycor() + i)
		}
		
		HashSet<Integer> allowedX = new HashSet<>()
		HashSet<Integer> allowedY = new HashSet<>()
		for (int i = getMinPxcor(); i <= getMaxPxcor(); i++) {
			if(!notAllowedX.contains(new Integer(i)))
				allowedX.add(i)
		}
		for (int i = getMinPycor(); i <= getMaxPycor(); i++) {
			if(!notAllowedY.contains(new Integer(i)))
				allowedY.add(i)
		}
		
		for (int i = 0; i < 2 * roadsVertically; i++) {
			if (allowedX.iterator().hasNext()) {
				Integer xRef = allowedX.iterator().next()
				
				for (int y = getMinPycor(); y <= getMaxPycor(); y++) {
					UserPatch p = patch(xRef, y)
					if (p.patchType == PatchType.FOOTPATH || p.patchType == PatchType.ROAD_NORMAL || p.patchType == PatchType.ROAD_SPECIAL) {
						markAsZebra(p)
					}
				}
				
				for(int it = 0; it < 5; it++) {
					allowedX.remove(xRef + it)
					allowedX.remove(xRef - it)
				}
			} else {
				// No more possibility to prepare zebra paths
				break
			}
		}
		
		for (int i = 0; i < 2 * roadsHorizontally; i++) {
			if (allowedY.iterator().hasNext()) {
				Integer yRef = allowedY.iterator().next()
				
				for (int x = getMinPxcor(); x <= getMaxPxcor(); x++) {
					UserPatch p = patch(x, yRef)
					if (p.patchType == PatchType.FOOTPATH || p.patchType == PatchType.ROAD_NORMAL || p.patchType == PatchType.ROAD_SPECIAL) {
						markAsZebra(p)
					}
				}
				
				for(int it = 0; it < 5; it++) {
					allowedY.remove(yRef + it)
					allowedY.remove(yRef - it)
				}
			} else {
				// No more possibility to prepare zebra paths
				break
			}
		}
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
			
			
			for (int roadNo = 0; roadNo < howWidthRoads; roadNo++) {
				for(int i = 0; i < 5; i++) {
					notAllowedY.add(rowIndex + roadNo + i)
					notAllowedY.add(rowIndex + roadNo - i)
					notAllowedY.add(rowIndex + howWidthRoads + roadNo + i)
					notAllowedY.add(rowIndex + howWidthRoads + roadNo - i)
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
			
			for (int roadNo = 0; roadNo < howWidthRoads; roadNo++) {
				for(int i = 0; i < 5; i++) {
					notAllowedX.add(rowIndex + roadNo + i)
					notAllowedX.add(rowIndex + roadNo - i)
					notAllowedX.add(rowIndex + howWidthRoads + roadNo + i)
					notAllowedX.add(rowIndex + howWidthRoads + roadNo - i)
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
	
	def markAsZebra(UserPatch patch) {
		patch.patchType = PatchType.ZEBRA
		patch.pcolor = yellow()
		patch.pedestianOnZebra = false
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
		turtle.setColor(42.0d)
	}
	
	def markAsCar(UserTurtle turtle) {
		turtle.vehicleType = VehicleType.CAR
		turtle.setShape("car")
		turtle.setColor(76.0d)
	}
}