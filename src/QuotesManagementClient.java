import java.util.*;
import java.io.*;
import java.net.*;

public class QuotesManagementClient {

	private static final String SERVER_HOST = "ec2-3-122-223-83.eu-central-1.compute.amazonaws.com";
	private static final int SERVER_MGT_PORT = 20001;

	public static void main(String[] args) {

		if (args.length == 0 || args[0].equals("-help")) {
			help();
		} 
		/* 
		else if (args[0].equals("-stop")) {
			if (args.length != 1) {
				help();
			} else {
				Socket cskt = null;
				try {
					cskt = new Socket(SERVER_HOST, SERVER_MGT_PORT);
					ObjectOutputStream oos = new ObjectOutputStream(cskt.getOutputStream());

					oos.writeObject(new String[] { "stop" });
					oos.close();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (cskt != null)
						try {
							cskt.close();
						} catch (IOException e) {
							System.err.println("Quotes Management Client: can not close socket to server.");
							e.printStackTrace();
						}
				}
			}
		} 
		*/
		else if (args[0].equals("-list")) {
			if (args.length != 1) {
				help();
			} else {
				Socket cskt = null;
				try {
					cskt = new Socket(SERVER_HOST, SERVER_MGT_PORT);
					ObjectOutputStream oos = new ObjectOutputStream(cskt.getOutputStream());
					ObjectInputStream ois = new ObjectInputStream(cskt.getInputStream());
					
					oos.writeObject(new String[] { "list" });
					ArrayList<String[]> al = (ArrayList<String[]>) ois.readObject();
					for (String[] tuple : al)
						System.out.println(tuple[0] + ": " + tuple[1] + " [" + tuple[2] + "]");
					ois.close();
					oos.close();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} finally {
					if (cskt != null)
						try {
							cskt.close();
						} catch (IOException e) {
							System.err.println("Quotes Management Client: can not close socket to server.");
							e.printStackTrace();
						}
				}
			}
		} else if (args[0].equals("-del")) {
			if (args.length != 2) {
				help();
			} else {
				Socket cskt = null;
				try {
					int deln = Integer.valueOf(args[1]);

					cskt = new Socket(SERVER_HOST, SERVER_MGT_PORT);
					ObjectOutputStream oos = new ObjectOutputStream(cskt.getOutputStream());

					oos.writeObject(new String[] { "del", args[1] });
					oos.close();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (NumberFormatException e) {
					System.err.println("Invalid number.");
					help();
				} finally {
					if (cskt != null)
						try {
							cskt.close();
						} catch (IOException e) {
							System.err.println("Quotes Management Client: can not close socket to server.");
							e.printStackTrace();
						}
				}
			}
		} else if (args[0].equals("-add")) {
			if (args.length != 3) {
				help();
			} else {
				Socket cskt = null;
				try {
					cskt = new Socket(SERVER_HOST, SERVER_MGT_PORT);
					ObjectOutputStream oos = new ObjectOutputStream(cskt.getOutputStream());

					oos.writeObject(new String[] { "add", args[1], args[2] });
					oos.close();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (cskt != null)
						try {
							cskt.close();
						} catch (IOException e) {
							System.err.println("Quotes Management Client: can not close socket to server.");
							e.printStackTrace();
						}
				}
			}
		} else
			help();
	}

	private static void help() {
		System.out.println("Usage:");
		System.out.println("    java QuotesManagementClient -help");
//		System.out.println("    java QuotesManagementClient -stop");
		System.out.println("    java QuotesManagementClient -list");
		System.out.println("    java QuotesManagementClient -del <num>");
		System.out.println("    java QuotesManagementClient -add \"<quote>\" \"<author>\"");
	}
}
