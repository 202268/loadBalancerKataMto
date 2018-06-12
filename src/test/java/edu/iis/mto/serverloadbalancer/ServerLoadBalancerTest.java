package edu.iis.mto.serverloadbalancer;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;
import org.junit.Test;

public class ServerLoadBalancerTest {
	@Test
	public void itCompiles() {
		assertThat(true, equalTo(true));
	}
	@Test
	public void balancingServerWithNoVms_serverStaysEmpty() {
		Server theServer=a(server().withCapacity(1));
		balancing(aServerListWith(theServer), anEmptyVmsList());
		
		assertThat(theServer, hasCurrentLoadOf(0.0d));
	}
	private Matcher<? super Server> hasCurrentLoadOf(double expectedPercentage) {
		return new CurrentLoadPercentageMatcher(expectedPercentage);
	}
	private void balancing(Server[] aServerListWith, Vm[] anEmptyVmsList) {
		new ServerLoadBalancer().balance(aServerListWith, anEmptyVmsList);
	}
	private Vm[] anEmptyVmsList() {
		// TODO Auto-generated method stub
		return null;
	}
	private Server a(ServerBuilder builder) {
		return builder.build();
	}
	private Server[] aServerListWith(Server...servers) {
		return servers;
	}
	private ServerBuilder server() {
		return new ServerBuilder();
	}


}
