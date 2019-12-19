import 'package:memeory/util/env/env.dart';

String getFrontendUrl({String uri = ""}) {
  return "$env.frontendUrl$uri";
}

String getBackendUrl({String uri = ""}) {
  return "$env.backendUrl$uri";
}
