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
	@Setup
	def setup(){
		// Clear environment
		clearAll()
		
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
	
	def markAsBus(turtle) {
		turtle.vehicleType = VehicleType.BUS
		turtle.setShape("truck")
	}
	
	def markAsCar(turtle) {
		turtle.vehicleType = VehicleType.CAR
		turtle.setShape("car")
	}
}