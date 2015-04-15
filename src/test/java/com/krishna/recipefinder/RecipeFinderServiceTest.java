package com.krishna.recipefinder;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.krishna.recipefinder.service.RecipeFinderService;

public class RecipeFinderServiceTest {

	private RecipeFinderService recipeFinderService;
	
	private static final String INPUT_DIR = "src/test/input_data/";
	
	@Before
	public void init()
	{
		recipeFinderService = new RecipeFinderService();
	}
	
	
	@Test
	public void testForValidInput() {
	
	try {
		
		String output =  recipeFinderService.getRecipeRecommendation(
		new File(INPUT_DIR+"fridge.csv"), 
		new File(INPUT_DIR+"recipes.json"), null);
		
		assertEquals(output, "salad sandwich");
		
	} catch (RecipeFinderException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	}
	
	@Test
	public void testForAnotherValidInput() {
	
	try {
		
		String output =  recipeFinderService.getRecipeRecommendation(
		new File(INPUT_DIR+"fridge.csv"), 
		new File(INPUT_DIR+"recipes.json"), null);
		
		assertEquals(output, "salad sandwich");
		
	} catch (RecipeFinderException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	}
	
	@Test
	public void testForTakeOutScenario() {
	
	try {
		
		String output =  recipeFinderService.getRecipeRecommendation(
		new File(INPUT_DIR+"fridge.csv"), 
		new File(INPUT_DIR+"recipes_takeout_case.json"), null);
		
		assertEquals(output, "Order Takeout");
		
	} catch (RecipeFinderException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	}
	

	@Test(expected=RecipeFinderException.class)
	public void testForInvalidCsvExtension() throws RecipeFinderException {
	
			String output =  recipeFinderService.getRecipeRecommendation(
			new File(INPUT_DIR+"fridge.txt"), 
			new File(INPUT_DIR+"recipes.json"), null);
		
	
	}
	
	@Test(expected=RecipeFinderException.class)
	public void testForInvalidJsonExtension() throws RecipeFinderException {
	
			String output =  recipeFinderService.getRecipeRecommendation(
			new File(INPUT_DIR+"fridge.csv"), 
			new File(INPUT_DIR+"recipes.jsn"), null);
		
	}
	
	@Test(expected=RecipeFinderException.class)
	public void testForInvalidCsvFormat() throws RecipeFinderException {
	
			String output =  recipeFinderService.getRecipeRecommendation(
			new File(INPUT_DIR+"fridge_invalid_format.csv"), 
			new File(INPUT_DIR+"recipes.json") , null);
	}
	
	@Test(expected=RecipeFinderException.class)
	public void testForInvalidJsonFormat() throws RecipeFinderException {
	//parenthesis not closed in json file
			String output =  recipeFinderService.getRecipeRecommendation(
			new File(INPUT_DIR+"fridge.csv"), 
			new File(INPUT_DIR+"recipes_invalid_json_format.json"), null);
		
	}
	
	@Test(expected=RecipeFinderException.class)
	public void testForInvalidUseByDateFormat() throws RecipeFinderException {
		//date separator is expected as / but mentioned as - in the input file
			String output =  recipeFinderService.getRecipeRecommendation(
			new File(INPUT_DIR+"fridge_invalid_date_format.csv"), 
			new File(INPUT_DIR+"recipe.json"), null);
		
	}
	
	@Test(expected=RecipeFinderException.class)
	public void testForInvalidQuantityInCsvFile() throws RecipeFinderException {
		//date separator is expected as / but mentioned as - in the input file
			String output =  recipeFinderService.getRecipeRecommendation(
			new File(INPUT_DIR+"fridge_invalid_date_format.csv"), 
			new File(INPUT_DIR+"recipe.json"), null);
		
	}
	
	@Test(expected=RecipeFinderException.class)
	public void testForValidInputAndCustomTodaysDate() throws RecipeFinderException {
		//date separator is expected as / but mentioned as - in the input file
	
    	Date customTodaysDate = null;
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
    	try {
			 customTodaysDate = simpleDateFormat.parse("01/04/2014");
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			String output =  recipeFinderService.getRecipeRecommendation(
			new File(INPUT_DIR+"fridge.csv"), 
			new File(INPUT_DIR+"recipe.json"), customTodaysDate);
		
	}
	
	

}
