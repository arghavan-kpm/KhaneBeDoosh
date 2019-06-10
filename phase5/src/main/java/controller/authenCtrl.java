package controller;

import domain.Individual;
import domain.InfoBank;
import domain.ORMapper;
import domain.Tools;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class authenCtrl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out =new PrintWriter (new OutputStreamWriter(response.getOutputStream(), "UTF8"), true);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String result = "", token = "", userName ="", passWord ="";
        try{

            JSONObject json = new JSONObject(request.getReader().readLine());
            userName = json.getString("userName");
            passWord = json.getString("passWord");

            System.out.println("Username : " + userName + "; Password : " + passWord);

            if(userName.equals("") ){                                // token e khali
                token = Tools.CompactJwt(userName);
                response.setStatus(200);
                result = "OK";
            }

            else{
                if(passWord.equals("")){
                    response.setStatus(406);
                    result = "Empty Pass";
                }

                else{
                    Individual ind = ORMapper.SelectUser(userName);
                    if( ind == null){
                        response.setStatus(412);
                        result = "User Not Found";
                    }
                    else{
                        if(ind.getPassword().equals(passWord)){
                            token = Tools.CompactJwt(userName);

                            response.setStatus(200);
                            result = "OK";
                        }
                        else{
                            response.setStatus(403);
                            result = "Unauthorized";
                        }

                    }
                }
            }



            out.println("{ \"result\" : \"" + result + "\",\"token\" : \""  + token + "\"}");
        }catch (Exception e43231){
            System.out.println(e43231.getClass().getName() + ": " + e43231.getMessage() );
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
