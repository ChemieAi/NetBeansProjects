// Fig. 23.4: PrintTask.java
// PrintTask class sleeps for a random time from 0 to 5 seconds
import java.util.Random;

public class PrintTask implements Runnable 
{
   private int sleepTime; // random sleep time for thread
   private String threadName; // name of thread
   private static Random generator = new Random();
    
   // assign name to thread
   public PrintTask( String name )
   {
      threadName = name; // set name of thread
        
      // pick random sleep time between 0 and 5 seconds
      sleepTime = generator.nextInt( 5000 );
   } // end PrintTask constructor
    
   // method run is the code to be executed by new thread
   public void run()
   {
      try // put thread to sleep for sleepTime amount of time 
      {
         System.out.printf( "%s going to sleep for %d milliseconds.\n", 
            threadName, sleepTime );
            
         Thread.sleep( sleepTime ); // put thread to sleep
      } // end try        
      // if thread interrupted while sleeping, print stack trace
      catch ( InterruptedException exception )
      {
         exception.printStackTrace();
      } // end catch
        
      // print thread name
      System.out.printf( "%s done sleeping\n", threadName );
   } // end method run
} // end class PrintTask

/**************************************************************************
 * (C) Copyright 1992-2005 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/