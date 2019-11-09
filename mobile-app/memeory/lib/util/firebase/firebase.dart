import 'package:firebase_remote_config/firebase_remote_config.dart';
import 'package:memeory/util/env/env.dart';

Future<String> getFrontendUrl({String uri = ""}) async {
  final baseUrl = env.frontendUrl.isNotEmpty
      ? env.frontendUrl
      : await _getFirebaseRemoteConfig("FRONTEND_URL");
  return "$baseUrl$uri";
}

Future<String> getBackendUrl({String uri = ""}) async {
  final baseUrl = env.backendUrl.isNotEmpty
      ? env.backendUrl
      : await _getFirebaseRemoteConfig("BACKEND_URL");
  return "$baseUrl$uri";
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
