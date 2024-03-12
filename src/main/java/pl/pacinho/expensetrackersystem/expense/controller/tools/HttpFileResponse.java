package pl.pacinho.expensetrackersystem.expense.controller.tools;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpFileResponse {
    public static void build(String content, String outputFileName, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = " + outputFileName;
        response.setHeader(headerKey, headerValue);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(content.getBytes());
        outputStream.close();
    }
}
