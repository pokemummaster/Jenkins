package priv.jesse.mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.jesse.mall.dao.OrderDao;
import priv.jesse.mall.dao.OrderItemDao;
import priv.jesse.mall.dao.ProductDao;
import priv.jesse.mall.entity.Order;
import priv.jesse.mall.entity.OrderItem;
import priv.jesse.mall.entity.Product;
import priv.jesse.mall.entity.User;
import priv.jesse.mall.service.OrderService;
import priv.jesse.mall.service.ShopCartService;
import priv.jesse.mall.service.exception.LoginException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ShopCartService shopCartService;
    String NAME_PREFIX = "shop_cart_";


    @Override
    public Order findById(int id) {
        return orderDao.getOne(id);
    }

    @Override
    public List<Order> findByNameLike(String name) {
        return orderDao.findByNameLike(name);
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return orderDao.findAll(pageable);
    }

    @Override
    public List<Order> findAllExample(Example<Order> example) {
        return orderDao.findAll(example);
    }

    @Override
    public void update(Order order) {
        orderDao.save(order);
    }

    @Override
    public int create(Order order) {
        Order order1 = orderDao.save(order);
        return order1.getId();
    }

    @Override
    public void delById(int id) {
        orderDao.delete(id);
    }

    /**
     * 查询订单项详情
     * @param orderId
     * @return
     *///就是重新再将产品放进里面
    @Override
    public List<OrderItem> findItems(int orderId) {
        List<OrderItem> list = orderItemDao.findByOrderId(orderId);
        for (OrderItem orderItem : list) {
            Product product = productDao.findOne(orderItem.getProductId());
            orderItem.setProduct(product);
        }
        return list;
    }

    @Override
    public List<OrderItem> findByProductIdIn(List<Integer> id) {
        return orderItemDao.findByProductIdIn(id);
    }

    /**
     * 更改订单状态
     *
     * @param id
     * @param status
     */
    @Override
    public void updateStatus(int id, int status) {
        Order order = orderDao.findOne(id);
        order.setState(status);
        orderDao.save(order);
    }

    /**
     * 查找用户的订单列表
     *
     * @param request
     * @return
     */
    @Override
    public List<Order> findUserOrder(HttpServletRequest request) {
        //从session中获取登录用户的id，查找他的订单
        Object user = request.getSession().getAttribute("user");
        if (user == null)
            throw new LoginException("请登录！");
        User loginUser = (User) user;
        List<Order> orders = orderDao.findByUserId(loginUser.getId());
        return orders;
    }

    /**
     * 支付
     *
     * @param orderId
     */
    @Override
    public void pay(int orderId) {
        //具体逻辑就不实现了，直接更改状态为 待发货
        Order order = orderDao.findOne(orderId);
        if (order == null)
            throw new RuntimeException("订单不存在");
        orderDao.updateState(STATE_WAITE_SEND,order.getId());
    }

    @Override
    public void submit(String name, String phone, String time, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object user = request.getSession().getAttribute("user");
        if (user == null)
            throw new LoginException("请登录！");
        User loginUser = (User) user;
        Order order = new Order();
        order.setName(name);
        order.setTime(time);
        order.setNumber(loginUser.getNumber());
        order.setPhone(phone);
        order.setOnubmer(getOrderIdByUUId());
        order.setOrderTime(new Date());
        order.setUserId(loginUser.getId());
        order.setState(STATE_NO_PAY);

        List<OrderItem> orderItems = shopCartService.listCart(request);

        Double total = 0.0;
        order.setTotal(total);
        order = orderDao.save(order);
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderId(order.getId());
            total += orderItem.getSubTotal();

            orderItemDao.save(orderItem);
        }

        for (OrderItem orderItem : orderItems) {
            Product product=productDao.findOne(orderItem.getProductId());
            int cout=product.getAmount();

            product.setAmount(cout-orderItem.getCount());
        }

        order.setTotal(total);
        orderDao.save(order);

        User loginUser2 = (User) request.getSession().getAttribute("user");
        List<Integer> productIds = (List<Integer>) request.getSession().getAttribute(NAME_PREFIX + loginUser2.getId());

        productIds.clear();
        //重定向到订单列表页面
        response.sendRedirect("/mall/order/toList.html");
    }

    public static String getOrderIdByUUId() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
//         0 代表前面补充0
//         4 代表长度为4
//         d 代表参数为正数型
        return  machineId+ String.format("%015d", hashCodeV);
    }
    /**
     * 提交订单
     *
     * @param name
     * @param phone

     * @param request
     * @param response
     */
//    @Override
//    @Transactional
//    public void submit2(String name, String phone, String onubmer,String time, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        Object user = request.getSession().getAttribute("user");
//        if (user == null)
//            throw new LoginException("请登录！");
//        User loginUser = (User) user;
//        Order order = new Order();
//        order.setName(name);
//        order.setNumber(loginUser.getNumber());
//        order.setPhone(phone);
//        order.setOnubmer(getOrderIdByUUId());
//        order.setOrderTime(new Date());
//        order.setUserId(loginUser.getId());
//        order.setState(STATE_NO_PAY);
//
//        List<OrderItem> orderItems = shopCartService.listCart(request);
//
//        Double total = 0.0;
//        order.setTotal(total);
//        order = orderDao.save(order);
//        for (OrderItem orderItem : orderItems) {
//            orderItem.setOrderId(order.getId());
//            total += orderItem.getSubTotal();
//
//            orderItemDao.save(orderItem);
//        }
//
//        for (OrderItem orderItem : orderItems) {
//            Product product=productDao.findOne(orderItem.getProductId());
//            int cout=product.getAmount();
//
//            product.setAmount(cout-orderItem.getCount());
//        }
//
//        order.setTotal(total);
//        orderDao.save(order);
//
//        User loginUser2 = (User) request.getSession().getAttribute("user");
//        List<Integer> productIds = (List<Integer>) request.getSession().getAttribute(NAME_PREFIX + loginUser2.getId());
//
//        productIds.clear();
//        //重定向到订单列表页面
//        response.sendRedirect("/mall/order/toList.html");
//    }

    /**
     * 确认收货
     *
     * @param orderId
     */
    @Override
    public void receive(int orderId) {
        Order order = orderDao.findOne(orderId);
        if (order == null)
            throw new RuntimeException("订单不存在");
        orderDao.updateState(STATE_COMPLETE,order.getId());
    }

    @Override
    public List<OrderItem> selectALL() {
        List<OrderItem> list2=orderItemDao.findAll();
        return list2;
    }
}
