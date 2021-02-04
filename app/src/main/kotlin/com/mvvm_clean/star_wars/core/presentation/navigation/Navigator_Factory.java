package com.mvvm_clean.star_wars.core.presentation.navigation;

import com.mvvm_clean.star_wars.features.login.Authenticator;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class Navigator_Factory implements Factory<Navigator> {
  private final Provider<Authenticator> authenticatorProvider;

  public Navigator_Factory(Provider<Authenticator> authenticatorProvider) {
    assert authenticatorProvider != null;
    this.authenticatorProvider = authenticatorProvider;
  }

  @Override
  public Navigator get() {
    return new Navigator(authenticatorProvider.get());
  }

  public static Factory<Navigator> create(Provider<Authenticator> authenticatorProvider) {
    return new Navigator_Factory(authenticatorProvider);
  }
}
