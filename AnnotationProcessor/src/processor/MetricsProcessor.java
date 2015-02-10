package processor;


import annoations.ActivityArg;
import com.squareup.javapoet.JavaFile;
import modal.ActivityArgsInfo;
import utils.Utils;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

        for (Element elem : roundEnv.getElementsAnnotatedWith(ActivityArg.class))
        {
            Utils.printElementProperties(_messager, elem);
            addToHashMap(elem);

        }
        generateCode();
        return false; // allow others to process this annotation type
    }


    private void addToHashMap(Element element)
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


    private void printMessage(String message)
    {
        Utils.printMessage(_messager, message);
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

        return SourceVersion.latestSupported();
    }

    public void generateCode()
    {

        for (String key : annotationMap.keySet())
        {
            Set<ActivityArgsInfo> infoSet = annotationMap.get(key);
            SourceGenerator sourceGenerator = new SourceGenerator(infoSet);
            sourceGenerator.generateCode();
            JavaFileObject jfo = null;
            try
            {
                jfo = _filer.createSourceFile(sourceGenerator.get_className());
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
        ;


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