package priv.jesse.mall.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.jesse.mall.entity.Classification;
import priv.jesse.mall.entity.Count;
import priv.jesse.mall.entity.OrderItem;
import priv.jesse.mall.entity.Product;
import priv.jesse.mall.entity.pojo.ResultBean;
import priv.jesse.mall.service.ClassificationService;
import priv.jesse.mall.service.OrderService;
import priv.jesse.mall.service.ProductService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/classification")
public class AdminClassificationController {
    @Autowired
    private ClassificationService classificationService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    /**
     * 返回列表页面
     *
     * @param type
     * @return
     */
    @RequestMapping("/toList.html")
    public String toList(int type) {
        if (type == 1) {// 一级分类页面
            return "admin/category/list";
        } else if (type == 2) {// 二级分类页面
            return "admin/categorysec/list";
        } else {
            return "";
        }
    }
    @RequestMapping("/toList2.html")
    public String toList2() {

            return "admin/order/count";

        }

    /**
     * 打开添加分类页面
     *
     * @param type
     * @return
     */
    @RequestMapping("/toAdd.html")
    public String toAdd(int type) {
        if (type == 1) {// 一级分类页面
            return "admin/category/add";
        } else if (type == 2) {// 二级分类页面
            return "admin/categorysec/add";
        } else {
            return "";
        }
    }

    /**
     * 打开编辑页面
     *
     * @param id
     * @param type
     * @param map
     * @return
     */
    @RequestMapping("/toEdit.html")
    public String toEdit(int id, int type, Map<String, Object> map) {
        Classification classification = classificationService.findById(id);
        map.put("cate", classification);
        if (type == 1) {// 一级分类页面
            return "admin/category/edit";
        } else if (type == 2) {// 二级分类页面
            Classification classification1 = classificationService.findById(classification.getParentId());
            map.put("cate", classification1);
            map.put("catese",classification);
            return "admin/categorysec/edit";
        } else {
            return "";
        }
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/add.do")
    public ResultBean<Boolean> add(String cname, int parentId, int type) {
        Classification classification = new Classification();
        classification.setCname(cname);
        classification.setParentId(parentId);
        classification.setType(type);
        classificationService.create(classification);
        return new ResultBean<>(true);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/update.do")
    public ResultBean<Boolean> update(int id, String cname, int parentId, int type) {
        Classification classification = classificationService.findById(id);
        classification.setCname(cname);
        classification.setParentId(parentId);
        classification.setType(type);
        classificationService.update(classification);
        return new ResultBean<>(true);
    }

    @ResponseBody
    @RequestMapping("/del.do")
    public ResultBean<Boolean> del(int id) {
        classificationService.delById(id);
        return new ResultBean<>(true);
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public ResultBean<List<Classification>> findAll(int type,
                                                    int pageindex, @RequestParam(value = "pageSize", defaultValue = "7") int pageSize) {
        List<Classification> list = new ArrayList<>();
        if (pageindex == -1)
            list = classificationService.findAll(type);
        else {
            Pageable pageable = new PageRequest(pageindex, pageSize, null);
            list = classificationService.findAll(type, pageable).getContent();
        }
        return new ResultBean<>(list);
    }

    @ResponseBody
    @RequestMapping("/getTotal.do")
    public ResultBean<Integer> getTotal(int type) {
        Pageable pageable = new PageRequest(1, 15, null);
        int count = (int) classificationService.findAll(type, pageable).getTotalElements();
        return new ResultBean<>(count);
    }

    @ResponseBody
    @RequestMapping("/getall.do")
    public Map<String, Object> listorder() {


        List<OrderItem> orderItemList2=orderService.selectALL();
        Map<String, Integer> data= new HashMap<>();
        List<Count> countList=new ArrayList<>();
        Pageable pageable = new PageRequest(0, 100);
        List<Product> products = productService.findNewProduct(pageable);

        for(Product product:products)
            data.put(product.getTitle(),0);

        for(OrderItem orderItem:orderItemList2)
        {
            String name= productService.findById(orderItem.getProductId()).getTitle();
            int count=data.get(name);
            data.put(name,orderItem.getCount()+count);
        }
        for(Map.Entry<String, Integer> a:data.entrySet()){


            Integer id=productService.findByTitle(a.getKey()).getId();
            Double price=productService.findByTitle(a.getKey()).getShopPrice();
            Count cout=new Count();
            cout.setId(id);
            cout.setName(a.getKey());
            cout.setCount(a.getValue());
            cout.setPrive(price*a.getValue());
            countList.add(cout);

        }
        Map<String,Object> tableData =new HashMap<String,Object>();

        tableData.put("code", 0);
        tableData.put("msg", "");
        //将全部数据的条数作为count传给前台（一共多少条）
        //将分页后的数据返回（每页要显示的数据）
        tableData.put("data", countList);

        return tableData;
    }

    @ResponseBody
    @RequestMapping("/getall2.do")
    public Map<String, Object> listorder2(String diqu) {

        Classification classification=classificationService.findByCname(diqu);
        List<Classification> classificationList=classificationService.findByParentId(classification.getId());
        List<Integer> csid=new ArrayList<>();
        List<Integer> proid=new ArrayList<>();

        for(Classification order1 : classificationList){
//方法体   c
            csid.add(order1.getId());
        }
        List<Product> products=productService.findByCsidIn(csid);

        for(Product product : products){
//方法体   c
            proid.add(product.getId());
        }
        List<OrderItem> orderItemList2=orderService.findByProductIdIn(proid);
        Map<String, Integer> data= new HashMap<>();
        List<Count> countList=new ArrayList<>();
        Pageable pageable = new PageRequest(0, 100);

//        List<Product> products = productService.findNewProduct(pageable);

        for(Product product:products)
            data.put(product.getTitle(),0);

        for(OrderItem orderItem:orderItemList2)
        {
            String name= productService.findById(orderItem.getProductId()).getTitle();
            int count=data.get(name);
            data.put(name,orderItem.getCount()+count);
        }
        for(Map.Entry<String, Integer> a:data.entrySet()){


            Integer id=productService.findByTitle(a.getKey()).getId();
            Double price=productService.findByTitle(a.getKey()).getShopPrice();
            Count cout=new Count();
            cout.setId(id);
            cout.setName(a.getKey());
            cout.setCount(a.getValue());
            cout.setPrive(price*a.getValue());
            countList.add(cout);

        }
        Map<String,Object> tableData =new HashMap<String,Object>();

        tableData.put("code", 0);
        tableData.put("msg", "");
        //将全部数据的条数作为count传给前台（一共多少条）
        //将分页后的数据返回（每页要显示的数据）
        tableData.put("data", countList);

        return tableData;
    }


    @RequestMapping("/list2.do")
    @ResponseBody
    public ResultBean<List<Classification>> findAll(int type) {
        List<Classification> list = new ArrayList<>();
            list = classificationService.findAll(type);
        return new ResultBean<>(list);
    }
}
