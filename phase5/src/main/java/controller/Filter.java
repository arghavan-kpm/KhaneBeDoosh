package controller;

import domain.InfoBank;
import domain.Tools;
import org.json.JSONObject;

import javax.json.JsonObject;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.SignatureException;

public class Filter implements javax.servlet.Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        PrintWriter out =new PrintWriter (new OutputStreamWriter(response.getOutputStream(), "UTF8"), true);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String result = "", userName;

        try {
            String body = request.getReader().readLine();
            JSONObject json = new JSONObject(body);
            String Token = json.getString("token");

            json = Tools.JwtParser(Token);
            userName = json.getString("jti");

            request.setAttribute("user", userName);
            request.setAttribute("body",body);
            chain.doFilter(request, response);
            return;

        }catch (Exception e){
            response.setStatus(403);
            out.println("{ \"result\" : \" Token Is Invalid \"}");
            return;
        }

        //chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
