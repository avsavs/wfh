package guestbook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;




@SuppressWarnings("serial")
public class CaliforniaServlet extends HttpServlet {
	
	public static final String limitCount = "100";
	
	public static final Integer splitCount = 84;


	
	private static final Logger l =  Logger.getLogger(CaliforniaServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		l.info("inside california servlet");
		
		List<String> dateList = new ArrayList<String>();
        String API_KEY="xxxxx";//get your own key
        URL url = null;
        try {
        	String urifrom = req.getRequestURI();
        	String callback = req.getParameter("callback");
        	String offset = req.getParameter("start");
        	String query = req.getParameter("query");
        	String inType = req.getParameter("inType");
        	String addOne = req.getParameter("addOne");
        	String addTwo = req.getParameter("addTwo");
        	String addThree = req.getParameter("addThree");
        	String addFour = req.getParameter("addFour");
        	String addFive = req.getParameter("addFive");
        	String hasPic = req.getParameter("hasPic");
        	String itemDate = req.getParameter("byDate");
        	String region = req.getParameter("region");
        	
        	System.out.println(" callback "+ callback); 
    		System.out.println(" offset "+ offset); 
    		System.out.println(" query "+ query); 
    		System.out.println(" inType "+ inType); 
    		System.out.println(" addOne "+ addOne); 
    		System.out.println(" addTwo "+ addTwo); 
    		System.out.println(" addThree "+ addThree); 
    		System.out.println(" addFour "+ addFour); 
    		System.out.println(" addFive "+ addFive); 
    		System.out.println(" hasPic "+ hasPic); 
    		System.out.println(" itemDate "+ itemDate); 
    		System.out.println(" region "+ region); 
    		
    		
        	
        	if (region!=null)
        	{
        		region =  region.trim();
        	}
        	String urlParams = "";
        	
        	if (addOne!=null && (!addOne.equals("")))
        	{
        		
        		
        		
        		urlParams = urlParams +
        	        	"is_telecommuting=1" ;
        	}
        	if (addTwo!=null && (!addTwo.equals("")))
        	{
        		urlParams = urlParams +
        	        	"&is_contract=1" ;
        	}
        	if (addThree!=null && (!addThree.equals("")))
        	{
        		urlParams = urlParams +
        	        	"&is_internship=1";
        	}
        	if (addFour!=null && (!addFour.equals("")))
        	{
        		urlParams = urlParams +
        	        	"&is_parttime=1" ;
        	}
        	if (addFive!=null && (!addFive.equals("")))
        	{
        		urlParams = urlParams +
        	        	"&is_nonprofit=1" ;
        	}
        	if (hasPic!=null && (!hasPic.equals("")))
        	{
        		urlParams = urlParams +
        	        	"&hasPic=1" ;
        	}
         	List<String> processList = new ArrayList<String>();
         	List<String> regionList = new ArrayList<String>();
         	CraigsRegions aCraigsRegions =  CraigsRegions.getInstance();
         	 ;
         	dateList.add(region);
         	regionList =aCraigsRegions.regionMap.get(region);
        	
        	
        	Boolean endLoop = false ;
        	String states="";
        	int start = 0 ;
    		int end = splitCount;
        	while (!endLoop)
        	{
        		
        		
        		
        		if (regionList.size()<=end)
        		{
        			end = regionList.size();
        			endLoop= true ;
        		}
        		
        		 
        	 states = join(regionList.subList(start,end),"%2C%0A");
        	
        	start = end ;
        	end = end +splitCount ;
        	
        	processList.add(states);
        	
        	}
        	
        	if (query=="")
        	{
        		query = "%20";
        	}
        	query = query.trim();
        	query=query.replace(" ", "%20");
        	if (offset!=null && offset.equals(""))
        		offset = "0";
        	
        	String limit=limitCount;
        	if (limit.equals(""))
        		limit = limitCount;
        	if (offset!=null && (!offset.equals("0")))
        	{
        		
        		urlParams = urlParams +
        	        	"%20and%20item.date%3C'"+itemDate+"'" ;
        	}
        	
        	
        	 URLConnection connection =null;
        	 String inputLine = "";
        	 BufferedReader in = null;
        	 
        	 CraigsListReadjson aCraigsListReadjson = new CraigsListReadjson();
        	List<JSONObject> lastlist = new ArrayList<JSONObject>();
        		for(String states1 :regionList)
        	   	{
        			states1 = states1.replace("'", "");
        	 String sXml = processRssLocal(states1, inType, query, urlParams, limit,region);
        	 List<JSONObject> citylist =  aCraigsListReadjson.filterJson(sXml,offset,limit,dateList) ;
        	 lastlist.addAll(citylist);
        	 
        	   	};
        	 
        	 
        	 
 Collections.sort(lastlist, new CraigsComparator());
 
 JSONObject filterJSONObject = new JSONObject();
	            
	            try {
	            	filterJSONObject.put("items", lastlist);
	            	
	            	filterJSONObject.put("totalProperty", lastlist.size());
	            	
	            } catch (JSONException e) {
	            	// TODO Auto-generated catch block
	            	e.printStackTrace();
	            }
	            //return filterJSONObject;
	            
        	 System.out.println( " filterJSONObject1  " + filterJSONObject);
        	WriteToFile.writeFile(" filterJSONObject  " + filterJSONObject);
         
            resp.setContentType("application/json");
    		try {
    			
    			//CraigsListReadjson aCraigsListReadjson = new CraigsListReadjson();
    		//	resp.getWriter().println( callback + '(' + aCraigsListReadjson.filterJson(aString,offset,limit) +')' );
    			resp.getWriter().println( filterJSONObject  );
    			req.setAttribute("pageDates", dateList);
    		} catch (Exception e) {
    			l.severe( e.getMessage());
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        } catch (Exception e) {
        	l.severe( e.getMessage());
            e.printStackTrace();
        }

	}
	
	
	
	
	
	private List<String> processRss(List<String> regionList ,String inType,String query,String urlParams ,String limit,String region)
	{
		URLConnection connection =null;
		String fileprefix = "craigslist";
		
		List<String> resList = new ArrayList<String>();
   	 String inputLine = "";
   	 BufferedReader in = null;
   	URL url =null;
   	 String s = "";
   	
   	l.info(query);
   	if (region.equalsIgnoreCase("India") || region.equalsIgnoreCase("Australia")|| region.equalsIgnoreCase("Japan")|| region.equalsIgnoreCase("Canada")|| region.equalsIgnoreCase("Mexico")
   			|| region.equalsIgnoreCase("UK")||region.equalsIgnoreCase("Euro"))
   	{
   		fileprefix = region ;
   	}
   			
   	for(String states :regionList)
   	{
   	try
   	{
   		
   		
   		url = new URL("http://bangalore.craigslist.co.in/search/sof?is_telecommuting=1&query=java&format=rss");
   				//new URL("http://query.yahooapis.com/v1/public/yql?q=use%20%22store%3A%2F%2FujqKKuV6yKsQdwY0io4jb4%22%20as%20craig_table%3B%20select%20item%20from%20craig_table%20where%20location%20in%20%28%27sfbay%27%2C%27losangeles%27%29%20and%20query%20%3D%27java%27%20%20and%20type%20%3D%27jjj%27&format=json&debug=true&callback=");
//       url = new URL("http://query.yahooapis.com/v1/public/yql?q=USE%20%22http%3A%2F%2Fsearchdeck.appspot.com%2F"+fileprefix+".search.xml"
//       		+ "%22%20AS%20craig_table%3B%20SELECT%20item%20FROM%20craig_table%20where%20location%20in%20("+states+")%20and%20type%20%3D%20'"+inType+"'%20and%20query%20%3D%20'"
//       		+query+ "'"+urlParams+ "%20%7C%20sort(field%3D%22item.date%22%20%2Cdescending%3D%22true%22)%20%7Ctruncate("+limit+")&format=json");
        connection = url.openConnection();
       connection.setConnectTimeout(200000000);
       connection.setReadTimeout(200000000);
        in = new BufferedReader(new InputStreamReader(connection
               .getInputStream()));
       
      
       while ((inputLine = in.readLine()) != null) {
              // System.out.println( inputLine);
               s = inputLine ;
           }
       resList.add(s);
       l.info(url.toString());
       l.info(s);
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   		if(url!=null)
         l.severe(url.toString());
         l.info(s);
   		 l.severe(e.getMessage());
   	}
       //url.
   	}
		
		
		
		
		return resList;
	}
	
	
	

	private String processRssLocal(String  city ,String inType,String query,String urlParams ,String limit,String region)
	{
		
		System.out.println(" city "+ city); 
		System.out.println(" inType "+ inType); 
		System.out.println(" query "+ query); 
		System.out.println(" urlParams "+ urlParams); 
		System.out.println(" limit "+ limit); 
		System.out.println(" region1 "+ region); 
		
		
		URLConnection connection =null;
		String fileprefix = "org";
		
		List<String> resList = new ArrayList<String>();
   	 String inputLine = "";
   	 BufferedReader in = null;
   	URL url =null;
   	 String s = "";
   	
   	l.info(query);
   	if (region.equalsIgnoreCase("India") )
   	{
   		fileprefix = "co.in" ;
   	}
   			
   	
   	try
   	{
   		
   		
   		url = new URL("http://"+city+".craigslist."+fileprefix+"/search/"+inType+"?"+urlParams+"&query="+query+"&format=rss");
   				//new URL("http://query.yahooapis.com/v1/public/yql?q=use%20%22store%3A%2F%2FujqKKuV6yKsQdwY0io4jb4%22%20as%20craig_table%3B%20select%20item%20from%20craig_table%20where%20location%20in%20%28%27sfbay%27%2C%27losangeles%27%29%20and%20query%20%3D%27java%27%20%20and%20type%20%3D%27jjj%27&format=json&debug=true&callback=");
//       url = new URL("http://query.yahooapis.com/v1/public/yql?q=USE%20%22http%3A%2F%2Fsearchdeck.appspot.com%2F"+fileprefix+".search.xml"
//       		+ "%22%20AS%20craig_table%3B%20SELECT%20item%20FROM%20craig_table%20where%20location%20in%20("+states+")%20and%20type%20%3D%20'"+inType+"'%20and%20query%20%3D%20'"
//       		+query+ "'"+urlParams+ "%20%7C%20sort(field%3D%22item.date%22%20%2Cdescending%3D%22true%22)%20%7Ctruncate("+limit+")&format=json");
        connection = url.openConnection();
       connection.setConnectTimeout(200000000);
       connection.setReadTimeout(200000000);
        in = new BufferedReader(new InputStreamReader(connection
               .getInputStream()));
       
       //System.out.println(" inoutbuufer "+ in.toString());  
       while ((inputLine = in.readLine()) != null) {
              // System.out.println( inputLine);
               s = s + inputLine ;
           }
       //resList.add(s);
       l.info(url.toString());
       l.info(s);
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   		if(url!=null)
         l.severe(url.toString());
         l.info(s);
   		 l.severe(e.getMessage());
   	}
       //url.
   
		
  // 	System.out.println(" inoutbuufer s "+ s); 
		
		
		return s;
	}
	
	
	private List<String> processRssC(List<String> regionList ,String inType,String query,String urlParams ,String limit,List<String> regionL)
	{
		URLConnection connection =null;
		String fileprefix = "craigslist";
		
		List<String> resList = new ArrayList<String>();
   	 String inputLine = "";
   	 BufferedReader in = null;
   	URL url =null;
   	 String s = "";
   	
   	l.info(query);
//   	if (region.equalsIgnoreCase("India") || region.equalsIgnoreCase("Australia")|| region.equalsIgnoreCase("Japan")|| region.equalsIgnoreCase("Canada")|| region.equalsIgnoreCase("Mexico")
//   			|| region.equalsIgnoreCase("UK")||region.equalsIgnoreCase("EURO"))
//   	{
//   		fileprefix = region ;
//   	}
//   			
  // 	for(String states :regionList && String region : regionL)
   		for(int i = 0 ; i < regionList.size() ;i++)
   	{
   			String states = regionList.get(i);
   			 fileprefix = regionL.get(i);
   	try
   	{
       url = new URL("http://query.yahooapis.com/v1/public/yql?q=USE%20%22http%3A%2F%2Fsearchdeck.appspot.com%2F"+fileprefix+".search.xml"
       		+ "%22%20AS%20craig_table%3B%20SELECT%20item%20FROM%20craig_table%20where%20location%20in%20("+states+")%20and%20type%20%3D%20'"+inType+"'%20and%20query%20%3D%20'"
       		+query+ "'"+urlParams+ "%20%7C%20sort(field%3D%22item.date%22%20%2Cdescending%3D%22true%22)%20%7Ctruncate("+limit+")&format=json");
        connection = url.openConnection();
       connection.setConnectTimeout(200000000);
       connection.setReadTimeout(200000000);
        in = new BufferedReader(new InputStreamReader(connection
               .getInputStream()));
       
      
       while ((inputLine = in.readLine()) != null) {
              // System.out.println( inputLine);
               s = inputLine ;
           }
       resList.add(s);
//       l.info(url.toString());
//       l.info(s);
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   		if(url!=null)
         l.severe(url.toString());
//         l.info(s);
   		 l.severe(e.getMessage());
   	}
       //url.
   	}
		
		
		
		
		return resList;
	}
	
	
	
	static String join(Collection<?> s, String delimiter) {
	     StringBuilder builder = new StringBuilder();
	     Iterator iter = s.iterator();
	     while (iter.hasNext()) {
	         builder.append(iter.next());
	         if (!iter.hasNext()) {
	           break;                  
	         }
	         builder.append(delimiter);
	     }
	     return builder.toString();
	 }

	
	
	private static String getJSONPObject(String callback, JSONObject s) throws JSONException {
	    return callback + "(" + s + ")";
	}
}
