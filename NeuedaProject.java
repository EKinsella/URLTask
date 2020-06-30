/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuedaproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.net.URL;
import java.util.*;

/**
 *
 * @author EPKinsella
 * Description
 * Most of us are familiar with seeing URLs like bit.ly or t.co on our Twitter or Facebook feeds. 
 * These are examples of shortened URLs, which are a short alias or pointer to a longer page link. 
 * For example, I can send you the shortened URL http://bit.ly/SaaYw5 
 * that will forward you to a very long Google URL with search results on how to iron a shirt. 
 * Mandatory Requirements
 * •	Design and implement an API for short URL creation
 * •	Implement forwarding of short URLs to the original ones
 * •	There should be some form of persistent storage
 * •	The application should be distributed as one or more Docker images
 * Additional Requirements
 * •	Design and implement an API for gathering different statistics
 * Assessment
 * Treat this as a real project. It should be readable, maintainable, and extensible where appropriate.
 * The implementation should preferably be in Java, however any language can be used.
 * If you will transfer it to another team - it should be clear how to work with it and what is going on.
 * You should send us a link to a Git repository that we will be able to clone.
 */

public class NeuedaProject {

    private HashMap<String, String> keyValue;
    private int keyLength;
    private char results[];
    static String domainName;
    private Random randomNum;

    public NeuedaProject()
    {
        keyValue = new HashMap<>();
        keyLength = 10;
        ////Using 62 bit encryption algorithm
        int num = 0;
        results = new char[62];
        int remainder;
        randomNum = new Random();
        while(num > 0)
        {
            remainder = num % 62;
            num = num / 62;
        }
        int j = 0;
        if(num <= 10)
        {
            j = num + 61;
        }
        results[num] = (char)j;
    }
    
    //Defining the url and length
    private NeuedaProject(String url, int length) {
        this.keyLength = length;
        if(url.isEmpty())
        {
            url = domainName;
        }
    }
    
    public static void main(String[] args) throws MalformedURLException, IOException {
        // TODO code application logic here
        
        NeuedaProject url = new NeuedaProject("http://t.co", 8);
        String urlArray[] = {"http://bit.ly/SaaYw5"};
        //Testing with different web links "http://bit.ly/gkr5op", "http://bit.ly/cb74","www.rte.ie", "www.bbc.com", "www.irishtimes.com", "www.tinyurl.com"
        URL link = new URL("http://bit.ly/SaaYw5");
        BufferedReader in = new BufferedReader(new InputStreamReader(link.openStream())) ;

        while((domainName = in.readLine()) != null)
        {
            System.out.println("Extended URL:"+domainName);
        }
        
        for (String urlArray1 : urlArray) {
            System.out.println("Short url: " + urlArray1);
        }
        //Exception to check if url is null
        try{
            for (String urlArray1 : urlArray) {
                System.out.println("Extended url: " + url.extendedURL(urlArray1));
            }
        }
        catch(NullPointerException e)
        {
            System.out.println("");
        }        
    }

    private String extendedURL(String shortURL) {
        String sURL;
        shortURL = formatURL(shortURL);        
        String key = shortURL.substring(domainName.length() + 1);
	sURL = keyValue.get(key);
	return sURL;
    }

    private String formatURL(String shortURL) {
        if(shortURL.substring(0, 7).equals("http://"))
        {
            shortURL = shortURL.substring(7);
        }
        else if(shortURL.substring(0, 8).equals("https://"))
        {
            shortURL = shortURL.substring(8);
        }
        return shortURL;
    }

    private String getKeyValue(String shortURL) {
        String key = generateKey();
        keyValue.put(key, shortURL);
        return key;
    }

    private String generateKey() 
    {
        String key = "";
	boolean flag = true;
	while (flag) 
        {
            for (int i = 0; i <= keyLength; i++)
            {
                key += results[randomNum.nextInt(62)];
            }
            if (keyValue.containsKey(key)) 
            {
                flag = true;
            }
        }
        return key;
    }
}
