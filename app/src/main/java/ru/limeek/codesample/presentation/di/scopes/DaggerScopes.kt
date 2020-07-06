package ru.limeek.codesample.presentation.di.scopes

import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.BINARY)
annotation class FragmentScope

@Scope
@Retention(AnnotationRetention.BINARY)
annotation class ActivityScope