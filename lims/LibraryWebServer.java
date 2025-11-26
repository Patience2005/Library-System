import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class LibraryWebServer {
    private static final int PORT = 8080;
    private static LibraryRepository libraryRepo;
    private static CatalogService catalogService;

    public static void main(String[] args) {
        libraryRepo = new LibraryRepository();
        catalogService = new CatalogService(libraryRepo);
        
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Library Web Server started on http://localhost:" + PORT);
            System.out.println("Open your browser and navigate to: http://localhost:" + PORT);
            System.out.println("Press Ctrl+C to stop the server");
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleRequest(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    private static void handleRequest(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
             BufferedOutputStream dataOut = new BufferedOutputStream(clientSocket.getOutputStream())) {
            
            String requestLine = in.readLine();
            if (requestLine == null) return;
            
            StringTokenizer tokens = new StringTokenizer(requestLine);
            String method = tokens.nextToken();
            String path = tokens.nextToken();
            
            // Handle different endpoints
            if (path.equals("/")) {
                serveFile(out, dataOut, "../web/index.html", "text/html");
            } else if (path.equals("/app.js")) {
                serveFile(out, dataOut, "../web/app.js", "application/javascript");
            } else if (path.startsWith("/api/")) {
                handleApiRequest(out, dataOut, path, method);
            } else {
                send404(out, dataOut);
            }
            
        } catch (IOException e) {
            System.err.println("Error handling request: " + e.getMessage());
        }
    }

    private static void handleApiRequest(PrintWriter out, BufferedOutputStream dataOut, String path, String method) {
        try {
            String jsonResponse = "";
            String contentType = "application/json";
            
            if (path.equals("/api/books")) {
                if (method.equals("GET")) {
                    jsonResponse = getBooksJson();
                } else if (method.equals("POST")) {
                    // Handle adding new book (would need to read POST data)
                    jsonResponse = "{\"status\":\"success\",\"message\":\"Book added\"}";
                }
            } else if (path.startsWith("/api/search")) {
                String query = path.substring(path.indexOf("?q=") + 3);
                jsonResponse = searchBooksJson(query);
            }
            
            byte[] responseData = jsonResponse.getBytes();
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: " + contentType);
            out.println("Content-Length: " + responseData.length);
            out.println("Access-Control-Allow-Origin: *");
            out.println();
            out.flush();
            
            dataOut.write(responseData, 0, responseData.length);
            dataOut.flush();
            
        } catch (Exception e) {
            send500(out, dataOut);
        }
    }

    private static String getBooksJson() {
        List<LibraryItem> books = libraryRepo.getAllBooks();
        StringBuilder json = new StringBuilder();
        json.append("[");
        
        for (int i = 0; i < books.size(); i++) {
            LibraryItem book = books.get(i);
            json.append("{");
            json.append("\"isbn\":\"").append(book.getIsbn()).append("\",");
            json.append("\"title\":\"").append(book.getTitle()).append("\",");
            json.append("\"author\":\"").append(book.getAuthor()).append("\",");
            json.append("\"available\":").append(book.isAvailable()).append(",");
            json.append("\"type\":\"").append(getBookType(book)).append("\",");
            json.append("\"category\":\"").append(getBookCategory(book)).append("\"");
            json.append("}");
            if (i < books.size() - 1) json.append(",");
        }
        
        json.append("]");
        return json.toString();
    }

    private static String searchBooksJson(String query) {
        List<LibraryItem> results = catalogService.searchBooks(query);
        StringBuilder json = new StringBuilder();
        json.append("[");
        
        for (int i = 0; i < results.size(); i++) {
            LibraryItem book = results.get(i);
            json.append("{");
            json.append("\"isbn\":\"").append(book.getIsbn()).append("\",");
            json.append("\"title\":\"").append(book.getTitle()).append("\",");
            json.append("\"author\":\"").append(book.getAuthor()).append("\",");
            json.append("\"available\":").append(book.isAvailable()).append(",");
            json.append("\"type\":\"").append(getBookType(book)).append("\",");
            json.append("\"category\":\"").append(getBookCategory(book)).append("\"");
            json.append("}");
            if (i < results.size() - 1) json.append(",");
        }
        
        json.append("]");
        return json.toString();
    }

    private static String getBookType(LibraryItem book) {
        if (book instanceof FictionBook) return "Fiction";
        if (book instanceof NonFictionBook) return "NonFiction";
        if (book instanceof ReferenceBook) return "Reference";
        return "Unknown";
    }

    private static String getBookCategory(LibraryItem book) {
        if (book instanceof FictionBook) return ((FictionBook) book).getGenre();
        if (book instanceof NonFictionBook) return ((NonFictionBook) book).getSubject();
        if (book instanceof ReferenceBook) return ((ReferenceBook) book).getCategory();
        return "General";
    }

    private static void serveFile(PrintWriter out, BufferedOutputStream dataOut, String filePath, String contentType) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                send404(out, dataOut);
                return;
            }
            
            byte[] fileData = Files.readAllBytes(file.toPath());
            
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: " + contentType);
            out.println("Content-Length: " + fileData.length);
            out.println();
            out.flush();
            
            dataOut.write(fileData, 0, fileData.length);
            dataOut.flush();
            
        } catch (IOException e) {
            send500(out, dataOut);
        }
    }

    private static void send404(PrintWriter out, BufferedOutputStream dataOut) {
        String response = "404 Not Found";
        byte[] responseData = response.getBytes();
        
        try {
            out.println("HTTP/1.1 404 Not Found");
            out.println("Content-Type: text/plain");
            out.println("Content-Length: " + responseData.length);
            out.println();
            out.flush();
            
            dataOut.write(responseData, 0, responseData.length);
            dataOut.flush();
        } catch (IOException e) {
            System.err.println("Error sending 404: " + e.getMessage());
        }
    }

    private static void send500(PrintWriter out, BufferedOutputStream dataOut) {
        String response = "500 Internal Server Error";
        byte[] responseData = response.getBytes();
        
        try {
            out.println("HTTP/1.1 500 Internal Server Error");
            out.println("Content-Type: text/plain");
            out.println("Content-Length: " + responseData.length);
            out.println();
            out.flush();
            
            dataOut.write(responseData, 0, responseData.length);
            dataOut.flush();
        } catch (IOException e) {
            System.err.println("Error sending 500: " + e.getMessage());
        }
    }
}
