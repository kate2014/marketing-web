package com.zhongmei.yunfu.thymeleaf;

import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

public class ThymeleafDialect extends AbstractProcessorDialect {

    private static final String DIALECT_NAME = "ThymeleafDialect";
    private static final String PREFIX = "th";
    public static final int PROCESSOR_PRECEDENCE = 1000;

    @Value("${im.static.resources:http://www.baidu.com}")
    private String filePath;


    public ThymeleafDialect() {
        super(DIALECT_NAME, PREFIX, PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<>();
        processors.add(new SampleJsTagProcessor(dialectPrefix, filePath));
        processors.add(new SampleCssTagProcessor(dialectPrefix, filePath));
        processors.add(new SampleSrcTagProcessor(dialectPrefix, filePath));
        return processors;
    }
}
