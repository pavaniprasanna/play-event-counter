/**
 * Event Counter Library
 *
 * Stores fresh event counts as a function of time for a maximum of MAX_TIME seconds.
 * Returns fresh event counts that occurred within a user specified time from current time.
*/

package library;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class EventCounter {

  // Ignore events that occured before MAX_TIME (seconds) from current time
  public static int MAX_TIME = 300;

  // Each list element stores the number of events in one second
  private LinkedList<Integer> list = new LinkedList<>();
  private long previousTime, currentTime, timeDelay;

 /**
  * Finds event counts in last MAX_TIME seconds from current time.
  *
  * @return total event counts in MAX_TIME
  */
  public String addGetCount() {
    refresh();
    add();
    return Integer.toString(count(MAX_TIME));
  }

  /**
   * Finds event counts in last 'input_time' (seconds) from current time
   *
   * @param   input_time seconds from current time
   * @return  event count
   */
  public int getCount(int input_time) {
    refresh();
    return count(input_time);
  }

  /**
   * Finds event counts in last 'input_time' (seconds) from current time. Assumes refreshed list.
   *
   * @param   input_time seconds from current time
   * @return  event count
   */
  private int count(int input_time) {
    int size = list.size();
    int count = 0;
    for (int index = size-1; index >= (size - input_time) && index >= 0; index--) {
      count = count + list.get(index);
    }
    return count;
  }

  /**
   * Updates list to account for periods of no events.
   */
  private void refresh() {
    currentTime = System.currentTimeMillis();
    timeDelay = (currentTime - previousTime)/1000;

    // List elements representing periods with 0 events are marked 0
    for(long x = 1; (x <= timeDelay) && (x <= MAX_TIME); x++) {
      list.add(0);
    }

    // List elements representing periods older than MAX_TIME are removed
    int size = list.size();
    for(long x = 1; ((size-x) >= MAX_TIME); x++) {
      list.remove();
    }

    previousTime = currentTime;
  }

  /**
   * Adds an event to the list. Accumulates events that occured within a second.
   */
  private void add() {
    currentTime = System.currentTimeMillis();
    timeDelay = (currentTime - previousTime)/1000;

    // Accumulate events within a second
    if (timeDelay < 1) {
      list.add(list.pollLast() + 1);
      return;
    }

    // List elements representing periods older than MAX_TIME are removed
    if(list.size() == MAX_TIME) {
      list.remove();
    }

    // Add event
    list.add(1);

    previousTime = currentTime;
  }
  
}
