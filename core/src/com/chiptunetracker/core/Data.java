package com.chiptunetracker.core;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.LinkedList;

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
