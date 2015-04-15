package com.krishna.recipefinder.model;

import java.util.List;

/**
 * Custom model object for wrapping the final recommendation object
 * 
 * @author krishnamisra
 *
 */

public class RecommendationObject {

	private Recipe recipe;
	private List<Ingredient> ingredientsFromStock;

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public List<Ingredient> getIngredientsFromStock() {
		return ingredientsFromStock;
	}

	public void setIngredientsFromStock(List<Ingredient> ingredientsFromStock) {
		this.ingredientsFromStock = ingredientsFromStock;
	}

}
