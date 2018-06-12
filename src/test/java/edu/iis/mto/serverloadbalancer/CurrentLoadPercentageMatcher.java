package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class CurrentLoadPercentageMatcher extends TypeSafeMatcher<Server> {

	private static final double EPSILON = 0.01d;
	private double expectedPercentage;

	public CurrentLoadPercentageMatcher(double expectedPercentage) {
		this.expectedPercentage = expectedPercentage;

	}

	public void describeTo(Description description) {
		description.appendText("a server with load percentage of").appendValue(expectedPercentage);
	}

	@Override
	protected void describeMismatchSafely(Server item, Description description) {
		description.appendText("a server with load percentage of").appendValue(item.getCurrentLoadPercentage());
	}

	@Override
	protected boolean matchesSafely(Server server) {
		return doublesAreEqual(expectedPercentage, server.getCurrentLoadPercentage());
	}

	private boolean doublesAreEqual(double d1, double d2) {
		return d1 == d2 || Math.abs(d1 - d2) < EPSILON;
	}

	public static CurrentLoadPercentageMatcher hasCurrentLoadOf(double expectedPercentage) {
		return new CurrentLoadPercentageMatcher(expectedPercentage);
	}

}
