package org.alma.middleware.coffeedream;

import org.alma.middleware.coffeedream.Bean.UserBean;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.io.File;
import java.util.Map;

/**
 * Created by david on 03/12/15.
 */
public class MapDBStorage {

    private DB database;

    private Map<String,String> authents;
    private Map<String,UserBean> identities;

    public MapDBStorage() {
        database = DBMaker.fileDB(new File("mapdb/authentification.db"))
                .transactionDisable()
                .make();

        if (database.exists("authentification")) {
            authents = database.treeMap("authentification");

        } else {
            authents = database.treeMapCreate("authentification")
                    .keySerializer(Serializer.STRING)
                    .makeOrGet();
        }

        if (database.exists("identities")) {
            identities = database.treeMap("identities");

        } else {
            identities = database.treeMapCreate("identities")
                    .keySerializer(Serializer.STRING)
                    .makeOrGet();
        }

    }

    public String getImei(String token) {
       return this.authents.get(token);
    }

    public UserBean getUserByToken(String token) {
        return  getUser(getImei(token));
    }

    public UserBean getUser(String imei) {
        return identities.get(imei);
    }

    public void putToken(String token,String imei) {
        this.authents.put(token,imei);
    }

    public void putUser(String imei,UserBean user) {
        this.identities.put(imei,user);
    }

    public void closeDB() {
        database.close();
    }

    public boolean containsImei(String imei) {
        return identities.containsKey(imei);
    }

    public boolean containsToken(String token) {
        return authents .containsKey(token);
    }

}
