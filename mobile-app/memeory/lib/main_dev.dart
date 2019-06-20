import 'package:memeory/app.dart';

import 'package:memeory/util/env.dart';

void main() {
  BuildEnvironment.init(
    flavor: BuildFlavor.development,
    backendUrl: "",
  );

  runMemeory();
}
