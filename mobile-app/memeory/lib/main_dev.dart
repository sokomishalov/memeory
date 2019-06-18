import 'package:memeory/app.dart';

import 'env/env.dart';

void main() {
  BuildEnvironment.init(
    flavor: BuildFlavor.development,
    backendUrl: "https://superapp-ift-kibana.kolebor.ru:8443",
  );

  runMemeory();
}
