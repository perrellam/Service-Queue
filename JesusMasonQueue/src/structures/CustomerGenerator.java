/**
 * Customer Generator Class
 * 
 * Generates Customer objects
 * 
 * @author jesusargel
 * @author perrellamason
 */
package structures;



public abstract class CustomerGenerator implements Runnable
{
    //Properties
    private int myMaxTimeBetweenCustomers;
    private int myTotalCustomers;
    private boolean mySuspended;


    private Thread myThread;
    private ServiceQueueManager myServiceQueueManager;
    private long myTime;
    
    //Constructor
    public CustomerGenerator (int maxTimeBetweenCustomers, ServiceQueueManager serviceManager, int totalCustomers)
    {
        myTotalCustomers = totalCustomers;
        mySuspended = false;
        myThread = new Thread(this);
        myServiceQueueManager = serviceManager;
        myMaxTimeBetweenCustomers = maxTimeBetweenCustomers;
    }
    
    /**
     * Run function, runs the thread
     */
    public void run()
    {
        try
        {
            synchronized(this)
            {
                this.doSomething();
            }
        }
        catch (InterruptedException e)
        {
            System.out.println("Thread suspended.");
        }   
    }
    private void doSomething() throws InterruptedException
    {
        String message;
        ServiceQueue<Customer> shortest;
        long endTime;
        int myCounter = 0;
        while(myCounter < myTotalCustomers)
        {
            // blocks while thread is suspended
            this.waitWhileSuspended();
            
            Customer customer = this.generateCostumer();
            shortest = myServiceQueueManager.determineShortestQueue();
            shortest.insertCustomer(customer);
            myServiceQueueManager.setMyPresentTime((int)System.currentTimeMillis());
            customer.setMyEntryTime(myServiceQueueManager.getMyPresentTime());//sets customer entry time
            System.out.println("Queue 1:" + myServiceQueueManager.getNumCustomers(0));
            System.out.println("Queue 2:" + myServiceQueueManager.getNumCustomers(1));
            System.out.println("Queue 3:" + myServiceQueueManager.getNumCustomers(2));
            System.out.println("Queue 4:" + myServiceQueueManager.getNumCustomers(3));
            System.out.println("Queue 5:" + myServiceQueueManager.getNumCustomers(4));
            System.out.println(myServiceQueueManager.totalWaitTime());
            System.out.println(myServiceQueueManager.averageWaitTime());
            System.out.println(myServiceQueueManager.averageIdleTime());
            System.out.println(myServiceQueueManager.averageServiceTime());
            int time = this.generateTimeBetweenCustomers();
            customer.setMyServiceTime(time);
            
            myCounter++;
            
            try
            {
                Thread.sleep(time);
               // endTime = System.currentTimeMillis();
                
              //  shortest.addToWaitTime((int) (endTime - myTime)); //adds 
            }
            catch(InterruptedException e)
            {
                message = e.getMessage();
                System.err.println(message);
            }
            
        }
    }

    /**
     * Function that indicates the thread to stop
     * while it is suspended
     * @throws InterruptedException
     */
    private void waitWhileSuspended() throws InterruptedException
    {
        while (mySuspended)
        {
            this.wait();
        }
    }
    
    /**
     * Function that suspends the thread
     */
    public void suspend()
    {
        mySuspended = true;
    }
    
    /**
     * Function that gets the thread started
     */
    public void start()
    {
        try
        {
            myThread.start();
            myServiceQueueManager.setMyStartTime((int)System.currentTimeMillis());
        }
        catch(IllegalThreadStateException e)
        {
            System.out.println("Thread already started");
        }
    }
    
    /**
     * Function that resumes the thread after being suspended
     */
    public synchronized void resume()
    {
        mySuspended = false;
        this.notify();
    }
    
    /**
     * Function that creates a new customer
     * 
     * @return Customer
     */
    public  Customer generateCostumer()
    {
        Customer customer = new Customer();
        return customer;
    }
    
    /**
     * Abstract method that returns a random time between customers
     * 
     * @return int
     */
    public abstract int generateTimeBetweenCustomers();
    

  //Accessors
    public int getMyMaxTimeBetweenCustomers()
    {
        return myMaxTimeBetweenCustomers;
    }
    
    public void setTotalNumOfCustomers(int numCustomers)
    {
        myTotalCustomers = numCustomers;
    }
    
    public boolean isMySuspended()
    {
        return mySuspended;
    }
}
