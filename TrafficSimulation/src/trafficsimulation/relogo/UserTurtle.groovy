package trafficsimulation.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.Plural;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;
import repast.simphony.relogo.schedule.Go;
import repast.simphony.relogo.schedule.Setup;
import trafficsimulation.ReLogoTurtle;

class UserTurtle extends ReLogoTurtle{
	public ActionRule moveRule
	public ActionRule lightExtraRule
	
	VehicleType vehicleType
	int passengersCount
	int destinationX
	int destinationY
	
	private int minLightTimer = 2
	
	def go() {
		if (!this.dieIfAlreadyOnPlace()) {
			UserPatch patch
			switch (this.moveRule) {
				case ActionRule.UP:
					patch = patchAt(0, 1)
					break
					
				case ActionRule.RIGHT:
					patch = patchAt(1, 0)
					break
					
				case ActionRule.DOWN:
					patch = patchAt(0, -1)
					break
				
				case ActionRule.LEFT:
					patch = patchAt(-1, 0)
					break
			}
			
			// Empty?
			if (patch.turtlesHere().isEmpty()) {
				switch (patch.patchType) {
					// If zebra crossing - can enter?
					case PatchType.ZEBRA:
						if (!patch.pedestianOnZebra) {
							forward(1)
						}
						break
						
					// If crossing
					case PatchType.CROSSING:
						// If crossing with lights
						if (patch.crossing.crossType == CrossType.TRAFFIC_WITH_LIGHTS) {
							if ((patch.crossing.lights.rule == this.moveRule || patch.crossing.lights.rule == this.lightExtraRule) && patch.crossing.lights.timer >= this.minLightTimer) {
								forward(1)
							} else if (this.patchHere().patchType == PatchType.CROSSING) {
								// Continue move if already on crossing
								forward(1)
							}
						} else if(patch.crossing.crossType == CrossType.TRAFFIC_CIRCLE) {
							// TODO maybe check left site?
							forward(1)
						}
						break
					
					default:
						forward(1)
						break
				}
			}
		}
	}
	
	def dieIfAlreadyOnPlace() {
		// TODO when changed road - need update destinations
		if (getXcor() == this.destinationX && getYcor() == this.destinationY) {
			die()
		}
	}
}