import { registerPlugin } from '@capacitor/core';

import type { MLKitKoPlugin } from './definitions';

const MLKitKo = registerPlugin<MLKitKoPlugin>('MLKitKo', {
  web: () => import('./web').then(m => new m.MLKitKoWeb()),
});

export * from './definitions';
export { MLKitKo };
