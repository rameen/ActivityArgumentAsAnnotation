package utils;

import com.squareup.javapoet.ClassName;
import modal.ActivityArgsInfo;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Utils
{
    public static void printMessage(Messager messager, String message)
    {

        messager.printMessage(Diagnostic.Kind.NOTE, message);
    }

    public static void printElementProperties(Messager messager, Element elem)
    {
        printMessage(messager, "ToString:" + elem.toString());
        printMessage(messager, "Kind name" + elem.getKind().name());
        printMessage(messager, "Simple Name " + elem.getSimpleName());
        List<? extends Element> list = elem.getEnclosedElements();
        for (int i = 0; i < list.size(); i++)
        {
            Element element = list.get(i);
            printMessage(messager, "enclosing element" + elem.getSimpleName());

        }
        TypeMirror elementTypeMirror = elem.asType();
        ClassName className = (ClassName) ClassName.get(elementTypeMirror);
        printMessage(messager, "class Name" + elementTypeMirror.toString());
        printMessage(messager, "other details " + elementTypeMirror.getClass());
        printMessage(messager, "poet class name" + className);
    }

    public void checkHashMapSize(Map<String, Set<ActivityArgsInfo>> annotationMap,Messager messager)
    {
        printMessage(messager,"validating hash map");
        for (Set<ActivityArgsInfo> infoSet : annotationMap.values())
        {
            if (infoSet != null)
            {
                for (ActivityArgsInfo activityArgsInfo : infoSet)
                {
                    printMessage(messager,"field Name:" + activityArgsInfo.getAnnotatedFieldName());
                    printMessage(messager,"field type:" + activityArgsInfo.getFieldType());
                }
            }

        }
        ;
    }
}
