package priv.jesse.mall.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class Product implements Serializable {
    @Id
    @GeneratedValue
    @Column
    private Integer id;
    /**
     * 商品标题
     */
    @Column
    private String title;

    @Column
    private Integer amount;
    /**
     * 市场价
     */
    @Column
    private String level;
    /**
     * 商城价
     */
    @Column
    private Double shopPrice;
    /**
     * 主图
     */
    @Column
    private String image;
    /**
     * 描述
     */
    @Column(name = "`desc`")
    private String desc;
    /**
     * 是否旺季景点
     */
    @Column
    private Integer isHot;
    /**
     * 二级分类Id
     */
    @Column
    private Integer csid;
    /**
     * 商品创建日期
     */
    @Column
    private Date pdate;

    @Transient
    private Classification categorySec;

    public Classification getCategorySec() {
        return categorySec;
    }

    public void setCategorySec(Classification categorySec) {
        this.categorySec = categorySec;
    }

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", amount=" + amount +
                ", level=" + level +
                ", shopPrice=" + shopPrice +
                ", image='" + image + '\'' +
                ", desc='" + desc + '\'' +
                ", isHot=" + isHot +
                ", csid=" + csid +
                ", pdate=" + pdate +
                ", categorySec=" + categorySec +
                '}';
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Product() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Double getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(Double shopPrice) {
        this.shopPrice = shopPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public Integer getCsid() {
        return csid;
    }

    public void setCsid(Integer csid) {
        this.csid = csid;
    }

    public Date getPdate() {
        return pdate;
    }

    public void setPdate(Date pdate) {
        this.pdate = pdate;
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
        Product other = (Product) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getShopPrice() == null ? other.getShopPrice() == null : this.getShopPrice().equals(other.getShopPrice()))
            && (this.getImage() == null ? other.getImage() == null : this.getImage().equals(other.getImage()))
            && (this.getDesc() == null ? other.getDesc() == null : this.getDesc().equals(other.getDesc()))
            && (this.getIsHot() == null ? other.getIsHot() == null : this.getIsHot().equals(other.getIsHot()))
            && (this.getCsid() == null ? other.getCsid() == null : this.getCsid().equals(other.getCsid()))
            && (this.getPdate() == null ? other.getPdate() == null : this.getPdate().equals(other.getPdate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getShopPrice() == null) ? 0 : getShopPrice().hashCode());
        result = prime * result + ((getImage() == null) ? 0 : getImage().hashCode());
        result = prime * result + ((getDesc() == null) ? 0 : getDesc().hashCode());
        result = prime * result + ((getIsHot() == null) ? 0 : getIsHot().hashCode());
        result = prime * result + ((getCsid() == null) ? 0 : getCsid().hashCode());
        result = prime * result + ((getPdate() == null) ? 0 : getPdate().hashCode());
        return result;
    }

    public Product(String title, Integer amount, String level, Double shopPrice, String image, String desc, Integer isHot, Integer csid, Date pdate, Classification categorySec) {
        this.title = title;
        this.amount = amount;
        this.level = level;
        this.shopPrice = shopPrice;
        this.image = image;
        this.desc = desc;
        this.isHot = isHot;
        this.csid = csid;
        this.pdate = pdate;
        this.categorySec = categorySec;
    }
}