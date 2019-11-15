package com.zyzh.test;

import com.zyzh.utils.RedisUtils;
import redis.clients.jedis.Jedis;

import java.sql.*;
import java.util.Random;

public class Test {
    private String url = "jdbc:mysql://192.168.1.158:3306/springtest?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false";
    private String user = "root";
    private String password = "310012";
    public void test(){
        Connection conn = null;
        PreparedStatement pstm =null;
        ResultSet rt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO userinfo(uid,uname,uphone,uaddress) VALUES(?,CONCAT('姓名',?),?,?)";
            pstm = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            Long startTime = System.currentTimeMillis();
            Random rand = new Random();
            int a,b,c,d;
            for (long i = 1; i <= 1000000; i++) {
                pstm.setLong(1, i);
                pstm.setLong(2, i);
                a = rand.nextInt(10);
                b = rand.nextInt(10);
                c = rand.nextInt(10);
                d = rand.nextInt(10);
                pstm.setString(3, "188"+a+"88"+b+c+"66"+d);
                pstm.setString(4, "xxxxxxxxxx_"+"188"+a+"88"+b+c+"66"+d);
                System.out.println(i);
                pstm.addBatch();
                if(i%50000 == 0){
                    pstm.executeBatch();
                    pstm.clearBatch();
                    conn.commit();
                }
            }
            Long endTime = System.currentTimeMillis();
            System.out.println("OK,用时：" + (endTime - startTime));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally{
            if(pstm!=null){
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void testRedis(){
        Jedis jedis = RedisUtils.getJedis();
        jedis.ping();
    }

}
