# play-event-counter
Stores fresh event counts as a function of time for a maximum of MAX_TIME seconds.  Returns fresh event counts that occurred within a user specified time from current time.

1. Install Play Framework <https://www.playframework.com/documentation/2.6.x/Requirements>
2. sbt run
3. Create Event: http://localhost:9000/event
4. Get Event Count: http://localhost:9000/count
