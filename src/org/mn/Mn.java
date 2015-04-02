package org.mn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import sun.org.mozilla.javascript.internal.json.JsonParser;

public class Mn extends HttpServlet{
	public void  doGet(HttpServletRequest req, HttpServletResponse resp)    throws IOException, ServletException{
		doPost(req, resp);
	}
	public void  doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		String paragraph=	"text="+(String) req.getParameter("data");
		 HttpURLConnection con = (HttpURLConnection) new URL("https://gate.d5.mpi-inf.mpg.de/aida/service/disambiguate").openConnection();
         con.setRequestMethod("POST");
         con.setDoOutput(true);
         con.getOutputStream().write(paragraph.getBytes("UTF-8"));
         BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
         String tmp;
         JSONParser parser = new JSONParser();
         String op ="";
         while((tmp = reader.readLine()) != null) {
         try {
			JSONObject jsonObject=(JSONObject) parser.parse(tmp);
			String originalFileName = (String) jsonObject.get("originalFileName");
            String originalText = (String) jsonObject.get("originalText");
            String overallTime = (String) jsonObject.get("overallTime");
            JSONArray mentions = (JSONArray) jsonObject.get("mentions");
            for(int i=0;i<mentions.size();i++)
            {
            	Object obj2 = parser.parse(mentions.get(i).toString());
                JSONObject jsonObject2 = (JSONObject) obj2;
                JSONArray allEntities = (JSONArray) jsonObject2.get("allEntities");
                
                for(int j=0;j<allEntities.size();j++)
                {
                	Object obj3 = parser.parse(allEntities.get(j).toString());
                	JSONObject jsonObject3 = (JSONObject) obj3;
                	String kdId = (String) jsonObject3.get("kbIdentifier");
                	String disambiguationScore = (String) jsonObject3.get("disambiguationScore");
                	op= op+kdId+"<br> Score :"+disambiguationScore;
                }
                
            }
            req.setAttribute("op",op);
            System.out.println(op);
    		req.getRequestDispatcher("home.jsp").forward(req, resp); 
           } catch (Exception e) {
		}
         }             
	}
}
