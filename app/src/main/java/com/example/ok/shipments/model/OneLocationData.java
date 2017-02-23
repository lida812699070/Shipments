package com.example.ok.shipments.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${lida} on 2016/6/7.
 */
public class OneLocationData {

    /**
     * status : 0
     * message : 成功
     * size : 1
     * total : 1
     * entities : [{"entity_name":"789","create_time":"2016-06-06 19:05:00","modify_time":"2016-06-07 10:57:53","realtime_point":{"loc_time":1465268272,"location":[113.82850330924,22.65898384219],"radius":396,"speed":1.15966,"direction":138}}]
     */

    private int status;
    private String message;
    private int size;
    private int total;
    /**
     * entity_name : 789
     * create_time : 2016-06-06 19:05:00
     * modify_time : 2016-06-07 10:57:53
     * realtime_point : {"loc_time":1465268272,"location":[113.82850330924,22.65898384219],"radius":396,"speed":1.15966,"direction":138}
     */

    private List<EntitiesBean> entities;

    public static OneLocationData objectFromData(String str) {

        return new Gson().fromJson(str, OneLocationData.class);
    }

    public static OneLocationData objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), OneLocationData.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<OneLocationData> arrayOneLocationDataFromData(String str) {

        Type listType = new TypeToken<ArrayList<OneLocationData>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<OneLocationData> arrayOneLocationDataFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<OneLocationData>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<EntitiesBean> getEntities() {
        return entities;
    }

    public void setEntities(List<EntitiesBean> entities) {
        this.entities = entities;
    }

    public static class EntitiesBean {
        private String entity_name;
        private String create_time;
        private String modify_time;
        /**
         * loc_time : 1465268272
         * location : [113.82850330924,22.65898384219]
         * radius : 396
         * speed : 1.15966
         * direction : 138
         */

        private RealtimePointBean realtime_point;

        public static EntitiesBean objectFromData(String str) {

            return new Gson().fromJson(str, EntitiesBean.class);
        }

        public static EntitiesBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), EntitiesBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<EntitiesBean> arrayEntitiesBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<EntitiesBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<EntitiesBean> arrayEntitiesBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<EntitiesBean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(str), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public String getEntity_name() {
            return entity_name;
        }

        public void setEntity_name(String entity_name) {
            this.entity_name = entity_name;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getModify_time() {
            return modify_time;
        }

        public void setModify_time(String modify_time) {
            this.modify_time = modify_time;
        }

        public RealtimePointBean getRealtime_point() {
            return realtime_point;
        }

        public void setRealtime_point(RealtimePointBean realtime_point) {
            this.realtime_point = realtime_point;
        }

        public static class RealtimePointBean {
            private int loc_time;
            private int radius;
            private double speed;
            private int direction;
            private List<Double> location;

            public static RealtimePointBean objectFromData(String str) {

                return new Gson().fromJson(str, RealtimePointBean.class);
            }

            public static RealtimePointBean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), RealtimePointBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<RealtimePointBean> arrayRealtimePointBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<RealtimePointBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<RealtimePointBean> arrayRealtimePointBeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<RealtimePointBean>>() {
                    }.getType();

                    return new Gson().fromJson(jsonObject.getString(str), listType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return new ArrayList();


            }

            public int getLoc_time() {
                return loc_time;
            }

            public void setLoc_time(int loc_time) {
                this.loc_time = loc_time;
            }

            public int getRadius() {
                return radius;
            }

            public void setRadius(int radius) {
                this.radius = radius;
            }

            public double getSpeed() {
                return speed;
            }

            public void setSpeed(double speed) {
                this.speed = speed;
            }

            public int getDirection() {
                return direction;
            }

            public void setDirection(int direction) {
                this.direction = direction;
            }

            public List<Double> getLocation() {
                return location;
            }

            public void setLocation(List<Double> location) {
                this.location = location;
            }
        }
    }
}
