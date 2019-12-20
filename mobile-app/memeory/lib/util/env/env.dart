import 'package:meta/meta.dart';

enum BuildFlavor {
  local,
  heroku,
  production,
}

BuildEnvironment get env => _env;
BuildEnvironment _env;

class BuildEnvironment {
  final String backendUrl;
  final String frontendUrl;
  final BuildFlavor flavor;

  BuildEnvironment._init({
    this.flavor,
    this.backendUrl,
    this.frontendUrl,
  });

  static void init({
    @required flavor,
    backendUrl,
    frontendUrl,
  }) =>
      _env ??= BuildEnvironment._init(
        flavor: flavor,
        backendUrl: backendUrl,
        frontendUrl: frontendUrl,
      );
}

String getFrontendUrl({String uri = ""}) {
  return "${env.frontendUrl}$uri";
}

String getBackendUrl({String uri = ""}) {
  return "${env.backendUrl}$uri";
}


bool isDebugModeOn() {
  bool inDebugMode = false;
  assert(inDebugMode = true);
  return inDebugMode;
}
