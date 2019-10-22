import 'package:memeory/util/env/env.dart';
import 'package:memeory/util/os/os.dart';

import 'app.dart';

void main() {
  BuildEnvironment.init(
    flavor: BuildFlavor.local,
    backendUrl: isAndroid() ? "http://10.0.2.2:8080/" : "http://localhost:8080/",
    frontendUrl: isAndroid() ? "http://10.0.2.2:3000/" : "http://localhost:3000/",
  );

  runMemeory();
}
