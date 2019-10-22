import 'package:firebase_remote_config/firebase_remote_config.dart';
import 'package:memeory/util/env/env.dart';

Future<String> getFrontendUrl() async {
  if (env.frontendUrl.isNotEmpty) {
    return env.frontendUrl;
  } else {
    return await _getFirebaseRemoteConfig("FRONTEND_URL");
  }
}

Future<String> getBackendUrl() async {
  if (env.backendUrl.isNotEmpty) {
    return env.backendUrl;
  } else {
    return await _getFirebaseRemoteConfig("BACKEND_URL");
  }
}

Future<String> _getFirebaseRemoteConfig(String key) async {
  final RemoteConfig remoteConfig = await RemoteConfig.instance;
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
