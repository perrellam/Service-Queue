/**
 * Simulation Controller class
 * 
 * Takes care of conecting the View and the Model
 * 
 * @author jesusargel
 * @author perrellamason
 */
package MVC;

import structures.ServiceQueueManager;

public class SimulationController implements Runnable
{
  //Data Members
    private Model myModel;
    private SimulationView myView;
    private boolean mySuspended;
    private Thread myThread;
    
    /**
     * Basic Constructor
     */
    public SimulationController()
    {
        myView = new SimulationView(this);
        myThread = new Thread(this);
        mySuspended = false;
        myModel = new Model(0,0,0,0);
        this.start();
        this.startPause();
    }
            
    /**
     * Displays the customer images in the appropriate lines.
     * @param index The queue index for the customers to be printed in.
     */
    
    private void displayCustomers(int queue)
    {
        int numInQueue;
        numInQueue = myModel.numInLine(queue);
        myView.setCustomersInLine(queue, numInQueue);
    }
            
    /**
     * Runs the thread that updates the view.
     */
    public void run()
    {
        try
        {
            
            synchronized(this)
            {
                this.updateView();
            }
        }
        catch (InterruptedException e)
        {
            System.out.println("Thread suspended.");
        }
    }
    
    /**
     * Updates the view.
     * @throws InterruptedException
     */
    private void updateView()throws InterruptedException
    {
        while(true)
        {
            this.waitWhileSuspended();
            
            try 
            {
                Thread.sleep(200);
                
                for(int x = 0; x < myModel.getMyNumOfQueues(); x++)
                {
                    this.displayCustomers(x);
                    this.updateServed(x);
                    this.updateOverflow(x);
                    this.updateOverallStats();
   //                 this.updateTellerInfo(x);
                }
            } 
            catch (InterruptedException e) 
            {
                    e.printStackTrace();
            }
        }
    }
    
    private void waitWhileSuspended() throws InterruptedException
    {
        while (mySuspended)
        {
            this.wait();
            
        }
    }
    
    public void suspend()
    {
        mySuspended = true;
    }
    
    public void start()
    {
        try
        {
            myThread.start();
            
        }
        catch(IllegalThreadStateException e)
        {
            System.out.println("Thread already started");
        }
    }
    
    public synchronized void resume()
    {
        mySuspended = false;
        this.notify();
        
    }
    
    public void startPause()
    {
        myView.changeStartPause();
        if (mySuspended)
        {
            this.resume();
            myModel = new Model(myView.getNumberOfCustomers(), myView.getGenerateTime(),myView.getMaxTime(), myView.getNumTellers());
            myModel.getMyCustomerGenerator().start();
            for(int i = 0; i < myModel.getMyNumOfQueues(); i++)
            {
                myModel.getMyCashier(i).start();
            }
        }
        else
        {
            this.suspend();
            myModel.getMyCustomerGenerator().suspend();
            for(int i = 0; i < myModel.getMyNumOfQueues(); i++)
            {
                myModel.getMyCashier(i).suspend();
            }
        }
    }

    public void updateServed(int queue)
    {
        
       int served =  myModel.getMyServiceQueues()[queue].getNumberCustomersServedSoFar();
        myView.setMyTotalServed("" + served, queue);
    }
    
    public void updateOverflow(int queue)
    {     
       int overflow = myModel.getOverflowCount();
       
           myView.setMyTotalOverflow("" + overflow, queue);
       
    }
    public void updateOverallStats()
    {
        String newLine = System.getProperty("line.separator");
        
        myView.setOverallStats("Total Wait:" + (myModel.getTotalWait()/1000) + " sec" + newLine + "Total Service Time: " + (myModel.getTotalService()/1000) 
                + " sec" + newLine + "Average Wait: " + (myModel.getAverageWait()/1000) +" sec" + newLine + "Average Service Time: " + (myModel.getAverageService()/1000) + " sec");
    }
    public void updateTellerInfo(int queue)
    {
        String newLine = System.getProperty("line.separator");
        myView.setTellerInfo("Total Wait:" + myModel.getQueueTotalWait(queue) + newLine + "Total Service Time: " + myModel.getQueueTotalService(queue)
                + newLine + "Average Wait: " + myModel.getQueueAverageWait(queue) + newLine + "Average Service Time: " + myModel.getQueueAverageService(queue), queue);
    }
    public static void main(String[] args)
    {
        new SimulationController();
    }
    
}

