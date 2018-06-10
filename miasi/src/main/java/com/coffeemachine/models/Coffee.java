package com.coffeemachine.models;

public class Coffee {

	private String name;
	private double price;
	private double water;
	private double milk;
	private double coffee;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getWater() {
		return water;
	}

	public void setWater(double water) {
		this.water = water;
	}

	public double getMilk() {
		return milk;
	}

	public void setMilk(double milk) {
		this.milk = milk;
	}

	public double getCoffee() {
		return coffee;
	}

	public void setCoffee(double coffee) {
		this.coffee = coffee;
	}

	public Coffee(String name, double price, double water, double milk, double coffee) {
		this.name = name;
		this.price = price;
		this.water = water;
		this.milk = milk;
		this.coffee = coffee;
	}

}
