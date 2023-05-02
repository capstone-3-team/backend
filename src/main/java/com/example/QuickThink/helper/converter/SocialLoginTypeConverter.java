package com.example.QuickThink.helper.converter;

import com.example.QuickThink.helper.constants.SocialLoginType;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class SocialLoginTypeConverter implements Converter<String, SocialLoginType> {
    @Override
    public SocialLoginType convert(String s){
        return SocialLoginType.valueOf(s.toUpperCase());
    }
}
