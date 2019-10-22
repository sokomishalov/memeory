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
    this.backendUrl = "",
    this.frontendUrl = "",
  });

  static void init({
    @required flavor,
    backendUrl = "",
    frontendUrl = "",
  }) =>
      _env ??= BuildEnvironment._init(
        flavor: flavor,
        backendUrl: backendUrl,
        frontendUrl: frontendUrl,
      );
}

bool isDebugModeOn() {
  bool inDebugMode = false;
  assert(inDebugMode = true);
  return inDebugMode;
}
