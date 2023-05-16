import { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'io.motofix.starter',
  appName: 'MotoFix',
  webDir: 'www',
  server: {
    androidScheme: 'https'
  }
};

export default config;
