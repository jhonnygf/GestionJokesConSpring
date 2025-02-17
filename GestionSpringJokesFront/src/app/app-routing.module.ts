import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { JokesListComponent } from './pages/jokes/jokes-list/jokes-list.component';
import { JokeFormComponent } from './pages/jokes/jokes-form/jokes-form.component';
import { JokeDetailComponent } from './pages/jokes/jokes-detail/jokes-detail.component';
import { CategoriesFormComponent } from './pages/categories/categories-form/categories-form.component';
import { CategoriesListComponent } from './pages/categories/categories-list/categories-list.component';
import { FlagsFormComponent } from './pages/flags/flags-form/flags-form.component';
import { FlagsListComponent } from './pages/flags/flags-list/flags-list.component';
import { LanguagesFormComponent } from './pages/languages/languages-form/languages-form.component';
import { LanguagesListComponent } from './pages/languages/languages-list/languages-list.component';
import { TypesListComponent } from './pages/types/types-list/types-list.component';
import { TypesFormComponent } from './pages/types/types-form/types-form.component';
import { PrimeraVezFormComponent } from './pages/primera-vez/primera-vez-form/primera-vez.form.component';
import { PrimeraVezListComponent } from './pages/primera-vez/primera-vez.list/primera-vez.list.component';
import { PrimeraVezListJokesComponent } from './pages/primera-vez/primera-vez.list-jokes/primera-vez.list-jokes.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent }, 
  { path: 'jokes', component: JokesListComponent },
  { path: 'jokes/nuevo', component: JokeFormComponent },
  { path: 'jokes/editar/:id', component: JokeFormComponent },
  { path: 'jokes/:id', component: JokeDetailComponent },
  { path: 'categories', component: CategoriesListComponent },
  { path: 'categories/nuevo', component: CategoriesFormComponent },
  { path: 'categories/editar/:id', component: CategoriesFormComponent },
  { path: 'flags', component: FlagsListComponent },
  { path: 'flags/nuevo', component: FlagsFormComponent },
  { path: 'flags/editar/:id', component: FlagsFormComponent },
  { path: 'languages',component: LanguagesListComponent },
  { path: 'languages/nuevo', component: LanguagesFormComponent },
  { path: 'languages/editar/:id', component: LanguagesFormComponent},
  { path: 'types', component: TypesListComponent }, 
  { path: 'types/nuevo', component: TypesFormComponent },
  { path: 'types/editar/:id', component: TypesFormComponent},
  { path: 'types/detalle/:id', component: TypesListComponent }, 
  { path: 'primeravez', component: PrimeraVezListComponent },
  { path: 'primeravez/nuevo/:jokeId', component: PrimeraVezFormComponent },
  { path: 'primeravez/editar/:id', component: PrimeraVezFormComponent },
  { path: 'primeravez/jokes', component: PrimeraVezListJokesComponent },
  { path: '**', redirectTo: '/home', pathMatch: 'full' } 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
