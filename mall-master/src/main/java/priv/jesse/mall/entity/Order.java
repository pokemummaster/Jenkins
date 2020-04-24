package priv.jesse.mall.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 订单
 */
@Entity
@Table(name = "`order`")
public class Order implements Serializable {
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Id
    @GeneratedValue
    @Column

    private Integer id;
    /**
     * 订单总价
     */
    @Column
    private Double total;
    @Column
    private String time;
    /**
     * 订单状态
     */
    @Column
    private Integer state;
    /**
     * 订单时间
     */
    @Column
    private Date orderTime;
    /**
     * 收货人姓名
     */
    @Column(name = "`name`")
    private String name;
    /**
     * 收货人联系电话
     */
    @Column
    private String phone;
    /**
     * 收货地址
     */
    @Column
    private String onubmer;
    @Column
    private String number;
    /**
     * 用户Id
     */
    @Column
    private Integer userId;

    private static final long serialVersionUID = 1L;



    public Order() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getOnubmer() {
        return onubmer;
    }

    public void setOnubmer(String onubmer) {
        this.onubmer = onubmer;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }



    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Order other = (Order) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTotal() == null ? other.getTotal() == null : this.getTotal().equals(other.getTotal()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getOrderTime() == null ? other.getOrderTime() == null : this.getOrderTime().equals(other.getOrderTime()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTotal() == null) ? 0 : getTotal().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getOrderTime() == null) ? 0 : getOrderTime().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        return result;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", total=" + total +
                ", time='" + time + '\'' +
                ", state=" + state +
                ", orderTime=" + orderTime +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", onubmer='" + onubmer + '\'' +
                ", number='" + number + '\'' +
                ", userId=" + userId +
                '}';
    }

    public Order(Double total, String time, Integer state, Date orderTime, String name, String phone, String onubmer, String number, Integer userId) {
        this.total = total;
        this.time = time;
        this.state = state;
        this.orderTime = orderTime;
        this.name = name;
        this.phone = phone;
        this.onubmer = onubmer;
        this.number = number;
        this.userId = userId;
    }
}