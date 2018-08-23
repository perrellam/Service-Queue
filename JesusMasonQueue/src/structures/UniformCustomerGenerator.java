/**
 * UniformCustomerGeneratorClass that extends the abstract CustomerGenerator
 * 
 * @author jesusargel
 * @author perrellamason
 */
package structures;

import java.util.Random;

public class UniformCustomerGenerator extends CustomerGenerator
{

  //Properties

    private Random myRandom;
    
    public UniformCustomerGenerator(int maxTimeBetweenCustomers, ServiceQueueManager serviceManger, int numCustomers)
    //Constructor
    {
        super(maxTimeBetweenCustomers, serviceManger, numCustomers);
        myRandom = new Random(maxTimeBetweenCustomers);
    }


    /**
     * Function that generates a random number that
     * will be the time between customers
     * 
     * @return int
     */
    public int generateTimeBetweenCustomers()
    {
        return myRandom.nextInt(getMyMaxTimeBetweenCustomers()) + 1;
    }

}
