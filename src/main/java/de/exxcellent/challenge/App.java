package de.exxcellent.challenge;

import de.exxcellent.challenge.Constants.*;
import de.exxcellent.challenge.SpreadCalculator;

import java.io.File;
import java.io.Reader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.net.URL;
import org.apache.commons.csv.*;


/**
 * Entry class for weatherdata coding challenge
 * @author Thomas Kuhn <thomas.kuhn.berlin@gmail.com>
 */
public final class App
{

    /**
     * A little helper method to get a file from the resources of this application
     */
    private File getFileFromResources(String fileName)
    {
        URL fileUrl = getClass().getResource(fileName);
        return new File(fileUrl.getFile());
    }


    /**
     * This is the main entry method the program.
     * @param args The CLI arguments passed
     */
    public static void main(String... args)
    {
        if(Constants.debugMode)
            System.out.printf("[running in debug mode...]%n");

        // get the expected data file from resources
        App main = new App();
        File weatherFile = main.getFileFromResources(Constants.FILEPATH_WEATHER);
        File footballFile = main.getFileFromResources(Constants.FILEPATH_FOOTBALL);

        // init the spread calculators
        SpreadCalculator weatherSpread  = new SpreadCalculator(weatherFile, "Day", "MnT", "MxT");
        SpreadCalculator footballSpread = new SpreadCalculator(footballFile, "Team", "Goals", "Goals Allowed");

        // determine the values in question
        String dayWithSmallestTempSpread    = weatherSpread.getResultWithSmallestSpread();
        String teamWithSmallestGoalSpread   = footballSpread.getResultWithSmallestSpread();

        // print out the fruits of our labour
        System.out.printf("Day with smallest temperature spread : %s%n", dayWithSmallestTempSpread);
        System.out.printf("Team with smallest goal spread       : %s%n", teamWithSmallestGoalSpread);
    }

}
