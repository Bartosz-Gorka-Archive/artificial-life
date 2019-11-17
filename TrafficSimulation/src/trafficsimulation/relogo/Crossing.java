package trafficsimulation.relogo;
import trafficsimulation.relogo.UserPatch;

import java.util.HashSet;

import trafficsimulation.relogo.CrossType;

public class Crossing {
	public HashSet<UserPatch> patches = new HashSet<>();
	public CrossType crossType;
	public TrafficLight lights;
}
