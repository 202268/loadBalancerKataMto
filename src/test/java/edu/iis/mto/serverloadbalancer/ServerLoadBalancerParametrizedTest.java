package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasLoadPercentageOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
@RunWith(Parameterized.class)
public class ServerLoadBalancerParametrizedTest extends ServerLoadBalancerBaseTest{
    private Random rand;
    private int server;
    private int client;
    
    public ServerLoadBalancerParametrizedTest(int clientNumber, int serverNumber) {
        rand=new Random();
        this.client=clientNumber;
        this.server=serverNumber;
    }
    @Parameters
    public static Collection<Integer[]> numbers() {
                return Arrays.asList(new Integer[][] { { 1, 1 }, { 2, 2 },
                        { 3, 3 }, { 4, 4 } });
            }

    @Test
	public void balancingOneServerWithOneSlotCapacity_andOneSlotVm_fillsTheServerWithTheVm() {
		Server theServer = a(server().withCapacity(server));
		Vm theVm = a(vm().ofSize(client));
		balance(aListOfServersWith(theServer), aListOfVmsWith(theVm));

		assertThat(theServer, hasLoadPercentageOf(100.0d));
		assertThat("the server should contain vm", theServer.contains(theVm));
	}
	
	
}
