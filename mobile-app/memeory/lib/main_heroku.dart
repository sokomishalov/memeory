import 'package:memeory/app.dart';
import 'package:memeory/util/env.dart';

void main() {
  BuildEnvironment.init(
    flavor: BuildFlavor.heroku,
    backendUrl: "https://memeory-backend.herokuapp.com",
  );

  runMemeory();
}
