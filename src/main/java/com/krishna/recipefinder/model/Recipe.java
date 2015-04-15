package com.krishna.recipefinder.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Model object for Recipe
 * 
 * @author krishnamisra
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe {
	
	private String name;
	private ArrayList<Ingredient> ingredients;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Ingredient> getIngredients() {
		return ingredients;
	}
	public void setIngredients(ArrayList<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	@Override
	public String toString() {
		return "Recipe [name=" + name + ", ingredients=" + ingredients + "]";
	}
	
	

}
