package controllers;

/**
 * Basic Ribbon gate prototype implementation.
 * @author Nepochatov Stanislav
 */
public final class MiniGate {

	/**
	 * Gate successful initiation flag.
	 */
	public static Boolean isGateInited = false;
	
	/**
	 * Gate lock (for multithread sync).
	 */
	private static Object gateLock = new Object();
	
	/**
	 * Socket for gate.
	 */
	private static java.net.Socket gateSocket;
	
	/**
	 * Reader for incoming messages.
	 */
	private static java.io.BufferedReader gateInput;
	
	/**
	 * Writer for send messages.
	 */
	private static java.io.PrintWriter gateOutput;
	
	/**
	 * Initialization block.
	 */
	static {
		try {
			gateSocket = new java.net.Socket("127.0.0.1", 3003);
			gateInput = new java.io.BufferedReader(new java.io.InputStreamReader(gateSocket.getInputStream()));
			gateOutput = new java.io.PrintWriter(gateSocket.getOutputStream());
			isGateInited = true;
		} catch (java.io.IOException ex) {
			
		}
	}
	
}
