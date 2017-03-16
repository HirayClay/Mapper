package com.example;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
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
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

/**
 * Created by CJJ on 2017/3/15.
 */
@AutoService(Processor.class)
public class TestGenerator extends AbstractProcessor {

    private Filer mFiler;
    //in this situation,includes String type
    private static final String[] PRIMITIVE_TYPES = {"int", "java.lang.String", "long", "char", "short", "byte", "boolean", "double", "float"};

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(Simple.class);
        parseElements(elementsAnnotatedWith);
        return false;
    }

    public void parseElements(Set<? extends Element> annotationelements) {
        Iterator<? extends Element> iterator = annotationelements.iterator();
        while (iterator.hasNext()) {
            Element ele = iterator.next();
            List<? extends Element> enclosedElements = ele.getEnclosedElements();
//            Iterator<? extends Element> fieldIterator = enclosedElements.iterator();
            //deal with Root Element(find the no-primitive) Class Element
//            List<? extends ExecutableElement>[] array = new List[2];
            TypeMirror typeMirror = ele.asType();
            String copyName = createName("copy");
            String sourceName = createName("source");
            ParameterSpec.Builder parameter = ParameterSpec.builder(TypeName.get(typeMirror), sourceName);
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("map")
                    .returns(TypeName.get(typeMirror))
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(parameter.build());

            methodBuilder.addStatement("$T " + copyName + " = new $T()", TypeName.get(typeMirror), TypeName.get(typeMirror));
            ExecutableElement getter = null;
            ExecutableElement setter;
            List<? extends ExecutableElement> methods = findMethods(enclosedElements, true);
            for (ExecutableElement me :
                    methods) {
                if (me.getReturnType().getKind() != TypeKind.VOID) {
                    //getter now
                    getter = me;
                } else {
                    setter = me;
                    //method-concat procedure
                    methodBuilder.addStatement(copyName + "." + setter.getSimpleName() + "(" + sourceName + "." + getter.getSimpleName() + "())");
                }
            }
            List<? extends ExecutableElement> noPrimitiveMethods = findNoPrimitiveMethods(enclosedElements);
            nestedCreate(sourceName, methodBuilder, noPrimitiveMethods);


            methodBuilder.addStatement("return " + copyName);
            TypeSpec typeBuilder = TypeSpec.classBuilder(ele.getSimpleName() + "Mapper")
                    .addMethod(methodBuilder.build())
                    .addModifiers(Modifier.PUBLIC)
                    .build();

            JavaFile javaFile = JavaFile.builder("com.example.mapper", typeBuilder).build();
            try {
                javaFile.writeTo(mFiler);

            } catch (IOException e) {
                e.printStackTrace();
            }
//            while (fieldIterator.hasNext()) {// traversal all the enclosedElements,deal the declared type ele specially;
//                Element fieldEle = fieldIterator.next();
//                TypeMirror typeMirror = fieldEle.asType();
//                TypeKind kind = typeMirror.getKind();
//                System.out.println(fieldEle);
//                //class type field(method also return declared type,so must be  field kin|d and holding declared type is the necessary condition)
//
//                if (kind == TypeKind.DECLARED && fieldEle.getKind() == ElementKind.FIELD) {//处理Type类型
//                    ParameterSpec parameterSpec = ParameterSpec.builder(TypeName.get(typeMirror), "one").build();
//                    MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("map")
//                            .addParameter(parameterSpec)
//                            .addModifiers(Modifier.PUBLIC)
//                            .returns(TypeName.get(typeMirror));
////                    System.out.println("???????????????????" + fieldEle + "==========type:" + typeMirror + "==============" + isPrimitiveType(fieldEle.asType()));
//                    if (isPrimitiveType(typeMirror)) {
//                        //add primitive setter and getter;
//
//                    } else if (isListCollectionType(typeMirror)) {
//                        //do nothing for now
//
//                    } else {//declared type
//                        DeclaredType realType = (DeclaredType) typeMirror;
//                        Element element = realType.asElement();
//                        List<? extends Element> enclosedElements1 = element.getEnclosedElements();
//                        Iterator<? extends Element> iterator1 = enclosedElements1.iterator();
//                        while (iterator1.hasNext()) {
//                            Element element1 = iterator1.next();
//
//                        }
//                    }
//
//                } else {//primitive field setter and getter
//
//                }
//            }
        }

    }

    private void nestedCreate(String source, MethodSpec.Builder methodBuilder, List<? extends ExecutableElement> methodElements) {

        ExecutableElement setter = null;
        ExecutableElement getter = null;
        TypeMirror typeMirror = null;

        for (ExecutableElement me :
                methodElements) {
            if (me.getReturnType().getKind() == TypeKind.VOID) {//setter
                setter = me;
                System.out.println("---------------------" + methodElements.size());
                String copyName = createName("copy");
                String copySourceName = createName("source");
                TypeName typeName = TypeName.get(typeMirror);
                methodBuilder.addStatement("$T " + copyName + " = new $T()", typeName, typeName);
                methodBuilder.addStatement("$T " + copySourceName + " = " + source+"."+getter.getSimpleName() + "()", typeName);
                List<? extends ExecutableElement> methods = findMethods((((DeclaredType) typeMirror).asElement()).getEnclosedElements(), true);
                System.out.println("-----------------NESTED----" + methods);
                glue(methodBuilder, copyName, copySourceName, methods);
                methodBuilder.addStatement(source + "." + setter.getSimpleName() + "(" + copyName + ")");
            } else {//getter
                typeMirror = me.getReturnType();
                getter = me;
            }
        }
    }

    private void glue(MethodSpec.Builder methodBuilder, String copyName, String copySourceName, List<? extends ExecutableElement> methods) {
        ExecutableElement getter = null;
        ExecutableElement setter;
        for (ExecutableElement me :
                methods) {
            if (me.getReturnType().getKind() == TypeKind.VOID) {
                setter = me;
                methodBuilder.addStatement(copyName + "." + setter.getSimpleName() + "(" +copySourceName+"."+ getter.getSimpleName() + "())");
            } else {
                getter = me;
            }
        }
    }

    private String createName(String suffix) {
        long l = System.nanoTime()%1000000;
        return suffix + ("_"+l);
    }

    /**
     * find all no-primitive methods that parameters are not all primitive type or return
     * type is not primitive
     *
     * @param enclosedElements
     * @return
     */
    private List<? extends ExecutableElement> findNoPrimitiveMethods(List<? extends Element> enclosedElements) {
        List<ExecutableElement> executableElements = new ArrayList<>();
        for (Element ele :
                enclosedElements) {

            if (ele.getKind() == ElementKind.METHOD && ele.getKind() != ElementKind.CONSTRUCTOR) {
                ExecutableElement executableElement = (ExecutableElement) ele;
                if (!isPrimitiveMethod(executableElement)) {
                    executableElements.add(executableElement);
                }

            }
        }
        return executableElements;
    }

    /**
     * @param enclosedElements ..
     * @param filterPrimitive  find all the method that parameter is primitive if set true,otherwise
     *                         find all methods
     * @return
     */
    private List<? extends ExecutableElement> findMethods(List<? extends Element> enclosedElements, boolean filterPrimitive) {
        List<ExecutableElement> executableElements = new ArrayList<>();
        for (Element ele :
                enclosedElements) {
            if (ele.getKind() == ElementKind.METHOD && ele.getKind() != ElementKind.CONSTRUCTOR) {
                if (filterPrimitive) {
                    if (isPrimitiveMethod((ExecutableElement) ele))
                        executableElements.add((ExecutableElement) ele);
                } else executableElements.add((ExecutableElement) ele);
            }
        }
        return executableElements;
    }


    /**
     * @param element
     * @return
     */
    private boolean isPrimitiveMethod(ExecutableElement element) {
        TypeMirror typeMirror = element.getReturnType();
        TypeKind kind = typeMirror.getKind();
        if (kind.isPrimitive())
            return true;
        else if (kind == TypeKind.DECLARED) {
            if (typeMirror.toString().equals("java.lang.String"))
                return true;
            return false;
        } else {
            return isPrimitiveParameters(element.getParameters());
        }

    }

    /**
     * check if the method's parameter contains no-primitive type,
     * if so this method is not  primitive field's getter or setter
     *
     * @param parameters
     * @return
     */
    private boolean isPrimitiveParameters(List<? extends VariableElement> parameters) {
        for (VariableElement parameterEle :
                parameters) {
            if (!isPrimitiveType(parameterEle.asType())) return false;
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return ImmutableSet.of(Simple.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    boolean isPrimitiveType(TypeMirror typeMirror) {
        for (int i = 0; i < PRIMITIVE_TYPES.length; i++) {
            if (PRIMITIVE_TYPES[i].equals(typeMirror.toString()))
                return true;
        }
        return false;
    }
}
