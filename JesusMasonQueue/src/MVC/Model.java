/**
 * Model class for the MVC
 * 
 * @author jesusargel
 * @author perrellamason
 */
package MVC;

import structures.Customer;
import structures.ServiceQueue;
import structures.ServiceQueueManager;
import structures.UniformCashier;
import structures.UniformCustomerGenerator;

public class Model
{
    private UniformCustomerGenerator myCustomerGenerator;
    private UniformCashier[] myCashiers;
    private ServiceQueue<Customer>[] myServiceQueues;

    private ServiceQueueManager myServiceQueueManager;
    private int myTotalNumCustomers;
    private int myMaxTimeBetweenCustomers;
    private int myMaxTimeOfService;
    private int myNumOfQueues;
    
    public Model(int totalNumCustomers, int maxTimeBetweenCustomers, int maxTimeOfService, int numOfQueues)
    {
        myTotalNumCustomers = totalNumCustomers;
        myMaxTimeBetweenCustomers = maxTimeBetweenCustomers;
        myMaxTimeOfService = maxTimeOfService;
        myNumOfQueues = numOfQueues;
        myCashiers = new UniformCashier[myNumOfQueues];
        myServiceQueueManager = new ServiceQueueManager();
        myServiceQueueManager.setNumberOfQueues(myNumOfQueues);
        myServiceQueues = myServiceQueueManager.getMyServiceQueues();
        
        for(int i = 0; i < myCashiers.length; i++)
        {
            myCashiers[i] = new UniformCashier(myMaxTimeOfService, myServiceQueues[i]);
        }
        
        myCustomerGenerator = new UniformCustomerGenerator(myMaxTimeBetweenCustomers, myServiceQueueManager, myTotalNumCustomers);
        
        
    }

    public int getMyTotalNumCustomers()
    {
        return myTotalNumCustomers;
    }
    
    public ServiceQueue<Customer>[] getMyServiceQueues()
    {
        return myServiceQueues;
    }
    
    public int getMyNumOfQueues()
    {
        return myNumOfQueues;
    }
    public int numInLine(int queue)
    {
        return myServiceQueues[queue].getNumberCustomersInLine();
    }
    
    public UniformCustomerGenerator getMyCustomerGenerator()
    {
        return myCustomerGenerator;
    }
    
    public UniformCashier getMyCashier(int queue)
    {
        return myCashiers[queue];
    }
    
    public void setMyTotalNumCustomers(int myTotalNumCustomers)
    {
        this.myTotalNumCustomers = myTotalNumCustomers;
    }
    
    public void setMyMaxTimeBetweenCustomers(int myMaxTimeBetweenCustomers)
    {
        this.myMaxTimeBetweenCustomers = myMaxTimeBetweenCustomers;
    }

    public void setMyMaxTimeOfService(int myMaxTimeOfService)
    {
        this.myMaxTimeOfService = myMaxTimeOfService;
    }

    public void setMyNumOfQueues(int myNumOfQueues)
    {
        this.myNumOfQueues = myNumOfQueues;
    }
    public int getOverflowCount()
    {
        int overflow = 0;
        for(int i = 0; i< myNumOfQueues; i++)
        {
            if(myServiceQueues[i].getNumberCustomersInLine() > 9)
            {
                overflow = myServiceQueues[i].getNumberCustomersInLine() - 9;
            }else
            {
                return 0;
            }
        }
        return overflow;
        
    }
    
    public int getTotalWait()
    {
        return myServiceQueueManager.totalWaitTime();
    }
    
    public int getTotalService()
    {
        return myServiceQueueManager.totalServiceTime();
    }
    
    public int getAverageService()
    {
        return myServiceQueueManager.averageServiceTime();
    }
    public int getAverageWait()
    {
        return myServiceQueueManager.averageWaitTime();
    }
    
    public int getQueueTotalWait(int queue)
    {
        return myServiceQueueManager.getMyServiceQueues()[queue].getTotalWaitTime();
    }
    
    public int getQueueTotalService(int queue)
    {
        return myServiceQueueManager.getMyServiceQueues()[queue].getTotalServiceTime();
    }
    
    public int getQueueAverageService(int queue)
    {
        return myServiceQueueManager.getMyServiceQueues()[queue].averageServiceTime();
    }
    public int getQueueAverageWait(int queue)
    {
       return myServiceQueueManager.getMyServiceQueues()[queue].averageWaitTime();

    }
    public int getQueueTotalIdle(int queue)
    {
        return myServiceQueueManager.getMyServiceQueues()[queue].getTotalIdleTime();
    }
    public int getQueueAverageIdle(int queue)
    {
       return myServiceQueueManager.getMyServiceQueues()[queue].averageIdleTime();

    }
    
    
}
