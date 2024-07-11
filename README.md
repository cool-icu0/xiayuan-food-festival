# 厦园美食汇

## 你们先组（第五组）

### 小组成员（23软件工程3班）

组长：

```
2312114306 陈连希
```

副组长

```
2312114316 许纪超
```

组员：

```
2312114314 王鹏
2312114349 张佳红
2312114312 关浩宇 
2312114321 俞子琰 
```

## 分工

```
陈连希项目整体架构设计，单体改微服务设计
许纪超负责分类管理模块，单元测试
王鹏负责菜品管理模块，单元测试
关浩宇负责套餐管理模块，单元测试
张佳红负责PPT，数据库设计
俞子琰负责订单明细模块，单元测试
```


#### 软件架构
软件架构说明
前端技术：
    Vue

    Axios

    Apache Echarts

    微信小程序

后端技术：
    Springboot

    MybatisPlus

    SpringCache+Redis

    SpringTask

    JWT

    WebSocket

    POI

    阿里云OSS

    Swagger





## 环境搭建：前端环境、后端环境、数据库环境

## 功能开发

员工管理
1.新增员工：
接口定义：
请求路径：/admin/employee
请求方式：POST
请求参数：json格式 {"name":"xx","username":"xxx"...}
响应数据：Result data:null

        数据模型：员工表employee
    
        思路分析：
            controller:
                在EmployeeController中定义一个新增方法：
                1.调用service的新增方法
                2.返回结果
            service:
                在EmployeeService接口中定义一个新增方法，并且在实现类中实现该方法：
                1.补齐基础属性字段
                2.设置默认密码（需要进行md5加密）
                3.调用mapper的新增方法
            mapper:
                在EmployeeMapper接口中定义一个新增方法：
                sql语句：insert into employee values(#{},#{}...)
                基于注解开发：@Insert()
    
        问题记录：
            1.新增重复的用户名，会报错：Duplicate entry 'zhangsan' for key 'employee.idx_username'
            原因：数据库employee给username设置了唯一约束
            解决方案：使用全局异常器解决
    
            2.新增用户的时候，创建人和更新人每次都是固定的值
            原因：业务层，设置创建人和更新人的id写死了
            解决方案：使用TreadLocal(线程中的一个局部变量)-->拦截器中通过jwt令牌解析之后，获取当前登录人id并将其设置到ThreadLocal中，
                    然后在service层，从ThreadLocal中取出登录人id给创建人和更新人赋值即可
    
    2.员工分页查询：
        接口定义：
            请求路径：/admin/employee/page
            请求方式：GET
            请求参数：?name=xx&page=xx&pageSize=xx
            响应数据：Result<PageResult>
        数据模型：员工表employee
        思路分析：
            controller:
                在EmployeeController中定义一个分页查询方法：
                1.调用service的分页查询方法，获取分页结果对象PageResult
                2.返回结果
            service:
                在EmployeeService接口中定义一个分页方法，并在实现类中实现该方法：
                //使用分页插件完成分页
                0.导入分页插件依赖
                1.设置分页参数 PageHelper.startPage(x,x)
                2.调用mapper的条件分页查询
                3.强转Page类型
                4.封装PageResult对象，并返回
            mapper:
                在EmployeeMapper接口中定义一个条件分页方法:
                基于xml开发--动态sql <if>
                sql语句：select * from employee where name like concat('%',#{name},'%') order by create_time desc
    
    3.启用禁用员工
        接口定义：
            请求路径：/admin/employee/status/{status}
            请求方式：POST
            请求参数：路径参数 status, 普通参数  id
            响应数据：Resutl data:null
    
        思路分析：
            controller：
            service：
                注意：更新员工需要构造员工对象，补充更新时间和更新人
            mapper：
                基于xml，为了兼容后续的编辑员工操作
    
    4.编辑员工
        回显操作：--根据id查询员工
            接口定义：
                请求路径：/admin/employee/{id}
                请求方式：GET
                请求参数：路径参数 id
                响应数据：Resutl data:员工对象
    
        更新操作：
            接口分析：
                请求路径：/admin/employee
                请求方式：PUT
                请求参数：json格式--使用EmployeeDTO封装
                响应数据：Resutl data:null


    5.修改密码：【扩展】
        接口分析：
            请求路径：/admin/employee/editPassword
            请求方式：PUT
            请求参数：{oldPassword: "123456", newPassword: "111111"}  //需要自己补充员工id
            响应数据：Result  data:null
    
        思路分析：
            controller：
                调用service
            service：
                1.需要自己补充员工id--从哪里获取？需要动态获取（获取当前登陆人的id）
                -- ThreadLocal存了id，从ThreadLocal获取
                2.对比数据库中的密码和前端传递过来的旧密码（加密之后对比）是否一致
                3.如果一致，对新密码进行加密
                4.调用mapper层的更新方法
            mapper：
                update employee set password = 111111 where id = ?

分类模块：--导入功能模块代码，涉及下面6个接口

- 新增分类
- 分类分页查询
- 根据id删除分类【注意业务规则：需要判断当前分类下是否存在关联的菜品和套餐，如果存在则不允许删除！！】
- 修改分类【注意：此处后端无需编写回显接口，因为点击修改没有发送动态请求调用后端的接口】
- 启用禁用分类
- 根据类型查询分类【注意：该接口是为菜品和套餐模块准备的】


day03
公共字段自动填充：
公共字段：创建时间、更新时间、创建人、更新人
实现目标：需要将各个模块下的新增或更新方法中公共字段补充逻辑抽取到一个地方
思路分析：使用AOP
1.导入aop依赖
2.定义切面类
3.定义通知方法，选择前置通知
4.切入点表达式选择注解方式
4.1 自定义一个注解 （需要在注解中定义一个属性，用来判断是新增方法还是更新方法）
5.通知方法中的业务逻辑：（需要在新增或者更新之前，补充公共字段）

5. 判断方法上面注解的属性
   如果属性是新增，应该补充4个字段
   如果属性是更新，应该补充2个字段

5. 通过连接点对象获取方法中的参数对象  eg：employee

5. 获取到实体对象，调用set方法      （通过反射）

       ------
       属性可以使用常量类去设置
       public class Xxx{
           public static final INSERT = "insert";
           。。。
       }
       或者使用枚举
       public enum Xxxx{
           INSERT, UPDATE
       }

菜品管理
1.新增菜品
根据类型查询分类【已完成】
文件上传
接口定义：
请求路径：/admin/common/upload
请求方式：POST
请求参数：file MultiFile类型修饰
响应数据：Result data:图片路径
新增菜品
接口定义：
请求路径：/admin/dish
请求方式：POST
请求参数：json格式 {"xx":"xx"...}  使用DTO接收
响应数据：Result data:null

        数据模型：dish菜品表、dish_flavor菜品口味表、category分类表
                菜品：口味 = 1:N 、  菜品：分类 = 1:N
    
    2.分页条件查询菜品
        接口定义：
            请求路径：/admin/dish/page
            请求方式：GET
            请求参数：?page=1&pageSize=10&name=test&categoryId=16&status=0
            响应数据：Result data: PageResult--》List<XxxxVO>
    
    3.删除菜品
        接口定义：
            请求路径：/admin/dish
            请求方式：DELETE
            请求参数：?ids=1,2,3
            响应数据：Result data:null
        数据模型：dish、dish_flavor、setmeal、setmal_dish
    
        思路分析：
            controller:
                调用service
            service:
                定义一个删除方法：
                0.开启事务
                1.判断菜品是否允许删除
                    检查菜品的状态，如果启售中，则不允许删除
                    检查是否被套餐关联，如果关联了，则不允许删除
                2.删除基本菜品信息
                3.删除菜品关联的口味信息
            mapper:
    
    4.修改菜品
        涉及到4个接口：
        4.1根据类型查询分类列表 【已完成】
        4.2文件上传    【已完成】
        4.3根据id查询菜品
            接口定义：
                请求路径：/admin/dish/{id}
                请求方式：GET
                请求参数：路径参数 id
                响应数据：Result data: DishVO
        4.4修改菜品
            接口定义：
                请求路径：/admin/dish
                请求方式：PUT
                请求参数：json格式  使用DishDTO封装
                响应数据：Result data:null
    
    5.菜品启售停售：【作业】
        接口定义：
            请求路径：/admin/dish/status/{status}
            请求方式：POST
            请求参数：路径参数status+普通参数id
            响应数据：Result data:null
        思路分析：
            菜品停售，则包含菜品的套餐同时停售
            controller:
                在DishController中定义一个启售停售方法：
                1.调用service的方法
                2.返回结果
            service:
                在DishService接口中定义一个启售停售方法，并在实现类中实现：
                1.构造Dish对象，调用mapper更新方法，更新状态
                2.如果是停售，需要将关联的套餐也停售
                2.1 根据菜品id查询套餐信息（联表查询dish、setmeal、setmeal_dish）
                2.2 如果能查出套餐，a则调用套餐mapper，修改套餐状态为停售
                注意：此处涉及到多张表的修改操作，所以需要开启事务
            mapper:
                在DishMapper中定义更新方法
                在SetmealMapper中定义查询方法和更新方法

---------------------------------------------------------------------------------------

## 套餐管理

- 新增套餐
- 套餐分页查询
- 删除套餐
- 修改套餐
- 起售停售套餐

---------------------------------------------------------------------------------------

    1.新增套餐
        1.1 文件上传【已完成】
        1.2 根据分类类型查询分类列表【已完成】
        1.3 根据分类id或菜品名称查询菜品
            接口定义：
                请求路径：/admin/dish/list
                请求方式：GET
                请求参数：categoryId \  name
                响应参数：Result data:菜品对象dish
        1.4 新增套餐
            接口定义：
                请求路径：/admin/setmeal
                请求方式：POST
                请求参数：json格式 使用SetmealDTO封装
                响应参数：Result data:null
    
    2.套餐分页查询
        接口定义：
            请求路径：/admin/setmeal/page
            请求方式：GET
            请求参数：page=1&pageSize=10&name=test&categoryId=13&status=1
            响应参数：Result data:套餐分页列表 PageResult
    
    3.删除套餐
        接口定义：
            请求路径：/admin/setmeal
            请求方式：DELETE
            请求参数：ids (数组参数)
            响应参数：Result data:null
    
    4.修改套餐
        4.1 根据id查询套餐
            接口定义：
                请求路径：/admin/setmeal/{id}
                请求方式：GET
                请求参数：路径参数 id
                响应参数：Result data: SetmealVO
    
        4.2 根据类型查询分类（已完成）
        4.3 根据分类id查询菜品（已完成）
        4.4 图片上传（已完成）
    
        4.5 修改套餐
            接口定义：
                请求路径：/admin/setmeal
                请求方式：PUT
                请求参数：json格式 封装到SetmealDTO
                响应参数：Result data: null
    
    5.起售停售套餐
        接口定义：
            请求路径：/admin/setmeal/status/{status}
            请求方式：POST
            请求参数：路径参数 status + 普通参数 id
            响应参数：Result data: null

## 购物车管理

购物车：
添加购物车
接口定义：
请求路径：/user/shoppingCart/add
请求方式：POST
请求参数：json格式  {"dishId":"","dishFlavor":"xxx", "setmealId":""} 使用DTO封装
响应参数：Result data:null

        查看购物车
            接口定义：
                请求路径：/user/shoppingCart/list
                请求方式：GET
                请求参数：无  ---》【注意：需要根据userId查询购物车】
                响应参数：Result data:List<ShoppingCart>
    
        清空购物车
            接口定义：
                请求路径：/user/shoppingCart/clean
                请求方式：DELETE
                请求参数：无  ---》【注意：需要根据userId清空购物车】
                响应参数：Result data:null
    
        购物车数量减一操作

## 下单

用户下单
接口定义：
请求路径：/user/order/submit
请求方式：POST
请求参数：json格式  ---》使用DTO封装
响应参数：Result data:XxxVO

---------------------------------------------------------------------------------------

## 微信小程序

项目实战--完成用户端订单功能 + 管理端订单模块
用户端：

1. 查询历史订单
2. 查询订单详情
3. 取消订单
4. 再来一单
   管理端
5. 订单搜索
6. 各个状态的订单数量统计
7. 查询订单详情
8. 接单
9. 拒单
10. 取消订单
11. 派送订单
12. 完成订单
13. 下单功能优化：加入校验逻辑，如果用户的收货地址距离商家门店超出配送范围
    需要对接百度地图平台 https://lbsyun.baidu.com/
    相关接口:
    https://lbsyun.baidu.com/index.php?title=webapi/guide/webservice-geocoding
    https://lbsyun.baidu.com/index.php?title=webapi/directionlite-v1
    新版相关接口地址：
    https://lbsyun.baidu.com/faq/api?title=webapi/guide/webservice-geocoding-base	【地理编码】
    https://lbsyun.baidu.com/faq/api?title=webapi/guide/webservice-lwrouteplanapi/dirve 【驾车路线规划】
    https://lbsyun.baidu.com/faq/api?title=webapi/guide/webservice-lwrouteplanapi/cycling【骑行路线规划】

---------------------------------------------------------------------------------------