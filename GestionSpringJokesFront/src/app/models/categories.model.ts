export interface Categories {
    id?: number;             // Identificador único
    category: string;       // Nombre de la categoría
    jokeses?: any[];     // IDs de los chistes relacionados (opcional)
    jokesCount?: number;
  }
  