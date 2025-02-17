// joke-dto.model.ts
export interface JokeDto {
  id: number;
  text1: string;
  text2: string | null;
  category: string;
  language: string;
  flagses: string[];  // o Array<string>
  flagsCount?: number;  // Nuevo campo opcional (se calcula en el Front)
}
