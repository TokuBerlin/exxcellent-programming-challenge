package de.exxcellent.challenge;

import java.io.File;
import java.io.Reader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.net.URL;
import org.apache.commons.csv.*;

/**
 * This class reads data rows from the given CSV file and returns the result identifier of the data row with the lowest spread between
 * minimum and maximum value of the row as a string. In case multiple rows have the same spread the first found result will be returned.
 */
public class SpreadCalculator
{
    private File dataCsvFile;
    private String columnName_result;
    private String columnName_min;
    private String columnName_max;


    /**
     * This method reads data from the given file and returns the number of the day with the lowest temparatur spread as a string.
     *
     * @param weatherCsvFile        The CSV file containing the weatherdata. The leading row of the file is expected to contain column names
     *                              Day,MxT,MnT,AvT,AvDP,1HrP TPcpn,PDir,AvSp,Dir,MxS,SkyC,MxR,Mn,R AvSLP
     * @param xcolumnName_result    The CSV column that contains the result identifier (e.g. the name or ID)
     * @param xcolumnName_min       The CSV column that contains the minimum value of the data row
     * @param xcolumnName_max       The CSV column that contains the maximum value of the data row
     */
    public SpreadCalculator( File xdataCsvFile, String xcolumnName_result, String xcolumnName_min, String xcolumnName_max )
    {
        dataCsvFile         = xdataCsvFile;
        columnName_result   = xcolumnName_result;
        columnName_min      = xcolumnName_min;
        columnName_max      = xcolumnName_max;
    }



    /**
     * This method reads data from the given file and returns the number of the day with the lowest temparatur spread as a string.
     *
     * @param weatherCsvFile    The CSV file containing the weatherdata. The leading row of the file is expected to contain column names
     *                          Day,MxT,MnT,AvT,AvDP,1HrP TPcpn,PDir,AvSp,Dir,MxS,SkyC,MxR,Mn,R AvSLP
     */
    public String getResultWithSmallestSpread()
    {
        // initialize local vars with safe values
        String result = "error in SpreadCalculator.getResultWithSmallestSpread(), maybe empty file?";
        float lowestSpreadFoundSoFar = Float.MAX_VALUE;

        try
        {
            // read all data of the file into memory (might lead to problems with large data files)
            Reader inputFileReader      = new FileReader(dataCsvFile);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(inputFileReader);

            // loop over all rows the data file contains
            for( CSVRecord record : records )
            {
                String res = record.get(columnName_result);
                String min = record.get(columnName_min);
                String max = record.get(columnName_max);

                // compute temperature spread (difference between maximum & minimum temperature of the day.)
                float spread = Math.abs(Float.parseFloat(max) - Float.parseFloat(min));

                // print debug information
                if( Constants.debugMode )
                    System.out.printf("res %s: min: %s, max: %s, spread: %f%n", res, min, max, spread);

                // overwrite the return result each time the spread of this data line is smaller than the smallest found so far
                if( spread < lowestSpreadFoundSoFar )
                {
                    result = res;
                    lowestSpreadFoundSoFar = spread;
                }
            }
        } catch( java.io.FileNotFoundException e )
        {
            System.out.printf("Error: FileNotFoundException - could not find input file %s%n", dataCsvFile.getAbsolutePath());
        } catch( java.io.IOException e )
        {
            System.out.printf("Error: IOException - unable to read input file %s%n", dataCsvFile.getAbsolutePath());
        }

        // return the result which is either an error string (init) or the lowest spread found
        return result;
    }

}