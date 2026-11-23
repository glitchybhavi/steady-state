package util;
/**
* CLASS: IdGenerator
* WHAT IS THIS CLASS?: Generates unique sequential run IDs (RUN-1, RUN-2).
*/
public class IdGenerator {
private static int counter = 0;
public static synchronized String nextRunId() {
counter++;
return "RUN-" + counter;
}
}