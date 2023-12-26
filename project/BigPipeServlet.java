import java.io.IOException;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/bigpipe", asyncSupported = true)
public class BigPipeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        final AsyncContext asyncContext = request.startAsync();
        asyncContext.setTimeout(30000); // Set a timeout if needed

        // Simulate asynchronous processing (e.g., fetching data from a database)
        new Thread(() -> {
            try {
                // Simulating a delay
                Thread.sleep(2000);

                // Retrieve data (replace with actual data retrieval logic)
                String data = "Data loaded asynchronously";

                // Dispatch to JSP for rendering
                request.setAttribute("data", data);
                asyncContext.dispatch("/WEB-INF/views/page1.jsp");

                // Complete the response
                asyncContext.complete();
            } catch (InterruptedException | ServletException | IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
