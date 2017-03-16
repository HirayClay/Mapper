package com.example;


import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;


//@AutoService(Processor.class)
public class Generator extends AbstractProcessor {

    public static final String suffix = "Mapper";
    private Filer filer;

    private static final boolean DEBUG = true;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();

    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        System.out.println("========================================processing");
        Set<? extends Element> targetClassMap = roundEnv.getElementsAnnotatedWith(Mapper.class);
        Iterator<? extends Element> iterator = targetClassMap.iterator();
        while (iterator.hasNext()) {
            Element item = iterator.next();
            ElementKind type = item.getKind();
            if (type == ElementKind.CLASS) {
                TypeElement oriType = (TypeElement) item;
                String clazzName = oriType.getSimpleName().toString();
                ClassName rawType = ClassName.get(oriType);
                String[] setters = null;
                String[] getters = null;
                if (DEBUG)
                    System.out.println("==================" + oriType.getQualifiedName() + "++++++++++++++++++++++" + oriType.getEnclosingElement());
                //test getEnclosedElements
                List<? extends Element> allEles = oriType.getEnclosedElements();
//                VariableElement varEle = (VariableElement)allEles.get(5);
//                System.out.println(varEle.getEnclosingElement());
                List<? extends ExecutableElement> enclosedElements = findMethods(allEles);
                int size = enclosedElements.size();
//                System.out.println(enclosedElements.size());
                if (size % 2 != 0)
                    throw new IllegalStateException("Getter and Setter is not in pair please check Class:" + ((TypeElement) item).getQualifiedName().toString());
                setters = new String[size / 2];
                getters = new String[size / 2];
                int count = size - 1;
                while (count >= 0) {
                    ExecutableElement element = enclosedElements.get(count);
                    TypeKind typeKind = element.getReturnType().getKind();
                    if (typeKind == TypeKind.VOID) {
                        setters[count / 2] = element.getSimpleName().toString();

                    } else {
                        getters[count / 2] = element.getSimpleName().toString();
                    }
                    count--;
                }
                ClassName list = ClassName.get("java.util", "List");
                ClassName arrayList = ClassName.get("java.util", "ArrayList");
                ParameterizedTypeName listRawType = ParameterizedTypeName.get(list, rawType);
                ParameterizedTypeName arrayListRawType = ParameterizedTypeName.get(arrayList, rawType);
                MethodSpec.Builder builder = MethodSpec.methodBuilder("map")
                        .returns(listRawType)
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(arrayListRawType, "sources")
                        .addStatement("$T array = new $T()", listRawType, arrayListRawType)
                        .beginControlFlow("for(int i = 0;i<sources.size();i++)")
                        .addStatement("$T copy = new $T()", rawType, rawType)
                        .addStatement("$T one = sources.get(i)", rawType);

                for (int i = 0; i < setters.length; i++) {
                    if (getters[i] != null && setters[i] != null) {
                        String getterName = getters[i];
                        String setterName = setters[i];
                        builder.addStatement("copy." + setterName + "(one." + getterName + "()" + ")");
                    }

                }
                builder.addStatement("array.add(copy)");
                builder.endControlFlow();
                builder.addStatement("return array");
                //**Mapper#map(List ones) method is created!
                MethodSpec methodSpec = builder.build();


                TypeSpec typeSpec = TypeSpec.classBuilder(clazzName + "Mapper")
                        .addMethod(methodSpec)
                        .addModifiers(Modifier.PUBLIC)
                        .build();
                JavaFile javaFile = JavaFile.builder("com.example.plugin.mapper", typeSpec).build();
                try {
                    javaFile.writeTo(filer);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
        return true;

    }

    private List<? extends ExecutableElement> findMethods(List<? extends Element> enclosedElements) {
        List<ExecutableElement> executableElements = new ArrayList<>();
        for (Element ele :
                enclosedElements) {
            if (ele.getKind() == ElementKind.METHOD)
                executableElements.add((ExecutableElement) ele);
        }
        return executableElements;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return ImmutableSet.of(Mapper.class.getCanonicalName());
    }

}
