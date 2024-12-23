/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import GUI.ChucNang_NhanVien;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dao Huy Vu
 */
public class ApiService {
    
    private final String employeeRequestPrefix = "http://localhost:9192/employee";
    public int getLatestEmployeeId(){
         try {
            URL url = new URI(employeeRequestPrefix + "/latestId").toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");


            StringBuilder response;
             try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                 String line;
                 response = new StringBuilder();
                 while ((line = reader.readLine()) != null) {
                     response.append(line);
                 }}

            // Print response
            return Integer.parseInt(response.toString()) + 1;
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        } catch (URISyntaxException ex) {
            Logger.getLogger(ApiService.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new RuntimeException("Can't get latest ID");
    }
    public String formatEmployeeId(int currentEmployeeId){
       return "NV" + String.format("%06d", currentEmployeeId);
    }
    public void pushEmployeeData(
            String employeeId,
            String name,
            String gender,
            String dateOfBirth,
            String pinCode,
            String publicKey
    ){
        HttpURLConnection connection = null;
        String requestBody = String.format("""
                            {
                                                 "id": "%s",
                                                 "name": "%s",
                                                 "gender": "%s",
                                                 "dateOfBirth": "%s",
                                                 "pinCode": "%s",
                                                 "publicKey": "%s"
                                             } 
                            """,employeeId,name,gender,dateOfBirth,pinCode,publicKey);
         try {
            URL url = URI.create(employeeRequestPrefix).toURL();
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accpet", "application/json");
            connection.setDoOutput(true);
            
         try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes("UTF-8");
                os.write(input,0,input.length);
            }

            // Get the response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
          finally {
            // Ensure connection is closed
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    public void updateData(String data){
       HttpURLConnection connection = null;
         try {
            URL url = URI.create(employeeRequestPrefix).toURL();
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            String body = String.format(Locale.getDefault(),"fields=%s&employeeId=%s",data,ChucNang_NhanVien.employeeId);
         try (OutputStream os = connection.getOutputStream()) {
                byte[] input = body.getBytes("UTF-8");
                os.write(input,0,input.length);
            }

            // Get the response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
          finally {
            // Ensure connection is closed
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
