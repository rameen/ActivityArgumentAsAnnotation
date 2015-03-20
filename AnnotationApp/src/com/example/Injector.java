package com.example;

import android.app.Activity;
import android.content.Intent;
import annoations.ActivityArg;

import java.lang.reflect.Field;

public class Injector {


    public static void inject(Activity activity) {

        Intent intent = activity.getIntent();
        for (Field field : activity.getClass().getDeclaredFields()) {
            System.out.println(field.getName());
            ActivityArg fieldAnnotation = field.getAnnotation(ActivityArg.class);
            if (fieldAnnotation != null) {

                System.out.println("field type" + field.getType());
                Class<?> type = field.getType();
                if (type.equals(Integer.class)) {

                    int intExtra = intent.getIntExtra(fieldAnnotation.key(), 0);
                    System.out.println("from intent " + intExtra);
                    try {
                        field.setAccessible(true);
                        field.set(activity, intExtra);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else if (type.equals(String.class)) {


                    String stringExtra = intent.getStringExtra(fieldAnnotation.key());
                    field.setAccessible(true);
                    try {
                        field.set(activity, stringExtra);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    System.out.println("from intent" + stringExtra);

                }
            }
        }
        ;

    }
/*
    static  ClassName activityClassName = ClassName.get("android.app", "activity");
    static ClassName intentClassName = ClassName.get("android.content", "Intent");
    public static void inject(Object object) {
        Class<?> objectClass = object.getClass();
        Method[] methods = objectClass.getMethods();
        Object returnedValue = getReturnedvalue(object, methods);
        Object intent = intentClassName.getClass().cast(returnedValue);
        System.out.println("intent " + intent.getClass() + " toString" + intent.toString());


    }

    private static Object getReturnedvalue(Object object, Method[] methods) {
        for (Method method : methods) {
            if (method.getName().equals("getIntent")) {

                try {
                    return method.invoke(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;

    }*/


}
