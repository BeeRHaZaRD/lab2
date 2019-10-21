package servlet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Vector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class AreaCheckServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Instant start = Instant.now();
        long startTime = System.nanoTime();
        double x = Double.parseDouble(req.getParameter("x_value"));
        double y = Double.parseDouble(req.getParameter("y_value").replace(',', '.'));
        double r = Double.parseDouble(req.getParameter("r_value"));
        if (checkArea(x, y, r)) {
            boolean correctCoordinate = false;
            if ((x >= 0 && x <= r && y >= 0 && y <= r) || (y >= -x*0.5 - r && x <= 0 && y <= 0) || ((x*x + y*y) <= r*r && x >= 0 && y <= 0)) {
                correctCoordinate = true;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                    .withLocale(Locale.UK)
                    .withZone( ZoneId.systemDefault() );
            String time = formatter.format(start);
            long elatedTime = System.nanoTime() - startTime;

            Coordinate coordinate = new Coordinate(x, y, r, time, elatedTime, correctCoordinate);
            ServletContext context = req.getSession().getServletContext();
            Object attribute = context.getAttribute("userData");
            Vector<Coordinate> coordinatesCollection = new Vector<>();
            if (!(attribute == null || ((Vector<Coordinate>) attribute).size() == 0)) {
                coordinatesCollection = (Vector<Coordinate>) attribute;
            }
            coordinatesCollection.add(coordinate);
            context.setAttribute("userData", coordinatesCollection);

            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter writer = resp.getWriter();
            StringBuilder tableRow = new StringBuilder();
            tableRow.append("<tr>\n")
                    .append("<td>").append(coordinate.getCastedX()).append("</td>\n")
                    .append("<td>").append(coordinate.getCastedY()).append("</td>\n")
                    .append("<td>").append(coordinate.getCastedR()).append("</td>\n")
                    .append(coordinate.getCorrectWords())
                    .append("<td>").append(coordinate.getRequestTime()).append("</td>\n")
                    .append("<td>").append(coordinate.getExecutionTime()).append("</td>\n")
                    .append("</tr>\n");
            writer.println(tableRow);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private boolean checkArea(Double x, Double y, Double r) {
        int[] xSet = {-4, -3, -2, -1, 0, 1, 2, 3, 4};
        double[] rSet = {1, 1.5, 2, 2.5, 3};
        return IntStream.of(xSet).anyMatch(rad -> rad == x) && y >= -5 && y <= 5 && DoubleStream.of(rSet).anyMatch(rad -> rad == r);
    }
}

