import 'app.dart';
import 'package:memeory/util/env.dart';

void main() {
  BuildEnvironment.init(
    flavor: BuildFlavor.local,
    backendUrl: "http://localhost:8080",
  );

  runMemeory();
}
