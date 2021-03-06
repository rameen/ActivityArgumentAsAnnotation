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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MetricsProcessor extends AbstractProcessor
{

    private Messager _messager;
    private Elements elementUtils;
    private Filer _filer;
    private Types _typeUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv)
    {
        super.init(processingEnv);
        _messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
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

            ActivityArgsInfo activityArgsInfo = new ActivityArgsInfo(elem, _messager);
            printMessage("acitivity args" + activityArgsInfo.getAnnotation().annotationType());
            ActivityArg activityArg = elem.getAnnotation(ActivityArg.class);
            generateCode(activityArgsInfo, _filer);
        }

        return false; // allow others to process this annotation type
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
        printMessage("other details "+elementTypeMirror.getClass());
        printMessage("poet class name"+ className);
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