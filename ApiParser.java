package apiparser;

import java.util.HashMap;
import java.util.Map;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiParser {

    private final String API_URL = "https://api.mailmailmail.net/v1.1/";
    public String username;
    public String token;
    public String endpoint;

    public void post(Map params, Map ContactFields) throws Exception {

        String url = API_URL + endpoint;
        URL obj = new URL(url);

        String url_params = URLBuilder.httpBuildQuery(params, null);
        String url_fields = URLBuilder.httpBuildQuery(ContactFields, null);
        String encodedData = url_params + "&" + url_fields;

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("Accept", "application/json; charset=utf-8");
        con.setRequestProperty("ApiUsername", username);
        con.setRequestProperty("ApiToken", token);

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(encodedData);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + encodedData);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }

    public void get(Map params) throws Exception {

        String encodedData = URLBuilder.httpBuildQuery(params, null);
        String url = API_URL+endpoint+"?"+encodedData;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("Accept", "application/json; charset=utf-8");
        con.setRequestProperty("ApiUsername", username);
        con.setRequestProperty("ApiToken", token);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }

    public void AddSubscriber() throws Exception {

        endpoint = "Subscribers/AddSubscriberToList";

        // Function params
        Map<String, String> params = new HashMap<String, String>();
        params.put("listid", "19823");
        params.put("emailaddress", "kasper+9@emailplatform.com");
        params.put("mobile", "0");
        params.put("mobilePrefix", "0");
        params.put("add_to_autoresponders", "1");
        params.put("skip_listcheck", "1");
        params.put("confirmed", "0");

        // ContactFields
        HashMap<String, HashMap<String, HashMap<String, String>>> ContactFields = new HashMap<String, HashMap<String, HashMap<String, String>>>();
        ContactFields.put("contactFields", new HashMap<String, HashMap<String, String>>());

        // First Name
        ContactFields.get("contactFields").put("0", new HashMap<String, String>());
        ContactFields.get("contactFields").get("0").put("fieldid", "2");
        ContactFields.get("contactFields").get("0").put("value", "Kasper");

        // Last Name
        ContactFields.get("contactFields").put("1", new HashMap<String, String>());
        ContactFields.get("contactFields").get("1").put("fieldid", "3");
        ContactFields.get("contactFields").get("1").put("value", "Bang");

        post(params, ContactFields);

    }

    public void GetSubscribers() throws Exception {

        endpoint = "Subscribers/GetSubscribers";
        // Function params
        Map<String, String> params = new HashMap<String, String>();
        params.put("searchinfo[List]", "19823");
        params.put("countonly", "0");
        params.put("limit", "1000");
        params.put("offset", "0");

        get(params);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        ApiParser parser = new ApiParser();
        parser.username = "username";
        parser.token = "token";
        
        parser.GetSubscribers();
        //parser.AddSubscriber();

    }

}
