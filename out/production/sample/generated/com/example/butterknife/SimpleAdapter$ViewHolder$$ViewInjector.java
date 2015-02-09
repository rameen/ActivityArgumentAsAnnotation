// Generated code from Butter Knife. Do not modify!
package com.example.butterknife;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class SimpleAdapter$ViewHolder$$ViewInjector<T extends com.example.butterknife.SimpleAdapter.ViewHolder> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2130968581, "field 'word'");
    target.word = finder.castView(view, 2130968581, "field 'word'");
    view = finder.findRequiredView(source, 2130968582, "field 'length'");
    target.length = finder.castView(view, 2130968582, "field 'length'");
    view = finder.findRequiredView(source, 2130968583, "field 'position'");
    target.position = finder.castView(view, 2130968583, "field 'position'");
  }

  @Override public void reset(T target) {
    target.word = null;
    target.length = null;
    target.position = null;
  }
}
