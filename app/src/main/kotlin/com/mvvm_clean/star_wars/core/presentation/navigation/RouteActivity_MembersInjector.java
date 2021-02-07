package com.mvvm_clean.star_wars.core.presentation.navigation;

import javax.annotation.Generated;
import javax.inject.Provider;

import dagger.MembersInjector;

@Generated(
        value = "dagger.internal.codegen.ComponentProcessor",
        comments = "https://google.github.io/dagger"
)
public final class RouteActivity_MembersInjector implements MembersInjector<RouteActivity> {
    private final Provider<Navigator> navigatorProvider;

    public RouteActivity_MembersInjector(Provider<Navigator> navigatorProvider) {
        assert navigatorProvider != null;
        this.navigatorProvider = navigatorProvider;
    }

    public static MembersInjector<RouteActivity> create(Provider<Navigator> navigatorProvider) {
        return new RouteActivity_MembersInjector(navigatorProvider);
    }

    @Override
    public void injectMembers(RouteActivity instance) {
        if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    instance.navigator = navigatorProvider.get();
  }
}
