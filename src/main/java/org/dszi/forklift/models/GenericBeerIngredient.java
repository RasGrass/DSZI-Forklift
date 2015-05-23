/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dszi.forklift.models;

/**
 *
 * @author RasGrass
 */
public class GenericBeerIngredient implements BeerIngredient {

	private final String name;

	public GenericBeerIngredient(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
