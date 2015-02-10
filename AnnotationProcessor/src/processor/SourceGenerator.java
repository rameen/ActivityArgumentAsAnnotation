package processor;

import com.squareup.javapoet.*;
import modal.ActivityArgsInfo;

import javax.lang.model.element.Modifier;

public class SourceGenerator
{

    public static final String FILE_NAME_SUFFIX = "Builder";
    public static final String SETTER_METHOD_PARAM_NAME_DATA = "data";
    private final ClassName _fieldType;
    ClassName intentClassName = ClassName.get("android.content", "Intent");
    private ActivityArgsInfo activityArgsInfo;
    private final String _fieldName;
    private final String _className;

    public SourceGenerator(ActivityArgsInfo activityArgsInfo)
    {

        this.activityArgsInfo = activityArgsInfo;
        _fieldName = activityArgsInfo.getAnnotatedFieldName();
        _className = activityArgsInfo.getEnclosingClassName() + FILE_NAME_SUFFIX;
        _fieldType = activityArgsInfo.getFieldType();


    }

    public String get_className()
    {
        return _className;
    }

    public TypeSpec getClassSource()
    {
        TypeSpec.Builder classSpecBuilder = TypeSpec.classBuilder(_className).addMethod(getIntentMethodSpec()).addField(getFieldSpec());
        classSpecBuilder.addMethod(getSetterMethod());
        classSpecBuilder.addModifiers(Modifier.PUBLIC);
        return classSpecBuilder.build();
    }


    private FieldSpec getFieldSpec()
    {

        FieldSpec fieldSpec = FieldSpec.builder(_fieldType, _fieldName).addModifiers(Modifier.PRIVATE).build();
        return fieldSpec;
    }

    private MethodSpec.Builder addNewIntentStatement(MethodSpec.Builder builder, ClassName intentClassName)
    {
        return builder.addStatement(intentClassName.toString() + " intent " + " = new $T()", intentClassName);
    }

    private MethodSpec.Builder addNotNullFieldCheck(MethodSpec.Builder builder, String key, String fieldName)
    {
        builder.beginControlFlow("if ( " + fieldName + " != null)");
        addToIntentStatement(builder, key, fieldName);
        builder.endControlFlow();
        return builder;

    }

    private MethodSpec.Builder addToIntentStatement(MethodSpec.Builder builder, String key, String fieldName)
    {
        builder.addStatement("intent.putExtra($S,$N)", key, fieldName);
        return builder;
    }

    public MethodSpec getIntentMethodSpec()
    {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("getIntent");
        addNewIntentStatement(builder, intentClassName);
        addNotNullFieldCheck(builder, activityArgsInfo.getAnnotation().key(), _fieldName);
        return builder.build();

    }

    private MethodSpec getSetterMethod()
    {

        ParameterSpec parameterSpec = getParameterForSetter();
        MethodSpec.Builder builder = MethodSpec.methodBuilder("set" + _fieldName);
        builder.addModifiers(Modifier.FINAL, Modifier.PUBLIC);
        builder.addParameter(parameterSpec);
        builder.addStatement("$N=$N", _fieldName, SETTER_METHOD_PARAM_NAME_DATA);
        builder.returns(void.class);
        return builder.build();

    }

    private ParameterSpec getParameterForSetter()
    {
        ParameterSpec.Builder builder = ParameterSpec.builder(_fieldType, SETTER_METHOD_PARAM_NAME_DATA, Modifier.FINAL);
        return builder.build();
    }
}
