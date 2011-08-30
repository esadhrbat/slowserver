import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;

public class HelloWorld extends HttpServlet {
	private static final long serialVersionUID = -3043828122010838408L;
	
	static Random r;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		
		resp.setContentType("text/html");

		PrintWriter out = resp.getWriter();
		out.println("<html><head><title>Sloooow server</title></head><body>");
        out.println("<h2>Hello from the slooooow server</h2>");
        out.print("<p>Waiting for 2seks...");
        out.flush();
        resp.flushBuffer();
        try {
        	// simulate a workload of 2seks
			Thread.sleep(r.nextInt(2000));
		} catch (InterruptedException e) {
			out.println("interrupted!</p>");
			return;
		}
        out.println("<strong>done!</strong></p>");
        out.println("</body>");
    }

    public static void main(String[] args) throws Exception{
    	int port = 8080;
    	if(System.getenv("PORT") != null) {
    		port = Integer.valueOf(System.getenv("PORT"));
    	}

    	r = new Random();
    	
        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new HelloWorld()),"/*");
        server.start();
        server.join();   
    }
}
