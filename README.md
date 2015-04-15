# RecipeFinder

# Setup Instructions

Software setup requirements:-

1. Java 1.7(Please check the link http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html
to download Java 1.7)

2. Maven 3.2.5 or above(Please the link check http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
for maven setup and installation instructions)

3. Eclipse 3.x or above(Optional. Needed only if you want to import it in eclipse)


To run the program from unix/linux console:

1. Clone it to your local PC or download as zip from github.

2. Go to the RecipeFinder directory.

3. Type the command "mvn package" (without quotations). You should be able to see a target folder and inside that
RecipeFinder.jar file. Please ignore other directory and files.

4. Copy the input data folder from src/test/input_data to the target folder. This is required for testing the program.

5. Once done, please execute the run.sh script(available on the RecipeFinder top level directory), as per the format below:-


./run.sh #ingredients csv file name# #recipe json file name# #optional customized today’s date#

Note: 
1. Third argument is optional and needed only if you want to setup a different date as today’s date.

2. Please don't include hash. It is shown here only display the separate the description. Please refer the below
example command and output.



If everything goes well, you should see output as below:-

—Console Output Starts--

$ ./run.sh fridge.csv recipes.json

salad sandwich

—Console Output Ends—



To run the program from Eclipse:-

 1. Clone it to your local PC or download as zip from github.
 
 2. Select File->Import->Existing Maven Projects->Browse to the downloaded RecipeFinder directory and click finish.
 
 3. It might take some time for maven to download the required dependencies.
 
 4. Once everything is ready, please run the unit test case file src/test/java/com/krishna/recipefinder/RecipeFinderTest.java to run the test cases.
 
 5. You may also run it directly using src/main/java/com/krishna/recipefinder/RecipeFinderMain.java. In this case, you need to manually set the arguments for fully qualified file names of ingredients csv file and recipe json file in the Run Configurations.


Please contact me if you face any problems in setup.

