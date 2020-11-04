package com.cqut.learn.MongoDB;

import com.cqut.learn.LitePalDB.CET4;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoDBUtil {

    String username = "admin_test";
    String host = "www.inmagician.xyz";
    int port = 2222;
    String dbname = "TEST";
    String password = "123456";
    public void startAndInsertWords(final List<CET4> words) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<10;i++) {
                    MongoDatabase mongodb = MongoDBUtil.getConnect2(host, port, username, dbname, password);
                    final MongoCollection<Document> docs = mongodb.getCollection("words");
                    insertWords(docs,"lixin");
                }
            }
        }).start();

    }

    public void insertWords(MongoCollection<Document> docs, String words) {
////创建文

            Document document = new Document("headWord", words);
            System.out.println("开始插入");
            docs.insertOne(document);
            System.out.println("插入成功");

    }
    public static MongoDatabase getConnect2(String host, int port, String userName, String dbName, String password) {
        List<ServerAddress> adds = new ArrayList<>();
        //ServerAddress()两个参数分别为 服务器地址 和 端口
        ServerAddress serverAddress = new ServerAddress(host, port);
        adds.add(serverAddress);

        List<MongoCredential> credentials = new ArrayList<>();
        //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(userName, dbName, password.toCharArray());
        credentials.add(mongoCredential);

        //通过连接认证获取MongoDB连接
        MongoClient mongoClient = new MongoClient(adds, credentials);

        //连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);

        //返回连接数据库对象
        return mongoDatabase;
    }

}
