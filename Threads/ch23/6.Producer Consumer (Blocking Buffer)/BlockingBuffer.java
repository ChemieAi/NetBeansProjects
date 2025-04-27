// Fig. 23.15: BlockingBuffer.java
// Class synchronizes access to a blocking buffer.
import java.util.concurrent.ArrayBlockingQueue;

public class BlockingBuffer implements Buffer
{
   private ArrayBlockingQueue<Integer> buffer;

   public BlockingBuffer()
   {
      buffer = new ArrayBlockingQueue<Integer>( 3 );
   } // end BlockingBuffer constructor
   
   // place value into buffer
   public void set( int value )
   {
      try
      {
         buffer.put( value ); // place value in circular buffer
         System.out.printf( "%s%2d\t%s%d\n", "Producer writes ", value,
            "Buffers occupied: ", buffer.size() );
      } // end try
      catch ( Exception exception )
      {
         exception.printStackTrace();
      } // end catch
   } // end method set

   // return value from buffer
   public int get()
   {
      int readValue = 0; // initialize value read from buffer

      try
      {
         readValue = buffer.take(); // remove value from circular buffer
         System.out.printf( "%s %2d\t%s%d\n", "Consumer reads ", readValue, 
            "Buffers occupied: ", buffer.size() );
      } // end try
      catch ( Exception exception )
      {
         exception.printStackTrace();
      } // end catch

      return readValue;
   } // end method get
} // end class BlockingBuffer


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