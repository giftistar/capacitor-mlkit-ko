export interface MLKitKoPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  ocr(image_uri: { value: string }): Promise<{ value: string }>;
  barcode(image_uri: { value: string }): Promise<{ value: string }>;
}
