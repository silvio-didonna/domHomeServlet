import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	double currentTemp = 15.5;
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
		
		String message;
		String richiesta = request.getRequestURI();
		richiesta = richiesta.replace("/domHome/", "");
		System.out.println("richiesta = " + richiesta);
		if (richiesta.equals("set-sicurezza") == true)
		{
			if (sicurezza)
				sicurezza = false;
			else
				sicurezza = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("get-sicurezza") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(sicurezza);
			out.print(message);
		}
		
		if (richiesta.equals("set-box-auto") == true)
		{
			if (boxAuto)
				boxAuto = false;
			else
				boxAuto = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("get-box-auto") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(boxAuto);
			out.print(message);
		}
		
		if (richiesta.equals("set-temperatura-off") == true)
		{
			tempAuto = false;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("get-temperatura") == true)
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
		
		if (richiesta.equals("get-temp-corr") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = String.valueOf(currentTemp);
			out.print(message);
		}
		
		if (richiesta.equals("set-temperatura-10") == true)
		{
			tempAuto = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-temperatura-11") == true)
		{
			tempAuto = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-temperatura-12") == true)
		{
			tempAuto = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-temperatura-13") == true)
		{
			tempAuto = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-temperatura-14") == true)
		{
			tempAuto = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-temperatura-15") == true)
		{
			tempAuto = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-temperatura-16") == true)
		{
			tempAuto = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-temperatura-17") == true)
		{
			tempAuto = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-temperatura-18") == true)
		{
			tempAuto = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-temperatura-19") == true)
		{
			tempAuto = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-temperatura-20") == true)
		{
			tempAuto = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-temperatura-21") == true)
		{
			tempAuto = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-temperatura-22") == true)
		{
			tempAuto = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-temperatura-23") == true)
		{
			tempAuto = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-temperatura-24") == true)
		{
			tempAuto = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-temperatura-25") == true)
		{
			tempAuto = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-temperatura-26") == true)
		{
			tempAuto = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-temperatura-27") == true)
		{
			tempAuto = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-temperatura-28") == true)
		{
			tempAuto = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-temperatura-29") == true)
		{
			tempAuto = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-temperatura-30") == true)
		{
			tempAuto = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-incendio") == true)
		{
			if (antiIncendio)
				antiIncendio = false;
			else
				antiIncendio = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("get-incendio") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(antiIncendio);
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-salone") == true)
		{
			if (luceSalone)
				luceSalone = false;
			else
				luceSalone = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("get-luce-salone") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(luceSalone);
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-camera") == true)
		{
			if (luceCamera)
				luceCamera = false;
			else
				luceCamera = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("get-luce-camera") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(luceCamera);
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-bagno") == true)
		{
			if (luceBagno)
				luceBagno = false;
			else
				luceBagno = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("get-luce-bagno") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(luceBagno);
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-cucina") == true)
		{
			if (luceCucina)
				luceCucina = false;
			else
				luceCucina = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("get-luce-cucina") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(luceCucina);
			out.print(message);
		}
		
		if (richiesta.equals("set-tapparella-salone") == true)
		{
			if (tapparellaSalone)
				tapparellaSalone = false;
			else
				tapparellaSalone = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("get-tapparella-salone") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(tapparellaSalone);
			out.print(message);
		}
		
		if (richiesta.equals("set-tapparella-camera") == true)
		{
			if (tapparellaCamera)
				tapparellaCamera = false;
			else
				tapparellaCamera = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("get-tapparella-camera") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(tapparellaCamera);
			out.print(message);
		}
		
		if (richiesta.equals("set-tapparella-bagno") == true)
		{
			if (tapparellaBagno)
				tapparellaBagno = false;
			else
				tapparellaBagno = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("get-tapparella-bagno") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(tapparellaBagno);
			out.print(message);
		}
		
		if (richiesta.equals("set-tapparella-cucina") == true)
		{
			if (tapparellaCucina)
				tapparellaCucina = false;
			else
				tapparellaCucina = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("get-tapparella-cucina") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(tapparellaCucina);
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-salone-off") == true)
		{
			luceAutoSalone = false;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-camera-off") == true)
		{
			luceAutoCamera = false;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-bagno-off") == true)
		{
			luceAutoBagno = false;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-cucina-off") == true)
		{
			luceAutoCucina = false;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-salone-1") == true)
		{
			luceAutoSalone = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-salone-2") == true)
		{
			luceAutoSalone = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-salone-3") == true)
		{
			luceAutoSalone = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-salone-4") == true)
		{
			luceAutoSalone = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-salone-5") == true)
		{
			luceAutoSalone = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-salone-6") == true)
		{
			luceAutoSalone = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-salone-7") == true)
		{
			luceAutoSalone = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-salone-8") == true)
		{
			luceAutoSalone = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-salone-9") == true)
		{
			luceAutoSalone = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-salone-10") == true)
		{
			luceAutoSalone = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-camera-1") == true)
		{
			luceAutoCamera = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-camera-2") == true)
		{
			luceAutoCamera = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-camera-3") == true)
		{
			luceAutoCamera = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-camera-4") == true)
		{
			luceAutoCamera = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-camera-5") == true)
		{
			luceAutoCamera = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-camera-6") == true)
		{
			luceAutoCamera = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-camera-7") == true)
		{
			luceAutoCamera = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-camera-8") == true)
		{
			luceAutoCamera = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-camera-9") == true)
		{
			luceAutoCamera = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-camera-10") == true)
		{
			luceAutoCamera = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-bagno-1") == true)
		{
			luceAutoBagno = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-bagno-2") == true)
		{
			luceAutoBagno = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-bagno-3") == true)
		{
			luceAutoBagno = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-bagno-4") == true)
		{
			luceAutoBagno = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-bagno-5") == true)
		{
			luceAutoBagno = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-bagno-6") == true)
		{
			luceAutoBagno = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-bagno-7") == true)
		{
			luceAutoBagno = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-bagno-8") == true)
		{
			luceAutoBagno = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-bagno-9") == true)
		{
			luceAutoBagno = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-bagno-10") == true)
		{
			luceAutoBagno = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-cucina-1") == true)
		{
			luceAutoCucina = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-cucina-2") == true)
		{
			luceAutoCucina = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-cucina-3") == true)
		{
			luceAutoCucina = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-cucina-4") == true)
		{
			luceAutoCucina = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-cucina-5") == true)
		{
			luceAutoCucina = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-cucina-6") == true)
		{
			luceAutoCucina = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-cucina-7") == true)
		{
			luceAutoCucina = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-cucina-8") == true)
		{
			luceAutoCucina = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-cucina-9") == true)
		{
			luceAutoCucina = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-luce-auto-cucina-10") == true)
		{
			luceAutoCucina = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-finestra-salone") == true)
		{
			if (finestraSalone)
				finestraSalone = false;
			else
				finestraSalone = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-finestra-camera") == true)
		{
			if (finestraCamera)
				finestraCamera = false;
			else
				finestraCamera = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-finestra-bagno") == true)
		{
			if (finestraBagno)
				finestraBagno = false;
			else
				finestraBagno = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("set-finestra-cucina") == true)
		{
			if (finestraCucina)
				finestraCucina = false;
			else
				finestraCucina = true;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = "Comando Eseguito";
			out.print(message);
		}
		
		if (richiesta.equals("get-luce-auto-salone") == true)
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
		
		if (richiesta.equals("get-luce-auto-camera") == true)
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
		
		if (richiesta.equals("get-luce-auto-bagno") == true)
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
		
		if (richiesta.equals("get-luce-auto-cucina") == true)
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
		
		if (richiesta.equals("get-finestra-salone") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(finestraSalone);
			out.print(message);
		}
		
		if (richiesta.equals("get-finestra-camera") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(finestraCamera);
			out.print(message);
		}
		
		if (richiesta.equals("get-finestra-bagno") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(finestraBagno);
			out.print(message);
		}
		
		if (richiesta.equals("get-finestra-cucina") == true)
		{
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			PrintWriter out = response.getWriter();
			message = Boolean.toString(finestraCucina);
			out.print(message);
		}
	}

}
