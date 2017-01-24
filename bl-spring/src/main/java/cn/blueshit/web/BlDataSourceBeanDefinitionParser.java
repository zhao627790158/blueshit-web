package cn.blueshit.web;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by zhaoheng on 17/1/24.
 */
public class BlDataSourceBeanDefinitionParser extends AbstractBeanDefinitionParser {


    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        return null;
    }
}
