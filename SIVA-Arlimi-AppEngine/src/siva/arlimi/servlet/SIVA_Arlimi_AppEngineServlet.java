package siva.arlimi.servlet;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class SIVA_Arlimi_AppEngineServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}
