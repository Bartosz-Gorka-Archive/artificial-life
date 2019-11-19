package trafficsimulation.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;

import repast.simphony.engine.environment.RunEnvironment
import repast.simphony.parameter.Parameters
import repast.simphony.relogo.Plural;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;
import repast.simphony.relogo.schedule.Go;
import repast.simphony.relogo.schedule.Setup;
import trafficsimulation.ReLogoTurtle;

class UserTurtle extends ReLogoTurtle{
	Parameters p = RunEnvironment.getInstance().getParameters();
	
	public ActionRule moveRule
	public ActionRule lightExtraRule
	
	VehicleType vehicleType
	int passengersCount = 0
	int destinationX
	int destinationY
	boolean alreadyMoved = false
	
	private int minLightTimer = p.getValue("minLightTimer")
	
	def go() {
		if (!this.isAlreadyOnPlace()) {
			alreadyMoved = false
			UserPatch patchUp
			UserPatch patchLeft
			UserPatch patchRight
			
			switch (this.moveRule) {
				case ActionRule.UP:
					patchUp = patchAt(0, 1)
					patchLeft = patchAt(-1, 1)
					patchRight = patchAt(1, 1)
					break
					
				case ActionRule.RIGHT:
					patchUp = patchAt(1, 0)
					patchLeft = patchAt(1, 1)
					patchRight = patchAt(1, -1)
					break
					
				case ActionRule.DOWN:
					patchUp = patchAt(0, -1)
					patchLeft = patchAt(1, -1)
					patchRight = patchAt(-1, -1)
					break
				
				case ActionRule.LEFT:
					patchUp = patchAt(-1, 0)
					patchLeft = patchAt(-1, -1)
					patchRight = patchAt(-1, 1)
					break
			}
			
			// Empty?
			if (patchUp.turtlesHere().isEmpty()) {
				switch (patchUp.patchType) {
					// If zebra crossing - can enter?
					case PatchType.ZEBRA:
						if (!patchUp.pedestianOnZebra) {
							forward(1)
							alreadyMoved = true
						}
						break
						
					// If crossing
					case PatchType.CROSSING:
						// If crossing with lights
						if (patchUp.crossing.crossType == CrossType.TRAFFIC_WITH_LIGHTS) {
							int carsBefore = 0
							boolean canEnter = true
							
							UserPatch p = patchUp
							while (p.patchType == PatchType.CROSSING) {
								if (!p.turtlesHere().isEmpty() ) {
									carsBefore++
									
									if (p.turtlesHere().get(0).moveRule != this.moveRule)
										canEnter = false
								}
								
								switch (this.moveRule) {
									case ActionRule.UP:
										p = p.patchAt(0, 1)
										break
										
									case ActionRule.RIGHT:
										p = p.patchAt(1, 0)
										break
										
									case ActionRule.DOWN:
										p = p.patchAt(0, -1)
										break
									
									case ActionRule.LEFT:
										p = p.patchAt(-1, 0)
										break
								}
							}
							
							// We expect `carsBefore` empty spaces before
							int empty = 0
							for (int i = 0; i <= carsBefore; i++) {
								if (p.patchType == PatchType.ROAD_SPECIAL || p.patchType == PatchType.ROAD_NORMAL) {
									if (p.turtlesHere().isEmpty()) {
										empty++
									}
									
									switch (this.moveRule) {
										case ActionRule.UP:
											p = p.patchAt(0, 1)
											break
											
										case ActionRule.RIGHT:
											p = p.patchAt(1, 0)
											break
											
										case ActionRule.DOWN:
											p = p.patchAt(0, -1)
											break
										
										case ActionRule.LEFT:
											p = p.patchAt(-1, 0)
											break
									}
								} else {
									break
								}
							}
							
							if (canEnter && (patchUp.crossing.lights.rule == this.moveRule || patchUp.crossing.lights.rule == this.lightExtraRule) && patchUp.crossing.lights.timer > carsBefore && empty > carsBefore) {
								forward(1)
								alreadyMoved = true
							}
						} else if(patchUp.crossing.crossType == CrossType.TRAFFIC_CIRCLE) {
							int carsBefore = 0
							boolean canEnter = true
							
							UserPatch p = patchUp
							while (p.patchType == PatchType.CROSSING) {
								if (!p.turtlesHere().isEmpty() ) {
									carsBefore++
									
									if (p.turtlesHere().get(0).moveRule != this.moveRule)
										canEnter = false
								}
								
								switch (this.moveRule) {
									case ActionRule.UP:
										p = p.patchAt(0, 1)
										break
										
									case ActionRule.RIGHT:
										p = p.patchAt(1, 0)
										break
										
									case ActionRule.DOWN:
										p = p.patchAt(0, -1)
										break
									
									case ActionRule.LEFT:
										p = p.patchAt(-1, 0)
										break
								}
							}
							
							// We expect `carsBefore` empty spaces before
							int empty = 0
							for (int i = 0; i <= carsBefore; i++) {
								if (p.patchType == PatchType.ROAD_SPECIAL || p.patchType == PatchType.ROAD_NORMAL) {
									if (p.turtlesHere().isEmpty()) {
										empty++
									}
									
									switch (this.moveRule) {
										case ActionRule.UP:
											p = p.patchAt(0, 1)
											break
											
										case ActionRule.RIGHT:
											p = p.patchAt(1, 0)
											break
											
										case ActionRule.DOWN:
											p = p.patchAt(0, -1)
											break
										
										case ActionRule.LEFT:
											p = p.patchAt(-1, 0)
											break
									}
								} else {
									break
								}
							}
							
							if (canEnter && empty > carsBefore) {
								forward(1)
								alreadyMoved = true
							}
						}
						break
						
					case PatchType.ROAD_NORMAL:
						forward(1)
						alreadyMoved = true
						break
				
					case PatchType.ROAD_SPECIAL:
						forward(1)
						alreadyMoved = true
						break
				}
			} else if ((patchLeft.patchType == PatchType.ROAD_NORMAL || (
					patchLeft.patchType == PatchType.ROAD_SPECIAL && this.vehicleType == VehicleType.BUS)
				) &&  patchLeft.turtlesHere().isEmpty() && patchLeft.actionRule == this.moveRule && (patchLeft.roadNo == this.patchHere().roadNo - 1 || patchLeft.roadNo == this.patchHere().roadNo + 1)) {
				forward(1)
				left(90)
				forward(1)
				right(90)
				alreadyMoved = true
				
				if (abs(patchLeft.getPxcor() - destinationX) == 1)
					destinationX = patchLeft.getPxcor()
				if (abs(patchLeft.getPycor() - destinationY) == 1)
					destinationY = patchLeft.getPycor()
					
			} else if ((patchRight.patchType == PatchType.ROAD_NORMAL || (
					patchRight.patchType == PatchType.ROAD_SPECIAL && this.vehicleType == VehicleType.BUS)
				) &&  patchRight.turtlesHere().isEmpty() && patchRight.actionRule == this.moveRule && (patchRight.roadNo == this.patchHere().roadNo - 1 || patchRight.roadNo == this.patchHere().roadNo + 1)) {
				forward(1)
				right(90)
				forward(1)
				left(90)
				alreadyMoved = true
				
				if (abs(patchRight.getPxcor() - destinationX) == 1)
					destinationX = patchRight.getPxcor()
				if (abs(patchRight.getPycor() - destinationY) == 1)
					destinationY = patchRight.getPycor()
			}
		}
	}
	
	def isAlreadyOnPlace() {
		return (getXcor() == this.destinationX && getYcor() == this.destinationY)
	}
}