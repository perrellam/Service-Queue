package structures;

import java.sql.Time;
import java.util.Random;
/**
 * Cashier class has its own thread, and is responsible for removing customers
 * from its service queue.  As it does this, it sleeps for a random time
 * limited by the constructor to give the impression of serving the customer.
 * 
 * 
 * @author MasonPerrella, JesusArgel
 *
 * @param <T>
 */
public abstract class Cashier<T> implements Runnable
{
    // properties
    private int myMaxTimeOfService;
    private ServiceQueue<Customer> myServiceQueue;
    private Thread myThread;
    private int myCounter;
    private boolean mySuspended;


    private int myNumCustomersServed;



    /**
     * 
     * Cashier Constructor 
     * 
     * @param maxTimeOfService
     * @param servciequeue
     */
    public Cashier(int maxServiceTimeOfService, ServiceQueue<Customer> queue)
    // (int maxTimeOfService, ServiceQueue servicequeue)
    {
        myServiceQueue =   queue;
       // myNumCustomersServed = myServiceQueue.getNumberCustomersServedSoFar();
        myCounter = 0;
        mySuspended = false;
        myThread = new Thread(this);
        myMaxTimeOfService = maxServiceTimeOfService;
    }

    /**
     * Calls the serve customer function in service queue class to remove
     * customer from the queue.
     * 
     * @return
     */
    public int serveCustomer()
    {
        myServiceQueue.serveCustomer();
        return myNumCustomersServed;
    }

    /**
     * Abstract class that generates a random service time with limits given in
     * the constructor.
     * UniformCashier class holds code for this abstract method
     * 
     * @return
     */
    public abstract int generateServiceTime();

    /**
     *Runs Thread 
     *
     */
    public void run()
    {
        try
        {
            synchronized (this)
            {
                this.doSomething();
            }
        } catch (InterruptedException e)
        {
            System.out.println("Thread suspended.");
        }
    }

    /**
     *  Method used in run() that checks for customers in the queue, serves them if available
     *  and sleeps for a random time. 
     *  
     * @throws InterruptedException
     */

    private void doSomething() throws InterruptedException
    {
     // while() get customer, if customer is there, dont sleep, poll, generate
        // service time
        // sleep for that time, then serve next.

        String message;
        Customer servedcustomer = new Customer();
        int servicetime;
        while (myCounter < 5000)
        {
            // blocks while thread is suspended
            this.waitWhileSuspended();
            
            
            if(myServiceQueue.empty())
            {
                Thread.sleep(5);
                myServiceQueue.addToIdleTime(5);
                servicetime = 0;
                myCounter ++;
            }
            else
            { 
                servedcustomer = myServiceQueue.serveCustomer();
                servicetime = this.generateServiceTime();
                myServiceQueue.addToServiceTime(servicetime);
            }
            
            try
            {
                Thread.sleep(servicetime);
                
                servedcustomer.setMyWaitTime((int) (System.currentTimeMillis() - (long)servedcustomer.getMyEntryTime()));
                myServiceQueue.addToWaitTime(servedcustomer.getMyWaitTime());
            } catch (InterruptedException e)
            {
                message = e.getMessage();
                System.err.println(message);
            }

        }
    }

    /**
     * Makes Thread wait if it is Suspended
     * 
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
     * Changes mySuspended boolean to true
     * 
     */
    public void suspend()
    {
        mySuspended = true;
    }

    /**
     * Used to start the thread when called.
     * 
     */
    public void start()
    {
        try
        {
            myThread.start();
        } 
        catch (IllegalThreadStateException e)
        {
            System.out.println("Thread already started");
        }
    }

    /**
     * Resumes thread when suspended
     * 
     */
    public synchronized void resume()
    {
        mySuspended = false;
        this.notify();
    }
    // Accessors

    public int getMyMaxTimeOfService()
    {
        return myMaxTimeOfService;
    }
    
    public boolean isMySuspended()
    {
        return mySuspended;
    }

    
}
