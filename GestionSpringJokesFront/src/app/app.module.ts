import { NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

// Angular Material
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatToolbarModule } from '@angular/material/toolbar';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HomeComponent } from './pages/home/home.component';
import { ConfirmDeleteDialogComponent } from './components/confirm-delete-jokes-flags/confirm-delete-dialog.component';
import { CategoriesFormComponent } from './pages/categories/categories-form/categories-form.component';
import { CategoriesListComponent } from './pages/categories/categories-list/categories-list.component';
import { FlagsFormComponent } from './pages/flags/flags-form/flags-form.component';
import { FlagsListComponent } from './pages/flags/flags-list/flags-list.component';
import { JokeFormComponent } from './pages/jokes/jokes-form/jokes-form.component';
import { JokesListComponent } from './pages/jokes/jokes-list/jokes-list.component';
import { JokeDetailComponent } from './pages/jokes/jokes-detail/jokes-detail.component';
import { JokesDetailDialogComponent } from './jokes-detail-dialog/jokes-detail-dialog.component';
import { LanguagesListComponent } from './pages/languages/languages-list/languages-list.component';
import { LanguagesFormComponent } from './pages/languages/languages-form/languages-form.component';
import { TypesFormComponent } from './pages/types/types-form/types-form.component';
import { TypesListComponent } from './pages/types/types-list/types-list.component';
import { TypesDetailComponent } from './pages/types/types-detail/types-detail.component';
import { ConfirmDeleteJokesCategoryComponent } from './components/confirm-delete-jokes-category/confirm-delete-jokes-category.component';
import { CategoryDetailDialogComponent } from './components/category-detail-dialog/category-detail-dialog.component';
import { LanguageDetailDialogComponent } from './components/language-detail-dialog/language-detail-dialog.component';
import { FlagDetailDialogComponent } from './components/flag-detail-dialog/flag-detail-dialog.component';
import { TypeDetailDialogComponent } from './components/types-detail-dialog/types-detail-dialog.component';
import { ConfirmDeleteJokesTypeComponent } from './components/confirm-delete-jokes-types/confirm-delete-jokes-types.component';
import { ConfirmDeleteLanguageComponent } from './components/confirm-delete-jokes-language/confirm-delete-jokes-language.component';
import { PrimeraVezFormComponent } from './pages/primera-vez/primera-vez-form/primera-vez.form.component';
import { PrimeraVezListComponent } from './pages/primera-vez/primera-vez.list/primera-vez.list.component';
import { PrimeraVezListJokesComponent } from './pages/primera-vez/primera-vez.list-jokes/primera-vez.list-jokes.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HomeComponent,
    CategoriesFormComponent,
    CategoriesListComponent,
    FlagsListComponent,
    FlagsFormComponent,
    JokeFormComponent,
    JokesListComponent,
    JokeDetailComponent,
    JokesDetailDialogComponent,
    LanguagesFormComponent,
    LanguagesListComponent,
    TypesDetailComponent,
    TypesFormComponent,
    TypesListComponent,
    ConfirmDeleteDialogComponent,
    ConfirmDeleteJokesCategoryComponent,
    CategoryDetailDialogComponent,
    LanguageDetailDialogComponent,
    FlagDetailDialogComponent,
    TypeDetailDialogComponent,
    ConfirmDeleteJokesTypeComponent,
    ConfirmDeleteLanguageComponent,
    PrimeraVezFormComponent,
    PrimeraVezListComponent,
    PrimeraVezListJokesComponent

  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    MatTableModule,
    MatPaginatorModule,
    MatSnackBarModule,
    MatDialogModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatCheckboxModule,
    MatIconModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
    MatToolbarModule
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
