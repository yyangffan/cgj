package com.lhkj.cgj.network.response;

/**
 * Created by 浩琦 on 2017/6/30.
 */

public class UserResponse extends HttpResponse {

    /**
     * code : 200
     * info : {"user_id":"53","password":"d41d8cd98f00b204e9800998ecf8427e","pay_points":"0","reg_time":"1499741380","last_login":"0","last_ip":"","mobile":"15531161308","head_pic":"","nickname":null,"level":"1","is_lock":"0","sex":null,"car_brand":null,"car_type":null,"car_guishu":null,"car_number":null,"car_date":null,"car_km":null,"bind_oil":null,"bind_oil_time":null,"porn":"gcpw53","parent_id":null,"points":"0"}
     */

    private InfoBean info;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public class InfoBean {

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        private String token;

        /**
         * user_id : 53
         * password : d41d8cd98f00b204e9800998ecf8427e
         * pay_points : 0
         * reg_time : 1499741380
         * last_login : 0
         * last_ip :
         * mobile : 15531161308
         * head_pic :
         * nickname : null
         * level : 1
         * is_lock : 0
         * sex : null
         * car_brand : null
         * car_type : null
         * car_guishu : null
         * car_number : null
         * car_date : null
         * car_km : null
         * bind_oil : null
         * bind_oil_time : null
         * porn : gcpw53
         * parent_id : null
         * points : 0
         */

        private String user_id;
        private String password;
        private String pay_points;
        private String reg_time;
        private String last_login;
        private String last_ip;
        private String mobile;
        private String head_pic;
        private String nickname;
        private String level;
        private String is_lock;
        private String sex;
        private String car_brand;
        private String car_type;
        private String car_guishu;
        private String car_number;
        private String car_date;
        private String car_km;
        private String bind_oil;
        private String bind_oil_time;
        private String porn;
        private String parent_id;
        private String points;
        private String oil_type;
        private String oil_type_name;

        public String getOil_type_name() {
            return oil_type_name;
        }

        public void setOil_type_name(String oil_type_name) {
            this.oil_type_name = oil_type_name;
        }

        public String getOil_type() {
            return oil_type;
        }

        public void setOil_type(String oil_type) {
            this.oil_type = oil_type;
        }

        public String getOil_name() {
            return oil_name;
        }

        public void setOil_name(String oil_name) {
            this.oil_name = oil_name;
        }

        private String oil_name;

        public String getUser_id() {
            return isNull(user_id);
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPay_points() {
            return pay_points;
        }

        public void setPay_points(String pay_points) {
            this.pay_points = pay_points;
        }

        public String getReg_time() {
            return reg_time;
        }

        public void setReg_time(String reg_time) {
            this.reg_time = reg_time;
        }

        public String getLast_login() {
            return last_login;
        }

        public void setLast_login(String last_login) {
            this.last_login = last_login;
        }

        public String getLast_ip() {
            return last_ip;
        }

        public void setLast_ip(String last_ip) {
            this.last_ip = last_ip;
        }

        public String getMobile() {
            return isNull(mobile);
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getHead_pic() {
            return isNull(head_pic);
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getNickname() {
            return isNull(nickname);
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getIs_lock() {
            return is_lock;
        }

        public void setIs_lock(String is_lock) {
            this.is_lock = is_lock;
        }

        public String getSex() {
            return isNull(sex);
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getCar_brand() {
            return car_brand;
        }

        public void setCar_brand(String car_brand) {
            this.car_brand = car_brand;
        }

        public String getCar_type() {
            return car_type;
        }

        public void setCar_type(String car_type) {
            this.car_type = car_type;
        }

        public String getCar_guishu() {
            return car_guishu;
        }

        public void setCar_guishu(String car_guishu) {
            this.car_guishu = car_guishu;
        }

        public String getCar_number() {
            return car_number;
        }

        public void setCar_number(String car_number) {
            this.car_number = car_number;
        }

        public String getCar_date() {
            return car_date;
        }

        public void setCar_date(String car_date) {
            this.car_date = car_date;
        }

        public String getCar_km() {
            return car_km;
        }

        public void setCar_km(String car_km) {
            this.car_km = car_km;
        }

        public String getBind_oil() {
            return isNull(bind_oil);
        }

        public void setBind_oil(String bind_oil) {
            this.bind_oil = bind_oil;
        }

        public String getBind_oil_time() {
            return bind_oil_time;
        }

        public void setBind_oil_time(String bind_oil_time) {
            this.bind_oil_time = bind_oil_time;
        }

        public String getPorn() {
            return porn;
        }

        public void setPorn(String porn) {
            this.porn = porn;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getPoints() {
            return isNull(points);
        }

        public void setPoints(String points) {
            this.points = points;
        }

        private String isNull(String s) {
            if (s == null) {
                return "";
            } else {
                return s;
            }
        }
    }
}
