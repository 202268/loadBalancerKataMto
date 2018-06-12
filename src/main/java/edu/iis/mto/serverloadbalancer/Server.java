package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matcher;

public class Server {

	public static final double MAXIMUM_LOAD = 100.0d;
	public double currentLoadPercentage;
	public int capacity;
	private List<Vm> listOfVm=new ArrayList<Vm>();
	public boolean contains(Vm theVm) {
		return listOfVm.contains(theVm);
	}

	public Server(int capacity) {
		super();
		this.capacity = capacity;
	}

	public void addVm(Vm vm) {
		currentLoadPercentage = (double)vm.size / (double)capacity * MAXIMUM_LOAD;
		this.listOfVm.add(vm);
	}

	public int countVms() {
		return listOfVm.size();
	}

	public boolean canFit(Vm vm) {
		return currentLoadPercentage+(double)vm.size / (double)capacity * MAXIMUM_LOAD <=MAXIMUM_LOAD;
	}

}
