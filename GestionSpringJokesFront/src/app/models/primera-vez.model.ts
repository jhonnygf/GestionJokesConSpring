export interface JokePrimeraVez {
    id: number;
    text1: string;
    text2: string;
    category: string;
    language: string;
    primeraVez?: {
      id: number;
      programa: string;
      fechaEmision: string;
      jokeId: number;
      telefonos: string[];
    }
}