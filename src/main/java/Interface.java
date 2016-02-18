

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
	boolean tempAuto = true;
	int tempVal = 18;
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
	boolean luceAutoSalone = true;
	int valLuceAutoSalone = 10;
	boolean luceAutoCamera = false;
	int valLuceAutoCamera = 4;
	boolean luceAutoBagno = false;
	int valLuceAutoBagno = 6;
	boolean luceAutoCucina = false;
	int valLuceAutoCucina = 8;
	boolean finestraSalone = false;
	boolean finestraCamera = false;
	boolean finestraBagno = false;
	boolean finestraCucina = false;

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		/*boolean sicurezza = false;
		boolean boxAuto = false;
		boolean tempAuto = true;
		int tempVal = 18;
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
		boolean luceAutoSalone = true;
		int valLuceAutoSalone = 10;
		boolean luceAutoCamera = false;
		int valLuceAutoCamera = 4;
		boolean luceAutoBagno = false;
		int valLuceAutoBagno = 6;
		boolean luceAutoCucina = false;
		int valLuceAutoCucina = 8;
		boolean finestraSalone = false;
		boolean finestraCamera = false;
		boolean finestraBagno = false;
		boolean finestraCucina = false;*/
		String message;
		String richiesta = request.getRequestURI();
		richiesta = richiesta.replace("/domHome/", "");
		System.out.println("richiesta = " + richiesta);
		if (richiesta.equals("set-security-general-null") == true)
		{
			/*
			if (sicurezza)
				sicurezza = false;
			else
				sicurezza = true;
			*/
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
			/*
			if (boxAuto)
				boxAuto = false;
			else
				boxAuto = true;
			*/
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
			/*
			if (tempAuto)
				tempAuto = false;
			else
				tempAuto = true;
			*/
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
		}
		
		if (richiesta.equals("set-temp-general-10") == true)
		{
			//tempVal = 10;
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
			//tempVal = 11;
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
			//tempVal = 12;
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
			//tempVal = 13;
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
			//tempVal = 14;
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
			//tempVal = 15;
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
			//tempVal = 16;
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
			//tempVal = 17;
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
			//tempVal = 18;
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
			//tempVal = 19;
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
			//tempVal = 20;
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
			//tempVal = 21;
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
			//tempVal = 22;
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
			//tempVal = 23;
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
			//tempVal = 24;
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
			//tempVal = 25;
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
			//tempVal = 26;
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
			//tempVal = 27;
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
			//tempVal = 28;
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
			//tempVal = 29;
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
			//tempVal = 30;
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
			/*
			if (antiIncendio)
				antiIncendio = false;
			else
				antiIncendio = true;
			*/
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
			/*
			if (luceSalone)
				luceSalone = false;
			else
				luceSalone = true;
			*/
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
		
		if (richiesta.equals("set-light-room-null") == true)
		{
			/*
			if (luceCamera)
				luceCamera = false;
			else
				luceCamera = true;
			*/
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
		
		if (richiesta.equals("get-light-room") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(luceCamera);
			out.print(message);
		}
		
		if (richiesta.equals("set-light-bathroom-null") == true)
		{
			/*
			if (luceBagno)
				luceBagno = false;
			else
				luceBagno = true;
			*/
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
			/*
			if (luceCucina)
				luceCucina = false;
			else
				luceCucina = true;
			*/
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
			/*
			if (tapparellaSalone)
				tapparellaSalone = false;
			else
				tapparellaSalone = true;
			*/
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
		
		if (richiesta.equals("set-shutter-room-null") == true)
		{
			/*
			if (tapparellaCamera)
				tapparellaCamera = false;
			else
				tapparellaCamera = true;
			*/
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
		
		if (richiesta.equals("get-shutter-room") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(tapparellaCamera);
			out.print(message);
		}
		
		if (richiesta.equals("set-shutter-bathroom-null") == true)
		{
			/*
			if (tapparellaBagno)
				tapparellaBagno = false;
			else
				tapparellaBagno = true;
			*/
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
			/*
			if (tapparellaCucina)
				tapparellaCucina = false;
			else
				tapparellaCucina = true;
			*/
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
			/*
			if (luceAutoSalone)
				luceAutoSalone = false;
			else
				luceAutoSalone = true;
			*/
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
		
		if (richiesta.equals("set-autoLightning-room-null") == true)
		{
			/*
			if (luceAutoCamera)
				luceAutoCamera = false;
			else
				luceAutoCamera = true;
			*/
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
			/*
			if (luceAutoBagno)
				luceAutoBagno = false;
			else
				luceAutoBagno = true;
			*/
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
			/*
			if (luceAutoCucina)
				luceAutoCucina = false;
			else
				luceAutoCucina = true;
			*/
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
			//valLuceAutoSalone = 1;
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
			//valLuceAutoSalone = 2;
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
			//valLuceAutoSalone = 3;
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
			//valLuceAutoSalone = 4;
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
			//valLuceAutoSalone = 5;
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
			//valLuceAutoSalone = 6;
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
			//valLuceAutoSalone = 7;
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
			//valLuceAutoSalone = 8;
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
			//valLuceAutoSalone = 9;
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
			//valLuceAutoSalone = 10;
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
		
		if (richiesta.equals("set-lightning-room-1") == true)
		{
			//valLuceAutoCamera = 1;
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
		
		if (richiesta.equals("set-lightning-room-2") == true)
		{
			//valLuceAutoCamera = 2;
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
		
		if (richiesta.equals("set-lightning-room-3") == true)
		{
			//valLuceAutoCamera = 3;
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
		
		if (richiesta.equals("set-lightning-room-4") == true)
		{
			//valLuceAutoCamera = 4;
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
		
		if (richiesta.equals("set-lightning-room-5") == true)
		{
			//valLuceAutoCamera = 5;
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
		
		if (richiesta.equals("set-lightning-room-6") == true)
		{
			//valLuceAutoCamera = 6;
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
		
		if (richiesta.equals("set-lightning-room-7") == true)
		{
			//valLuceAutoCamera = 7;
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
		
		if (richiesta.equals("set-lightning-room-8") == true)
		{
			//valLuceAutoCamera = 8;
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
		
		if (richiesta.equals("set-lightning-room-9") == true)
		{
			//valLuceAutoCamera = 9;
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
		
		if (richiesta.equals("set-lightning-room-10") == true)
		{
			//valLuceAutoCamera = 10;
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
			//valLuceAutoBagno = 1;
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
			//valLuceAutoBagno = 2;
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
			//valLuceAutoBagno = 3;
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
			//valLuceAutoBagno = 4;
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
			//valLuceAutoBagno = 5;
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
			//valLuceAutoBagno = 6;
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
			//valLuceAutoBagno = 7;
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
			//valLuceAutoBagno = 8;
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
			//valLuceAutoBagno = 9;
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
			//valLuceAutoBagno = 10;
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
			//valLuceAutoCucina = 1;
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
			//valLuceAutoCucina = 2;
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
			//valLuceAutoCucina = 3;
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
			//valLuceAutoCucina = 4;
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
			//valLuceAutoCucina = 5;
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
			//valLuceAutoCucina = 6;
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
			//valLuceAutoCucina = 7;
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
			//valLuceAutoCucina = 8;
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
			//valLuceAutoCucina = 9;
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
			//valLuceAutoCucina = 10;
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
			/*
			if (finestraSalone)
				finestraSalone = false;
			else
				finestraSalone = true;
			*/
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
		
		if (richiesta.equals("set-window-room-null") == true)
		{
			/*
			if (finestraCamera)
				finestraCamera = false;
			else
				finestraCamera = true;
			*/
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
			/*
			if (finestraBagno)
				finestraBagno = false;
			else
				finestraBagno = true;
			*/
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
			/*
			if (finestraCucina)
				finestraCucina = false;
			else
				finestraCucina = true;
			*/
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
		
		if (richiesta.equals("get-autoLightning-room") == true)
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
		
		if (richiesta.equals("get-window-room") == true)
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
	}
	
	
	public void init(ServletConfig config) throws ServletException {
	    super.init(config);
		
		Properties pp = new Properties();
		pp.setProperty(Profile.MAIN_HOST, "localhost");
		pp.setProperty(Profile.MAIN_PORT, "1200");
		JadeGateway.init("gateway.agent.MyGateWayAgent", pp);		
	
	}

}
