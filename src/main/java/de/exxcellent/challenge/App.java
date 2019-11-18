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
     * A little helper function to get a file from the resources of this application
     */
    private File getFileFromResources(String fileName)
    {
        URL fileUrl = getClass().getResource(fileName);
        return new File(fileUrl.getFile());
    }


    /**
     * This functions reads data from the given file and returns the number of the day with the lowest temparatur spread as a string.
     *
     * @param weatherCsvFile    The CSV file containing the weatherdata. The leading row of the file is expected to contain column names
     *                          Day,MxT,MnT,AvT,AvDP,1HrP TPcpn,PDir,AvSp,Dir,MxS,SkyC,MxR,Mn,R AvSLP
     */
    private static String getDayWithSmallestTempSpreadFromCsvFile( File weatherCsvFile )
    {
        // initialize local vars with safe values
        String result = "error in getDayWithSmallestTempSpreadFromCsvFile()";
        float lowestSpreadFoundSoFar = 9999;

        try
        {
            // read all data of the file into memory (might lead to problems with large data files)
            Reader inputFileReader      = new FileReader(weatherCsvFile);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(inputFileReader);

            // loop over all rows the data file contains
            for( CSVRecord record : records )
            {
                String day = record.get("Day");
                String min = record.get("MnT");
                String max = record.get("MxT");

                // compute temperature spread (difference between maximum & minimum temperature of the day.)
                float spread = Math.abs(Float.parseFloat(max) - Float.parseFloat(min));

                // print debug information
                if( Constants.debugMode )
                    System.out.printf("day %s: min: %s, max: %s, spread: %f%n", day, min, max, spread);

                // overwrite the return result each time the spread of this data line is smaller than the smallest found so far
                if( spread < lowestSpreadFoundSoFar )
                {
                    result = day;
                    lowestSpreadFoundSoFar = spread;
                }
            }
        } catch( java.io.FileNotFoundException e )
        {
            System.out.printf("Error: FileNotFoundException - could not find input file %s%n", weatherCsvFile.getAbsolutePath());
        } catch( java.io.IOException e )
        {
            System.out.printf("Error: IOException - unable to read input file %s%n", weatherCsvFile.getAbsolutePath());
        }

        // return the result which is either an error string (init) or the lowest spread found
        return result;
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

        // determine the values in question
        String dayWithSmallestTempSpread    = getDayWithSmallestTempSpreadFromCsvFile(weatherFile);
        String teamWithSmallestGoalSpread   = "Football solution not implemented";

        // print out the fruits of our labour
        System.out.printf("Day with smallest temperature spread : %s%n", dayWithSmallestTempSpread);
        System.out.printf("Team with smallest goal spread       : %s%n", teamWithSmallestGoalSpread);
    }

}
