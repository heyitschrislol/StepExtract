package main;


public class Step {
    private String stepnum;
	private String step;
	private String data;
	private String result;
	
	public Step() {
		
	}
	public Step(String stepnum, String step, String data, String result) {
		super();
		this.stepnum = new String(stepnum);
		this.step = new String(step);
		this.data = new String(data);
		this.result = new String(result);
	}
	/**
	 * @return the step number
	 */
	public String getStepnum() {
		return this.stepnum;
	}
	/**
	 * @param stepnum the step number to set
	 */
	public void setStepnum(String stepnum) {
		this.stepnum = stepnum;
	}
	/**
	 * @return the step
	 */
	public String getStep() {
		return this.step;
	}
	/**
	 * @param step the step to set
	 */
	public void setStep(String step) {
		this.step = step;
	}
	/**
	 * @return the data
	 */
	public String getData() {
		return this.data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}
	/**
	 * @return the result
	 */
	public String getResult() {
		return this.result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}
}
