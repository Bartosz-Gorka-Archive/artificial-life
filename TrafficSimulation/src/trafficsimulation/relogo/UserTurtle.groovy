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
	VehicleType vehicleType
	int passengersCount
	int destinationX
	int destinationY
	
	def go() {
		if (!this.dieIfAlreadyOnPlace()) {
			UserPatch patch = patchAt(0, 1)
			
			// Empty?
			if (patch.turtlesHere().size() < 1) {
				switch (patch.patchType) {
					// If zebra crossing - can enter?
					case PatchType.ZEBRA:
						if (!patch.pedestianOnZebra) {
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
		if (getXcor() == this.destinationX && getYcor() == this.destinationY) {
			die()
		}
	}
}