package firesimulation.relogo

import firesimulation.ReLogoPatch

import static repast.simphony.relogo.Utility.*
import static repast.simphony.relogo.UtilityG.*

class UserPatch extends ReLogoPatch {
    def density = 0.5 // TODO move it to project variables

    def initialize() {
        // Set empty space or tree
        if (Math.random() <= density) {
            setPcolor(green())
        } else {
            setPcolor(black())
        }
    }

    def makeNextStep() {
        def isTree = getPcolor() == green()

        for (neighbor in neighbors()) {
            if (isTree && neighbor.getPcolor() == red()) {
                setPcolor(red())
                break
            }
        }
    }
}