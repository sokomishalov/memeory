import 'app.dart';
import 'env/env.dart';

void main() {
  BuildEnvironment.init(
    flavor: BuildFlavor.local,
    backendUrl: "http://localhost:8080",
  );

  runMemeory();
}
