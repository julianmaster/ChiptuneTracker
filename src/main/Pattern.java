package main;

import java.io.Serializable;

import org.simpleframework.xml.Attribute;

public class Pattern implements Serializable {
	@Attribute(required=false)
	public Integer sample1;
	
	@Attribute(required=false)
	public Integer sample2;
	
	@Attribute(required=false)
	public Integer sample3;
	
	@Attribute(required=false)
	public Integer sample4;
}
