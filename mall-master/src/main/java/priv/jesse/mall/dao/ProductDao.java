package priv.jesse.mall.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import priv.jesse.mall.entity.Order;
import priv.jesse.mall.entity.OrderItem;
import priv.jesse.mall.entity.Product;

import java.awt.print.PrinterGraphics;
import java.util.Date;
import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {
    /**
     * 通过二级分类查找商品列表
     *
     * @param csid
     * @param pageable
     * @return
     */
    List<Product> findByCsid(int csid, Pageable pageable);
    Product findByCsid(int csid);

    List<Product> findByCsidIn(List<Integer> csids,Pageable pageable);
    List<Product> findByTitleLike(String name);
    /**
     * 通过标题搜索商品
     *
     * @param keyword
     * @param pageable
     * @return
     */
    List<Product> findByTitleIsLike(String keyword, Pageable pageable);

    /**
     * 查找某个日期之后上架的商品
     * @param date
     * @param pageable
     * @return
     */
    List<Product> findByPdateAfter(Date date, Pageable pageable);
    Product findByTitle(String title);

    /**
     * 查找热门商品
     * @param isHot
     * @param pageable
     * @return
     */
    List<Product> findByIsHot(int isHot,Pageable pageable);
    List<Product> findAll();
    List<Product> findByLevel(String level);
    List<Product> findByCsidIn(List<Integer> id);


    /**
     * 查询所有商品
     * @param pageable
     * @return
     */
    @Query(value = "SELECT * FROM (SELECT  * FROM product) a /*#pageable*/",nativeQuery = true)
    List<Product> findNew(Pageable pageable);
}
