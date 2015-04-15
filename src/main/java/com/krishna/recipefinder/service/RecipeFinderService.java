package com.krishna.recipefinder.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krishna.recipefinder.RecipeFinderException;
import com.krishna.recipefinder.model.Ingredient;
import com.krishna.recipefinder.model.Recipe;
import com.krishna.recipefinder.model.RecommendationObject;

	/**
	 * 
	 * This class is for processing the recommendations for food based on the recipes and the ingredients inputs.
	 * 
	 * @author krishnamisra
	 *
	 */

public class RecipeFinderService {
	
	
	
	private List<Ingredient> captureIngredientsFromCsvInput(File ingredientsCsvFile) throws RecipeFinderException
	{
		
		
		if(ingredientsCsvFile==null || !ingredientsCsvFile.getName().endsWith(".csv"))
		{
			throw new RecipeFinderException("Invalid ingredients file name. The length should be more than 0 and file extension must be .csv");
		}
		
		BufferedReader br = null;
		String line = "";
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		
		try {
			br = new BufferedReader(new FileReader(ingredientsCsvFile));
			while ((line = br.readLine()) != null) {
				
				Ingredient ingredient = new Ingredient();
				
				String[] strArrayOfIngredients = line.split(",");
				
				if(strArrayOfIngredients==null || strArrayOfIngredients.length!=4)
				{
					throw new  RecipeFinderException("Invalid record format and/or delimiter");
				}
				
				ingredient.setItem(strArrayOfIngredients[0]);
				ingredient.setAmount(Integer.parseInt(strArrayOfIngredients[1]));
				ingredient.setUnit(strArrayOfIngredients[2]);
				
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
				Date useByDate = simpleDateFormat.parse(strArrayOfIngredients[3]);
				
				ingredient.setUsebyDate(useByDate);
				ingredients.add(ingredient);
				
			}
	 
		} catch (FileNotFoundException f) {
			
			f.printStackTrace();
			
			throw new RecipeFinderException("File not found. Detailed error message is as follows->"+f.getMessage());
			
		} catch (IOException io) {
		
			io.printStackTrace();
			
			throw new RecipeFinderException("Exception file reading csv file. Detailed error message is as follows->"+io.getMessage());
			
		} catch (ParseException pe) {
			
			pe.printStackTrace();
			
			throw new RecipeFinderException("Exception while parsing date. Detailed error message is as follows->"+pe.getMessage());
			
		}catch (Exception e) {
			
			e.printStackTrace();
			
			throw new RecipeFinderException("Generic Exception while processing ingredients csv input. Detailed error message is as follows->"+e.getMessage());
		}
		
		finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException io) {
					
					io.printStackTrace();
					
					throw new RecipeFinderException("Exception while closing the file. Detailed error message is as follows->"+io.getMessage());
				}
			}
		}
		
		return ingredients;
		
	  }
	
	private List<Recipe> captureRecipesFromJsonInput(File recipeJsonFile) throws RecipeFinderException 
	{	
		
		if(recipeJsonFile==null || !recipeJsonFile.getName().endsWith(".json"))
		{
			throw new RecipeFinderException("Invalid recipe file name. The length should be more than 0 and file extension must be .json");		
		}
		
			ObjectMapper objectMapper = new ObjectMapper();
		
			ArrayList<Recipe> list;
			try {
				list = objectMapper.readValue(new FileReader(recipeJsonFile), new TypeReference<ArrayList<Recipe>>(){
				});
			} catch (JsonParseException jpe) {

				jpe.printStackTrace();
				
				throw new RecipeFinderException("Exception while parsing json file. Detailed error message is as follows->"+jpe.getMessage());
				
			} catch (JsonMappingException jm) {
				
				jm.printStackTrace();
				
				throw new RecipeFinderException("Exception while mapping json file. Detailed error message is as follows->"+jm.getMessage());
				
			} catch (FileNotFoundException f) {
				
				f.printStackTrace();
				
				throw new RecipeFinderException("File not found exception while reading json file. Detailed error message is as follows->"+f.getMessage());
				
			} catch (IOException io) {
				
				io.printStackTrace();
				
				throw new RecipeFinderException("IO Exception while processing json file. Detailed error message is as follows->"+io.getMessage());
				
			}catch (Exception e) {
				
				e.printStackTrace();
				
				throw new RecipeFinderException("Generic Exception while processing ingredients csv input. Detailed error message is as follows->"+e.getMessage());
			}
	
		return list;
		
	}
	
	/**
	 * 
	 * This method is for processing the recipe recommendation received as per the inputs received from ingredients and recipes.
	 * 
	 * 
	 * @param ingredientsCsvFile
	 * @param recipeJsonFile
	 * @return
	 * @throws RecipeFinderException
	 */
	
	public String getRecipeRecommendation(File ingredientsCsvFile, File recipeJsonFile, Date customTodaysDate) throws RecipeFinderException
	{	
		try {
			
			List<Ingredient> ingredientsInStock = captureIngredientsFromCsvInput(ingredientsCsvFile);
		
			List<Recipe> recipes = captureRecipesFromJsonInput(recipeJsonFile);
			
			if(ingredientsInStock==null || ingredientsInStock.size()==0
					|| recipes==null ||recipes.size()==0)
			{
				
				throw new RecipeFinderException("Invalid recipe/ingredients input. Cannot continue processing.");
				
			}
				
			List<RecommendationObject> recommendedRecipes = new ArrayList<>(); 
			
			for(Recipe recipe:recipes)
			{
				
				Integer ingredientsCountForRecipe=recipe.getIngredients().size();
				
				List<Ingredient> ingredientsFromStock = new ArrayList<>();
				
				for(Ingredient ingredientRequiredForRecipe:recipe.getIngredients())
				{	
					Integer indexOfIngredient = ingredientsInStock.indexOf(ingredientRequiredForRecipe);
					
					if(indexOfIngredient>=0)
					{
						Ingredient ingredientInStock = ingredientsInStock.get(indexOfIngredient);
						
						//Check if the use by date is greater than or equal to current date
						if(ingredientInStock.getUsebyDate().compareTo(customTodaysDate)>=0)
						{
							if(ingredientInStock.getAmount() >= ingredientRequiredForRecipe.getAmount())
							{
								ingredientsFromStock.add(ingredientInStock);
							}else
							{
								System.out.println("Ingredient Quantity Insufficient");
							}
						}						
					}else
					{
						System.out.println("The required ingredient is not found");
					}
					
				}
				
				if(ingredientsFromStock.size()==ingredientsCountForRecipe)
				{
					RecommendationObject recommendationObject = new RecommendationObject();
					recommendationObject.setRecipe(recipe);
					recommendationObject.setIngredientsFromStock(ingredientsFromStock);
					recommendedRecipes.add(recommendationObject);
				}
				
			}
			
			Integer recommendedRecipeSize = recommendedRecipes.size();
			
			if(recommendedRecipeSize==1)
			{
				return  recommendedRecipes.get(0).getRecipe().getName();
				
			}else if(recommendedRecipes.size()>1)
			{
				//Start with the first recipe as default/inital recipe with closest use by ingredient
				RecommendationObject finalRecommendationObject = recommendedRecipes.get(0);
				
				 Date minUseByDateOfFinalRecommendedRecipe = 
				Collections.min(finalRecommendationObject.getIngredientsFromStock(),
			    new IngredientUseByDateComparator()).getUsebyDate();
				
				for(RecommendationObject currentRecommendationObject:recommendedRecipes)
				{
					  List<Ingredient> ingredientsOfCurrentRecipe =  currentRecommendationObject.getIngredientsFromStock();
					  
					  Date minUseByDateOfCurrentRecipe = Collections.min(ingredientsOfCurrentRecipe,
					  new IngredientUseByDateComparator()).getUsebyDate();
					  
					//Change if found a recipe, one of the ingredient's useby date is closer than previously chosen recipe
					  if(minUseByDateOfCurrentRecipe.compareTo(minUseByDateOfFinalRecommendedRecipe)<0)
					  { 
						  finalRecommendationObject = currentRecommendationObject;
					  }
				}
				
				return finalRecommendationObject.getRecipe().getName();
			}
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			throw new RecipeFinderException("Generic Exception while processing recipe recommendations. Detailed error message is as follows->"+e.getMessage());
		}
		
		return "Order Takeout";
	}
	
	class IngredientUseByDateComparator implements Comparator<Ingredient>
	{


		@Override
		public int compare(Ingredient o1, Ingredient o2) {
			// TODO Auto-generated method stub
		
			
			return o1.getUsebyDate().compareTo(o2.getUsebyDate());
		}
		
		
	}
	

}
