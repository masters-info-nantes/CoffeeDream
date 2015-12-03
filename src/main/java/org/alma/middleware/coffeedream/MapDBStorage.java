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
                .closeOnJvmShutdown()
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
                    .keySerializer(Serializer.LONG)
                    .makeOrGet();
        }

    }

    public String getToken(String imei) {
       return this.authents.get(imei);
    }

    public UserBean getUser(String token) {
        return identities.get(token);
    }

    public void putToken(String imei,String token) {
        this.authents.put(imei,token);
    }

    public void putUser(String imei,UserBean user) {
        this.identities.put(imei,user);
    }

    public void closeDB() {
        database.close();
    }

}
