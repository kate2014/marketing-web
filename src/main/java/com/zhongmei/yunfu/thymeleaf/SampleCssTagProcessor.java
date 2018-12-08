package com.zhongmei.yunfu.thymeleaf;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

public class SampleCssTagProcessor extends AbstractAttributeTagProcessor {

    private static final String ATTR_NAME = "js";
    private static final String ELE_NAME = "script";
    private static final int PRECEDENCE = 10000;
    private String filePath;

    protected SampleCssTagProcessor(String dialectPrefix, String filePath) {
        super(
                TemplateMode.HTML,
                dialectPrefix,
                ELE_NAME,
                false,
                ATTR_NAME,
                true,
                PRECEDENCE,
                true);
        this.filePath = filePath;
    }

    @Override
    protected void doProcess(ITemplateContext context,
                             IProcessableElementTag tag, AttributeName attributeName,
                             String attributeValue, IElementTagStructureHandler structureHandler) {
        final IEngineConfiguration configuration = context.getConfiguration();
        final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);
        final IStandardExpression expression = parser.parseExpression(context, attributeValue);
        final String url = (String) expression.execute(context);
        structureHandler.setAttribute("src", filePath + url);
    }

}
