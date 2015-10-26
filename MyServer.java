CSC 460
By: Kyle Marcum
Uses sockets to communicate with client.

import java.net.*;
import java.io.*;
import java.util.*;  // for random numbers
 
public class MyServer {
	
	static String question;		//holds each line from text file as string
	static int points;			//holds number of points given for each question
	static int totalPoints;		//calculates total points to date
	static int numQuestion = 0;	//index for questions stored in array list
	
    public static void main(String[] args) throws IOException {
    	
        try
        {
            ServerSocket serverSocket = new ServerSocket(8241);
            Socket clientSocket = serverSocket.accept();     
            PrintWriter out =
            new PrintWriter(clientSocket.getOutputStream(),
                      true);                   
            BufferedReader in = new BufferedReader(
            new InputStreamReader(clientSocket.getInputStream()));
    
            ArrayList<String> list = getQuestions();
            
            for( int i = 0; i < 10; i++ )		// loop thru array list and send strings to client for each question
            {
	        out.println(list.get(numQuestion++));
	        out.println(list.get(numQuestion++));
	        out.println(list.get(numQuestion++));
	        out.println(list.get(numQuestion++));
	        out.println(list.get(numQuestion++));
	        String wrongAnswer = list.get(numQuestion);
	        checkAnswer(in.readLine(), wrongAnswer);		// call to method that checks and scores answer from client
	        out.println(list.get(numQuestion++));
	        out.println(Integer.toString(points));
	        out.println(Integer.toString(totalPoints));
            }
            serverSocket.close();
            
        }
        
	    catch (IOException e) 
	    {
	    	System.out.println("Exception caught when trying to listen on port 8241 or listening for a connection");
	    	System.out.println(e.getMessage());
	    }
    }  // end main
    
   public static ArrayList<String> getQuestions() throws FileNotFoundException, IOException
    {
	   String line = null;
   	ArrayList<String> list= new ArrayList<String>();
   	try (BufferedReader br = new BufferedReader(new FileReader("src/questions.txt"))) 
   	{
       		while((line = br.readLine()) != null)	// while not at EOF, put each line into array list as string
       	    {
       			list.add(line);	
       	    }
       		 return list;
       }
    	
    }
   
   public static void checkAnswer(String userAnswer, String wrongAnswer) throws FileNotFoundException, IOException
   {
	   String answer = userAnswer;
	   String wrong = wrongAnswer;
	   
	   if(answer.contains(wrong))		// if wrong answer then minus 250 points
	   {
		   points = -250;
	   }
	   else if(answer.length() == 3)	// if 3 correct answers then plus 750 points
	   {
		   points = 750;
	   }
	   else if(answer.length() == 2)	// if 2 correct answers then plus 500 points
	   {
		   points = 500;
	   }
	   else if(answer.length() == 1)	// if 1 correct answer then plus 250 points
	   {
		   points = 250;
	   } 
	   
	   totalPoints += points;			// add up total points to date
   }

}  // end class
