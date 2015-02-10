package processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import modal.ActivityArgsInfo;

import javax.lang.model.element.Modifier;

public class BuilderClassGenerator
{
    private final ClassName _fieldType;
    private final String _fieldName;
    private ActivityArgsInfo _activityArgsInfo;
    private String _dataKey;

    ClassName intentClassName = ClassName.get("android.content", "Intent");
    public static final String SETTER_METHOD_PARAM_NAME_DATA = "data";

    public BuilderClassGenerator(ActivityArgsInfo activityArgsInfo)
    {
        this._activityArgsInfo = activityArgsInfo;
        this._fieldType = activityArgsInfo.getFieldType();
        this._fieldName = activityArgsInfo.getAnnotatedFieldName();
        this._dataKey = activityArgsInfo.getAnnotation().key();
    }


    public FieldSpec getFieldSpec()
    {

        FieldSpec fieldSpec = FieldSpec.builder(_fieldType, _fieldName).addModifiers(Modifier.PRIVATE).build();
        return fieldSpec;
    }

    public void addNotNullFieldCheck(MethodSpec.Builder builder)
    {
        builder.beginControlFlow("if ( " + _fieldName + " != null)");
        addToIntentStatement(builder, _dataKey, _fieldName);
        builder.endControlFlow();

    }

    private MethodSpec.Builder addToIntentStatement(MethodSpec.Builder builder, String key, String fieldName)
    {
        builder.addStatement("intent.putExtra($S,$N)", key, fieldName);
        return builder;
    }

    public MethodSpec getSetterMethodSpec()
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
