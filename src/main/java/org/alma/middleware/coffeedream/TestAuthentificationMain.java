package org.alma.middleware.coffeedream;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * Created by david on 04/12/15.
 */
public class TestAuthentificationMain {

    public static void main( String[] args) throws IOException, JSONException {

        CloseableHttpClient httpClient = HttpClients.createDefault();


        /**
         * Création et alimentation de la base
         *
         */
        HttpPost requestAlimentation = new HttpPost("http://localhost:8080/coffeedream/alimentation");
        requestAlimentation.setHeader("Content-type","application/json");
        requestAlimentation.setHeader("Accept","application/json");

        CloseableHttpResponse response = httpClient.execute(requestAlimentation);

        System.out.println(response);


        /**
         * test sur l'appel par imei
         * Exemple utilisé :
         *  imei 123456789012345 ,
         *  utilisateur attendu : "0600000001", "John", "Doe"
         */

        System.out.println("\n----- Appel sur http://localhost:8080/coffeedream/auth avec imei : 123456789012345 ----\n");
        HttpPost requestAuth = new HttpPost("http://localhost:8080/coffeedream/auth");
        requestAuth.setHeader("Content-type","application/json");
        requestAuth.setHeader("Accept","application/json");

        // mise en place d'un object json pour la requête
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("imei", "123456789012345");

        requestAuth.setEntity(new StringEntity(jsonParam.toString(), "UTF-8"));

        // Execution de la requête
        CloseableHttpResponse authresponse = httpClient.execute(requestAuth);

        // Affichage des données de la réponse (statusCode + headers)
        System.out.println(authresponse);

        // Récupération d'une valeur dans les headers
        Header header = response.getFirstHeader("Content-Type");
        System.out.println(header.getValue());

        // Affichage du contenu de la réponse
        String authResponse = getContextAsString(response);
        System.out.println(authResponse);

        // Récupération du token pour la prochaine requête
        String token = getToken(authResponse);

        /**
         * test de l'appel sur l'url pour obtenir les informations utilisateurs pour un token récupéré par l'appel auth
         */
        System.out.println("\n----- Appel sur http://localhost:8080/coffeedream/token avec token : "+token+" ----\n");

        HttpPost requestToken = new HttpPost("http://localhost:8080/coffeedream/token");
        requestToken.setHeader("Content-type","application/json");
        requestToken.setHeader("Accept","application/json");

        // mise en place d'un object json pour la requête
        jsonParam = new JSONObject();
        jsonParam.put("token", token);

        requestToken.setEntity(new StringEntity(jsonParam.toString(), "UTF-8"));

        // Execution de la requête
        response = httpClient.execute(requestToken);

        // Affichage des données de la réponse (statusCode + headers)
        System.out.println(response);

        // Récupération d'une valeur dans les headers
        header = response.getFirstHeader("Content-Type");
        System.out.println(header.getValue());

        // Affichage du contenu de la réponse
        String tokenResponse = getContextAsString(response);
        System.out.println(tokenResponse);


    }
    protected static String getContextAsString(HttpResponse response) throws IOException {

        StringWriter writer = new StringWriter();
        InputStream inputStream = response.getEntity().getContent();
        try {
            IOUtils.copy(inputStream, writer, "UTF-8");
        } finally {
            inputStream.close();
        }
        return writer.toString();
    }

    protected static String getToken(String jsonString) throws JSONException {
       JSONObject tmpJson = new JSONObject(jsonString);
       JSONObject resJson = (JSONObject) tmpJson.get("answer");

       return resJson.get("token").toString();
    }
}
