

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gateway.bean.BlackBoardBean;
import jade.core.Profile;
import jade.util.leap.Properties;
import jade.wrapper.ControllerException;
import jade.wrapper.gateway.JadeGateway;

public class Interface extends HttpServlet 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7764740926466198393L;
	
	boolean sicurezza = false;
	boolean boxAuto = false;
	boolean tempAuto = false;
	int tempVal = 20;
	double currentTemp = 20;
	boolean antiIncendio = false;
	boolean luceSalone = false;
	boolean luceCamera = true;
	boolean luceBagno = true;
	boolean luceCucina = false;
	boolean tapparellaSalone = false;
	boolean tapparellaCamera = true;
	boolean tapparellaBagno = false;
	boolean tapparellaCucina = true;
	boolean luceAutoSalone = false;
	int valLuceAutoSalone = 1;
	boolean luceAutoCamera = false;
	int valLuceAutoCamera = 1;
	boolean luceAutoBagno = false;
	int valLuceAutoBagno = 1;
	boolean luceAutoCucina = false;
	int valLuceAutoCucina = 1;
	boolean finestraSalone = false;
	boolean finestraCamera = false;
	boolean finestraBagno = false;
	boolean finestraCucina = false;
	boolean ventilatoreSalone = false;
	boolean ventilatoreCamera = false;
	boolean ventilatoreBagno = false;
	boolean ventilatoreCucina = false;

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		
		String message;
		String richiesta = request.getRequestURI();
		richiesta = richiesta.replace("/domHomeServlet/", "");
		System.out.println("richiesta = " + richiesta);
		if (richiesta.equals("set-security-general-null") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sicurezza = Boolean.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + sicurezza + "-----------");
		}
		
		if (richiesta.equals("get-security") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(sicurezza);
			out.print(message);
		}
		
		if (richiesta.equals("set-garageDoor-general-null") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			boxAuto = Boolean.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + boxAuto + "-----------");
		}
		
		if (richiesta.equals("get-garageDoor") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(boxAuto);
			out.print(message);
		}
		
		if (richiesta.equals("set-autoTemp-general-null") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempAuto = Boolean.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + tempAuto + "-----------");
		}
		
		if (richiesta.equals("get-autoTemp") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			if (tempAuto)
				message = Integer.toString(tempVal);
			else
				message = Boolean.toString(tempAuto);
			out.print(message);
		}
		
		if (richiesta.equals("get-currentTemp") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = String.valueOf(currentTemp);
			out.print(message);
			String messageToAgent = "set-currentTemp-hall-null"; //compatibilita'
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			currentTemp = Float.parseFloat(board.getMessage());
		}
		
		if (richiesta.equals("set-temp-general-10") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempVal = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + tempVal + "-----------");
		}
		
		if (richiesta.equals("set-temp-general-11") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempVal = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + tempVal + "-----------");
		}
		
		if (richiesta.equals("set-temp-general-12") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempVal = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + tempVal + "-----------");
		}
		
		if (richiesta.equals("set-temp-general-13") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempVal = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + tempVal + "-----------");
		}
		
		if (richiesta.equals("set-temp-general-14") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempVal = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + tempVal + "-----------");
		}
		
		if (richiesta.equals("set-temp-general-15") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempVal = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + tempVal + "-----------");
		}
		
		if (richiesta.equals("set-temp-general-16") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempVal = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + tempVal + "-----------");
		}
		
		if (richiesta.equals("set-temp-general-17") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempVal = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + tempVal + "-----------");
		}
		
		if (richiesta.equals("set-temp-general-18") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempVal = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + tempVal + "-----------");
		}
		
		if (richiesta.equals("set-temp-general-19") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempVal = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + tempVal + "-----------");
		}
		
		if (richiesta.equals("set-temp-general-20") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempVal = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + tempVal + "-----------");
		}
		
		if (richiesta.equals("set-temp-general-21") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempVal = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + tempVal + "-----------");
		}
		
		if (richiesta.equals("set-temp-general-22") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempVal = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + tempVal + "-----------");
		}
		
		if (richiesta.equals("set-temp-general-23") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempVal = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + tempVal + "-----------");
		}
		
		if (richiesta.equals("set-temp-general-24") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempVal = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + tempVal + "-----------");
		}
		
		if (richiesta.equals("set-temp-general-25") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempVal = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + tempVal + "-----------");
		}
		
		if (richiesta.equals("set-temp-general-26") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempVal = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + tempVal + "-----------");
		}
		
		if (richiesta.equals("set-temp-general-27") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempVal = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + tempVal + "-----------");
		}
		
		if (richiesta.equals("set-temp-general-28") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempVal = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + tempVal + "-----------");
		}
		
		if (richiesta.equals("set-temp-general-29") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempVal = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + tempVal + "-----------");
		}
		
		if (richiesta.equals("set-temp-general-30") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempVal = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + tempVal + "-----------");
		}
		
		if (richiesta.equals("set-fireSystem-general-null") == true)
		{

			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			antiIncendio = Boolean.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + antiIncendio + "-----------");
		}
		
		if (richiesta.equals("get-fireSystem") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(antiIncendio);
			out.print(message);
		}
		
		if (richiesta.equals("set-light-hall-null") == true)
		{

			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			luceSalone = Boolean.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + luceSalone + "-----------");
		}
		
		if (richiesta.equals("get-light-hall") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(luceSalone);
			out.print(message);
		}
		
		if (richiesta.equals("set-light-bedroom-null") == true)
		{

			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			luceCamera = Boolean.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + luceCamera + "-----------");
		}
		
		if (richiesta.equals("get-light-bedroom") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(luceCamera);
			out.print(message);
		}
		
		if (richiesta.equals("set-light-bathroom-null") == true)
		{

			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			luceBagno = Boolean.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + luceBagno + "-----------");
		}
		
		if (richiesta.equals("get-light-bathroom") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(luceBagno);
			out.print(message);
		}
		
		if (richiesta.equals("set-light-kitchen-null") == true)
		{

			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			luceCucina = Boolean.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + luceCucina + "-----------");
		}
		
		if (richiesta.equals("get-light-kitchen") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(luceCucina);
			out.print(message);
		}
		
		if (richiesta.equals("set-shutter-hall-null") == true)
		{

			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tapparellaSalone = Boolean.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + tapparellaSalone + "-----------");
		}
		
		if (richiesta.equals("get-shutter-hall") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(tapparellaSalone);
			out.print(message);
		}
		
		if (richiesta.equals("set-shutter-bedroom-null") == true)
		{

			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tapparellaCamera = Boolean.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + tapparellaCamera + "-----------");
		}
		
		if (richiesta.equals("get-shutter-bedroom") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(tapparellaCamera);
			out.print(message);
		}
		
		if (richiesta.equals("set-shutter-bathroom-null") == true)
		{

			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tapparellaBagno = Boolean.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + tapparellaBagno + "-----------");
		}
		
		if (richiesta.equals("get-shutter-bathroom") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(tapparellaBagno);
			out.print(message);
		}
		
		if (richiesta.equals("set-shutter-kitchen-null") == true)
		{

			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tapparellaCucina = Boolean.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + tapparellaCucina + "-----------");
		}
		
		if (richiesta.equals("get-shutter-kitchen") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(tapparellaCucina);
			out.print(message);
		}
		
		if (richiesta.equals("set-autoLightning-hall-null") == true)
		{

			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			luceAutoSalone = Boolean.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + luceAutoSalone + "-----------");
		}
		
		if (richiesta.equals("set-autoLightning-bedroom-null") == true)
		{

			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			luceAutoCamera = Boolean.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + luceAutoCamera + "-----------");
		}
		
		if (richiesta.equals("set-autoLightning-bathroom-null") == true)
		{

			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			luceAutoBagno = Boolean.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + luceAutoBagno + "-----------");
		}
		
		if (richiesta.equals("set-autoLightning-kitchen-null") == true)
		{

			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			luceAutoCucina = Boolean.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + luceAutoCucina + "-----------");
		}
		
		if (richiesta.equals("set-lightning-hall-1") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoSalone = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoSalone + "-----------");
		}
		
		if (richiesta.equals("set-lightning-hall-2") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoSalone = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoSalone + "-----------");
		}
		
		if (richiesta.equals("set-lightning-hall-3") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoSalone = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoSalone + "-----------");
		}
		
		if (richiesta.equals("set-lightning-hall-4") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoSalone = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoSalone + "-----------");
		}
		
		if (richiesta.equals("set-lightning-hall-5") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoSalone = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoSalone + "-----------");
		}
		
		if (richiesta.equals("set-lightning-hall-6") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoSalone = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoSalone + "-----------");
		}
		
		if (richiesta.equals("set-lightning-hall-7") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoSalone = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoSalone + "-----------");
		}
		
		if (richiesta.equals("set-lightning-hall-8") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoSalone = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoSalone + "-----------");
		}
		
		if (richiesta.equals("set-lightning-hall-9") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoSalone = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoSalone + "-----------");
		}
		
		if (richiesta.equals("set-lightning-hall-10") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoSalone = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoSalone + "-----------");
		}
		
		if (richiesta.equals("set-lightning-bedroom-1") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoCamera = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoCamera + "-----------");
		}
		
		if (richiesta.equals("set-lightning-bedroom-2") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoCamera = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoCamera + "-----------");
		}
		
		if (richiesta.equals("set-lightning-bedroom-3") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoCamera = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoCamera + "-----------");
		}
		
		if (richiesta.equals("set-lightning-bedroom-4") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoCamera = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoCamera + "-----------");
		}
		
		if (richiesta.equals("set-lightning-bedroom-5") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoCamera = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoCamera + "-----------");
		}
		
		if (richiesta.equals("set-lightning-bedroom-6") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoCamera = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoCamera + "-----------");
		}
		
		if (richiesta.equals("set-lightning-bedroom-7") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoCamera = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoCamera + "-----------");
		}
		
		if (richiesta.equals("set-lightning-bedroom-8") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoCamera = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoCamera + "-----------");
		}
		
		if (richiesta.equals("set-lightning-bedroom-9") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoCamera = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoCamera + "-----------");
		}
		
		if (richiesta.equals("set-lightning-bedroom-10") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoCamera = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoCamera + "-----------");
		}
		
		if (richiesta.equals("set-lightning-bathroom-1") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoBagno = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoBagno + "-----------");
		}
		
		if (richiesta.equals("set-lightning-bathroom-2") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoBagno = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoBagno + "-----------");
		}
		
		if (richiesta.equals("set-lightning-bathroom-3") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoBagno = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoBagno + "-----------");
		}
		
		if (richiesta.equals("set-lightning-bathroom-4") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoBagno = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoBagno + "-----------");
		}
		
		if (richiesta.equals("set-lightning-bathroom-5") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoBagno = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoBagno + "-----------");
		}
		
		if (richiesta.equals("set-lightning-bathroom-6") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoBagno = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoBagno + "-----------");
		}
		
		if (richiesta.equals("set-lightning-bathroom-7") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoBagno = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoBagno + "-----------");
		}
		
		if (richiesta.equals("set-lightning-bathroom-8") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoBagno = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoBagno + "-----------");
		}
		
		if (richiesta.equals("set-lightning-bathroom-9") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoBagno = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoBagno + "-----------");
		}
		
		if (richiesta.equals("set-lightning-bathroom-10") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoBagno = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoBagno + "-----------");
		}
		
		if (richiesta.equals("set-lightning-kitchen-1") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoCucina = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoCucina + "-----------");
		}
		
		if (richiesta.equals("set-lightning-kitchen-2") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoCucina = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoCucina + "-----------");
		}
		
		if (richiesta.equals("set-lightning-kitchen-3") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoCucina = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoCucina + "-----------");
		}
		
		if (richiesta.equals("set-lightning-kitchen-4") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoCucina = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoCucina + "-----------");
		}
		
		if (richiesta.equals("set-lightning-kitchen-5") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoCucina = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoCucina + "-----------");
		}
		
		if (richiesta.equals("set-lightning-kitchen-6") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoCucina = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoCucina + "-----------");
		}
		
		if (richiesta.equals("set-lightning-kitchen-7") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoCucina = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoCucina + "-----------");
		}
		
		if (richiesta.equals("set-lightning-kitchen-8") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoCucina = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoCucina + "-----------");
		}
		
		if (richiesta.equals("set-lightning-kitchen-9") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoCucina = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoCucina + "-----------");
		}
		
		if (richiesta.equals("set-lightning-kitchen-10") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valLuceAutoCucina = Integer.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + valLuceAutoCucina + "-----------");
		}
		
		if (richiesta.equals("set-window-hall-null") == true)
		{

			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finestraSalone = Boolean.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + finestraSalone + "-----------");
		}
		
		if (richiesta.equals("set-window-bedroom-null") == true)
		{

			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finestraCamera = Boolean.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + finestraCamera + "-----------");
		}
		
		if (richiesta.equals("set-window-bathroom-null") == true)
		{

			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finestraBagno = Boolean.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + finestraBagno + "-----------");
		}
		
		if (richiesta.equals("set-window-kitchen-null") == true)
		{

			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finestraCucina = Boolean.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + finestraCucina + "-----------");
		}
		
		if (richiesta.equals("get-autoLightning-hall") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			if (luceAutoSalone)
				message = Integer.toString(valLuceAutoSalone);
			else
				message = Boolean.toString(luceAutoSalone);
			out.print(message);
		}
		
		if (richiesta.equals("get-autoLightning-bedroom") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			if (luceAutoCamera)
				message = Integer.toString(valLuceAutoCamera);
			else
				message = Boolean.toString(luceAutoCamera);
			out.print(message);
		}
		
		if (richiesta.equals("get-autoLightning-bathroom") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			if (luceAutoBagno)
				message = Integer.toString(valLuceAutoBagno);
			else
				message = Boolean.toString(luceAutoBagno);
			out.print(message);
		}
		
		if (richiesta.equals("get-autoLightning-kitchen") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			if (luceAutoCucina)
				message = Integer.toString(valLuceAutoCucina);
			else
				message = Boolean.toString(luceAutoCucina);
			out.print(message);
		}
		
		if (richiesta.equals("get-window-hall") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(finestraSalone);
			out.print(message);
		}
		
		if (richiesta.equals("get-window-bedroom") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(finestraCamera);
			out.print(message);
		}
		
		if (richiesta.equals("get-window-bathroom") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(finestraBagno);
			out.print(message);
		}
		
		if (richiesta.equals("get-window-kitchen") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(finestraCucina);
			out.print(message);
		}
		
		if (richiesta.equals("set-fan-hall-null") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ventilatoreSalone = Boolean.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + ventilatoreSalone + "-----------");
		}
		
		if (richiesta.equals("set-fan-bedroom-null") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ventilatoreCamera = Boolean.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + ventilatoreCamera + "-----------");
		}
		
		if (richiesta.equals("set-fan-bathroom-null") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ventilatoreBagno = Boolean.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + ventilatoreBagno + "-----------");
		}
		
		if (richiesta.equals("set-fan-kitchen-null") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
			String messageToAgent = richiesta;
			BlackBoardBean board = new BlackBoardBean(messageToAgent);
			try {
				JadeGateway.execute(board);
			} catch (ControllerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ventilatoreCucina = Boolean.valueOf(board.getMessage());
			System.out.println("risposta gateway : " + ventilatoreCucina + "-----------");
		}
		
		if (richiesta.equals("get-fan-hall") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(ventilatoreSalone);
			out.print(message);
		}
		
		if (richiesta.equals("get-fan-bedroom") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(ventilatoreCamera);
			out.print(message);
		}
		
		if (richiesta.equals("get-fan-bathroom") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(ventilatoreBagno);
			out.print(message);
		}
		
		if (richiesta.equals("get-fan-kitchen") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(ventilatoreCucina);
			out.print(message);
		}
	}
	
	
	public void init(ServletConfig config) throws ServletException {
	    super.init(config);
		
		Properties pp = new Properties();
		pp.setProperty(Profile.MAIN_HOST, "localhost");
		pp.setProperty(Profile.MAIN_PORT, "1200");
		JadeGateway.init("gateway.agent.MyGateWayAgent", pp);		
	
	}

}
