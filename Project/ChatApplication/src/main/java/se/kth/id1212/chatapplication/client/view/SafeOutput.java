package se.kth.id1212.chatapplication.client.view;

/**
 * This class creates a thread safe output, using the synchronized keyword
 */
class SafeOutput {
    /**
     * Prints the specified output
     * 
     * @param output The output string. 
     */
    synchronized void print(String output) {
        System.out.print(output);
    }

    /**
     * Prints the specified output, plus a line break
     * 
     * @param output - The output string 
     */
    synchronized void println(String output) {
        System.out.println(output);
    }
}