package processor;


import annoations.ActivityArg;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import modal.ActivityArgsInfo;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class MetricsProcessor extends AbstractProcessor
{

    private Messager _messager;
    private Elements _elementUtils;
    private Filer _filer;
    private Types _typeUtils;

    Map<String, Set<ActivityArgsInfo>> annotationMap = new HashMap<String, Set<ActivityArgsInfo>>();


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv)
    {
        super.init(processingEnv);
        _messager = processingEnv.getMessager();
        _elementUtils = processingEnv.getElementUtils();
        _filer = processingEnv.getFiler();
        _typeUtils = processingEnv.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> arg0, RoundEnvironment roundEnv)
    {

        StringBuilder message = new StringBuilder();
        for (Element elem : roundEnv.getElementsAnnotatedWith(ActivityArg.class))
        {
            printMessage("element class" + elem.getClass());
            printMessage("element type" + elem.asType().getKind().toString());
            printElementProperties(elem);
            addToHashmap(elem);
            ActivityArgsInfo activityArgsInfo = new ActivityArgsInfo(elem, _messager);
            printMessage("activity args" + activityArgsInfo.getAnnotation().annotationType());

        }
        //checkHashMapSize();
        return false; // allow others to process this annotation type
    }

    private void checkHashMapSize()
    {
        printMessage("validating hash map");
        for (Set<ActivityArgsInfo> infoSet : annotationMap.values())
        {
            if (infoSet != null)
            {
                for (ActivityArgsInfo activityArgsInfo : infoSet)
                {
                    printMessage("field Name:" + activityArgsInfo.getAnnotatedFieldName());
                    printMessage("field type:" + activityArgsInfo.getFieldType());
                }
            }

        }
        ;
    }

    private void addToHashmap(Element element)
    {
        ActivityArgsInfo activityArgsInfo = new ActivityArgsInfo(element, _messager);
        String enclosingClassName = activityArgsInfo.getEnclosingClassName();
        Set<ActivityArgsInfo> infoSet = annotationMap.get(enclosingClassName);
        if (infoSet != null)
        {
            printMessage("set is not null");
            infoSet.add(activityArgsInfo);

        } else
        {
            printMessage("set is  null");
            infoSet = new HashSet<ActivityArgsInfo>();
            infoSet.add(activityArgsInfo);
            annotationMap.put(enclosingClassName, infoSet);
        }


    }

    private void printElementProperties(Element elem)
    {
        printMessage("ToString:" + elem.toString());
        printMessage("Kind name" + elem.getKind().name());
        printMessage("Simple Name " + elem.getSimpleName());
        List<? extends Element> list = elem.getEnclosedElements();
        for (int i = 0; i < list.size(); i++)
        {
            Element element = list.get(i);
            printMessage("enclosing element" + elem.getSimpleName());

        }
        TypeMirror elementTypeMirror = elem.asType();
        ClassName className = (ClassName) ClassName.get(elementTypeMirror);
        printMessage("class Name" + elementTypeMirror.toString());
        printMessage("other details " + elementTypeMirror.getClass());
        printMessage("poet class name" + className);
    }

    private void printMessage(String message)
    {
        _messager.printMessage(Diagnostic.Kind.NOTE, "Compiler : " + message);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes()
    {
        Set<String> supportedAnnotations = new HashSet<String>();
        supportedAnnotations.add(ActivityArg.class.getCanonicalName());
        return supportedAnnotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion()
    {

        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "supported version");
        return SourceVersion.latestSupported();
    }

    public void generateCode(ActivityArgsInfo activityArgsInfo, Filer filer)
    {
        String simpleName = activityArgsInfo.getEnclosingClassName();
        JavaFileObject jfo = null;
        try
        {
            SourceGenerator sourceGenerator = new SourceGenerator(activityArgsInfo);
            jfo = filer.createSourceFile(sourceGenerator.get_className());
            Writer writer = jfo.openWriter();
            JavaFile javaFile = JavaFile.builder("", sourceGenerator.getClassSource()).build();
            printMessage("source generated");
            printMessage(javaFile.toString());
        } catch (IOException e)
        {
            printMessage("Exception while generating source");
            e.printStackTrace();
        }


    }
    /**
     *
     *  //SchedularActivityBuilder
     class Builder
     {
     private int tabPosition;
     private String favorite;

     /**
     * As more fields are added or annotated
     * aim is code generation
     */

/*
    public Builder setTabPosition(int tabPos)
    {
        this.tabPosition = tabPos;
        return this;
    }

    public Builder setFavorite(Favourite fav)
    {
        this.favorite = new Gson().toJson(fav);
        return this;
    }


    public Intent getIntent()
    {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TAB_POS, tabPosition);
        if (favorite != null)
        {
            intent.putExtra(EXTRA_FAVORITE, favorite);
        }
        return intent;
    }
}

*/


}