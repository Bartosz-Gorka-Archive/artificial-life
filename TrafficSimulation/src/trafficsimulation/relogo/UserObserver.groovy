package trafficsimulation.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;

import repast.simphony.engine.environment.RunEnvironment
import repast.simphony.parameter.Parameters
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;
import repast.simphony.relogo.schedule.Go;
import repast.simphony.relogo.schedule.Setup;
import trafficsimulation.ReLogoObserver;

class UserObserver extends ReLogoObserver{
	Parameters p = RunEnvironment.getInstance().getParameters();
	Object pg = RunEnvironment.getInstance().endAt(500);
	
	int notUsedPatches = p.getValue("notUsedPatches") // TODO can be removed
	
	int howManyBusRoads = p.getValue("howManyBusRoads")
	int roadsHorizontally = p.getValue("roadsHorizontally")
	int roadsVertically = p.getValue("roadsVertically")
	int howWidthRoads = p.getValue("howWidthRoads")
	int howManyCrossingWithLights = p.getValue("howManyCrossingWithLights")
	int howManyCrossingCircles = p.getValue("howManyCrossingCircles") 
	int lightTimerTicks = p.getValue("lightTimerTicks")
	int zebraMoveTimerTicks = p.getValue("zebraMoveTimerTicks")
	boolean usePedestrians = p.getValue("usePedestrians")
	int busStartTicks = p.getValue("busStartTicks")
	int passengersToDeliverEveryTick = p.getValue("passengersToDeliverEveryTick")
	
	// Privates
	private int ticksToStartBus = 0
	private int passengersInBuses = 0
	private int transferedPassengersBuses = 0
	private int transferedPassengersCars = 0
	private int transferedBuses = 0
	private int transferedCars = 0
	private int blockedCars = 0
	private int blockedBuses = 0
	HashSet<UserPatch> patchesCrossing = new HashSet<>()
	HashSet<Crossing> crossings = new HashSet<>()
	HashSet<ZebraCrossing> zebraCrossings = new HashSet<>()
	HashSet<Integer> notAllowedX = new HashSet<>()
	HashSet<Integer> notAllowedY = new HashSet<>()
	ArrayList<Location> startLocations = new ArrayList<>()
	Random rnd = new Random()
	
	def countBlockedCars() {
		return blockedCars
	}
	
	def countBlockedBuses() {
		return blockedBuses
	}
	
	def countTransferedCars() {
		return transferedCars
	}
	
	def countTransferedBuses() {
		return transferedBuses
	}
	
	def carCount() {
		int cars = 0
		for(UserTurtle t in userTurtles()) {
			if (t.vehicleType == VehicleType.CAR) cars++
		}
		
		return cars
	}
	
	def busCount() {
		int buses = 0
		for(UserTurtle t in userTurtles()) {
			if (t.vehicleType == VehicleType.BUS) buses++
		}
		
		return buses
	}
	
	def blockedZebraCrossings() {
		int blocked = 0
		for (ZebraCrossing c in zebraCrossings) {
			if (c.timer > 0) blocked++
		}
		
		return blocked
	}
	
	def percentageBlockedZebraCrossings() {
		if (zebraCrossings.size() == 0) return 0
		return blockedZebraCrossings() / zebraCrossings.size()
	}
	
	def transferedPassengersCars() {
		return transferedPassengersCars
	}
	
	def transferedPassengersBuses() {
		return transferedPassengersBuses
	}
	
	def passengersWaitingCars() {
		int passengers = 0
		for(UserTurtle t in userTurtles()) {
			if (t.vehicleType == VehicleType.CAR)
				passengers += t.passengersCount
		}
		
		return passengers
	}
	
	def passengersWaitingBuses() {
		int passengers = 0
		for(UserTurtle t in userTurtles()) {
			if (t.vehicleType == VehicleType.BUS)
				passengers += t.passengersCount
		}
		
		return passengers
	}
	
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
		
		// Find start and destination places
		findLocations()
	}
		
	@Go
	def go(){
		// Pedestrians move
		if (usePedestrians) {
			for(ZebraCrossing zebra in zebraCrossings) {
				// TODO better random
				if (rnd.nextDouble() >= 0.9) {
					zebra.timer = zebraMoveTimerTicks + 1
				}
				
				// Time tick
				zebra.timer--
				
				// If -1 we will set zero
				if (zebra.timer < 0) zebra.timer = 0
				
				// If timer > 0, we should set restriction
				boolean blocked = zebra.timer > 0
				for (UserPatch patch in zebra.patches) {
					patch.pedestianOnZebra = blocked
				}
			}
		}
		
		// Tick lights
		for (Crossing crossing in crossings) {
			if (crossing.crossType == CrossType.TRAFFIC_WITH_LIGHTS) {
				crossing.lights.nextTick()
			}
		}
		
		// Tick buses generator
		ticksToStartBus--
		if (ticksToStartBus < 0)
			ticksToStartBus = 0
		
		// Calculate passengers in cars and buses
		int passengersInCars = 0
		for (int passengerNo = 0; passengerNo < passengersToDeliverEveryTick; passengerNo++) {
			// TODO better random
			if (rnd.nextDouble() <= 0.1)
				passengersInCars++
			else
				passengersInBuses++
		}
		
		// Prepare new objects
		ArrayList<Location> xloc = startLocations.collect()
		Collections.shuffle(xloc)
		
		for (Location l in xloc) {
			// Is empty?
			if (l.startLocationPatch.turtlesHere().isEmpty()) {
				if (l.startLocationPatch.patchType == PatchType.ROAD_NORMAL && passengersInCars >= 1 && ticksToStartBus >= 0 && passengersInBuses != 0) {
					// 1, 2, 3, 4 or 5 passengers
					int passengersCount = rnd.nextInt(4) + 1
					
					// If want add too much - allow only maximum current
					if (passengersCount > passengersInCars) {
						passengersCount = passengersInCars
					}
						
					// Decrement used passengers
					passengersInCars -= passengersCount
					
					UserTurtle car = createNewVehicle(l)
					markAsCar(car)
					car.passengersCount = passengersCount
				} else if (l.startLocationPatch.patchType == PatchType.ROAD_SPECIAL || l.startLocationPatch.patchType == PatchType.ROAD_NORMAL) {
					// Can we start new bus?
					if (ticksToStartBus == 0 && passengersInBuses > 0) {
						// Refresh ticks
						ticksToStartBus = busStartTicks
						
						// Create bus
						UserTurtle bus = createNewVehicle(l)
						markAsBus(bus)
						bus.passengersCount = Math.min(passengersInBuses, 2 * passengersToDeliverEveryTick)
						passengersInBuses = 0
					}
				}
			}
		}
		
		blockedCars = 0
		blockedBuses = 0
		ask(userTurtles()) { UserTurtle turtle ->
			if (turtle.vehicleType == VehicleType.CAR && turtle.isAlreadyOnPlace()) {
				transferedPassengersCars += turtle.passengersCount
				transferedCars++
				turtle.die()
			} else if (turtle.vehicleType == VehicleType.BUS && turtle.isAlreadyOnPlace()) {
				transferedPassengersBuses += turtle.passengersCount
				transferedBuses++
				turtle.die()
			} else {
				turtle.go()
				
				if (!turtle.alreadyMoved) {
					if (turtle.vehicleType == VehicleType.CAR) blockedCars++
					else if (turtle.vehicleType == VehicleType.BUS) blockedBuses++
				}
			}
		}
	}
	
	def createNewVehicle(Location l) {
		UserTurtle newTurtle
		createUserTurtles(1) { UserTurtle turtle ->
			newTurtle = turtle
			turtle.setxy(l.startLocationPatch.getPxcor(), l.startLocationPatch.getPycor())
			turtle.moveRule = l.moveRule
			turtle.lightExtraRule = l.extraLightRule
			turtle.destinationX = l.destinationLocationPatch.getPxcor()
			turtle.destinationY = l.destinationLocationPatch.getPycor()
		
			switch (l.moveRule) {
				case ActionRule.UP:
					turtle.setHeading(0)
					break
					
				case ActionRule.RIGHT:
					turtle.setHeading(90)
					break
					
				case ActionRule.DOWN:
					turtle.setHeading(180)
					break
				
				case ActionRule.LEFT:
					turtle.setHeading(270)
					break
			}
		}
		
		return newTurtle
	}
	
	def findLocations() {
		int y, previousRoadNo
		
		// Find UP locations
		y = getMaxPycor()
		previousRoadNo = -1
		for (int x = getMinPxcor(); x <= getMaxPxcor(); x++) {
			UserPatch p = patch(x, y)
			if (p.patchType == PatchType.ROAD_NORMAL || p.patchType == PatchType.ROAD_SPECIAL) {		
				if (p.roadNo > previousRoadNo) {
					Location location = new Location()
					location.destinationLocationPatch = patch(x, getMinPycor())
					location.startLocationPatch = p
					location.moveRule = ActionRule.DOWN
					location.extraLightRule = ActionRule.UP
					startLocations.add(location)
				}				
			}
			previousRoadNo = p.roadNo
		}
		
		// Find DOWN locations
		y = getMinPycor()
		previousRoadNo = -1
		for (int x = getMinPxcor(); x <= getMaxPxcor(); x++) {
			UserPatch p = patch(x, y)
			if (p.patchType == PatchType.ROAD_NORMAL || p.patchType == PatchType.ROAD_SPECIAL) {
				if (p.roadNo <= previousRoadNo) {
					Location location = new Location()
					location.destinationLocationPatch = patch(x, getMaxPycor())
					location.startLocationPatch = p
					location.moveRule = ActionRule.UP
					location.extraLightRule = ActionRule.DOWN
					startLocations.add(location)
				}
			}
			previousRoadNo = p.roadNo
		}
		
		// Find LEFT locations
		int x = getMinPxcor()
		previousRoadNo = -1
		for (y = getMinPycor(); y <= getMaxPycor(); y++) {
			UserPatch p = patch(x, y)
			if (p.patchType == PatchType.ROAD_NORMAL || p.patchType == PatchType.ROAD_SPECIAL) {
				if (p.roadNo > previousRoadNo) {
					Location location = new Location()
					location.destinationLocationPatch = patch(getMaxPxcor(), y)
					location.startLocationPatch = p
					location.moveRule = ActionRule.RIGHT
					location.extraLightRule = ActionRule.LEFT
					startLocations.add(location)
				}
			}
			previousRoadNo = p.roadNo
		}
		
		// Find RIGHT locations
		x = getMaxPxcor()
		previousRoadNo = -1
		for (y = getMinPycor(); y <= getMaxPycor(); y++) {
			UserPatch p = patch(x, y)
			if (p.patchType == PatchType.ROAD_NORMAL || p.patchType == PatchType.ROAD_SPECIAL) {
				if (p.roadNo <= previousRoadNo) {
					Location location = new Location()
					location.destinationLocationPatch = patch(getMinPxcor(), y)
					location.startLocationPatch = p
					location.moveRule = ActionRule.LEFT
					location.extraLightRule = ActionRule.RIGHT
					startLocations.add(location)
				}
			}
			previousRoadNo = p.roadNo
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
				ZebraCrossing crossing = null
				
				for (int y = getMinPycor(); y <= getMaxPycor(); y++) {
					UserPatch p = patch(xRef, y)
					if (p.patchType == PatchType.FOOTPATH || p.patchType == PatchType.ROAD_NORMAL || p.patchType == PatchType.ROAD_SPECIAL) {
						markAsZebra(p)
						// If previous not found crossing
						if (!crossing) {
							crossing = new ZebraCrossing()
							zebraCrossings.add(crossing)
						}
						
						// Add patch to zebra crossing reference
						crossing.patches.add(p)
					} else {
						// Do not remember previous crossing when exit group
						crossing = null
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
				ZebraCrossing crossing = null
				
				for (int x = getMinPxcor(); x <= getMaxPxcor(); x++) {
					UserPatch p = patch(x, yRef)
					if (p.patchType == PatchType.FOOTPATH || p.patchType == PatchType.ROAD_NORMAL || p.patchType == PatchType.ROAD_SPECIAL) {
						markAsZebra(p)
						// If previous not found crossing
						if (!crossing) {
							crossing = new ZebraCrossing()
							zebraCrossings.add(crossing)
						}
						
						// Add patch to zebra crossing reference
						crossing.patches.add(p)
					} else {
						// Do not remember previous crossing when exit group
						crossing = null
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
					patch(i, rowIndex + roadNo).roadNo = roadNo + 1
					patch(i, rowIndex + roadNo).actionRule = ActionRule.LEFT
					if (roadNo < howManyBusRoads) {
						markAsBusRoad(patch(i, rowIndex + roadNo))
					} else {
						markAsNormalRoad(patch(i, rowIndex + roadNo))
					}
				}
				
				for (int roadNo = 0; roadNo < howWidthRoads; roadNo++) {
					patch(i, rowIndex + howWidthRoads + roadNo).roadNo = howWidthRoads - roadNo
					patch(i, rowIndex + howWidthRoads + roadNo).actionRule = ActionRule.RIGHT
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
					patch(rowIndex + roadNo, i).roadNo = roadNo + 1
					patch(rowIndex + roadNo, i).actionRule = ActionRule.DOWN
					
					// If already set patch type - we have crossing now
					if (patch(rowIndex + roadNo, i).patchType) {
						patch(rowIndex + roadNo, i).roadNo = 0
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
					patch(rowIndex + howWidthRoads + roadNo, i).roadNo = howWidthRoads - roadNo
					patch(rowIndex + howWidthRoads + roadNo, i).actionRule = ActionRule.UP
					
					// If already set patch type - we have crossing now
					if (patch(rowIndex + howWidthRoads + roadNo, i).patchType) {
						patch(rowIndex + howWidthRoads + roadNo, i).roadNo = 0
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
		patch.pcolor = 66.2d
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
		if (turtle.moveRule == ActionRule.UP)
			turtle.setColor(76.0d)
		else if (turtle.moveRule == ActionRule.DOWN)
			turtle.setColor(66.0d)
		else if (turtle.moveRule == ActionRule.LEFT)
			turtle.setColor(56.0d)
		else if (turtle.moveRule == ActionRule.RIGHT)
			turtle.setColor(86.0d)
	}
}