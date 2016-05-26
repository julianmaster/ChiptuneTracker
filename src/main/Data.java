package main;

import java.util.LinkedList;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Data {
	@ElementList
	public LinkedList<Sample> samples;
	
	@ElementList
	public LinkedList<Pattern> patterns;
	
	public Data() {
		samples = new LinkedList<>();
		patterns = new LinkedList<>();
	}
}
