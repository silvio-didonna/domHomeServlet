package gateway.action;
/*****************************************************************

Generic Action interface for the servlet

*****************************************************************/

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public interface Action {

	 public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) 
	 	throws IOException, ServletException;
	 
}
