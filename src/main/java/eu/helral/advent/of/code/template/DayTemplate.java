package eu.helral.advent.of.code.template;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class DayTemplate {

	public Stream<String> getInput(){
		return new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(this.getClass().getSimpleName()))).lines();
	}
}
