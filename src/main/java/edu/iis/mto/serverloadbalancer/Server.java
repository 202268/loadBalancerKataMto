package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matcher;

public class Server {

	public static final double MAXIMUM_LOAD = 100.0d;
	private double currentLoadPercentage;
	private int capacity;
	private List<Vm> listOfVm = new ArrayList<Vm>();

	public boolean contains(Vm theVm) {
		return listOfVm.contains(theVm);
	}

	public Server(int capacity) {
		super();
		this.capacity = capacity;
	}

	public void addVm(Vm vm) {
		currentLoadPercentage += loadOfVm(vm);
		this.listOfVm.add(vm);
	}

	private double loadOfVm(Vm vm) {
		return (double) vm.getSize() / (double) capacity * MAXIMUM_LOAD;
	}

	public int countVms() {
		return listOfVm.size();
	}

	public boolean canFit(Vm vm) {
		return currentLoadPercentage + loadOfVm(vm) <= MAXIMUM_LOAD;
	}

	public int getCapacity() {
		return capacity;
	}

	public double getCurrentLoadPercentage() {
		return currentLoadPercentage;
	}

}
