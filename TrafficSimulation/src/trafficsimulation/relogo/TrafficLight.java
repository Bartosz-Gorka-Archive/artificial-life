package trafficsimulation.relogo;
import java.util.Random;

import trafficsimulation.relogo.ActionRule;

public class TrafficLight {
	public ActionRule rule;
	public int timer;
	private int ruleInt;
	private int originalTimer;
	
	public TrafficLight(int timer) {
		this.ruleInt = new Random().nextInt((3 - 0) + 1) + 0;
		this.timer = timer;
		this.originalTimer = timer;
		this.setActionRule();
	}
	
	public void nextTick() {
		// Decrement time
		this.timer--;
		
		if(this.timer <= 0) {
			this.timer = this.originalTimer;
			this.ruleInt = (this.ruleInt + 1) % 4;
			this.setActionRule();
		}
	}
	
	private void setActionRule() {
		switch (this.ruleInt) {
		case 0:
			this.rule = ActionRule.UP;
			break;
			
		case 1:
			this.rule = ActionRule.RIGHT;
			break;
			
		case 2:
			this.rule = ActionRule.DOWN;
			break;
			
		case 3:
			this.rule = ActionRule.LEFT;
			break;
		}
	}
}
