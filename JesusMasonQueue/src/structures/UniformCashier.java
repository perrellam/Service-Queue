package structures;

import java.util.Random;

public class UniformCashier extends Cashier<Customer>
{
  

//property
    private Random myRandom;
    private int myMaxServiceTime;
    
	public UniformCashier(int maxTimeOfService, ServiceQueue<Customer> queue)
    {
        super(maxTimeOfService, queue);
        myRandom = new Random(maxTimeOfService);
        myMaxServiceTime = maxTimeOfService;
        
    }

    @Override
    public int generateServiceTime()
    {
        //create random service time here
        return myRandom.nextInt(getMyMaxTimeOfService())+1;
    }
/*
    public static void main(String[] args)
    {
       Queue q = new Queue();
        UniformCashier c = new UniformCashier(20,q );
        
        //System.out.println(c.generateServiceTime());
        
    }
    */
	
	
	
}
