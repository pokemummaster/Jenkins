package priv.jesse.mall.web.user;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.jesse.mall.entity.Order;
import priv.jesse.mall.entity.OrderItem;
import priv.jesse.mall.entity.Product;
import priv.jesse.mall.entity.User;
import priv.jesse.mall.entity.pojo.Detail;
import priv.jesse.mall.entity.pojo.ResultBean;
import priv.jesse.mall.service.OrderService;
import priv.jesse.mall.service.ProductService;
import priv.jesse.mall.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    /**
     * 打开订单列表页面
     *
     * @return
     */
    @RequestMapping("/toList.html")
    public String toOrderList() {
        return "mall/order/list";
    }

    /**
     * 查询用户订单列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/list.do")
    @ResponseBody
    public ResultBean<List<Order>> listData(HttpServletRequest request) {
        List<Order> orders = orderService.findUserOrder(request);
        return new ResultBean<>(orders);
    }


//    @RequestMapping("/getDetail.do")
//    @ResponseBody
//    public ResultBean<Detail> getDetail2(int orderId) {
//        List<OrderItem> orderItems = orderService.findItems(orderId);
//        Order order=orderService.findById(orderId);
//        Detail detail=new Detail();
//        detail.setOrderItems(orderItems);
//        detail.setPhone(order.getPhone());
//        detail.setTotal(order.getName());
//        return new ResultBean<>(detail);
//    }


    /**
     * 查询订单详情
     *
     * @param orderId
     * @return
     */
    @RequestMapping("/getDetail.do")
    @ResponseBody
    public ResultBean<Detail> getDetail(int orderId) throws ParseException {
        List<OrderItem> orderItems = orderService.findItems(orderId);
        Order order=orderService.findById(orderId);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date sDate = sdf.parse(order.getTime());
        System.out.println("String类型转Date类型 "+sDate);
        Calendar c = Calendar.getInstance();
        c.setTime(sDate);
        c.add(Calendar.DAY_OF_MONTH, 20);         //利用Calendar 实现 Date日期+1天
        sDate = c.getTime();
        System.out.println("Date结束日期+1 " +sdf.format(sDate));//打印Date日期,显示成功+1天
       String endDate = sdf.format(sDate);
        Detail detail=new Detail();
        detail.setTime(order.getTime());
        detail.setOrderItems(orderItems);
        detail.setPhone(order.getPhone());
        detail.setEndtime(endDate);
        detail.setTotal(order.getName());
        return new ResultBean<>(detail);
    }
    @ResponseBody
    @RequestMapping("/hot.do")
    public ResultBean<List<Product>> getHotProduct() {
        List<Product> products = productService.findHotProduct();
        return new ResultBean<>(products);
    }


    @RequestMapping("/haha.do")
    @ResponseBody
    public ResultBean<Detail> getDetail2(int orderId) {
        List<OrderItem> orderItems = orderService.findItems(orderId);
        Order order=orderService.findById(orderId);
        Detail detail=new Detail();
        detail.setOrderItems(orderItems);
        detail.setPhone(order.getPhone());
        detail.setTotal(order.getName());
        return new ResultBean<>(detail);
    }
    /**
     * 提交订单
     *
     * @param name
     * @param phone

     * @param request
     * @param response
     */
    @RequestMapping("/submit.do")
    public void submit(String name,
                       String phone,
                       String time,
                       HttpServletRequest request,
                       HttpServletResponse response) throws Exception {
        orderService.submit(name, phone, time, request, response);
    }

    /**
     * 支付方法
     *
     * @param orderId
     */
    @RequestMapping("pay.do")
    @ResponseBody
    public ResultBean<Boolean> pay(int orderId, HttpServletResponse response) throws IOException {
        orderService.pay(orderId);
        return new ResultBean<>(true);
    }

    /**
     * 确认收货
     * @param orderId
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("receive.do")
    @ResponseBody
    public ResultBean<Boolean> receive(int orderId, HttpServletResponse response) throws IOException {
        orderService.receive(orderId);
        return new ResultBean<>(true);
    }


}
