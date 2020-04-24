package priv.jesse.mall.entity.pojo;

import priv.jesse.mall.entity.OrderItem;

import java.io.Serializable;
import java.util.List;

/**
 * 接口返回数据模型
 *
 */
public class Detail {
    List<OrderItem> orderItems;
    String total;
    String phone;
    String time;
    String endtime;

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
