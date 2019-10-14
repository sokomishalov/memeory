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
  final BuildFlavor flavor;

  BuildEnvironment._init({this.flavor, this.backendUrl});

  static void init({
    @required flavor,
    @required backendUrl,
  }) =>
      _env ??= BuildEnvironment._init(
        flavor: flavor,
        backendUrl: backendUrl,
      );
}

bool isDebugModeOn() {
  bool inDebugMode = false;
  assert(inDebugMode = true);
  return inDebugMode;
}
