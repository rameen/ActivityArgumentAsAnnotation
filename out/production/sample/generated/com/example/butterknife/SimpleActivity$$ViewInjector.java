// Generated code from Butter Knife. Do not modify!
package com.example.butterknife;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class SimpleActivity$$ViewInjector<T extends com.example.butterknife.SimpleActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2130968576, "field 'title'");
    target.title = finder.castView(view, 2130968576, "field 'title'");
    view = finder.findRequiredView(source, 2130968577, "field 'subtitle'");
    target.subtitle = finder.castView(view, 2130968577, "field 'subtitle'");
    view = finder.findRequiredView(source, 2130968578, "field 'hello', method 'sayHello', and method 'sayGetOffMe'");
    target.hello = finder.castView(view, 2130968578, "field 'hello'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.sayHello();
        }
      });
    view.setOnLongClickListener(
      new android.view.View.OnLongClickListener() {
        @Override public boolean onLongClick(
          android.view.View p0
        ) {
          return target.sayGetOffMe();
        }
      });
    view = finder.findRequiredView(source, 2130968579, "field 'listOfThings' and method 'onItemClick'");
    target.listOfThings = finder.castView(view, 2130968579, "field 'listOfThings'");
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.onItemClick(p2);
        }
      });
    view = finder.findRequiredView(source, 2130968580, "field 'footer'");
    target.footer = finder.castView(view, 2130968580, "field 'footer'");
    target.headerViews = Finder.listOf(
        finder.<android.view.View>findRequiredView(source, 2130968576, "field 'headerViews'"),
        finder.<android.view.View>findRequiredView(source, 2130968577, "field 'headerViews'"),
        finder.<android.view.View>findRequiredView(source, 2130968578, "field 'headerViews'")
    );
  }

  @Override public void reset(T target) {
    target.title = null;
    target.subtitle = null;
    target.hello = null;
    target.listOfThings = null;
    target.footer = null;
    target.headerViews = null;
  }
}
