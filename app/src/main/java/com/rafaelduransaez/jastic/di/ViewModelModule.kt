package com.rafaelduransaez.jastic.di

/*import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)*/
class ViewModelModule {

/*    @Provides
    @ViewModelScoped
    @RepoId
    fun provideRepoId(savedStateHandle: SavedStateHandle): Int {

        val repoId = RepositoryDetailFragmentArgs.fromSavedStateHandle(savedStateHandle).repoId //nav component
        return if (repoId > INVALID_ID) repoId
        else savedStateHandle[NavArgs.RepoId.key] ?: INVALID_ID //compose nav
    }*/

    companion object {
        private const val INVALID_ID = -1
    }

}
