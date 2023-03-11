package com.memrepo

import android.app.Application
import com.memrepo.service.NoteCardService
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule  = module {
    viewModel { MainViewModel(NoteCardService(androidApplication())) }
}