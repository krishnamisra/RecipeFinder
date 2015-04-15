package com.krishna.recipefinder;

import java.io.File;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.krishna.recipefinder.service.RecipeFinderService;

/**
 * Recipe Recommendation Program
 *@author krishnamisra
 *
 */
public class RecipeFinderMain 
{
    public static void main( String[] args)
    {
    	if(args.length>=2 && args.length<=3)
    	{	
    		try {
    			
    			String ingredientsFileName = args[0];
    			
    			String recipeFileName = args[1];
    			
    			String customDateFromArgs = null;
    			
    			Date customTodaysDate = null;
    			
    			if(args.length==3)
    			{
    				customDateFromArgs = args[2];
    				
        			customTodaysDate = convertStringToDate(customDateFromArgs);
        		
    			}
    			
    			if(customTodaysDate==null)
    			{
    				customTodaysDate = new Date();
    			}
    			
    			
    			
        		RecipeFinderService recipeFinderService = new RecipeFinderService();
            	
        		String returnValue = recipeFinderService.getRecipeRecommendation(
        		new File(ingredientsFileName), 
        		new File(recipeFileName), customTodaysDate);
        		
        		System.out.println(returnValue);
        	} catch (RecipeFinderException e) {
        		// TODO Auto-generated catch block
        		
        		System.out.println("Exception in RecipeFinderMain Class"+e.getMessage());
        		
        		e.printStackTrace();
        	}catch (Exception e) {
        		// TODO Auto-generated catch block
        		
        		System.out.println("GeneralException in RecipeFinderMain Class"+e.getMessage());
        		
        		e.printStackTrace();
        	}
    		
    		
    	}else
    	{
    		System.out.println("This is not valid input.\n1. First arguement should be"
    				+ "fully qualified file name of ingredients file in csv format.\n"
    				+ "2. Second arguement must be fully qualified file name of recipe file in json format\n"
    				+ "3. Third optional arguement is for setting today's date. If not set, today's date with system time zone is taken by default");
    		
    	
    	}
    	
    }
    
    private static Date convertStringToDate(String customDateFromArgs)
    {
    	Date customTodaysDate = null;
    	
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
    	try {
			 customTodaysDate = simpleDateFormat.parse(customDateFromArgs);
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return customTodaysDate;
    }
}
