package com.lhkj.cgj.network.response;

/**
 * Created by 浩琦 on 2017/7/18.
 */

public class CarPostResponse extends HttpResponse {

    /**
     * code : 200
     * success : 获取成功
     * info : {"car_number":null,"car_date":null,"car_km":null,"brand_name":null,"diqu_name":null,"series_name":"爱唯欧(海外)","is_imported":"0"}
     */

    private InfoBean info;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public class InfoBean {
        /**
         * car_number : null
         * car_date : null
         * car_km : null
         * brand_name : null
         * diqu_name : null
         * series_name : 爱唯欧(海外)
         * is_imported : 0
         */

        private String car_number;
        private String car_date;
        private String car_km;
        private String brand_name;
        private String diqu_name;
        private String series_name;
        private String is_imported;
        public String car_brand;
        public String car_type;
        public String car_guishu;

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

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public String getDiqu_name() {
            return diqu_name;
        }

        public void setDiqu_name(String diqu_name) {
            this.diqu_name = diqu_name;
        }

        public String getSeries_name() {
            return series_name;
        }

        public void setSeries_name(String series_name) {
            this.series_name = series_name;
        }

        public String getIs_imported() {
            return is_imported;
        }

        public void setIs_imported(String is_imported) {
            this.is_imported = is_imported;
        }
    }
}
