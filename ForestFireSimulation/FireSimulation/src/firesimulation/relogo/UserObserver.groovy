package firesimulation.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;
import repast.simphony.relogo.schedule.Go;
import repast.simphony.relogo.schedule.Setup;
import firesimulation.ReLogoObserver;

class UserObserver extends ReLogoObserver{
	def density = 0.5 // TODO move it to project variables
	
	@Setup
	def setup(){
		clearAll()
		
		ask(patches()) {
			// Set empty space or tree
			if(Math.random() <= density) {
				it.setPcolor(green())
			} else {
				it.setPcolor(black())
			}
		}
		
		// Set fire on left side
		(getMinPycor()..getMaxPycor()).forEach({
			patch(getMinPxcor(), it).setPcolor(red())
		})
	}

	@Go
	def go(){
		ask(patches()) {
			def isTree = it.getPcolor() == green()
			
			for(neighbor in it.neighbors()) {
				if (isTree && neighbor.getPcolor() == red()) {
					it.setPcolor(red())
					break;
				}
			}
		}
		sleep(1000)
	}
}