// src/utils/getEnv.ts

// 读取所有 VITE_ 开头的环境变量
export function wrapperEnv(envConf: Record<string, string>): ViteEnv {
  const ret = {} as ViteEnv;  // ← 改这里
  
  for (const envName of Object.keys(envConf)) {
    const value = envConf[envName]?.replace(/\\n/g, '\n') ?? '';
    let realName: string | boolean | number = value;
    
    if (value === 'true') {
      realName = true;
    } else if (value === 'false') {
      realName = false;
    } else if (value !== '' && !isNaN(Number(value))) {
      realName = Number(value);
    }

    (ret as any)[envName] = realName;  // ← 这里也要 as any
  }

  return ret;
}
