package com.amg.simplecalculatorserver.server;


import com.amg.simplecalculatorserver.ServerController;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public
class RequestHandler {

    ClientHandler clientHandler;
    ServerHandler serverHandler;
    HBox hBox;
    boolean started = false;
    Integer num1;
    Integer num2;
    String operator;

    public RequestHandler(ClientHandler clientHandler, ServerHandler serverHandler) {

        this.clientHandler = clientHandler;
        this.serverHandler = serverHandler;

    }

    public void executeRequest(String request) {
String response = null;

        if(request.equals("start")){
                if (!started) {
                    started = true;
                    response="OK";
                } else {
                    response="already started!";

                }
        }
        else if(request.equals("exit")){
            started=false;
            num1=null;
            num2=null;
            operator=null;
            response="exit";
            try {
                clientHandler.getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
clientHandler.interrupt();
        }
        else if(num1==null ){
           try{
            num1= Integer.valueOf(request);
            response="first number accepted";
           }
           catch (NumberFormatException numberFormatException){
               response="bad input!";
           }


        }
        else if(num2==null ){
            try{
                num2= Integer.valueOf(request);
                response="second number accepted";
            }
            catch (NumberFormatException numberFormatException){
                response="bad input!";
            }


        }
        else if(operator==null ){
            operator=request;
            int answer = 0;
            switch (operator){
                case "+":
                    answer=num1+num2;
                    num1=null;
                    num2=null;
                    operator=null;
                    started=false;
                    break;
                case "-":
                    answer=num1-num2;
                    num1=null;
                    num2=null;
                    operator=null;
                    started=false;
                    break;
                case "*":
                    answer=num1*num2;
                    num1=null;
                    num2=null;
                    operator=null;
                    started=false;
                    break;
                case "/":
                    answer=num1/num2;
                    num1=null;
                    num2=null;
                    operator=null;
                    started=false;
                    break;
                case "^":
                    answer= (int) Math.pow(num1,num2);
                    num1=null;
                    num2=null;
                    operator=null;
                    started=false;
                    break;
                default:
                    response="bad input!";
                    operator=null;

            }
        if(response==null)response=String.valueOf(answer);
        }
sendResponse(clientHandler.getPrintWriter(),response);
    }


    public void sendResponse(PrintWriter printWriter, String response) {
        ServerController.writeOnConsole("Sending Response to "+clientHandler.getId()+":["+response+"] at "+ LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        printWriter.println(response);

    }

}
