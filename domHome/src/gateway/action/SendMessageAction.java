package gateway.action;
/*****************************************************************

SendMessageAction carries out sending the message to the GateWayAgent

*****************************************************************/

import jade.wrapper.gateway.JadeGateway;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import solarforce.action.Action;
import solarforce.bean.BlackBoardBean;

public class SendMessageAction implements Action {

	public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException	{
		

		// create a BlackBoard for the session if it not exist
		BlackBoardBean board = new BlackBoardBean();


		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
				
		
		board.setReceiver("PingJani");
		board.setMessage("Hey whats up");
		
		try	{
			JadeGateway.execute(board);
		} catch(Exception e) { e.printStackTrace(); }
						
		
		out.print("Message has been sent!<br/>");
		
		out.print("Reply:"+board.getMessage());
		
		out.print("<br/><a href='index.html'> Go back </a>");
		
		out.flush();
		out.close();
		
	}

}

