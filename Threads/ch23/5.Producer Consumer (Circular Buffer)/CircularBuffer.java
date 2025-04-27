// Fig. 23.13: CircularBuffer.java
// SynchronizedBuffer synchronizes access to a single shared integer.
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class CircularBuffer implements Buffer
{
   // Lock to control synchronization with this buffer   
   private Lock accessLock = new ReentrantLock();

   // conditions to control reading and writing
   private Condition canWrite = accessLock.newCondition();
   private Condition canRead = accessLock.newCondition();

   private int[] buffer = { -1, -1, -1 };

   private int occupiedBuffers = 0; // count number of buffers used
   private int writeIndex = 0; // index to write next value
   private int readIndex = 0; // index to read next value
   
   // place value into buffer
   public void set( int value )
   {
      accessLock.lock(); // lock this object
               
      // output thread information and buffer information, then wait
      try 
      {
         // while no empty locations, place thread in waiting state
         while ( occupiedBuffers == buffer.length ) 
         {
            System.out.printf( "All buffers full. Producer waits.\n" );
            canWrite.await();// await until a buffer element is free
         } // end while

         buffer[ writeIndex ] = value; // set new buffer value

         // update circular write index
         writeIndex = ( writeIndex + 1 ) % buffer.length;

         occupiedBuffers++; // one more buffer element is full
         displayState( "Producer writes " + value );
         canRead.signal(); // signal threads waiting to read from buffer
      } // end try
      catch ( InterruptedException exception )
      {
         exception.printStackTrace();
      } // end catch
      finally
      {
         accessLock.unlock(); // unlock this object
      } // end finally
   } // end method set
    
   // return value from buffer
   public int get()
   {
      int readValue = 0; // initialize value read from buffer
      accessLock.lock(); // lock this object

      // wait until buffer has data, then read value
      try 
      {
         // while no data to read, place thread in waiting state
         while ( occupiedBuffers == 0 ) 
         {
            System.out.printf( "All buffers empty. Consumer waits.\n" );
            canRead.await(); // await until a buffer element is filled
         } // end while

         readValue = buffer[ readIndex ]; // read value from buffer

         // update circular read index
         readIndex = ( readIndex + 1 ) % buffer.length;

         occupiedBuffers--; // one more buffer element is empty
         displayState( "Consumer reads " + readValue );
         canWrite.signal(); // signal threads waiting to write to buffer
      } // end try
      // if waiting thread interrupted, print stack trace
      catch ( InterruptedException exception ) 
      {
         exception.printStackTrace();
      } // end catch
      finally
      {
         accessLock.unlock(); // unlock this object
      } // end finally

      return readValue;
   } // end method get
    
   // display current operation and buffer state
   public void displayState( String operation )
   {
      // output operation and number of occupied buffers
      System.out.printf( "%s%s%d)\n%s", operation, 
         " (buffers occupied: ", occupiedBuffers, "buffers:  " );

      for ( int value : buffer )
         System.out.printf( " %2d  ", value ); // output values in buffer

      System.out.print( "\n         " );
      for ( int i = 0; i < buffer.length; i++ )
         System.out.print( "---- " );

      System.out.print( "\n         " );
      for ( int i = 0; i < buffer.length; i++ )
      {
         if ( i == writeIndex && i == readIndex )
            System.out.print( " WR" ); // both write and read index
         else if ( i == writeIndex )
            System.out.print( " W   " ); // just write index
         else if ( i == readIndex )
            System.out.print( "  R  " ); // just read index
         else
            System.out.print( "     " ); // neither index
      } // end for

      System.out.println( "\n" );
   } // end method displayState
} // end class CircularBuffer


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