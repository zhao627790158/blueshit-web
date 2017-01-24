package cn.blueshit.web;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by zhaoheng on 17/1/24.
 */
public class BlDataSourceNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("bldatasource", new BlDataSourceBeanDefinitionParser());
    }
}
