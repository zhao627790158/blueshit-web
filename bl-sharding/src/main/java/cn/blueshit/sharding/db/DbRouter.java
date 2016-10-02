package cn.blueshit.sharding.db;

/**
 * Created by zhaoheng on 2016/5/20.
 * 路由接口
 */
public interface DbRouter {


    public String doRoute(String fieldId) throws Exception;


    public String doRouteByPayId(String resourceCode);

}
