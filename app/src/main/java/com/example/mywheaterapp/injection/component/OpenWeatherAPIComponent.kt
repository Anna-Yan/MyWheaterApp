package com.example.mywheaterapp.injection.component

import com.example.mywheaterapp.MainPresenter
import com.example.mywheaterapp.injection.module.OpenWeatherAPIModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [OpenWeatherAPIModule::class])
 interface OpenWeatherAPIComponent {
  fun inject(presenter: MainPresenter)
}