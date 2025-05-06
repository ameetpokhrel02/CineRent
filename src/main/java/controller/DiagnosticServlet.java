package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/diagnostic")
public class DiagnosticServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Database Diagnostic</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 20px; }");
        out.println("h1, h2 { color: #333; }");
        out.println("table { border-collapse: collapse; width: 100%; margin-bottom: 20px; }");
        out.println("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
        out.println("th { background-color: #f2f2f2; }");
        out.println("tr:nth-child(even) { background-color: #f9f9f9; }");
        out.println(".error { color: red; }");
        out.println(".success { color: green; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Database Diagnostic</h1>");
        
        Connection conn = null;
        
        try {
            // Test database connection
            out.println("<h2>Database Connection</h2>");
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cinerent?useSSL=false", "root", "");
            out.println("<p class='success'>Successfully connected to the database!</p>");
            
            // Get database metadata
            DatabaseMetaData metaData = conn.getMetaData();
            out.println("<p>Database: " + metaData.getDatabaseProductName() + " " + metaData.getDatabaseProductVersion() + "</p>");
            out.println("<p>JDBC Driver: " + metaData.getDriverName() + " " + metaData.getDriverVersion() + "</p>");
            
            // Check if movie table exists
            out.println("<h2>Tables</h2>");
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            out.println("<table>");
            out.println("<tr><th>Table Name</th></tr>");
            boolean movieTableExists = false;
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                out.println("<tr><td>" + tableName + "</td></tr>");
                if (tableName.equalsIgnoreCase("movie")) {
                    movieTableExists = true;
                }
            }
            out.println("</table>");
            
            if (!movieTableExists) {
                out.println("<p class='error'>Movie table does not exist!</p>");
            } else {
                out.println("<p class='success'>Movie table exists.</p>");
                
                // Get movie table structure
                out.println("<h2>Movie Table Structure</h2>");
                ResultSet columns = metaData.getColumns(null, null, "movie", null);
                out.println("<table>");
                out.println("<tr><th>Column Name</th><th>Type</th><th>Size</th><th>Nullable</th></tr>");
                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    String columnType = columns.getString("TYPE_NAME");
                    int columnSize = columns.getInt("COLUMN_SIZE");
                    String nullable = columns.getInt("NULLABLE") == 1 ? "Yes" : "No";
                    out.println("<tr><td>" + columnName + "</td><td>" + columnType + "</td><td>" + columnSize + "</td><td>" + nullable + "</td></tr>");
                }
                out.println("</table>");
                
                // Check if movie table has data
                out.println("<h2>Movie Table Data</h2>");
                try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM movie")) {
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        int count = rs.getInt(1);
                        out.println("<p>Total movies: " + count + "</p>");
                        
                        if (count == 0) {
                            out.println("<p class='error'>Movie table is empty!</p>");
                        } else {
                            out.println("<p class='success'>Movie table has data.</p>");
                            
                            // Show sample data
                            try (PreparedStatement sampleStmt = conn.prepareStatement("SELECT * FROM movie LIMIT 5")) {
                                ResultSet sampleRs = sampleStmt.executeQuery();
                                ResultSetMetaData rsMetaData = sampleRs.getMetaData();
                                int columnCount = rsMetaData.getColumnCount();
                                
                                out.println("<h3>Sample Data (up to 5 rows)</h3>");
                                out.println("<table>");
                                
                                // Print column headers
                                out.println("<tr>");
                                for (int i = 1; i <= columnCount; i++) {
                                    out.println("<th>" + rsMetaData.getColumnName(i) + "</th>");
                                }
                                out.println("</tr>");
                                
                                // Print data rows
                                while (sampleRs.next()) {
                                    out.println("<tr>");
                                    for (int i = 1; i <= columnCount; i++) {
                                        String value = sampleRs.getString(i);
                                        out.println("<td>" + (value != null ? value : "NULL") + "</td>");
                                    }
                                    out.println("</tr>");
                                }
                                out.println("</table>");
                            }
                        }
                    }
                }
            }
            
        } catch (Exception e) {
            out.println("<p class='error'>Error: " + e.getMessage() + "</p>");
            out.println("<pre>");
            e.printStackTrace(out);
            out.println("</pre>");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace(out);
                }
            }
        }
        
        out.println("<h2>Servlet Information</h2>");
        out.println("<p>Context Path: " + request.getContextPath() + "</p>");
        out.println("<p>Servlet Path: " + request.getServletPath() + "</p>");
        out.println("<p>Request URI: " + request.getRequestURI() + "</p>");
        
        out.println("</body>");
        out.println("</html>");
    }
}
