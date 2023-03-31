import { registerPlugin } from '@capacitor/core';

import type { CapacitorMLKitPlugin } from './definitions';

const CapacitorMLKit = registerPlugin<CapacitorMLKitPlugin>('CapacitorMLKit', {
  web: () => import('./web').then(m => new m.CapacitorMLKitWeb()),
});

export * from './definitions';
export { CapacitorMLKit };
