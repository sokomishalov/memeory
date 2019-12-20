import 'package:memeory/app.dart';
import 'package:memeory/util/env/env.dart';

void main() {
  BuildEnvironment.init(
    flavor: BuildFlavor.heroku,
    backendUrl: "https://memeory-backend.herokuapp.com/",
    frontendUrl: "https://memeory-app.herokuapp.com/",
  );

  runMemeory();
}
