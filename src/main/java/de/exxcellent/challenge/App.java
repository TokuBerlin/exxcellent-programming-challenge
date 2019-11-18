package de.exxcellent.challenge;

import de.exxcellent.challenge.Constants.*;

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
     * A little helper function to get a file from resources
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

        App main = new App();
        File weatherFile = main.getFileFromResources(Constants.FILEPATH_WEATHER);

        // determine the values in question
        String dayWithSmallestTempSpread    = "Weather  solution not implemented";
        String teamWithSmallestGoalSpread   = "Football solution not implemented";

        try {
            Reader in = new FileReader(weatherFile);

            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);;
            for (CSVRecord record : records)
            {
                String day = record.get("Day");
                String min = record.get("MnT");
                String max = record.get("MxT");

                // compute temperature spread (difference between maximum & minimum temperature of the day.)
                float spread = Math.abs(Float.parseFloat(max) - Float.parseFloat(min));

                if(Constants.debugMode)
                    System.out.printf("day %s: min: %s, max: %s, spread: %f%n", day, min, max, spread);
            }
        } catch( java.io.FileNotFoundException e )
        {
            System.out.printf("Error: FileNotFoundException - could not find input file %s%n", weatherFile.getAbsolutePath());
        } catch( java.io.IOException e )
        {
            System.out.printf("Error: IOException - unable to read input file %s%n", weatherFile.getAbsolutePath());
        }


        // print out the fruits of our labour
        System.out.printf("Day with smallest temperature spread : %s%n", dayWithSmallestTempSpread);
        System.out.printf("Team with smallest goal spread       : %s%n", teamWithSmallestGoalSpread);
    }

}
