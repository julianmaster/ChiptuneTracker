package main;

public abstract class View {
	protected Synthesizer synthesizer;
	
	public View(Synthesizer synthesizer) {
		this.synthesizer = synthesizer;
	}

	public abstract void init();
	
	public abstract void update(double delta);
	
	public abstract void paint();
	
	public abstract void quit();	
}
