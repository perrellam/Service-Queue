/**
 * ServiceQueueManager class controls the time and
 * is in charge of locating elements in the smallest queue
 * 
 * @author jesusargel
 * @author perrellamason
 */
package structures;

public class ServiceQueueManager
{
    // properties

    private static final int MAX_NUMBER_OF_QUEUES = 5;
    private int myNumberOfServiceQueues;
    private ServiceQueue<Customer>[] myServiceQueues;
    private int myTotalWaitTime;
    private int myTotalServiceTime;
    private int myTotalIdleTime;
    private int myAverageWaitTime;
    private int myAverageServiceTime;
    private int myAverageIdleTime;
    private int myPresentTime;
    private int myStartTime;

    public ServiceQueueManager()
    {
        myTotalIdleTime = 0;
        myNumberOfServiceQueues = 5;
        myServiceQueues = new ServiceQueue[myNumberOfServiceQueues];
        for (int i = 0; i < myNumberOfServiceQueues; i++)
        {
            myServiceQueues[i] = new ServiceQueue<Customer>();
            myTotalIdleTime += myServiceQueues[i].getTotalIdleTime();
        }
      //  myStartTime = (int) System.currentTimeMillis();
        

    }

    // methods
    public int totalServedSoFar()
    {
        int totalserved = 0;
        for (int i = 0; i < myNumberOfServiceQueues; i++)
        {
            totalserved = totalserved + (myServiceQueues[i].getNumberCustomersServedSoFar());
        }
        return totalserved;
    }

    public int totalWaitTime()
    {
        for (int i = 0; i < myNumberOfServiceQueues; i++)
        {
            myTotalWaitTime = myTotalWaitTime + (myServiceQueues[i].getTotalWaitTime());
        }
        return myTotalWaitTime;
    }

    public int totalServiceTime()
    {

        for (int i = 0; i < myNumberOfServiceQueues; i++)
        {
            myTotalServiceTime = myTotalServiceTime + (myServiceQueues[i].getTotalServiceTime());
        }
        return myTotalServiceTime;
    }

    public ServiceQueue<Customer> determineShortestQueue()
    {
        int nextline;
        int shortestline;
        ServiceQueue<Customer> shortestQueue = myServiceQueues[0];
        for (int i = 0; i < myNumberOfServiceQueues; i++)
        {
            shortestline = myServiceQueues[i].getNumberCustomersInLine();

            if (shortestline == 0)
            {
                return myServiceQueues[i];

            } else
            {

                if (i + 1 >= myNumberOfServiceQueues)
                {
                    break;
                } else
                    nextline = myServiceQueues[i + 1].getNumberCustomersInLine();

                if (nextline < shortestline)
                {
                    shortestline = nextline;
                    shortestQueue = myServiceQueues[i + 1];
                }
            }

        }
        return shortestQueue;
    }

    public int averageWaitTime()
    {

        if(totalServedSoFar() != 0)
        {
        myAverageWaitTime = totalWaitTime() / totalServedSoFar();
        }
        return myAverageWaitTime;
    }

    public int averageServiceTime()
    {
        if(totalServedSoFar() != 0)
        {
        myAverageServiceTime = totalServiceTime() / totalServedSoFar();
        }

        return myAverageServiceTime;
    }

    // needs code
    public int averageIdleTime()
    {
        if(totalServedSoFar() != 0)
        {
        myAverageIdleTime = myTotalIdleTime / myNumberOfServiceQueues;
        }
        return myAverageIdleTime;
    }

    public int getNumCustomers(int queueNum)
    {
        return myServiceQueues[queueNum].getNumberCustomersInLine();
    }

    public void setNumberOfQueues(int num)
    {        
        myNumberOfServiceQueues = num;
    }
    
    public ServiceQueue<Customer>[] getMyServiceQueues()
    {
        return myServiceQueues;
    }
    
    public int getMyPresentTime()
    {
        return myPresentTime;
    }

    public void setMyPresentTime(int myPresentTime)
    {
        this.myPresentTime = myPresentTime;
    }

    public int getMyStartTime()
    {
        return myStartTime;
    }

    public void setMyStartTime(int myStartTime)
    {
        this.myStartTime = myStartTime;
    }

}
