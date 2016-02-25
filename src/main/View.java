package main;

public abstract class View {
	public abstract void init();
	
	public abstract void update(double delta);
	
	public abstract void paint();
	
	public abstract void quit();	
}
