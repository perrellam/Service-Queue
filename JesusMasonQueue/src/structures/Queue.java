/**
 * Queue class that uses a Linked list to hold 
 * data in the form of a queue.
 * 
 * @author jesusargel
 * @author perrellamason
 */
package structures;

import java.util.LinkedList;

public class Queue<T>
{
    //Properties
	private LinkedList<T> myData;
	
	//Constructor
	public Queue()
	{
		myData = new LinkedList<T>();
	}
	
	/**
	 * Inserts a new element in the back of the queue
	 * 
	 * @param o
	 * @return boolean
	 */
	public boolean offer(T o)
	{
		return myData.offer(o);
	}
	
	/**
	 * Takes out the first element in the queue
	 * 
	 * @return T
	 */
	public T poll()
	{
		return myData.poll();
	}
	
	/**
	 * Returns the first element of the queue without
	 * modifying the queue
	 * 
	 * @return T
	 */
	public T peek()
	{
		return myData.peek();
	}
	
	/**
	 * Checks if the queue is empty
	 * 
	 * @return boolean
	 */
	public boolean empty()
	{
		return myData.isEmpty();
	}
	
	/**
	 * Checks the amount of elements in the queue
	 * 
	 * @return int
	 */
	public int size()
	{
	    return myData.size();
	}

}
