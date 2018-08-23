/**
 * SimulationView class
 * 
 * View for the MVC
 * 
 * @author jesusargel
 * @author perrellamason
 * 
 */
package MVC;

import java.awt.*;
import java.lang.reflect.Method;
import javax.swing.*;

@SuppressWarnings("serial")
public class SimulationView extends JFrame
{
    // Constants
    private final int TELLER_WIDTH = 90;
    private final int TELLER_HEIGHT = 85;
    private final String TELLER_IMG = "images/squid.png";
    private final String FACE_IMG = "images/patrick.png";
    private final int COUNTER_BOX_WIDTH = 50;
    private final int COUNTER_BOX_HEIGHT = 25;
    private final int CUSTOMER_WIDTH = 80;
    private final int CUSTOMER_HEIGHT = 60;
    private final int INFO_BOX_WIDTH = 95;
    private final int INFO_BOX_HEIGHT = 85;
    private final int ROW_1 = 350;
    private final int ROW_2 = 450;
    private final int ROW_TOP = 10;
    private final int MAX_PEOPLE_IN_LINE = 9;
    private final int MAX_NUM_OF_TELLERS = 5;

    // Data Members
    private Image myScaledImage;
    private SimulationController myController;
    private Container myContentPane;
    private JLabel[] myTotalServed;
    private JLabel[] myTotalOverflow;
    private JTextArea[] myQueuesInfo;
    private JTextArea myOverallStats;
    private ButtonListener myStartPauseListener;
    //private ButtonListener myTellerInfoListeners;
    private JLabel[][] myCustomer;
    private JButton myStartPauseButton;
    private JLabel[] myTeller;
    private JPanel mySimPanel;
    private JPanel myOptionsPanel;
    private JLabel myGenTime;
    private JLabel myNumCustomers;
    private JLabel myNumberTellers;
    private JLabel myMaxServiceTime;
    private JTextArea myTimeGeneratedText;
    private JTextArea myNumCustomerText;
    private JTextArea myNumTellerText;
    private JTextArea myMaxTimeText;
    private JScrollPane myScroll;
    private JScrollPane myTellerInfoScroll;
    private JTextArea myInfo;
    

    // Constructor

    /**
     * Constructor that creates the view.
     * 
     * @param controller
     *            the SimulationController that gives function to the buttons.
     */
    public SimulationView(SimulationController controller)
    {
        Image face = Toolkit.getDefaultToolkit().getImage(FACE_IMG);
        myScaledImage = face.getScaledInstance(CUSTOMER_WIDTH, CUSTOMER_HEIGHT, Image.SCALE_SMOOTH);

        myController = controller;

        // Start/Pause Button
        myStartPauseButton = new JButton("Pause");

        this.associateListeners(myController);

        // Frame info
        this.setSize(650, 600);
        this.setLocation(100, 100);
        this.setTitle("Krusty Krab");
        this.setResizable(false);

        myContentPane = getContentPane();
        myContentPane.setLayout(new BorderLayout());
        
        

        // Sim Panel
        mySimPanel = new JPanel();
        mySimPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        mySimPanel.setLayout(null);

        
        // Customer Served Counter
        myTotalServed = new JLabel[MAX_NUM_OF_TELLERS];
        myTotalOverflow = new JLabel[MAX_NUM_OF_TELLERS];
        myQueuesInfo = new JTextArea[MAX_NUM_OF_TELLERS];
        
        for (int i = 0; i < myTotalServed.length; i++)
        {
            myTotalServed[i] = new JLabel("0");
            myTotalServed[i].setSize(COUNTER_BOX_WIDTH, COUNTER_BOX_HEIGHT);
            myTotalServed[i].setLocation(35 + (TELLER_WIDTH * i), ROW_2);
            myTotalServed[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            myTotalServed[i].setBackground(Color.WHITE);
            myTotalServed[i].setOpaque(true); 
            
            myTotalOverflow[i] = new JLabel("+0");
            myTotalOverflow[i].setSize(COUNTER_BOX_WIDTH, COUNTER_BOX_HEIGHT);
            myTotalOverflow[i].setLocation(35 + (TELLER_WIDTH * i), ROW_TOP);
            myTotalOverflow[i].setBackground(Color.WHITE);
            myTotalOverflow[i].setOpaque(true);            
            myTotalOverflow[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
         /*
            myQueuesInfo[i] = new JTextArea("Teller info " + (i+1));
            myQueuesInfo[i].setSize(INFO_BOX_WIDTH, INFO_BOX_HEIGHT);
            myQueuesInfo[i].setLocation(5 + (100 * i), 490);
            myQueuesInfo[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            
          //  myQueueScrolls[i] = new JScrollPane(myQueuesInfo[i]);
           // myQueueScrolls[i].setBounds(0, 0, 100, 100);


            mySimPanel.add(myQueuesInfo[i]);
            */
            mySimPanel.add(myTotalOverflow[i]);
            mySimPanel.add(myTotalServed[i]);

        }

        // Teller locations
        myTeller = new JLabel[MAX_NUM_OF_TELLERS];

        for (int i = 0; i < MAX_NUM_OF_TELLERS; i++)
        {
            myTeller[i] = new JLabel(new ImageIcon(TELLER_IMG));
            myTeller[i].setSize(TELLER_WIDTH, TELLER_HEIGHT);
            myTeller[i].setLocation(20 + (TELLER_WIDTH * i), ROW_1);
            myTeller[i].setVisible(true);
            mySimPanel.add(myTeller[i]);
        }

        // Customer Lines
        myCustomer = new JLabel[MAX_NUM_OF_TELLERS][MAX_PEOPLE_IN_LINE];
        for (int i = 0; i < MAX_NUM_OF_TELLERS; i++)
        {
            for (int j = 0; j < MAX_PEOPLE_IN_LINE; j++)
            {
                myCustomer[i][j] = new JLabel();
                myCustomer[i][j].setSize(CUSTOMER_WIDTH, CUSTOMER_HEIGHT);
                myCustomer[i][j].setLocation(40 + (CUSTOMER_WIDTH * i), 300 - (35 * j));
                myCustomer[i][j].setVisible(true);
                mySimPanel.add(myCustomer[i][j]);
            }
        }

        // Background
        JLabel bg;
        bg = new JLabel(new ImageIcon("images/background.jpg"));
        bg.setSize(520, 480);
        bg.setLocation(0, 0);
        mySimPanel.add(bg);
        
        //Overall Stats area
        myOverallStats = new JTextArea("Overall Stats");
        myScroll = new JScrollPane(myOverallStats);
        myScroll.setBounds(0, 0, 100, 100);
        
      //Teller Stats area
        myInfo = new JTextArea("Click a teller to view its stats");
        myInfo.setSize(INFO_BOX_WIDTH, INFO_BOX_HEIGHT);
        myInfo.setLocation(10, 490);
        myInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        myTellerInfoScroll = new JScrollPane(myInfo);
        myTellerInfoScroll.setBounds(10, 490, 500, 100);
        
        
        //Labels for options
        myGenTime = new JLabel("Generate Time: ");
        myGenTime.setBackground(Color.gray);
        myGenTime.setOpaque(true);
        myNumCustomers = new JLabel("Number Customers: ");
        myNumCustomers.setBackground(Color.gray);
        myNumCustomers.setOpaque(true);
        myNumberTellers = new JLabel("Number Tellers: ");
        myNumberTellers.setBackground(Color.gray);
        myNumberTellers.setOpaque(true);
        myMaxServiceTime = new JLabel("Max Time: ");
        myMaxServiceTime.setBackground(Color.gray);
        myMaxServiceTime.setOpaque(true);

        //Options
        myTimeGeneratedText = new JTextArea();
        myNumCustomerText = new JTextArea();
        myNumTellerText = new JTextArea();
        myMaxTimeText = new JTextArea();

        myOptionsPanel = new JPanel();
        myOptionsPanel.setLayout(new GridLayout(10,1));
        
        myContentPane.add(mySimPanel, BorderLayout.CENTER);
        myContentPane.add(myOptionsPanel, BorderLayout.EAST);
        myOptionsPanel.add(myScroll);
        myOverallStats.setBorder(BorderFactory.createLineBorder(Color.BLACK));        
        myOptionsPanel.add(myGenTime);
        myOptionsPanel.add(myTimeGeneratedText);
        myOptionsPanel.add(myNumCustomers);
        myOptionsPanel.add(myNumCustomerText);
        myOptionsPanel.add(myNumberTellers);
        myOptionsPanel.add(myNumTellerText);
        myOptionsPanel.add(myMaxServiceTime);
        myOptionsPanel.add(myMaxTimeText);
        myOptionsPanel.add(myStartPauseButton);
        mySimPanel.add(myTellerInfoScroll);
        
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //////////////////////////////////////////
    // Methods //
    //////////////////////////////////////////

    public void changeStartPause()
    {
        if (myStartPauseButton.getText().equals("Start"))
        {
            myStartPauseButton.setText("Pause");
        } else
        {
            myStartPauseButton.setText("Start");
        }
    }

    public void setCustomersInLine(int queue, int numInLine)
    {
       // System.out.println("Queue: " + queue + "  numInLine = " + numInLine);

        myTeller[queue].setIcon(new ImageIcon(TELLER_IMG));
        for (int i = 0; i < MAX_PEOPLE_IN_LINE; i++)
        {
            myCustomer[queue][i].setVisible(false);
        }
        try
        {
            for (int i = 0; i < numInLine && i < MAX_PEOPLE_IN_LINE; i++)
            {
                
                myCustomer[queue][i].setVisible(true);
                myCustomer[queue][i].setIcon(new ImageIcon(myScaledImage));
            }
        } catch (NullPointerException e)
        {

        }
    }

    /**
     * Associates the button with the appropriate method
     * 
     * @param controller
     *            The controller in which the method is included.
     */
    private void associateListeners(SimulationController controller)
    {
        Class<? extends SimulationController> controllerClass;
        Method startPauseMethod, tellerInfoMethod;

        controllerClass = myController.getClass();

        startPauseMethod = null;
        //tellerInfoMethod = null;

        try
        {
            //tellerInfoMethod = controllerClass.getMethod("tellerInfo", (Class<?>[]) null);
            startPauseMethod = controllerClass.getMethod("startPause", (Class<?>[]) null);
        } catch (SecurityException e)
        {
            String error;

            error = e.toString();
            System.out.println(error);
        } catch (NoSuchMethodException e)
        {
            String error;

            error = e.toString();
            System.out.println(error);
        }

        myStartPauseListener = new ButtonListener(myController, startPauseMethod, null);
       
       // myTellerInfoListeners = new ButtonListener(myController, tellerInfoMethod, null);
        
        myStartPauseButton.addMouseListener(myStartPauseListener);
    }
    
    public int getGenerateTime()
    {
        int time;
        time = Integer.parseInt(myTimeGeneratedText.getText());
        return time;
    }

    public int getNumberOfCustomers()
    {
        int numcustomers;
        numcustomers = Integer.parseInt(myNumCustomerText.getText());
        return numcustomers;
    }

    public int getNumTellers()
    {
        int numtellers;
        numtellers = Integer.parseInt(myNumTellerText.getText());

        return numtellers;
    }

    public int getMaxTime()
    {
        int time;
        time = Integer.parseInt(myMaxTimeText.getText());

        return time;
    }

    public void setTellerInfo(String text, int queue)
    {

        myQueuesInfo[queue].setText(text);

    }
   

    public void setMyTotalServed(String text, int queue)
    {
        myTotalServed[queue].setText(text);
    }

    public void setMyTotalOverflow(String text, int queue)
    {
        myTotalOverflow[queue].setText(text);

    }
    public void setOverallStats(String text)
    {
        myOverallStats.setText(text);
    }
    

}
