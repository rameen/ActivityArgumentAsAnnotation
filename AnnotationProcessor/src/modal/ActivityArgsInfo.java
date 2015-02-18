package modal;

import annoations.ActivityArg;
import com.squareup.javapoet.ClassName;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.TypeMirror;

public class ActivityArgsInfo
{
    private final String annotatedFieldName;
    private final TypeMirror typeMirror;
    String enclosingClass;
    Element element;
    Messager messager;
    private ActivityArg activityArgs;
    private String packageName;


    public ActivityArgsInfo(Element element, Messager messager)
    {
        this.element = element;
        this.messager = messager;
        this.enclosingClass = element.getEnclosingElement().getSimpleName().toString();
        this.activityArgs = element.getAnnotation(ActivityArg.class);
        this.annotatedFieldName = element.getSimpleName().toString();
        typeMirror = element.asType();
        init();

    }

    private void init()
    {
        Element enclosingElement = this.element.getEnclosingElement();
        if (enclosingElement.getKind() == ElementKind.CLASS)
        {
             String fullName = enclosingElement.toString();
            this.packageName =fullName.substring(0,fullName.lastIndexOf("."));
        }
    }

    public String getEnclosingClassName()
    {
        return enclosingClass;
    }

    public Element getElement()
    {
        return element;
    }

    public String getAnnotatedFieldName()
    {
        return annotatedFieldName;
    }

    public ActivityArg getAnnotation()
    {
        return activityArgs;
    }
    public ClassName getFieldType()
    {
        return (ClassName) ClassName.get(typeMirror);
    }

    public String getPackageName()
    {
        return packageName;
    }
}
