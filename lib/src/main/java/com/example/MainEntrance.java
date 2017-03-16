package com.example;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;

import javax.lang.model.element.Modifier;

/**
 * Created by CJJ on 2017/3/15.
 */

public class MainEntrance {
    public static void main(String[] args) {/*

        Class<SetterGetterBean> type = SetterGetterBean.class;
        String clzzName = type.getSimpleName();
        ClassName rawType = ClassName.get(type);
        Method[] setters = null;
        Method[] getters = null;
        //
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type, Object.class);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            int length = propertyDescriptors.length;
            setters = new Method[length];
            getters = new Method[length];
            for (int i = 0; i < length; i++) {
                PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
                setters[i] = propertyDescriptor.getWriteMethod();
                getters[i] = propertyDescriptor.getReadMethod();
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }

        ClassName list = ClassName.get("java.util", "List");
        ClassName arrayList = ClassName.get("java.util","ArrayList");
        ParameterizedTypeName listRawType = ParameterizedTypeName.get(arrayList, rawType);
        MethodSpec.Builder builder = MethodSpec.methodBuilder("map")
                .returns(listRawType)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(listRawType, "sources")
                .addStatement("$T array = new $T<>()", list, listRawType)
                .beginControlFlow("for(int i = 0;i<source.size();i++)");

        System.out.println("length:"+setters.length);
        for (int i = 0; i < setters.length; i++) {
            if (setters[i] != null && getters[i] != null) {
                String getterName = getters[i].getName();
                String setterName = setters[i].getName();
                builder.addStatement("$T one = new $T()", type, type)
                        .addStatement("one." + setterName + "(source." + getterName + "()" + ")");
            }

        }
        builder.endControlFlow();
        builder.addStatement("return array");
        /*//**Mapper#map(List ones) method is created!
        MethodSpec methodSpec = builder.build();


        TypeSpec typeSpec = TypeSpec.classBuilder(clzzName + "Mapper")
                .addMethod(methodSpec)
                .addModifiers(Modifier.PUBLIC)
                .build();
        JavaFile javaFile = JavaFile.builder("com.example", typeSpec).build();
        try {
            javaFile.writeTo(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
