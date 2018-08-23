package structures;
/**
 * ServiceQueue class extends Queue class. Acts as a wrapper for Queue that tracks
 * and calculates service, wait, and idle times for the queue as customers offered
 * and polled(inserted and served).
 * 
 * @author MasonPerrella, JesusArgel
 *
 * @param <T>
 */
public class ServiceQueue<T> extends Queue<Customer>
{

    // properties
    private int myNumberCustomersServedSoFar;
    private int myNumberCustomersInLine;
    private int myTotalWaitTime;
    private int myTotalServiceTime;;
    private int myTotalIdleTime;
    
    /**
     * ServiceQueue Constructor
     * 
     */
    public ServiceQueue()
    {
        myNumberCustomersServedSoFar = 0;
        myNumberCustomersInLine = 0;
        myTotalWaitTime = 0;
        myTotalServiceTime = 0;
        myTotalIdleTime = 0;
    }
    /**
     * Adds integer passed in to the total Idle time
     * 
     * @param idle
     */
    public void addToIdleTime(int idle)
    {
        myTotalIdleTime = myTotalIdleTime + idle;
    }

    /**
     * Adds integer passed in to the total wait time
     * @param wait
     */
    //timer
    public void addToWaitTime(int wait)
    {
        myTotalWaitTime = myTotalWaitTime + wait;

    }

    /**
     * Adds integer passed in to the total service time
     * @param service
     */
    public void addToServiceTime(int service)
    {
        myTotalServiceTime = myTotalServiceTime + service;
    }

    /**
     * 
     * Adds customer being passed in tot he tail of the ServiceQueue
     * @param customer
     */
    public void insertCustomer(Customer customer)
    {
        this.offer(customer);
        myNumberCustomersInLine++;
    }

    /**
     * Removes the head of the queue( serves the first customer in line)
     * 
     * @return
     */
    public Customer serveCustomer()
    {            
        Customer customer;
        customer =  this.poll();
        myNumberCustomersServedSoFar++;
        
        return customer;
    }

   /**
    * Calculates average idle time
    * @return
    */
    public int averageIdleTime()
    {
        return myTotalIdleTime/myNumberCustomersServedSoFar;
    }
    /**
     * Calculates average wait time
     * @return
     */
    public int averageWaitTime()
    {
        return myTotalWaitTime/myNumberCustomersInLine;

    }
    /**
     * Calculates average service time
     * @return
     */
    public int averageServiceTime()
    {
        return myTotalServiceTime/myNumberCustomersServedSoFar;


    }
    // Accessors

    public int getNumberCustomersServedSoFar()
    {
        return myNumberCustomersServedSoFar;
    }

    public int getNumberCustomersInLine()
    {
        myNumberCustomersInLine = this.size();
        return myNumberCustomersInLine;
    }

    public int getTotalWaitTime()
    {
        return myTotalWaitTime;
    }

    public int getTotalServiceTime()
    {
        return myTotalServiceTime;
    }

    public int getTotalIdleTime()
    {
        return myTotalIdleTime;
    }
    
    public void setTotalWaitTime(int newtime)
    {
        myTotalWaitTime = newtime;
    }
    
    public void setTotalServiceTime(int newtime)
    {
        myTotalWaitTime = newtime;
    } 
    
    public void setTotalIdleTime(int newtime)
    {
        myTotalWaitTime = newtime;
    }

}
