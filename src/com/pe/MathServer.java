package com.pe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MathServer {

	private ServerSocket serverSocket = null;
	private static String FUNCTIONS = "function factorial  (n) { if (n == 0) {  return 1;  } return n * factorial (n-1);  }";

	// function factorial  (n) { if (n == 0) {  return 1;  } return n * factorial (n-1);  }
	// function mcd (a, b) { if (b==0) return a; else return mcd(b, a % b);}
	
	
	/**
	 * Metodo RUN
	 * @throws IOException
	 */
	public void run() throws IOException {
		try {
			serverSocket = new ServerSocket(10007);
		} catch (IOException e) {
			System.err.println("No se encuentra disponible el puerto: 10007.");
			System.exit(1);
		}

		Socket clientSocket = null;
		System.out.println("Esperando conexion.....");

		try {
			clientSocket = serverSocket.accept();
		} catch (IOException e) {
			System.err.println("Error al aceptar socket ");
			System.exit(1);
		}

		System.out.println("Conexion establecida");
		System.out.println("Esperando recibir informacion de entrada .....");

		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			System.out.println("Server: " + inputLine);

			if (inputLine.equalsIgnoreCase("0000")) //salimos
			{	
				clientSocket.close();
				System.out.println("Cliente desconectado");
			   	break;
		    }
			
			try {
				out.println(calc(inputLine));
			} catch (ScriptException e) {
				System.out.println("La expresión es incorrecta, verifique");
			}
		}
		out.close();
		in.close();
		clientSocket.close();
		serverSocket.close();
	}

	/**
	 * Metodo de calculo
	 * @param expression
	 *            expresion matematica a calcular ejemplo : 10+2/2
	 * @return Valor calculado
	 * @throws ScriptException
	 */
	public Object calc(String expression) throws ScriptException {
		// Creando el script engine manager
		ScriptEngineManager factory = new ScriptEngineManager();
		// Creando el JavaScript engine
		ScriptEngine engine = factory.getEngineByName("JavaScript");
		// Evaluando la expression en JavaScript
		
		Object obj = engine.eval(FUNCTIONS + expression);
		System.out.println(obj);
		return obj;
	}

	public static void main(String[] args) throws IOException {
		new MathServer().run();
	}
}
