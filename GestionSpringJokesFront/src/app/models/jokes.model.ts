import { Categories } from './categories.model'
import { Language } from './language.model'
import { Types } from './types.model'
import { Flags } from './flags.model';

export interface Joke {
  id?: number;
  text1: string;
  text2?: string;
  categories: Categories; // Relación con categorías
  language: Language; // Relación con idiomas
  types: Types; // Relación con tipos
  flagses: Flags[]; // Relación con flags
}
