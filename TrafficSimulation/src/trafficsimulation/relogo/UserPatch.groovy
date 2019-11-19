package trafficsimulation.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;
import repast.simphony.relogo.ast.Diffusible;
import repast.simphony.relogo.schedule.Go;
import repast.simphony.relogo.schedule.Setup;
import trafficsimulation.ReLogoPatch;

class UserPatch extends ReLogoPatch{
	public PatchType patchType
	public Crossing crossing
	public boolean pedestianOnZebra
	public int roadNo = -1
	public ActionRule actionRule
}