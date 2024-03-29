package servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Vector;

public class ControllerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String xString = request.getParameter("x_value");
        String yString = request.getParameter("y_value");
        String rString = request.getParameter("r_value");
        if (xString == null || yString == null || rString == null || !isNumeric(xString) || !isNumeric(yString.replace(',', '.')) || !isNumeric(rString)) {
            if (request.getParameter("type").equals("clear")) {
                ServletContext context = request.getSession().getServletContext();
                Object attribute = context.getAttribute("userData");
                if (!(attribute == null || ((Vector<Coordinate>) attribute).size() == 0)) {
                    context.setAttribute("userData", new Vector<Coordinate>());
                }
            } else
                request.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        } else {
            request.getServletContext().getRequestDispatcher("/checking").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}