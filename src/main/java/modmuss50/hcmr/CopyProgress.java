package modmuss50.hcmr;

public class CopyProgress {
	private int step;
	private int steps;
	private String stage;

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public void next(){
		this.step++;
	}
}
