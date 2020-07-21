package com.usaas.alice


import android.content.res.Resources
import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase

public class DatabaseOps {

    lateinit var vmongoClient: MongoClient
    lateinit var app_db_object: MongoDatabase
    lateinit var app_db_coll_object: MongoCollection<*>
    lateinit var db_host_name: String
    lateinit var db_user_id: String
    lateinit var db_user_pwd: String
    lateinit var app_db_name: String

    //Method to get Mongo CLient//
    public fun f_getMongoClient(): MongoClient? {

        if (vmongoClient == null) {
            db_host_name = Resources.getSystem().getString(R.string.db_host_name)
            db_user_id = Resources.getSystem().getString(R.string.db_user_id)
            db_user_pwd = Resources.getSystem().getString(R.string.db_user_pwd)
            val uri = MongoClientURI("mongodb://" + db_user_id + ":" + db_user_pwd + "@" + db_host_name + "/?authSource=db1&ssl=true")
            vmongoClient = MongoClient(uri)
        }

        return vmongoClient

    }

    //Method to get database instance//
    public fun f_getMongoDatabase() {

        app_db_name = Resources.getSystem().getString(R.string.db_name)
        app_db_object = vmongoClient.getDatabase(app_db_name)

    }


    //Method to get collection object//
    public fun f_getMongoCollection(input_coll_name: String) {

        app_db_coll_object = app_db_object.getCollection(input_coll_name)

    }


    //Method to close the database//
    public fun f_closeDB() {

        vmongoClient.close()

    }


}