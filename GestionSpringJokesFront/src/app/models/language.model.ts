export interface Language {
    id: number;             // Identificador único
    code?: string;          // Código del idioma (opcional, longitud 2)
    language: string;       // Nombre del idioma
    jokes?: string[];     // IDs de los chistes relacionados (opcional)
  }
  