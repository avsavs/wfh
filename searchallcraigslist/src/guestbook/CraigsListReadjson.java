package guestbook;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.appengine.labs.repackaged.org.json.XML;

//import sun.misc.IOUtils;

public class CraigsListReadjson {
	
	SimpleDateFormat aSF = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
	public static int PRETTY_PRINT_INDENT_FACTOR = 4;
	
	private static final Logger log =  Logger.getLogger(CraigsListReadjson.class.getName());
	
	//String content = new Scanner(Readjson.class.getResourceAsStream("airports.txt")).useDelimiter("\\Z").next();
	
	
	
	
	public   List<JSONObject> filterJson(String sXml,String offset ,String limit,List<String> dateList) {
		//ClassLoader cl = Readjson.class.getClassLoader();
		JSONObject xmlJSONObj = null ;
		
		List<JSONObject> alist = new ArrayList<JSONObject>();
		//List<JSONObject> lastlist = new ArrayList<JSONObject>();
		//Boolean nextPage =false ;
		int totalCount = 0;
		//int count1 =0;
		boolean goExcp = false ;
		try
		{
			
			
		 xmlJSONObj = XML.toJSONObject(sXml);
			List<Date> aDateList = new ArrayList<Date>();
			 String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
	            System.out.println(jsonPrettyPrintString);
	            
	            JSONArray jarray  = new JSONArray ();
	            
//	            try{
	            	
	            	
	            	//Object = new JSONObject(contents.trim());
//	            	if (xmlJSONObj.has("item"))
//	            	{
//	                Iterator<?> keys = xmlJSONObj.keys();
//
//	                while( keys.hasNext() ){
//	                    String key = (String)keys.next();
//	                   if (key!=null && key.equalsIgnoreCase("item"))
//	                    if( xmlJSONObj.get("item") instanceof JSONObject ){
//
//	                    	JSONObject item = (JSONObject)xmlJSONObj.get("item");
//	    	 	            
//		 	            	//JSONObject aJSONObject = jarray.getJSONObject(i);
//		 	            //	 item =  jarray.getJSONObject(i);
//		 	            	String link = item.getString("link");
//		 	            	//String titlet = item.getString("title");
//		 	            	String title = item.getString("title");
//		 	            	JSONObject bJSONObject = new JSONObject();
//		 	            	bJSONObject.put("link", link);
//		 	            	String city = link.substring(7, link.indexOf("."));
//		 	            	
//		 	            	//String titleS = title.getString(0);
//		 	            	bJSONObject.put("title", title);
//		 	            	//bJSONObject.put("title", titlet);
//		 	            	bJSONObject.put("city", city);
//		 	            	
//		 	            	String date = item.getString("dc:date");
//		 	            	bJSONObject.put("date", date);
//		 	            	
//		 	            	
//		 	            	alist.add(bJSONObject);
//	                    }
//	                    else if (xmlJSONObj.get("item") instanceof JSONArray)
//	                    {
//	                    	jarray = (JSONArray)xmlJSONObj.get("item") ;
//	                    	totalCount = totalCount + jarray.length() ;
//	        	            
//	        	            for(int i=0 ; i < jarray.length(); i++) {
//	        	            	//JSONObject aJSONObject = jarray.getJSONObject(i);
//	        	            	JSONObject item =  jarray.getJSONObject(i);
//	        	            	String link = item.getString("link");
//	        	            	//String titlet = item.getString("title");
//	        	            	String title = item.getString("title");
//	        	            	JSONObject bJSONObject = new JSONObject();
//	        	            	bJSONObject.put("link", link);
//	        	            	String city = link.substring(7, link.indexOf("."));
//	        	            	
//	        	            	//String titleS = title.getString(0);
//	        	            	bJSONObject.put("title", title);
//	        	            	//bJSONObject.put("title", titlet);
//	        	            	bJSONObject.put("city", city);
//	        	            	
//	        	            	String date = item.getString("dc:date");
//	        	            	bJSONObject.put("date", date);
//	        	            	
//	        	            	
//	        	            	alist.add(bJSONObject);
//	                    }
//	                }
//	            	}
	           	          
	            
	            if (xmlJSONObj.get("rdf:RDF") instanceof JSONArray)
	            {
	            	
	            	if (xmlJSONObj.getJSONArray("rdf:RDF").getJSONObject(0).has("item"))
	            	if (xmlJSONObj.getJSONArray("rdf:RDF").getJSONObject(0).get("item") instanceof JSONArray)
	            	{
	            jarray = xmlJSONObj.getJSONArray("rdf:RDF").getJSONObject(0).getJSONArray("item");
	            totalCount = totalCount + jarray.length() ;
	            
	            for(int i=0 ; i < jarray.length(); i++) {
	            	//JSONObject aJSONObject = jarray.getJSONObject(i);
	            	JSONObject item =  jarray.getJSONObject(i);
	            	String link = item.getString("link");
	            	//String titlet = item.getString("title");
	            	String title = item.getString("title");
	            	JSONObject bJSONObject = new JSONObject();
	            	bJSONObject.put("link", link);
	            	String city = link.substring(7, link.indexOf("."));
	            	
	            	//String titleS = title.getString(0);
	            	bJSONObject.put("title", title);
	            	//bJSONObject.put("title", titlet);
	            	bJSONObject.put("city", city);
	            	
	            	String date = item.getString("dc:date");
	            	bJSONObject.put("date", date);
	            	
	            	
	            	alist.add(bJSONObject);
	            	
	            }
	            
	            	}
	            
		}
	            
	            if (xmlJSONObj.get("rdf:RDF") instanceof JSONObject)
	            {
	            	
	            	if(xmlJSONObj.getJSONObject("rdf:RDF").has("item"))
	            	
	            	if (xmlJSONObj.getJSONObject("rdf:RDF").get("item") instanceof JSONObject) 
	            	{
	            	JSONObject item = xmlJSONObj.getJSONObject("rdf:RDF").getJSONObject("item");
	 	            
	 	            	//JSONObject aJSONObject = jarray.getJSONObject(i);
	 	            //	 item =  jarray.getJSONObject(i);
	 	            	String link = item.getString("link");
	 	            	//String titlet = item.getString("title");
	 	            	String title = item.getString("title");
	 	            	JSONObject bJSONObject = new JSONObject();
	 	            	bJSONObject.put("link", link);
	 	            	String city = link.substring(7, link.indexOf("."));
	 	            	
	 	            	//String titleS = title.getString(0);
	 	            	bJSONObject.put("title", title);
	 	            	//bJSONObject.put("title", titlet);
	 	            	bJSONObject.put("city", city);
	 	            	
	 	            	String date = item.getString("dc:date");
	 	            	bJSONObject.put("date", date);
	 	            	
	 	            	
	 	            	alist.add(bJSONObject);
	 	            	
	            	
	            	
	            }
	            	
	            	else	if (xmlJSONObj.getJSONObject("rdf:RDF").get("item") instanceof JSONArray)
	            	{
	            jarray = xmlJSONObj.getJSONObject("rdf:RDF").getJSONArray("item");
	            totalCount = totalCount + jarray.length() ;
	            
	            for(int i=0 ; i < jarray.length(); i++) {
	            	//JSONObject aJSONObject = jarray.getJSONObject(i);
	            	JSONObject item =  jarray.getJSONObject(i);
	            	String link = item.getString("link");
	            	//String titlet = item.getString("title");
	            	String title = item.getString("title");
	            	JSONObject bJSONObject = new JSONObject();
	            	bJSONObject.put("link", link);
	            	String city = link.substring(7, link.indexOf("."));
	            	
	            	//String titleS = title.getString(0);
	            	bJSONObject.put("title", title);
	            	//bJSONObject.put("title", titlet);
	            	bJSONObject.put("city", city);
	            	
	            	String date = item.getString("dc:date");
	            	bJSONObject.put("date", date);
	            	
	            	
	            	alist.add(bJSONObject);
	            	
	            }
	            
	            	}
	            }
	           
	            
	            	        
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
			
			
		return alist;
		}
	
	
	
	
	

public   JSONObject filterJson(List<String> lListJon,String offset ,String limit,List<String> dateList) {
//ClassLoader cl = Readjson.class.getClassLoader();

	List<Date> aDateList = new ArrayList<Date>();
	
	Date pageDate = new Date();
    Date tempDate =  new Date();
    String pgDate = "";
JSONObject filterJSONObject = new JSONObject();
List<JSONObject> alist = new ArrayList<JSONObject>();
List<JSONObject> lastlist = new ArrayList<JSONObject>();
Boolean nextPage =false ;
int totalCount = 0;
int count1 =0;


if (lListJon == null || lListJon.size()==0)
{
	return filterJSONObject ;
}
//try {
	
for (String craigsListJon:lListJon )
{
//InputStream is = cl.getResourceAsStream("airports.txt");
try {
//	fis = new FileInputStream("airports.txt");
//
//	//try {
//		InputStreamReader in = new InputStreamReader(fis, "UTF-8");
//		
	
	
	String content = craigsListJon;
	//System.out.println(content);
	
	

		
		//InputStream input = Readjson.class.getResourceAsStream("airports.txt");
		
	//	String s = IOUtils.toString( input );

		
//String s =  input.toString();
JSONObject json = new JSONObject(content);
JSONArray jarray  = new JSONArray ();
String count = json.getJSONObject("query").getString("count");
count1 = new Integer(count);
totalCount =totalCount+ count1;
if((count1==100) && (!nextPage))
{
	nextPage=true;
}

if (count1>1)
 jarray = json.getJSONObject("query").getJSONObject("results").getJSONArray("RDF");
else if (count1==1)
{
	jarray.put(json.getJSONObject("query").getJSONObject("results").getJSONObject("RDF"));
	//filterJSONObject.accumulate("items", new JSONObject());
}
else 
{
	filterJSONObject.accumulate("items", new JSONObject());
}

//String count = json.getJSONObject("query").getString("count");

JSONArray filterArray = new JSONArray();

int off = new Integer(offset);
int lim = new Integer(limit);
int loopTill = lim+off ;
if (count1<loopTill)
{
	loopTill = count1 ;
}
//System.out.println("ssssssss" + searchresults.optString("count", ""));
//JSONArray array = searchresults.getJSONArray("URI");
for(int i=0 ; i < jarray.length(); i++) {
	JSONObject aJSONObject = jarray.getJSONObject(i);
	JSONObject item = aJSONObject.getJSONObject("item");
	String link = item.getString("about");
	//String titlet = item.getString("title");
	JSONArray title = item.getJSONArray("title");
	JSONObject bJSONObject = new JSONObject();
	bJSONObject.put("link", link);
	WriteToFile.writeFile(link.toString());
	WriteToFile.writeFile("\n");

	String city = link.substring(7, link.indexOf("."));
	
	String titleS = title.getString(0);
	bJSONObject.put("title", titleS);
	//bJSONObject.put("title", titlet);
	bJSONObject.put("city", city);
	
	String date = item.getString("date");
	bJSONObject.put("date", date);
	
	
	if(i ==(jarray.length()-1))
	{
		
		//log.info("dates in contention are"+ date);
		
		dateList.add(date);
		lastlist.add(bJSONObject);
		
	}
	else
	{
		alist.add(bJSONObject);
	}
		
	//	filterArray.put(aJSONObject);
		continue;
//	}
//	else if (code.toLowerCase().contains(craigsListJon.toLowerCase()))
//	{
//		filterJSONObject.accumulate("airports", aJSONObject);
//		continue;
//	}
//	else if (name.toLowerCase().contains(craigsListJon.toLowerCase()))
//	{
//		filterJSONObject.accumulate("airports", aJSONObject);
//		continue;
//	}
	
	
	
}
}




 catch(Exception e) {
e.printStackTrace();
}
//System.out.println("jarray [" + "] --------" + filterJSONObject.toString());
}
Collections.sort(alist, new CraigsComparator());
Collections.sort(lastlist, new CraigsComparator());
Collections.reverse(lastlist);
alist.addAll(lastlist);
//int count1 = new Integer(count);
int limit1  = new Integer(limit);
int offset1 = new Integer(offset);
int countFinal = count1;
//if (count1 == limit1)
if (count1 > 0)
	countFinal = limit1+offset1+1;
try {
	filterJSONObject.put("items", alist);
	if(nextPage)
	{
		totalCount++;
	}
	filterJSONObject.put("totalProperty", countFinal);
	
} catch (JSONException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

return filterJSONObject;
}



}