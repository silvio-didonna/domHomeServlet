package gateway.servlet;
/*****************************************************************

The servlet who communicates with the user

*****************************************************************/

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.leap.Properties;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.gateway.JadeGateway;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gateway.action.SendMessageAction;
import gateway.action.*;
import gateway.bean.BlackBoardBean;

import java.util.*;

public class GateWayServlet extends HttpServlet {

	Hashtable actions = null;

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request,response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		// this is a generic doPost
		// we have some actions in the action hashmap
		
		String actionName = request.getParameter("action");
		
		if (actionName == null)	{
			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return;
		}
		
		Action action = (Action) actions.get(actionName);
		if (action == null)	{
			response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
			return;
		}
		
		
		action.perform(this, request, response);
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init(ServletConfig config) throws ServletException {
	    super.init(config);
		
		
		actions = new Hashtable();
		
		// we have only an action
		actions.put("sendmessage",new SendMessageAction());
		
		Properties pp = new Properties();
		pp.setProperty(Profile.MAIN_HOST, "localhost");
		pp.setProperty(Profile.MAIN_PORT, "1200");
		JadeGateway.init("gateway.agent.MyGateWayAgent", pp);
		
	
	}
		
}
