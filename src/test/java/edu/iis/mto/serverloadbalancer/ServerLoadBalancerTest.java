package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasCurrentLoadOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
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
		Server theServer = a(server().withCapacity(1));
		balancing(aServerListWith(theServer), anEmptyVmsList());

		assertThat(theServer, hasCurrentLoadOf(0.0d));
	}

	@Test
	public void balancingOneServerWithOneSlotCapacity_andOneSlotVm_fillsServerWithTheVm() {
		Server theServer = a(server().withCapacity(1));
		Vm theVm = a(vm().ofSize(1));
		balancing(aServerListWith(theServer), aVmsListWith(theVm));

		assertThat(theServer, hasCurrentLoadOf(100.0d));
		assertThat("server should contain the vm", theServer.contains(theVm));
	}

	private Vm a(VmBuilder builder) {
		return builder.build();
	}

	private Vm[] aVmsListWith(Vm... vms) {
		return vms;
	}

	private VmBuilder vm() {
		return new VmBuilder();
	}

	private void balancing(Server[] aServerListWith, Vm[] anEmptyVmsList) {
		new ServerLoadBalancer().balance(aServerListWith, anEmptyVmsList);
	}

	private Vm[] anEmptyVmsList() {
		return new Vm[0];
	}

	private Server a(ServerBuilder builder) {
		return builder.build();
	}

	private Server[] aServerListWith(Server... servers) {
		return servers;
	}

}
