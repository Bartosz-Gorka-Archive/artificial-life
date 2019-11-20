package trafficsimulation.relogo

import repast.simphony.relogo.factories.AbstractReLogoGlobalsAndPanelFactory

public class UserGlobalsAndPanelFactory extends AbstractReLogoGlobalsAndPanelFactory{
	public void addGlobalsAndPanelComponents(){
		addMonitorWL("carCount", "Ilosc samochodow", 1.0)
		addMonitorWL("busCount", "Ilosc autobusow", 1.0)
		addMonitorWL("blockedZebraCrossings", "Blocked zebra crossing", 1.0)
		addMonitorWL("percentageBlockedZebraCrossings", "Percentage of blocked zebra crossings", 1.0)
		addMonitorWL("passengersWaitingCars", "Passengers in cars", 1.0)
		addMonitorWL("passengersWaitingBuses", "Passengers in buses", 1.0)
		addMonitorWL("transferedPassengersBuses", "Passengers delivered by buses", 1.0)
		addMonitorWL("transferedPassengersCars", "Passengers delivered by cars", 1.0)
		addMonitorWL("countBlockedCars", "Cars without move in previous tick", 1.0)
		addMonitorWL("countBlockedBuses", "Buses without move in previous tick", 1.0)
		addMonitorWL("countTransferedCars", "Cars moved across map", 1.0)
		addMonitorWL("countTransferedBuses", "Buses moved across map", 1.0)
	}
}