package client.view;

/**
 * Defines the commands that the client can use
 */
public enum Command {
    /**
     * Starts a new game.
     */
    START,
    /**
     * Establish a connection to the server. The first parameter is IP address (or host name), the
     * second is port number.
     */
    CONNECT,
    /**
     * Leave the game
     */
    QUIT,
    /**
     * Guessing a new letter in the word or the whole word
     */
    GUESS,
    /**
     * Gets the current game info for the client.
     */
    GAMEINFO,
    /**
     * No command was specified. The user will be advised to use a known command.
     */
    NO_COMMAND
    
}