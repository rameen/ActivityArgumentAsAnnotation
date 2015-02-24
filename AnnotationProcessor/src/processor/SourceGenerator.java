package processor;

import com.squareup.javapoet.*;
import modal.ActivityArgsInfo;

import javax.lang.model.element.Modifier;
import java.util.Set;

public class SourceGenerator
{

    public static final String FILE_NAME_SUFFIX = "Builder";
    public static final String INTENT_VAR_NAME = " intent ";

    ClassName intentClassName = ClassName.get("android.content", "Intent");
    ClassName contextClassName = ClassName.get("android.content","Context");
    private String _className;
    private Set<ActivityArgsInfo> annotationSet;
    private TypeSpec.Builder _classBuilder;
    private String _packageName;

    private String enclosingClassName;

    public SourceGenerator(Set<ActivityArgsInfo> annotationSet)
    {
        this.annotationSet = annotationSet;
        int i = 0;

        for (ActivityArgsInfo activityArgInfo : annotationSet)
        {
            if (i == 0)
            {
                init(activityArgInfo);
                i++;
            }


        }

    }


    private void init(ActivityArgsInfo activityArgsInfo)
    {

        enclosingClassName = activityArgsInfo.getEnclosingClassName();
        _className = activityArgsInfo.getEnclosingClassName() + FILE_NAME_SUFFIX;
        _packageName = activityArgsInfo.getPackageName();

    }

    public String getPackageName()
    {
        return _packageName;
    }

    public void generateCode()
    {
        _classBuilder = getClassBuilder();
        MethodSpec.Builder getIntentMethodBuilder = getIntentMethodSpecBuilder();
        ActivityAnnotationCodeGenerator argumentCodeGenerator;
        for (ActivityArgsInfo argsInfo : annotationSet)
        {
            argumentCodeGenerator = new ActivityAnnotationCodeGenerator(argsInfo);
            FieldSpec fieldSpec = argumentCodeGenerator.getFieldSpec();
            MethodSpec methodSpec = argumentCodeGenerator.getSetterMethodSpec();

            argumentCodeGenerator.addNotNullFieldCheck(getIntentMethodBuilder);
            _classBuilder.addField(fieldSpec);
            _classBuilder.addMethod(methodSpec);
        }
        addReturnIntentStatement(getIntentMethodBuilder);
        _classBuilder.addMethod(getIntentMethodBuilder.build());


    }
    public MethodSpec.Builder getIntentMethodSpecBuilder()
    {

        ParameterSpec contextParamSpec = ParameterSpec.builder(contextClassName,"context",Modifier.FINAL).build();
        MethodSpec.Builder builder = MethodSpec.methodBuilder("getIntent");
        builder.addParameter(contextParamSpec);
        addNewIntentStatement(builder, intentClassName,contextParamSpec);

        builder.returns(intentClassName);
        builder.addModifiers(Modifier.PUBLIC);

        return builder;

    }

    private void addReturnIntentStatement(MethodSpec.Builder builder)
    {
        builder.addStatement("return $N",INTENT_VAR_NAME);
    }

    private MethodSpec.Builder addNewIntentStatement(MethodSpec.Builder builder, ClassName intentClassName,ParameterSpec contextParamSpec)
    {
        return builder.addStatement(intentClassName.toString() + INTENT_VAR_NAME + " = new $T($N,$N)", intentClassName,contextParamSpec.name,enclosingClassName.toString() + ".class");
    }


    public String get_className()
    {
        return _className;
    }

    public TypeSpec.Builder getClassBuilder()
    {
        TypeSpec.Builder classSpecBuilder = TypeSpec.classBuilder(_className);
        classSpecBuilder.addModifiers(Modifier.PUBLIC);
        return classSpecBuilder;
    }


    public TypeSpec getClassSource()
    {
        return _classBuilder.build();

    }
}
