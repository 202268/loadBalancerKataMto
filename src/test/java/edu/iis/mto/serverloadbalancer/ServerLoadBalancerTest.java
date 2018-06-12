package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasCurrentLoadOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.ServerVmsCountMatcher.hasAVmsCountOf;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
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

	@Test
	public void balancingOneServerWithTenSlotCapacity_andOneSlotVm_fillsTheServerWithTenPercent() {
		Server theServer = a(server().withCapacity(10));
		Vm theVm = a(vm().ofSize(1));
		balancing(aServerListWith(theServer), aVmsListWith(theVm));

		assertThat(theServer, hasCurrentLoadOf(10.0d));
		assertThat("server should contain the vm", theServer.contains(theVm));
	}

	@Test
	public void balancingTenServerWithEnoughRoom_fillsTheServerWithAllVms() {
		Server theServer = a(server().withCapacity(100));
		Vm theFirstVm = a(vm().ofSize(1));
		Vm theSecondVm = a(vm().ofSize(1));
		balancing(aServerListWith(theServer), aVmsListWith(theFirstVm, theSecondVm));

		assertThat(theServer, hasAVmsCountOf(2));
		assertThat("server should contain the first vm", theServer.contains(theFirstVm));
		assertThat("server should contain the second vm", theServer.contains(theSecondVm));
	}
	@Test
	public void vmsShouldBeBalancedOnLessLoadServerFirst() {
		Server moreLoadedServer = a(server().withCapacity(100).withCurrentLoadOf(50.0d));
		Server lessLoadedServer = a(server().withCapacity(100).withCurrentLoadOf(45.0d));
		
		Vm theVm = a(vm().ofSize(10));
		
		balancing(aServerListWith(moreLoadedServer,lessLoadedServer), aVmsListWith(theVm));

		assertThat("less loaded server should contain the vm", lessLoadedServer.contains(theVm));
		assertThat("more loaded server should not contain the vm", !moreLoadedServer.contains(theVm));
	}
	@Test
	public void balancingServerWithNoEnoughRoom_shouldNotBeFilledWithTheVm() {
		Server theServer = a(server().withCapacity(10).withCurrentLoadOf(90.0d));
		Vm theVm = a(vm().ofSize(2));
		balancing(aServerListWith(theServer), aVmsListWith(theVm));

		assertThat("server should not contain the vm", !theServer.contains(theVm));
	}
	@Test
	public void balancingServersAndVms() {
		Server server1=a(server().withCapacity(4));
		Server server2=a(server().withCapacity(6));
		
		Vm vm1=a(vm().ofSize(1));
		Vm vm2=a(vm().ofSize(4));
		Vm vm3=a(vm().ofSize(2));
		
		balancing(aServerListWith(server1, server2), aVmsListWith(vm1, vm2, vm3));
		
		assertThat("server1 should contain the vm1", server1.contains(vm1));
		assertThat("server2 should contain the vm2", server2.contains(vm2));
		assertThat("server1 should contain the vm3", server1.contains(vm3));
		
		assertThat(server1, hasCurrentLoadOf(75.0d));
		assertThat(server2, hasCurrentLoadOf(66.66d));
	}
	private Vm[] aVmsListWith(Vm... vms) {
		return vms;
	}

	private void balancing(Server[] aServerListWith, Vm[] anEmptyVmsList) {
		new ServerLoadBalancer().balance(aServerListWith, anEmptyVmsList);
	}

	private Vm[] anEmptyVmsList() {
		return new Vm[0];
	}

	private Server[] aServerListWith(Server... servers) {
		return servers;
	}

	private <T> T a(Builder<T> builder) {
		return builder.build();
	}

}
