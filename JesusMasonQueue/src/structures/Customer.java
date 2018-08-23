/**
 * Customer class, object Customer
 * 
 * @author jesusargel
 * @author perrellamason
 */
package structures;


public class Customer
{
    // Properties
    private int myServiceTime;
    private int myEntryTime;
    private int myWaitTime;

    //Constructor
    public Customer()
    {
       
    }

    //Accessors
    
    public int getMyServiceTime()
    {
        return myServiceTime;
    }

    public void setMyServiceTime(int myServiceTime)
    {
        this.myServiceTime = myServiceTime;
    }

    public int getMyEntryTime()
    {
        return myEntryTime;
    }

    public void setMyEntryTime(int myEntryTime)
    {
        this.myEntryTime = myEntryTime;
    }

    public int getMyWaitTime()
    {
        return myWaitTime;
    }

    public void setMyWaitTime(int myWaitTime)
    {
        this.myWaitTime = myWaitTime;
    }
    
}
