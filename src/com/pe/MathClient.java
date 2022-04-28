package com.pe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MathClient {
	public static void main(String[] args) throws IOException {

		String serverHostname = new String("127.0.0.1");

		if (args.length > 0)
			serverHostname = args[0];
		System.out.println("Intentando conectar al servidor " + serverHostname + " el puerto 10007.");

		Socket echoSocket 	= null;
		PrintWriter out 	= null;
		BufferedReader in 	= null;

		try {
			echoSocket 	= new Socket(serverHostname, 10007);
			out 		= new PrintWriter(echoSocket.getOutputStream(), true);
			in 			= new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			
		} catch (UnknownHostException e) {
			System.err.println("No se a ubicado al host: " + serverHostname);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("No supo obtener entrada o salida para el servidor : " + serverHostname);
			System.exit(1);
		}

		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String userInput;

		System.out.print("Expresión: ");
		
		while ((userInput = stdIn.readLine()) != null) {
				out.println(userInput);
				System.out.println("resultado: " + in.readLine());
				System.out.print("Expresión: ");
	   }
		

		out.close();
		in.close();
		stdIn.close();
		echoSocket.close();
	}
}
