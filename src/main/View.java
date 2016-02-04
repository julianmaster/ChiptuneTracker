package main;

public abstract class View {
	protected ChiptuneSynthesizer chiptuneSynthesizer;
	
	public View(ChiptuneSynthesizer chiptuneSynthesizer) {
		this.chiptuneSynthesizer = chiptuneSynthesizer;
	}

	public abstract void init();
	
	public abstract void update(double delta);
	
	public abstract void paint();
	
	public abstract void quit();	
}
