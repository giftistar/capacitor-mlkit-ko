import { WebPlugin } from '@capacitor/core';

import type { MLKitKoPlugin } from './definitions';

export class MLKitKoWeb extends WebPlugin implements MLKitKoPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
  async ocr(image_uri: { value: string }): Promise<{ value: string }> {
    console.log('ocr', image_uri);
    return image_uri;
  }
  async barcode(image_uri: { value: string }): Promise<{ value: string }> {
    console.log('barcode', image_uri);
    return image_uri;
  }
}
