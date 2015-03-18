package modal;

import annoations.ActivityArg;
import com.squareup.javapoet.ClassName;
import utils.Utils;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.TypeMirror;

public class ActivityArgsInfo {
    

    private final String annotatedFieldName;
    private final TypeMirror typeMirror;
    private final Element enclosingClassElement;
    private String enclosingClass;
    private Element element;
    private Messager messager;
    private ActivityArg activityArgs;
    private String packageName;

    public ActivityArgsInfo(Element element, Messager messager) {
        this.element = element;
        this.messager = messager;
        this.enclosingClassElement = element.getEnclosingElement();
        this.enclosingClass = element.getEnclosingElement().getSimpleName().toString();
        this.activityArgs = element.getAnnotation(ActivityArg.class);
        this.annotatedFieldName = element.getSimpleName().toString();
        typeMirror = element.asType();
        initializePackagename();

    }


    private void initializePackagename() {
        Element enclosingElement = this.element.getEnclosingElement();
        if (enclosingElement.getKind() == ElementKind.CLASS) {
            String fullName = enclosingElement.toString();
            Utils.printMessage(messager, "full name" + fullName);
            this.packageName = fullName.substring(0, fullName.lastIndexOf("."));
            Utils.printMessage(messager, "package name" + packageName);
        }
    }

    public Element getEnclosingClassElement() {
        return enclosingClassElement;
    }

    public String getEnclosingClassName() {
        return enclosingClass;
    }

    public Element getElement() {
        return element;
    }

    public String getAnnotatedFieldName() {
        return annotatedFieldName;
    }

    public ActivityArg getAnnotation() {
        return activityArgs;
    }

    public ClassName getFieldType() {
        return (ClassName) ClassName.get(typeMirror);
    }

    public String getPackageName() {
        return packageName;
    }
}
