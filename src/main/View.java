package main;

public abstract class View {
	public abstract void init();
	
	public abstract boolean update(double delta);
	
	public abstract void paint();
	
	public abstract void quit();	
}
