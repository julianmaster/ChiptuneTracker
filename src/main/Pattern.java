package main;

import java.util.LinkedList;

import org.simpleframework.xml.Attribute;

public class Pattern {
	@Attribute(required=false)
	public Integer sample1;
	
	@Attribute(required=false)
	public Integer sample2;
	
	@Attribute(required=false)
	public Integer sample3;
	
	@Attribute(required=false)
	public Integer sample4;
	
	LinkedList<Integer> getList() {
		return new LinkedList<Integer>(){{
			add(sample1);
			add(sample2);
			add(sample3);
			add(sample4);
		}};
	}
}
