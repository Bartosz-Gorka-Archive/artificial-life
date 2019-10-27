package firesimulation.relogo

import firesimulation.ReLogoObserver
import repast.simphony.relogo.schedule.Go
import repast.simphony.relogo.schedule.Setup

import static repast.simphony.relogo.Utility.*
import static repast.simphony.relogo.UtilityG.*

class UserObserver extends ReLogoObserver {

    @Setup
    def setup() {
        clearAll()

        ask(patches()) {
            initialize()
        }

        // Set fire on left side
        (getMinPycor()..getMaxPycor()).forEach({
            patch(getMinPxcor(), it).setPcolor(red())
        })
    }

    @Go
    def go() {
        ask(patches()) {
            makeNextStep()
        }
    }
}
