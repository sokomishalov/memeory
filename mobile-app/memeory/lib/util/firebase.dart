import 'package:firebase_remote_config/firebase_remote_config.dart';

import 'env.dart';

Future<String> getBackendUrl() async {
  if (env.backendUrl.isNotEmpty) {
    return env.backendUrl;
  } else {
    final RemoteConfig remoteConfig = await RemoteConfig.instance;
    const key = "BACKEND_URL";
    final cachedValue = remoteConfig.getString(key);

    if (cachedValue != null && cachedValue.isNotEmpty) {
      return cachedValue;
    } else {
      try {
        await remoteConfig.fetch(expiration: Duration(days: 1));
        await remoteConfig.activateFetched();
      } catch (e) {
        print(e);
      }
      return remoteConfig.getString(key);
    }
  }
}
